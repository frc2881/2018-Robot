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

/**
 * This handles all of the robot movement motors, the high
 * gear piston and the drop omni pancake, as well as
 * the NavX and the encoders.
 */
public class DriveSubsystem extends Subsystem implements SendableWithChildren {
    public enum IntakeLocation {
        FRONT, BACK
    }

    static final double kP = 0.03;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;

    PIDController drivePID;
    //double rotateToAngleRate;

    public DriveSubsystem() {

        //requires(Robot.DriveSubsystem);
        drivePID = new PIDController(kP, kI, kD, kF, RobotMap.driveSubsystemNavX, new PIDOutput() {

            @Override
            public void pidWrite(double output) {

            }
        });

        drivePID.setInputRange(-180, 180);
        drivePID.setOutputRange(-1.0, 1.0);
        drivePID.setAbsoluteTolerance(5);
        drivePID.setContinuous(true);
        drivePID.disable();

        //depending on whether we need to turn or not, one or the other would be used
        /*turnPOV.setSetpoint(getDriverPOVAngle());
        rotateToAngleRate = 0;
        turnPOV.enable();       this needs to be put in a new method

        Robot.driveSubsystem.rotate(rotateToAngleRate);
        this goes somewhere else

        @Override
    public void pidWrite(double output) {
        rotateToAngleRate = output;
        }
        This ends up in the pidwrite place up top
        */

    }


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

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithController());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
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
