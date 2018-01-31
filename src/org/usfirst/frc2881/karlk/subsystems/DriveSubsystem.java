package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.DriveWithController;
import org.usfirst.frc2881.karlk.commands.RumbleJoysticks;

/**
 * This handles all of the robot movement motors, the high
 * gear piston and the drop omni pancake, as well as
 * the NavX and the encoders.
 */
public class DriveSubsystem extends Subsystem implements SendableWithChildren {
    public enum IntakeLocation {
        FRONT, BACK
    }

    //0.33 * 3 = 1 drive at full speed until reaching 0.03
    private static final double kP = 0.03;
    private static final double kI = 0.00;
    private static final double kD = 0.00;
    private static final double kF = 0.00;

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

    private IntakeLocation intakeLocation = IntakeLocation.FRONT;
    private PIDController turnPID;
    private double rotateToAngleRate;

    public DriveSubsystem() {
    /*this is the code to implement the PID loop for turning the robot*/
        turnPID = new PIDController(kP, kI, kD, kF, RobotMap.driveSubsystemNavX, new PIDOutput() {
            @Override
            public void pidWrite(double output) {
                rotateToAngleRate = output;
            }
        });
        turnPID.setInputRange(-180, 180);
        turnPID.setOutputRange(-1.0, 1.0);
        turnPID.setAbsoluteTolerance(5);
        turnPID.setContinuous(true);
        turnPID.disable();
        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        turnPID.setName("DriveSystem", "RotateController");

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithController());
    }

    @Override
    public void periodic() {
        //This is called periodically, updating the speed of the rotation based on the angle set
        //by the PID controller in the pidWrite() command.
        if (turnPID.isEnabled()) {
            rotate(rotateToAngleRate);
        }
    }


    public void setIntakeLocation(IntakeLocation intakeLocation) {
        this.intakeLocation = intakeLocation;
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        // Use 'squaredInputs' to get better control at low speed
        if (intakeLocation == IntakeLocation.FRONT) {
            driveTrain.tankDrive(leftSpeed, rightSpeed, true);
        } else {
            driveTrain.tankDrive(-rightSpeed, -leftSpeed, true);
        }
    }

    public void rotate(double speed) {
        driveTrain.tankDrive(speed, -speed, false);
    }
/*This is the code for implementing a PID loop for turning.  This includes initializing, update the heading if needed,
* checking for isFinished, and ending by disabling the PID loop*/

//We need to initialize by setting the angle desired, set the motor speed (rotateToAngleRate) to zero and enabling the PID loop
    public void initializeTurnToHeading(int angle) {
        //depending on whether we need to turn or not, one or the other would be used
        turnPID.setSetpoint(angle);
        rotateToAngleRate = 0;
        turnPID.enable();
    }

    public void changeHeadingTurnToHeading(int angle) {
        //update the setPoint of the PID loop if the driver has changed the controller value before the turn was finished
        turnPID.setSetpoint(angle);
    }

    public boolean isFinishedTurnToHeading() {
        //called to finish the command when PID loop is finished
        if(turnPID.onTarget()) {
            new RumbleJoysticks().start();
        }
        return turnPID.onTarget();
    }

    public void endTurnToHeading() {
        //Disable the PID loop when the turn is finished
        turnPID.disable();

    }

    public void highGear() {
        if(Robot.compressorSubsystem.hasEnoughPressureForShifting()) {
            gearShift.set(true);
        } else {
            DriverStation.reportWarning("Not enough pressure to shift gears",false);
        }

    }

    public void lowGear() {
       if(Robot.compressorSubsystem.hasEnoughPressureForShifting()) {
           gearShift.set(false);
       } else {
               DriverStation.reportWarning("Not enough pressure to shift gears",false);
       }
    }

    public void dropOmniPancakePiston(boolean deploy) {
        if(Robot.compressorSubsystem.hasEnoughPressureForShifting()) {
            dropOmniPancake.set(deploy);
        } else {
            DriverStation.reportWarning("Not enough pressure to drop omnis", false);
        }
    }
}
