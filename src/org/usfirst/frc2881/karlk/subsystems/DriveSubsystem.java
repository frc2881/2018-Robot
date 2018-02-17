package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.DriveWithController;
import org.usfirst.frc2881.karlk.commands.RumbleYes;
import org.usfirst.frc2881.karlk.sensors.NavX;

/**
 * This handles all of the robot movement motors, the high
 * gear piston and the drop omni pancake, as well as
 * the NavX and the encoders.
 */
public class DriveSubsystem extends Subsystem implements SendableWithChildren {
    public enum OmnisState {
        UP, DOWN
    }
    public enum IntakeLocation {
        FRONT, BACK
    }

    //It takes the robot about 1 foot to stop
    private static final double straightP = 1.0;
    private static final double straightI = 0.00;
    private static final double straightD = 0.00;
    private static final double straightF = 0.00;

    //0.03 * 33 degrees = 1.0, drive at full speed until reaching error <= 33 degrees
    private static final double turnP = 0.03;
    private static final double turnI = 0.00;
    private static final double turnD = 0.00;
    private static final double turnF = 0.00;


    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final SpeedController leftRearMotor = add(RobotMap.driveSubsystemLeftRearMotor);
    private final SpeedController leftFrontMotor = add(RobotMap.driveSubsystemLeftFrontMotor);
    private final SpeedControllerGroup driveLeft = add(RobotMap.driveSubsystemDriveLeft);
    private final SpeedController rightRearMotor = add(RobotMap.driveSubsystemRightRearMotor);
    private final SpeedController rightFrontMotor = add(RobotMap.driveSubsystemRightFrontMotor);
    private final SpeedControllerGroup driveRight = add(RobotMap.driveSubsystemDriveRight);
    private final DifferentialDrive driveTrain = add(RobotMap.driveSubsystemDriveTrain);
    private final Solenoid dropOmniPancake = add(RobotMap.driveSubsystemDropOmniPancake);
    private final Encoder leftEncoder = add(RobotMap.driveSubsystemLeftEncoder);
    private final Encoder rightEncoder = add(RobotMap.driveSubsystemRightEncoder);
    private final Solenoid gearShift = add(RobotMap.driveSubsystemGearShift);
    private final NavX navX = add(RobotMap.driveSubsystemNavX);
    private final Timer timer = new Timer();


    private IntakeLocation intakeLocation = IntakeLocation.FRONT;
    private PIDController turnPID;
    private double rotateToAngleRate;
    private PIDController straightPID;
    private double straightSpeed;

    public DriveSubsystem() {
        /*this is the code to implement the PID loop for turning the robot*/

        turnPID = new PIDController(turnP, turnI, turnD, turnF, RobotMap.driveSubsystemNavX, new PIDOutput() {
            @Override
            public void pidWrite(double output) {
                rotateToAngleRate = output;
            }
        });
        addChild("TurnPID", turnPID);

        turnPID.setInputRange(-180, 180);
        turnPID.setOutputRange(-1.0, 1.0);
        turnPID.setAbsoluteTolerance(5);
        turnPID.setContinuous(true);
        turnPID.disable();
        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        turnPID.setName("DriveSubystem", "RotateController");

        //This is the code to implement code to drive straight a certain distance
        straightPID = new PIDController(straightP, straightI, straightD, straightF, new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {

            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kDisplacement;
            }

            @Override
            public double pidGet() {
                return getDistanceDriven();
            }
        }, new PIDOutput() {
            @Override
            public void pidWrite(double output) {
                straightSpeed = output;
            }
        });
        addChild("StraightPID", straightPID);

