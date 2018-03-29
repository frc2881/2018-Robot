package org.usfirst.frc2881.karlk.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.actuators.SmoothSpeedController;
import org.usfirst.frc2881.karlk.commands.ControlArm;

/**
 * This handles the arm and the claw at the end
 * of the arm that is used to deliver cubes to the
 * switch and the scale.
 */
public class LiftSubsystem extends PIDSubsystem implements SendableWithChildren {
    public enum ClawState {OPEN, CLOSED}

    //define constants for scale and switch height
    public static double UPPER_SCALE_HEIGHT = 7.2;
    public static double LOWER_SCALE_HEIGHT = 4.5;
    public static double SWITCH_HEIGHT = 3.5;
    public static double ZERO_ARM_HEIGHT = 0.1;

    private static final double topLimit = 7;
    private static final double bottomLimit = 0;
    private static final double topThreshold = 5;
    private static final double bottomThreshold = 3;

    //these break eject cube and lift to height
    private static final double liftKc = 1.0;
    private static final double liftPc = 5.0;  // period of oscillation
    private static final double liftP = 0.6 * liftKc;
    private static final double liftI = 0;//2 * liftP * 0.05 / liftPc;
    private static final double liftD = 0.125 * liftP * liftPc / 0.05;
    private static final double liftF = 0.00;
    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final WPI_TalonSRX armMotor = add(RobotMap.liftSubsystemArmMotor);
    private final SmoothSpeedController smoothArmController = add(new SmoothSpeedController(armMotor, .1, .25, 0));
    private final Encoder armEncoder = add(RobotMap.liftSubsystemArmEncoder);
    private final DigitalInput limitSwitch = add(RobotMap.liftSubsystemRevMagneticLimitSwitch);
    private final Solenoid claw = add(RobotMap.liftSubsystemClaw);
    private final Solenoid armInitialDeploy1 = add(RobotMap.liftSubsystemArmInitialDeploy1);
    private final DoubleSolenoid armInitialDeploy2 = add(RobotMap.liftSubsystemArmInitialDeploy2);
    private final DigitalInput clawPosition = add(RobotMap.liftSubsystemHallEffectSensor);

    private NeutralMode armNeutralMode;
    private boolean isArmCalibrated;

    // Initialize your subsystem here
    public LiftSubsystem() {
        super("LiftSubsystem", 1.0, 0.0, 0.0);
        /*This makes a call to the PIDSubsystem constructor
        PIDSubsystem(double p, double i, double d)
        that instantiates a PIDSubsystem that will use the given p, i and d values.*/
        setAbsoluteTolerance(1.0 / 12);  //Set the absolute error which is considered tolerable for use with OnTarget.
        getPIDController().setContinuous(false);
        setName("LiftSubsystem", "PIDSubsystem Controller");
        setInputRange(bottomLimit, topLimit);
        setOutputRange(-1.0, 1.0);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }

    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("UpperScaleHeight", () -> UPPER_SCALE_HEIGHT, (height) -> UPPER_SCALE_HEIGHT = height);
        builder.addDoubleProperty("LowerScaleHeight", () -> LOWER_SCALE_HEIGHT, (height) -> LOWER_SCALE_HEIGHT = height);
        builder.addDoubleProperty("SwitchHeight", () -> SWITCH_HEIGHT, (height) -> SWITCH_HEIGHT = height);
        builder.addDoubleProperty("ZeroArmHeight", () -> ZERO_ARM_HEIGHT, (height) -> ZERO_ARM_HEIGHT = height);

        builder.addDoubleProperty("MinSpeed", this::getArmMotorMin, null);
        builder.addDoubleProperty("MaxSpeed", this::getArmMotorMax, null);
        builder.addBooleanProperty("BottomLimit", this::isBottomLimitSwitchTriggered, null);
        builder.addBooleanProperty("TopLimit", this::isTopLimitSwitchTriggered, null);
    }

    public void reset() {
        isArmCalibrated = false;
        armMotor.setSafetyEnabled(false);  // wait for calibration before enabling motor safety
        getPIDController().reset();
        armEncoder.reset();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ControlArm());
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        return armEncoder.pidGet();
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        setArmMotorSpeed(output);
    }

    public void setArmNeutralMode(NeutralMode neutralMode) {
        if (this.armNeutralMode != neutralMode) {
            this.armNeutralMode = neutralMode;
            armMotor.setNeutralMode(neutralMode);
        }
    }

    public void setClaw(ClawState state) {
        claw.set(state == ClawState.OPEN);
    }

    public ClawState getClaw() {
        return claw.get() ? ClawState.OPEN : ClawState.CLOSED;
    }

    public void armInitialDeploy(boolean deploy) {
        armInitialDeploy1.set(deploy);
        armInitialDeploy2.set(deploy ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public void armAssistDeploy(boolean deploy){
        armInitialDeploy1.set(deploy);
    }

    public void setArmAssistOff(){
        armInitialDeploy1.set(false);
    }

    public void setArmMotorSpeed(double speed) {
        // Make sure the motor doesn't move too fast when it's close to the top & bottom limits
        double min = getArmMotorMin();
        double max = getArmMotorMax();

        if (speed < min) {
            speed = min;
        }
        if (speed > max) {
            speed = max;
        }

        smoothArmController.set(speed);
        //I love Robots!!!
    }

    private double getArmMotorMin() {
        double position = armEncoder.getDistance();
        double min = -1;
        if (!isArmCalibrated) {
            if (isLimitSwitchTriggered()) {
                min = -.1;
            } else {
                min = -.3;
            }
        } else {
            if (position <= bottomLimit) {
                min = -0.3;
            } else if (position <= bottomThreshold) {
                min = -(.3 + (.7 * (position - bottomLimit) / (bottomThreshold - bottomLimit)));
            }
            if (isBottomLimitSwitchTriggered()) {
                min = 0;
            }
        }
        return min;
    }

    private double getArmMotorMax() {
        double position = armEncoder.getDistance();
        double max = 1;
        if (!isArmCalibrated) {
            max = 0;
        } else {
            if (position >= topLimit) {
                max = 0.3;
            } else if (position >= topThreshold) {
                max = 1 - (.7 * (position - topThreshold) / (topLimit - topThreshold));
            }
            if (isTopLimitSwitchTriggered()) {
                max = 0;
            }
        }
        return max;
    }

    public void setMotorForCalibration() {
        if (isLimitSwitchTriggered()) {
            armMotor.set(-0.1);
        } else {
            armMotor.set(-0.3);
        }
    }

    private boolean isBottomLimitSwitchTriggered() {
        return !limitSwitch.get() && armEncoder.getDistance() < 0.5;
    }

    private boolean isTopLimitSwitchTriggered() {
        return !limitSwitch.get() && armEncoder.getDistance() > 0.5;
    }

    public boolean isLimitSwitchTriggered() {
        return !limitSwitch.get();
    }

    public boolean isSpeedReallySmall() {
        return Math.abs(armEncoder.getRate()) < .05;
    }

    public void resetArmEncoder() {
        armEncoder.reset();
        isArmCalibrated = true;
//        armMotor.setExpiration(0.1);
//        armMotor.setSafetyEnabled(true);
    }

    public boolean cubeInClaw() {
        return getClaw() == ClawState.CLOSED && !clawPosition.get();
    }
}
