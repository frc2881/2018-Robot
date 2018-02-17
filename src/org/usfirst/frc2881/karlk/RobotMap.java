package org.usfirst.frc2881.karlk;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc2881.karlk.sensors.NavX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int INTAKE_SUBSYSTEM_INTAKE_ROLLER_LEFT_PDP_CHANNEL = 3;
    public static final int INTAKE_SUBSYSTEM_INTAKE_ROLLER_RIGHT_PDP_CHANNEL = 4;
    public static Spark driveSubsystemLeftRearMotor;
    public static Spark driveSubsystemLeftFrontMotor;
    public static SpeedControllerGroup driveSubsystemDriveLeft;
    public static Spark driveSubsystemRightRearMotor;
    public static Spark driveSubsystemRightFrontMotor;
    public static SpeedControllerGroup driveSubsystemDriveRight;
    public static DifferentialDrive driveSubsystemDriveTrain;
    public static Solenoid driveSubsystemDropOmniPancake;
    public static NavX driveSubsystemNavX;
    public static Encoder driveSubsystemLeftEncoder;
    public static Encoder driveSubsystemRightEncoder;
    public static Solenoid driveSubsystemGearShift;
    public static Solenoid intakeSubsystemGrasper;
    public static Ultrasonic intakeSubsystemIntakeDetectorUltrasonic;
    public static AnalogInput intakeSubsystemIntakeDetectorIR;
    public static Spark intakeSubsystemIntakeRollerLeft;
    public static Spark intakeSubsystemIntakeRollerRight;
    public static SpeedControllerGroup intakeSubsystemIntakeRollerGroup;
    public static WPI_TalonSRX liftSubsystemArmMotor;
    public static Encoder liftSubsystemArmEncoder;
    public static DigitalInput liftSubsystemRevMagneticLimitSwitch;
    public static Solenoid liftSubsystemClaw;
    public static Solenoid liftSubsystemArmInitialDeploy;
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
        driveSubsystemNavX = new NavX(SPI.Port.kMXP);
        driveSubsystemNavX.setName("DriveSubsystem", "NavX");
        driveSubsystemLeftEncoder = new Encoder(5, 6, false, EncodingType.k4X);
        driveSubsystemLeftEncoder.setName("DriveSubsystem", "Left Encoder");
        driveSubsystemLeftEncoder.setDistancePerPulse(4.0/12.0*Math.PI/100);//100 tick encoder  distance/pulse  4/12*Math.Pi/100
        driveSubsystemLeftEncoder.setSamplesToAverage(16);
        driveSubsystemLeftEncoder.setMinRate(1 / 12.0);  // in feet per second
        driveSubsystemLeftEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveSubsystemRightEncoder = new Encoder(7, 8, false, EncodingType.k4X);
        driveSubsystemRightEncoder.setName("DriveSubsystem", "Right Encoder");
        driveSubsystemRightEncoder.setDistancePerPulse(4.0/12.0*Math.PI/100);//100 tick encoder 4 inches * 12 inchesper foot * pi divided by number of ticks
        driveSubsystemRightEncoder.setSamplesToAverage(16);
        driveSubsystemRightEncoder.setMinRate(1 / 12.0);  // in feet per second
        driveSubsystemRightEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveSubsystemRightEncoder.setReverseDirection(true);
        driveSubsystemGearShift = new Solenoid(11, 0);
        driveSubsystemGearShift.setName("DriveSubsystem", "Gear Shift");

        intakeSubsystemGrasper = new Solenoid(11, 1);
        intakeSubsystemGrasper.setName("IntakeSubsystem", "Grasper");

        intakeSubsystemIntakeDetectorUltrasonic = new Ultrasonic(4,9);
        intakeSubsystemIntakeDetectorUltrasonic.setName("IntakeSubsystem", "IntakeCube Detector Ultrasonic");
        intakeSubsystemIntakeDetectorUltrasonic.setAutomaticMode(true); // turns on automatic mode

        intakeSubsystemIntakeDetectorIR = new AnalogInput(2);
        intakeSubsystemIntakeDetectorIR.setName("IntakeSubsystem", "IntakeCube Detector Infrared");

        intakeSubsystemIntakeRollerLeft = new Spark(0);
        intakeSubsystemIntakeRollerLeft.setInverted(true);
        intakeSubsystemIntakeRollerRight = new Spark(1);
        intakeSubsystemIntakeRollerRight.setInverted(false);
        intakeSubsystemIntakeRollerGroup = new SpeedControllerGroup(intakeSubsystemIntakeRollerLeft, intakeSubsystemIntakeRollerRight);
        intakeSubsystemIntakeRollerGroup.setName("IntakeSubsystem", "IntakeCube Roller Group");

        liftSubsystemArmMotor = new WPI_TalonSRX(0);

        liftSubsystemArmEncoder = new Encoder(2, 3, true, EncodingType.k4X);
        liftSubsystemArmEncoder.setName("LiftSubsystem", "Arm Encoder");
        liftSubsystemArmEncoder.setDistancePerPulse(7.0 / 1600);
        liftSubsystemArmEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        liftSubsystemArmEncoder.setReverseDirection(true);
        liftSubsystemRevMagneticLimitSwitch = new DigitalInput(1);
        liftSubsystemRevMagneticLimitSwitch.setName("LiftSubsystem", "Limit Switch");

        liftSubsystemClaw = new Solenoid(11, 4);
        liftSubsystemClaw.setName("LiftSubsystem", "Claw");

        liftSubsystemArmInitialDeploy = new Solenoid(11, 3);
        liftSubsystemArmInitialDeploy.setName("LiftSubsystem", "ArmInitialDeploy");
        liftSubsystemArmInitialDeploy.set(false);

        climbingSubsystemWinch = new Spark(6);
        climbingSubsystemWinch.setName("ClimbingSubsystem", "Winch");
        climbingSubsystemWinch.setInverted(false);
        climbingSubsystemWinch.setExpiration(0.1);
        climbingSubsystemWinch.setSafetyEnabled(true);

        compressorSubsystemCompressor = new Compressor(11);
        compressorSubsystemCompressor.setName("CompressorSubsystem", "Compressor");

        compressorSubsystemCompressorPressure = new AnalogInput(0);
        compressorSubsystemCompressorPressure.setName("CompressorSubsystem", "Compressor Pressure");

        otherPowerDistributionPanel = new PowerDistributionPanel(10);
        otherPowerDistributionPanel.setName("Other", "Power Distribution Panel");

        otherFancyLights = new Spark(9);
        otherFancyLights.setName("Other", "Twinkles!");
        otherFancyLights.set(-0.25);

    }
}
