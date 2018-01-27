package org.usfirst.frc2881.karlk;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static Spark driveSubsystemLeftRearMotor;
    public static Spark driveSubsystemLeftFrontMotor;
    public static SpeedControllerGroup driveSubsystemDriveLeft;
    public static Spark driveSubsystemRightRearMotor;
    public static Spark driveSubsystemRightFrontMotor;
    public static SpeedControllerGroup driveSubsystemDriveRight;
    public static DifferentialDrive driveSubsystemDriveTrain;
    public static Solenoid driveSubsystemDropOmniPancake;
    public static AHRS driveSubsystemNavX;
    public static Encoder driveSubsystemLeftEncoder;
    public static Encoder driveSubsystemRightEncoder;
    public static Solenoid driveSubsystemGearShift;
    public static Solenoid intakeSubsystemIntakeDeploy;
    public static Solenoid intakeSubsystemGrasper;
    public static DigitalInput intakeSubsystemIntakeDetector;
    public static Spark intakeSubsystemIntakeRollerLeft;
    public static Spark intakeSubsystemIntakeRollerRight;
    public static SpeedControllerGroup intakeSubsystemIntakeRollerGroup;
    public static WPI_TalonSRX liftSubsystemArmMotor;
    public static Encoder liftSubsystemArmEncoder;
    public static DigitalInput liftSubsystemArmTop;
    public static DigitalInput liftSubsystemArmBottom;
    public static Solenoid liftSubsystemClaw;
    public static Solenoid climbingSubsystemExtender;
    public static Spark climbingSubsystemWinch;
    public static Compressor compressorSubsystemCompressor;
    public static AnalogInput compressorSubsystemCompressorPressure;
    public static PowerDistributionPanel otherPowerDistributionPanel;
    public static Spark otherFancyLights;

    public static void init() {
        driveSubsystemLeftRearMotor = new Spark(3);
        driveSubsystemLeftFrontMotor = new Spark(2);
        driveSubsystemDriveLeft = new SpeedControllerGroup(driveSubsystemLeftRearMotor, driveSubsystemLeftFrontMotor);
        driveSubsystemDriveLeft.setName("DriveSubsystem", "Drive Left");
        driveSubsystemDriveLeft.setInverted(true);
        driveSubsystemRightRearMotor = new Spark(4);
        driveSubsystemRightFrontMotor = new Spark(5);
        driveSubsystemDriveRight = new SpeedControllerGroup(driveSubsystemRightRearMotor, driveSubsystemRightFrontMotor);
        driveSubsystemDriveRight.setName("DriveSubsystem", "Drive Right");
        driveSubsystemDriveRight.setInverted(true);
        driveSubsystemDriveTrain = new DifferentialDrive(driveSubsystemDriveLeft, driveSubsystemDriveRight);
        driveSubsystemDriveTrain.setName("DriveSubsystem", "Drive Train");
        driveSubsystemDriveTrain.setSafetyEnabled(true);
        driveSubsystemDriveTrain.setExpiration(0.1);
        driveSubsystemDriveTrain.setMaxOutput(1.0);

        driveSubsystemDropOmniPancake = new Solenoid(11, 2);
        driveSubsystemDropOmniPancake.setName("DriveSubsystem", "Drop Omni Pancake");
        driveSubsystemNavX = new AHRS(SPI.Port.kMXP);
        driveSubsystemNavX.setName("DriveSubsystem", "NavX");
        driveSubsystemLeftEncoder = new Encoder(5, 6, false, EncodingType.k4X);
        driveSubsystemLeftEncoder.setName("DriveSubsystem", "Left Encoder");
        driveSubsystemLeftEncoder.setDistancePerPulse(1.0);
        driveSubsystemLeftEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveSubsystemRightEncoder = new Encoder(7, 8, false, EncodingType.k4X);
        driveSubsystemRightEncoder.setName("DriveSubsystem", "Right Encoder");
        driveSubsystemRightEncoder.setDistancePerPulse(1.0);
        driveSubsystemRightEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveSubsystemGearShift = new Solenoid(11, 3);
        driveSubsystemGearShift.setName("DriveSubsystem", "Gear Shift");

        intakeSubsystemIntakeDeploy = new Solenoid(11, 0);
        intakeSubsystemIntakeDeploy.setName("IntakeSubsystem", "IntakeCube Deploy");

        intakeSubsystemGrasper = new Solenoid(11, 1);
        intakeSubsystemGrasper.setName("IntakeSubsystem", "Grasper");

        intakeSubsystemIntakeDetector = new DigitalInput(4);
        intakeSubsystemIntakeDetector.setName("IntakeSubsystem", "IntakeCube Detector");

        intakeSubsystemIntakeRollerLeft = new Spark(0);
        intakeSubsystemIntakeRollerLeft.setInverted(false);
        intakeSubsystemIntakeRollerRight = new Spark(1);
        intakeSubsystemIntakeRollerRight.setInverted(true);
        intakeSubsystemIntakeRollerGroup = new SpeedControllerGroup(intakeSubsystemIntakeRollerLeft, intakeSubsystemIntakeRollerRight);
        intakeSubsystemIntakeRollerGroup.setName("IntakeSubsystem", "IntakeCube Roller Group");

        liftSubsystemArmMotor = new WPI_TalonSRX(0);

        liftSubsystemArmEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        liftSubsystemArmEncoder.setName("LiftSubsystem", "Arm Encoder");
        liftSubsystemArmEncoder.setDistancePerPulse(1.0);
        liftSubsystemArmEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        liftSubsystemArmTop = new DigitalInput(1);
        liftSubsystemArmTop.setName("LiftSubsystem", "Arm Top");

        liftSubsystemArmBottom = new DigitalInput(0);
        liftSubsystemArmBottom.setName("LiftSubsystem", "Arm Bottom");

        liftSubsystemClaw = new Solenoid(11, 4);
        liftSubsystemClaw.setName("LiftSubsystem", "Claw");

        climbingSubsystemExtender = new Solenoid(11, 5);
        climbingSubsystemExtender.setName("ClimbingSubsystem", "Extender");
        climbingSubsystemExtender.set(false);

        climbingSubsystemWinch = new Spark(6);
        climbingSubsystemWinch.setName("ClimbingSubsystem", "Winch");
        climbingSubsystemWinch.setInverted(false);
        compressorSubsystemCompressor = new Compressor(11);
        compressorSubsystemCompressor.setName("CompressorSubsystem", "Compressor");

        compressorSubsystemCompressorPressure = new AnalogInput(0);
        compressorSubsystemCompressorPressure.setName("CompressorSubsystem", "Compressor Pressure");

        otherPowerDistributionPanel = new PowerDistributionPanel(10);
        otherPowerDistributionPanel.setName("Other", "Power Distribution Panel");

        otherFancyLights = new Spark (9);
        otherFancyLights.setName ("Other", "Twinkles!");
        otherFancyLights.set (-0.25);

    }
}
