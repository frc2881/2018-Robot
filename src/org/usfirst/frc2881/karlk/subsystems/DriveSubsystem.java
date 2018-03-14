package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
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
import edu.wpi.first.wpilibj.filters.LinearDigitalFilter;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.OI;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.DriveWithController;
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

    //It takes the robot about 1 foot to stop?
    //PID Tuning as described at http://i.imgur.com/aptC5bP.png and https://www.youtube.com/watch?v=UOuRx9Ujsog
    private static final double straightKc = 1.9;
    private static final double straightPc = 1.271679;  // period of oscillation
    private static final double straightP = 0.6 * straightKc;
    private static final double straightI = 0;//2 * straightP * 0.05 / straightPc;
    private static final double straightD = 0.125 * straightP * straightPc / 0.05;
    private static final double straightF = 0.00;

    //0.03 * 33 degrees = 1.0, drive at full speed until reaching error <= 33 degrees
    private static final double turnKc = 0.08;
    private static final double turnPc = 1.225;
    private static final double turnP = .6 * turnKc;
    private static final double turnI = 0;  //2*turnP/turnTu;
    private static final double turnD = 0.125 * turnP * turnPc / 0.05;
    private static final double turnF = 0.00;
    private static final double omniTurnKc = 0.04;
    private static final double omniTurnPc = 10.4 / 8.0;
    private static final double omniTurnP = .6 * omniTurnKc;
    private static final double omniTurnI = 0;  //2*turnP/turnTu;
    private static final double omniTurnD = 0.125 * omniTurnP * omniTurnPc / 0.05;
    private static final double omniTurnF = 0.00;


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
    private final LinearDigitalFilter currentMovingAverage;
    private final LinearDigitalFilter straightMovingAverage;
    private final LinearDigitalFilter turnMovingAverage;

    private IntakeLocation intakeLocation = IntakeLocation.FRONT;
    private PIDController turnPID;
    private double rotateToAngleRate;
    private PIDController straightPID;
    private double straightSpeed;
    private PIDController omniTurnPID;
    private double maxCurrent;

    private double x, y, d, a;

    public DriveSubsystem() {
        currentMovingAverage = LinearDigitalFilter.movingAverage(new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kRate;
            }

            @Override
            public double pidGet() {
                return RobotMap.otherPowerDistributionPanel.getTotalCurrent();
            }
        }, 15);

        /*this is the code to implement the PID loop for turning the robot*/
        turnPID = new PIDController(turnP, turnI, turnD, turnF, RobotMap.driveSubsystemNavX, new PIDOutput() {
            @Override
            public void pidWrite(double output) {
                rotateToAngleRate = output;
            }
        });
        addChild("TurnPID", turnPID);

        turnPID.setInputRange(-180, 180);
        turnPID.setOutputRange(-1, 1);
        turnPID.setAbsoluteTolerance(5);
        turnPID.setContinuous(true);
        turnPID.disable();
        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        turnPID.setName("DriveSubsystem", "RotateController");
        turnMovingAverage = LinearDigitalFilter.movingAverage(RobotMap.driveSubsystemNavX, 3);

        //This is the code to implement code to drive straight a certain distance
        straightPID = new PIDController(straightP, straightI, straightD, straightF, new DistancePIDSource(), new PIDOutput() {
            @Override
            public void pidWrite(double output) {
                straightSpeed = output;
            }
        });
        addChild("StraightPID", straightPID);

        straightPID.setOutputRange(-1, 1);
        straightPID.setAbsoluteTolerance(0.1);
        straightPID.disable();
        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        straightPID.setName("DriveSubsystem", "StraightController");
        straightMovingAverage = LinearDigitalFilter.movingAverage(new DistancePIDSource(), 3);

        omniTurnPID = new PIDController(omniTurnP, omniTurnI, omniTurnD, omniTurnF, RobotMap.driveSubsystemNavX, new PIDOutput() {
            @Override
            public void pidWrite(double output) {
                rotateToAngleRate = output;
            }
        });
        addChild("OmniTurnPID", omniTurnPID);

        omniTurnPID.setInputRange(-180, 180);
        omniTurnPID.setOutputRange(-1, 1);
        omniTurnPID.setAbsoluteTolerance(5);
        omniTurnPID.setContinuous(true);
        omniTurnPID.disable();
        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        omniTurnPID.setName("DriveSubsystem", "OmniController");
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("MaxCurrent", () -> maxCurrent, null);
        builder.addStringProperty("Location", this::getLocation, null);
        builder.addDoubleProperty("X", () -> x, null);
        builder.addDoubleProperty("Y", () -> y, null);
    }

    public boolean abortAuto() {
        boolean pitch = Math.abs(navX.getPitch()) >= 10;
        boolean roll = Math.abs(navX.getRoll()) >= 10;
        return pitch || roll;
    }

    public String getLocation() {
        return String.format("(%.2f,%.2f) %.1f°", x, y, a);
    }

    public void reset() {
        straightPID.reset();
        turnPID.reset();
        omniTurnPID.reset();
        leftEncoder.reset();
        rightEncoder.reset();
        navX.reset();  // WaitUntilNavXCalibrated will wait until the navX is ready to use again
        maxCurrent = 0;
        x = y = d = a = 0;
    }

    private double getDistanceDriven() {
        double left = leftEncoder.getDistance();
        double right = rightEncoder.getDistance();
        return (left + right) / 2;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithController());
    }

    @Override
    public void periodic() {
        double current = RobotMap.otherPowerDistributionPanel.getTotalCurrent();
        if (maxCurrent < current) {
            maxCurrent = current;
        }

        double distance = getDistanceDriven();
        double angle = navX.getAngle();  // not constrained to -180 to 180

        double change = distance - d;
        double midAngle = (a + angle) / 2.0;  // half way between previous angle and new angle
        double radians = midAngle * Math.PI / 180;
        x += change * Math.sin(radians);
        y += change * Math.cos(radians);
        d = distance;
        a = angle;
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
            double averageJoystickSpeed = (leftSpeed + rightSpeed) / 2;
            double averageEncoderSpeed = getAverageEncoderSpeed();

            // gear shift logic here
            if (isInLowGear()) {
                rightSpeed = rightSpeed * 2;
                leftSpeed = leftSpeed * 2;
            }

            // gear shift from low to high
            if (Math.abs(averageEncoderSpeed) > 3.5 && Math.abs(averageJoystickSpeed) > .5) {
                highGear();
            }

            // gear shift from high to low
            if (/*Math.abs(averageEncoderSpeed) < 3.1 &&*/ Math.abs(averageJoystickSpeed) < .4 /*&& timer.get() >= 0.5*/) {
                /*&& gearShift.set(true) hasn't been used in the last 2sec?.... how do you do this?????*/
                lowGear();
            }
        }

        // Use 'squaredInputs' to get better control at low speed
        driveTrain.setDeadband(OI.DEADBAND);
        if (intakeLocation == IntakeLocation.FRONT) {
            driveTrain.tankDrive(leftSpeed, rightSpeed, true);
        } else {
            driveTrain.tankDrive(-rightSpeed, -leftSpeed, true);
        }
    }

    private double getAverageEncoderSpeed() {
        return (rightEncoder.getRate() + leftEncoder.getRate()) / 2;
    }

    public void autonomousArcadeDrive(double straightSpeed, double rotateSpeed) {
        // DONT Use 'squaredInputs' or deadband in autonomous
        driveTrain.setDeadband(0);
        driveTrain.arcadeDrive(straightSpeed, rotateSpeed, false);
    }

    public void changeHeadingTurnToHeading(double angle, boolean omnis) {
        //update the setPoint of the PID loop if the driver has changed the controller value before the turn was finished
        if (omnis) {
            omniTurnPID.setSetpoint(angle);
        } else {
            turnPID.setSetpoint(angle);
        }
    }

    public void initializeDriveForward(double distance, double angle, boolean omnis) {
        straightPID.setSetpoint(getDistanceDriven() + distance);
        straightSpeed = 0;
        straightPID.enable();
        if (omnis) {
            omniTurnPID.setSetpoint(angle);
            rotateToAngleRate = 0;
            omniTurnPID.enable();
        } else {
            turnPID.setSetpoint(angle);
            rotateToAngleRate = 0;
            turnPID.enable();
        }
        currentMovingAverage.reset();

    }
    //this will drive the robot straight with the speed indicated

    public boolean isFinishedDriveForward(boolean omnis) {
        //called to finish the command when PID loop is finished
        boolean stopped =
                (Math.abs(getDistanceDriven() - straightMovingAverage.pidGet()) < 0.02) &&
                (Math.abs(navX.pidGet() - turnMovingAverage.pidGet()) < 0.1);
        boolean pushing = currentMovingAverage.pidGet() > 60 &&
                (Math.abs(rightEncoder.getRate()) + Math.abs(leftEncoder.getRate())) / 2 < 1;
        if (pushing) {
            System.out.println("Drive Forward interrupted");
        }
        return stopped && straightPID.onTarget() && (omnis ? omniTurnPID.onTarget() : turnPID.onTarget()) ||
                pushing;
    }

    public void endDriveForward(boolean omnis) {
        //Disable the PID loop when the turn is finished
        straightPID.disable();
        if (omnis) {
            omniTurnPID.disable();
        } else {
            turnPID.disable();
        }
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

    public void dropOmniPancakePiston(OmnisState state) {
        if (Robot.compressorSubsystem.hasEnoughPressureForShifting()) {
            dropOmniPancake.set(state == OmnisState.DOWN);
        } else {
            DriverStation.reportWarning("Not enough pressure to drop omnis", false);
        }
    }

    private class DistancePIDSource implements PIDSource {
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
    }

    public boolean getOmnisState() {
        return dropOmniPancake.get();
    }
}
