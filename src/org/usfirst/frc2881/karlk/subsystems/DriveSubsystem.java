package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.DriveWithController;

/**
 * This handles all of the robot movement motors, the high
 * gear piston and the drop omni pancake, as well as
 * the NavX and the encoders.
 */
public class DriveSubsystem extends Subsystem implements SendableWithChildren {

    private static final double DEADBAND = 0.1;

    //grab hardware objects from RobotMap and add them into the LiveWindow at the same ti
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

    public void tankDrive(double leftSpeed, double rightSpeed) {
        // Use 'squaredInputs' to get better control at low speed
        driveTrain.tankDrive(adjust(leftSpeed), adjust(rightSpeed), true);
    }

    public void rotate(double speed){
        driveTrain.tankDrive(speed, -speed, false);
    }

    private double adjust(double speed) {
        return Math.abs(speed) <= DEADBAND ? 0.0 : speed;
    }

    public void highGear() {
        gearShift.set(true);
    }

    public void lowGear() {
        gearShift.set(false);
    }

    public void dropOmniPancakePiston(boolean deploy) {
        dropOmniPancake.set(deploy);
    }
}

