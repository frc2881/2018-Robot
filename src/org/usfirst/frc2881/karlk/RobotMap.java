// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2881.karlk;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController driveSubsystemLeftRearMotor;
    public static SpeedController driveSubsystemLeftFrontMotor;
    public static SpeedControllerGroup driveSubsystemDriveLeft;
    public static SpeedController driveSubsystemRightRearMotor;
    public static SpeedController driveSubsystemRightFrontMotor;
    public static SpeedControllerGroup driveSubsystemDriveRight;
    public static DifferentialDrive driveSubsystemDriveTrain;
    public static Solenoid driveSubsystemDropOmniPancake;
    public static AnalogGyro driveSubsystemNavX;
    public static Encoder driveSubsystemLeftEncoder;
    public static Encoder driveSubsystemRightEncoder;
    public static Solenoid driveSubsystemGearShift;
    public static Solenoid intakeSubsystemIntakeDeploy;
    public static Solenoid intakeSubsystemGrasper;
    public static DigitalInput intakeSubsystemIntakeDetector;
    public static SpeedController intakeSubsystemIntakeRollerLeft;
    public static SpeedController intakeSubsystemIntakeRollerRight;
    public static SpeedControllerGroup intakeSubsystemIntakeRollerGroup;
    public static WPI_TalonSRX liftSubsystemArmMotor;
    public static Encoder liftSubsystemArmEncoder;
    public static DigitalInput liftSubsystemArmTop;
    public static DigitalInput liftSubsystemArmBottom;
    public static Solenoid liftSubsystemClaw;
    public static Solenoid climbingSubsystemExtender;
    public static SpeedController climbingSubsystemWinch;
    public static PowerDistributionPanel compressorSubsystemPowerDistributionPanel;
    public static Compressor compressorSubsystemCompressor;
    public static AnalogInput compressorSubsystemCompressorPressure;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveSubsystemLeftRearMotor = new Spark(3);
        LiveWindow.addActuator("DriveSubsystem", "Left Rear Motor", (Spark) driveSubsystemLeftRearMotor);
        driveSubsystemLeftRearMotor.setInverted(false);
        driveSubsystemLeftFrontMotor = new Spark(2);
        LiveWindow.addActuator("DriveSubsystem", "Left Front Motor", (Spark) driveSubsystemLeftFrontMotor);
        driveSubsystemLeftFrontMotor.setInverted(false);
        driveSubsystemDriveLeft = new SpeedControllerGroup(driveSubsystemLeftRearMotor, driveSubsystemLeftFrontMotor);
        LiveWindow.addActuator("DriveSubsystem", "Drive Left", driveSubsystemDriveLeft);

        driveSubsystemRightRearMotor = new Spark(4);
        LiveWindow.addActuator("DriveSubsystem", "Right Rear Motor", (Spark) driveSubsystemRightRearMotor);
        driveSubsystemRightRearMotor.setInverted(false);
        driveSubsystemRightFrontMotor = new Spark(5);
        LiveWindow.addActuator("DriveSubsystem", "Right Front Motor", (Spark) driveSubsystemRightFrontMotor);
        driveSubsystemRightFrontMotor.setInverted(false);
        driveSubsystemDriveRight = new SpeedControllerGroup(driveSubsystemRightRearMotor, driveSubsystemRightFrontMotor);
        LiveWindow.addActuator("DriveSubsystem", "Drive Right", driveSubsystemDriveRight);

        driveSubsystemDriveTrain = new DifferentialDrive(driveSubsystemDriveLeft, driveSubsystemDriveRight);
        LiveWindow.addActuator("DriveSubsystem", "Drive Train", driveSubsystemDriveTrain);
        driveSubsystemDriveTrain.setSafetyEnabled(true);
        driveSubsystemDriveTrain.setExpiration(0.1);
        driveSubsystemDriveTrain.setMaxOutput(1.0);

        driveSubsystemDropOmniPancake = new Solenoid(0, 2);
        LiveWindow.addActuator("DriveSubsystem", "Drop Omni Pancake", driveSubsystemDropOmniPancake);

        driveSubsystemNavX = new AnalogGyro(0);
        LiveWindow.addSensor("DriveSubsystem", "NavX", driveSubsystemNavX);
        driveSubsystemNavX.setSensitivity(0.007);
        driveSubsystemLeftEncoder = new Encoder(5, 6, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveSubsystem", "Left Encoder", driveSubsystemLeftEncoder);
        driveSubsystemLeftEncoder.setDistancePerPulse(1.0);
        driveSubsystemLeftEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveSubsystemRightEncoder = new Encoder(7, 8, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveSubsystem", "Right Encoder", driveSubsystemRightEncoder);
        driveSubsystemRightEncoder.setDistancePerPulse(1.0);
        driveSubsystemRightEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveSubsystemGearShift = new Solenoid(0, 3);
        LiveWindow.addActuator("DriveSubsystem", "Gear Shift", driveSubsystemGearShift);

        intakeSubsystemIntakeDeploy = new Solenoid(0, 0);
        LiveWindow.addActuator("IntakeSubsystem", "Intake Deploy", intakeSubsystemIntakeDeploy);

        intakeSubsystemGrasper = new Solenoid(0, 1);
        LiveWindow.addActuator("IntakeSubsystem", "Grasper", intakeSubsystemGrasper);

        intakeSubsystemIntakeDetector = new DigitalInput(4);
        LiveWindow.addSensor("IntakeSubsystem", "Intake Detector", intakeSubsystemIntakeDetector);

        intakeSubsystemIntakeRollerLeft = new Spark(0);
        LiveWindow.addActuator("IntakeSubsystem", "Intake Roller Left", (Spark) intakeSubsystemIntakeRollerLeft);
        intakeSubsystemIntakeRollerLeft.setInverted(false);
        intakeSubsystemIntakeRollerRight = new Spark(1);
        LiveWindow.addActuator("IntakeSubsystem", "Intake Roller Right", (Spark) intakeSubsystemIntakeRollerRight);
        intakeSubsystemIntakeRollerRight.setInverted(true);
        intakeSubsystemIntakeRollerGroup = new SpeedControllerGroup(intakeSubsystemIntakeRollerLeft, intakeSubsystemIntakeRollerRight);
        LiveWindow.addActuator("IntakeSubsystem", "Intake Roller Group", intakeSubsystemIntakeRollerGroup);

        liftSubsystemArmMotor = new WPI_TalonSRX(0);


        liftSubsystemArmEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("LiftSubsystem", "Arm Encoder", liftSubsystemArmEncoder);
        liftSubsystemArmEncoder.setDistancePerPulse(1.0);
        liftSubsystemArmEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        liftSubsystemArmTop = new DigitalInput(1);
        LiveWindow.addSensor("LiftSubsystem", "Arm Top", liftSubsystemArmTop);

        liftSubsystemArmBottom = new DigitalInput(0);
        LiveWindow.addSensor("LiftSubsystem", "Arm Bottom", liftSubsystemArmBottom);

        liftSubsystemClaw = new Solenoid(9, 0);
        LiveWindow.addActuator("LiftSubsystem", "Claw", liftSubsystemClaw);

        climbingSubsystemExtender = new Solenoid(0, 4);
        LiveWindow.addActuator("ClimbingSubsystem", "Extender", climbingSubsystemExtender);

        climbingSubsystemWinch = new Spark(6);
        LiveWindow.addActuator("ClimbingSubsystem", "Winch", (Spark) climbingSubsystemWinch);
        climbingSubsystemWinch.setInverted(false);
        compressorSubsystemPowerDistributionPanel = new PowerDistributionPanel(0);
        LiveWindow.addSensor("CompressorSubsystem", "Power Distribution Panel", compressorSubsystemPowerDistributionPanel);

        compressorSubsystemCompressor = new Compressor(0);
        LiveWindow.addActuator("CompressorSubsystem", "Compressor", compressorSubsystemCompressor);

        compressorSubsystemCompressorPressure = new AnalogInput(1);
        LiveWindow.addSensor("CompressorSubsystem", "Compressor Pressure", compressorSubsystemCompressorPressure);


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
