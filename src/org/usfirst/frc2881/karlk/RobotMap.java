package org.usfirst.frc2881.karlk;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalGlitchFilter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2881.karlk.actuators.SmoothSpeedController;
import org.usfirst.frc2881.karlk.sensors.NavX;

import java.util.concurrent.TimeUnit;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    private static final double DISTANCE_CALIBRATION = 28.5/27.75;
    public static final int climbingSubsystemMoverPdpChannel = 7;

    public static Spark driveSubsystemLeftRearMotor;
    public static Spark driveSubsystemLeftFrontMotor;
    public static SpeedControllerGroup driveSubsystemDriveLeft;
    public static SmoothSpeedController driveSubsystemSmoothDriveLeft;
    public static Spark driveSubsystemRightRearMotor;
    public static Spark driveSubsystemRightFrontMotor;
    public static SpeedControllerGroup driveSubsystemDriveRight;
    public static SmoothSpeedController driveSubsystemSmoothDriveRight;
    public static DifferentialDrive driveSubsystemDriveTrain;
    public static Solenoid liftSubsystemRollerExtension;
    public static NavX driveSubsystemNavX;
    public static Encoder driveSubsystemLeftEncoder;
    public static Encoder driveSubsystemRightEncoder;
    public static Solenoid driveSubsystemGearShift;
    public static Solenoid intakeSubsystemGrasper;
    public static AnalogInput intakeSubsystemIntakeDetectorShortIR;
    public static AnalogInput intakeSubsystemIntakeDetectorLongIR;
    public static Spark intakeSubsystemIntakeRollerLeft;
    public static Spark intakeSubsystemIntakeRollerRight;
    public static SpeedControllerGroup intakeSubsystemIntakeRollerGroup;
    public static WPI_TalonSRX liftSubsystemArmMotor;
    public static Encoder liftSubsystemArmEncoder;
    public static DigitalInput liftSubsystemRevMagneticLimitSwitch;
    public static DigitalInput liftSubsystemHallEffectSensor;
    public static DigitalGlitchFilter liftSubsystemHallEffectFilter;
    public static Solenoid liftSubsystemClaw;
    public static Solenoid liftSubsystemArmInitialDeploy1;
    public static DoubleSolenoid liftSubsystemArmInitialDeploy2;
    public static Spark climbingSubsystemWinch;
    public static Spark climbingSubsystemMover;
    public static SmoothSpeedController climbingSubsystemSmoothWinch;
    public static Compressor compressorSubsystemCompressor;
    public static AnalogInput compressorSubsystemCompressorPressure;
    public static PowerDistributionPanel otherPowerDistributionPanel;
    public static Spark otherFancyLights;
    public static DigitalInput armLiftTestOverride;

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

        driveSubsystemSmoothDriveLeft = new SmoothSpeedController(driveSubsystemDriveLeft, .1, .25);
        driveSubsystemSmoothDriveRight = new SmoothSpeedController(driveSubsystemDriveRight, .1, .25);

        driveSubsystemDriveTrain = new DifferentialDrive(driveSubsystemSmoothDriveLeft, driveSubsystemSmoothDriveRight);
        driveSubsystemDriveTrain.setName("DriveSubsystem", "Drive Train");
        driveSubsystemDriveTrain.setSafetyEnabled(true);
        driveSubsystemDriveTrain.setExpiration(0.1);
        driveSubsystemDriveTrain.setMaxOutput(1.0);

        liftSubsystemRollerExtension = new Solenoid(11, 2);
        liftSubsystemRollerExtension.setName("DriveSubsystem", "Drop Omni Pancake");

        driveSubsystemNavX = new NavX(SPI.Port.kMXP);
        driveSubsystemNavX.setName("DriveSubsystem", "NavX");

        driveSubsystemLeftEncoder = new Encoder(5, 6, false, EncodingType.k4X);
        driveSubsystemLeftEncoder.setName("DriveSubsystem", "Left Encoder");
        driveSubsystemLeftEncoder.setDistancePerPulse(4.0/12.0*Math.PI/100 / DISTANCE_CALIBRATION);//100 tick encoder  distance/pulse  4/12*Math.Pi/100
        driveSubsystemLeftEncoder.setSamplesToAverage(16);
        driveSubsystemLeftEncoder.setMinRate(1 / 12.0);  // in feet per second
        driveSubsystemLeftEncoder.setPIDSourceType(PIDSourceType.kRate);

        driveSubsystemRightEncoder = new Encoder(7, 8, false, EncodingType.k4X);
        driveSubsystemRightEncoder.setName("DriveSubsystem", "Right Encoder");
        driveSubsystemRightEncoder.setDistancePerPulse(4.0/12.0*Math.PI/100 / DISTANCE_CALIBRATION);//100 tick encoder 4 inches * 12 inchesper foot * pi divided by number of ticks
        driveSubsystemRightEncoder.setSamplesToAverage(16);
        driveSubsystemRightEncoder.setMinRate(1 / 12.0);  // in feet per second
        driveSubsystemRightEncoder.setPIDSourceType(PIDSourceType.kRate);
        driveSubsystemRightEncoder.setReverseDirection(true);

        driveSubsystemGearShift = new Solenoid(11, 0);
        driveSubsystemGearShift.setName("DriveSubsystem", "Gear Shift");



        intakeSubsystemGrasper = new Solenoid(11, 1);
        intakeSubsystemGrasper.setName("IntakeSubsystem", "Grasper");

        intakeSubsystemIntakeDetectorShortIR = new AnalogInput(2);
        intakeSubsystemIntakeDetectorShortIR.setName("IntakeSubsystem", "IntakeCube Short Distance Detector Infrared");

        intakeSubsystemIntakeDetectorLongIR = new AnalogInput(3);
        intakeSubsystemIntakeDetectorLongIR.setName("IntakeSubsystem", "IntakeCube Long Distance Detector Infrared");

        intakeSubsystemIntakeRollerLeft = new Spark(0);
        intakeSubsystemIntakeRollerLeft.setInverted(true);
        intakeSubsystemIntakeRollerRight = new Spark(1);
        intakeSubsystemIntakeRollerRight.setInverted(false);
        intakeSubsystemIntakeRollerGroup = new SpeedControllerGroup(intakeSubsystemIntakeRollerLeft, intakeSubsystemIntakeRollerRight);
        intakeSubsystemIntakeRollerGroup.setName("IntakeSubsystem", "IntakeCube Roller Group");
        intakeSubsystemIntakeRollerGroup.setInverted(true);

        liftSubsystemArmMotor = new WPI_TalonSRX(0);

        liftSubsystemArmEncoder = new Encoder(2, 3, true, EncodingType.k4X);
        liftSubsystemArmEncoder.setName("LiftSubsystem", "Arm Encoder");
        liftSubsystemArmEncoder.setDistancePerPulse(7.0 / 1600);
        liftSubsystemArmEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
        liftSubsystemArmEncoder.setMinRate(1 / 12.0);
        liftSubsystemArmEncoder.setReverseDirection(true);
        liftSubsystemRevMagneticLimitSwitch = new DigitalInput(1);
        liftSubsystemRevMagneticLimitSwitch.setName("LiftSubsystem", "Limit Switch");

        liftSubsystemHallEffectSensor = new DigitalInput(4);
        liftSubsystemHallEffectSensor.setName("LiftSubsystem", "Claw Sensor");

        liftSubsystemHallEffectFilter = new DigitalGlitchFilter();
        liftSubsystemHallEffectFilter.setPeriodNanoSeconds(TimeUnit.MILLISECONDS.toNanos(100));
        liftSubsystemHallEffectFilter.add(liftSubsystemHallEffectSensor);

        liftSubsystemClaw = new Solenoid(11, 4);
        liftSubsystemClaw.setName("LiftSubsystem", "Claw");

        liftSubsystemArmInitialDeploy1 = new Solenoid(11, 3);
        liftSubsystemArmInitialDeploy1.setName("LiftSubsystem", "ArmInitialDeploy");
        liftSubsystemArmInitialDeploy1.set(false);

        liftSubsystemArmInitialDeploy2 = new DoubleSolenoid(11, 5, 6);
        liftSubsystemArmInitialDeploy2.setName("LiftSubsystem", "ArmInitialDeploy2");
        liftSubsystemArmInitialDeploy2.set(DoubleSolenoid.Value.kOff);

        armLiftTestOverride = new DigitalInput(0);
        armLiftTestOverride.setName("LiftSubsystem", "LiftOverride");

        climbingSubsystemMover = new Spark(7);
        climbingSubsystemMover.setName("ClimbingSubsystem", "Mover");


        climbingSubsystemWinch = new Spark(6);
        climbingSubsystemWinch.setName("ClimbingSubsystem", "Winch");
        climbingSubsystemWinch.setInverted(true);
        climbingSubsystemWinch.setExpiration(0.1);
        climbingSubsystemWinch.setSafetyEnabled(true);
        climbingSubsystemSmoothWinch = new SmoothSpeedController(climbingSubsystemWinch, .05, 0.25);

        compressorSubsystemCompressor = new Compressor(11);
        compressorSubsystemCompressor.setName("CompressorSubsystem", "Compressor");

        compressorSubsystemCompressorPressure = new AnalogInput(0);
        compressorSubsystemCompressorPressure.setName("CompressorSubsystem", "Compressor Pressure");

        otherPowerDistributionPanel = new PowerDistributionPanel(10);
        otherPowerDistributionPanel.setName("Other", "Power Distribution Panel");

        otherFancyLights = new Spark(9);
        otherFancyLights.setName("Other", "Twinkles!");
        otherFancyLights.set(-0.25);

        // Adding the left & drive smooth controllers to DifferentialDrive makes them disappear
        // from the LiveWindow so add them back in the SmartDashboard section
        SmartDashboard.putData(driveSubsystemSmoothDriveLeft);
        SmartDashboard.putData(driveSubsystemSmoothDriveRight);
    }
}