        straightPID.setOutputRange(-1.0, 1.0);
        straightPID.setAbsoluteTolerance(0.1);
        straightPID.disable();
        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        straightPID.setName("DriveSubsystem", "StraightController");
    }

    public void reset() {
        straightPID.reset();
        turnPID.reset();
        leftEncoder.reset();
        rightEncoder.reset();
        navX.reset();
    }

    private double getDistanceDriven() {
        double left = leftEncoder.getDistance();
        double right = rightEncoder.getDistance();
        System.out.println("Distance Driven is " + (left + right) / 2);
        return (left + right) / 2;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithController());
    }

    public double getRotateToAngleRate() {
        return rotateToAngleRate;
    }

    public double getStraightSpeed() {
        return straightSpeed;
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void setIntakeLocation(IntakeLocation intakeLocation) {
        this.intakeLocation = intakeLocation;
    }

    public void tankDrive(double leftSpeed, double rightSpeed, boolean manualShift) {
        if (!manualShift) {
            // gear shift logic here
            if (isInLowGear()) {
                rightSpeed = rightSpeed * 2;
                leftSpeed = leftSpeed * 2;
            }

            // gear shift from low to high
            if (Math.abs(getAverageEncoderSpeed()) > 3.5 && getAverageJoystick() > .5) {
                highGear();
            }
            // gear shift from high to low
            if (Math.abs(getAverageEncoderSpeed()) < 3.1 && getAverageJoystick() < .45 && getTimer() >= 0.5) {
                /*&& gearShift.set(true) hasn't been used in the last 2sec?.... how do you do this?????*/
                lowGear();
            }
        }

        // Use 'squaredInputs' to get better control at low speed
        if (intakeLocation == IntakeLocation.FRONT) {
            driveTrain.tankDrive(leftSpeed, rightSpeed, true);
        } else {
            driveTrain.tankDrive(-rightSpeed, -leftSpeed, true);

        }
    }

    //getAverageJoystick, and getAverageEncoderSpeed are for shifting up and down, so that there isn't too much chunkiness
    private double getAverageJoystick() {
        return (-Robot.oi.driver.getY(GenericHID.Hand.kLeft) + -Robot.oi.driver.getY(GenericHID.Hand.kRight)) / 2;
    }

    private double getAverageEncoderSpeed() {
        return (rightEncoder.getRate() + leftEncoder.getRate()) / 2;
    }

    public void autonomousArcadeDrive(double straightSpeed, double rotateSpeed) {
        // DONT Use 'squaredInputs' in autonomous
        driveTrain.arcadeDrive(straightSpeed, rotateSpeed, false);
    }

    public void rotate(double rotateSpeed) {
        driveTrain.tankDrive(rotateSpeed, -rotateSpeed, false);
    }

    /*This is the code for implementing a PID loop for turning.  This includes initializing, update the heading if needed,
     * checking for isFinished, and ending by disabling the PID loop*/

    //We need to initialize by setting the angle desired, set the motor speed (rotateToAngleRate) to zero and enabling the PID loop
    public void initializeTurnToHeading(double angle) {
        //depending on whether we need to turn or not, one or the other would be used
        turnPID.setSetpoint(angle);
        rotateToAngleRate = 0;
        turnPID.enable();
    }

    public void changeHeadingTurnToHeading(double angle) {
        //update the setPoint of the PID loop if the driver has changed the controller value before the turn was finished
        turnPID.setSetpoint(angle);
    }

    public boolean isFinishedTurnToHeading() {
        //called to finish the command when PID loop is finished
        if (turnPID.onTarget()) {
            new RumbleYes(Robot.oi.driver).start();
        }
        return turnPID.onTarget();
    }

    public void endTurnToHeading() {
        //Disable the PID loop when the turn is finished
        turnPID.disable();
    }

    public void initializeDriveForward(double distance) {
        straightPID.setSetpoint(getDistanceDriven() + distance);
        straightSpeed = 0;
        straightPID.enable();
    }
    //this will drive the robot straight with the speed indicated

    public boolean isFinishedDriveForward() {
        //called to finish the command when PID loop is finished
        if (straightPID.onTarget()) {
            new RumbleYes(Robot.oi.driver).start();
        }
        return straightPID.onTarget();
    }

    public void endDriveForward() {
        //Disable the PID loop when the turn is finished
        straightPID.disable();
    }

    public void highGear() {
        if (isInHighGear()) {
            return;  //already in high gear
        }
        if (Robot.compressorSubsystem.hasEnoughPressureForShifting()) {
            gearShift.set(true);
            timer.reset();
            timer.start();
        } else {
            DriverStation.reportWarning("Not enough pressure to shift gears", false);
        }
    }

    public void lowGear() {
        if (isInLowGear()) {
            return;  //already in low gear
        }
        if (Robot.compressorSubsystem.hasEnoughPressureForShifting()) {
            gearShift.set(false);
        } else {
            DriverStation.reportWarning("Not enough pressure to shift gears", false);
        }
    }

    private boolean isInLowGear() {
        return !isInHighGear();
    }

    private boolean isInHighGear() {
        return gearShift.get();
    }

    private double getTimer() {
        return timer.get();
    }

    public void dropOmniPancakePiston(OmnisState state) {
        if (Robot.compressorSubsystem.hasEnoughPressureForShifting()) {
            dropOmniPancake.set(state == OmnisState.DOWN);
        } else {
            DriverStation.reportWarning("Not enough pressure to drop omnis", false);
        }
    }

}
