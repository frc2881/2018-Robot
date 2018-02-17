package org.usfirst.frc2881.karlk.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.RobotMap;
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
    public static double ZERO_ARM_HEIGHT = 0;

    private static final double topLimit = 7;
    private static final double bottomLimit = 0;
    private static final double topThreshold = 5;
    private static final double bottomThreshold = 2;

    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final WPI_TalonSRX armMotor = add(RobotMap.liftSubsystemArmMotor);
    private final Encoder armEncoder = add(RobotMap.liftSubsystemArmEncoder);
    private final DigitalInput limitSwitch = add(RobotMap.liftSubsystemRevMagneticLimitSwitch);
    private final Solenoid claw = add(RobotMap.liftSubsystemClaw);
    private final Solenoid armInitialDeploy = add(RobotMap.liftSubsystemArmInitialDeploy);
    private final Timer timer = new Timer();

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

    /*Not sure how to do this with the Rev Magnetic Limit Switch,
    so am commenting out this code until we can figure that out.
    public boolean checkTopLimit(){
        return limitSwitch.get();
    }

    public boolean checkBottomLimit(){
        return limitSwitch.get();
    }
*/

    public double checkEncoder() {
        return armEncoder.getDistance();
    }

    public void resetEncoder() {
        armEncoder.reset();
    }

    public void setClaw(ClawState state) {
        claw.set(state == ClawState.OPEN);
    }

    public void armInitialDeploy(boolean deploy) {
        armInitialDeploy.set(deploy);
    }

    public void armControl(double speed) {
        speed = applyDeadband(speed, 0.05);
        // Use 'squaredInputs' to get better control at low speed
        setArmMotorSpeed(Math.copySign(speed * speed, speed));
    }

    private void setArmMotorSpeed(double speed) {
        // Make sure the motor doesn't move too fast when it's close to the top & bottom limits
        double position = armEncoder.getDistance();
        double min = -1;
        double max = 1;
        if (!isArmCalibrated) {
            if (isLimitSwitchTriggered()) {
                min = -.1;
            } else {
                min = -.3;
            }
            max = 0;
        } else {
            if (position <= bottomLimit) {
                min = -0.3;
            } else if (position >= topLimit) {
                max = 0.3;
            } else if (position <= bottomThreshold) {
                min = (position - bottomLimit) * (-.7 / (bottomThreshold - bottomLimit)) - .2;
            } else if (position >= topThreshold) {
                max = (position - topThreshold) * (-.8 / (topLimit - topThreshold)) + 1;
            }
            if (isBottomLimitSwitchTriggered()) {
                min = 0;
            }
            if (isTopLimitSwitchTriggered()) {
                max = 0;
            }
        }
        if (speed < min) {
            speed = min;
        }
        if (speed > max) {
            speed = max;
        }

        armMotor.set(speed);
        //I love Robots!!!
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

    private double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }

    public boolean isSpeedReallySmall() {
        return Math.abs(armEncoder.getRate()) < .05;
    }

    public void startTimer() {
        timer.reset();
        timer.start();
    }

    public void resetArmEncoder() {
        armEncoder.reset();
        isArmCalibrated = true;
        armMotor.setExpiration(0.1);
        armMotor.setSafetyEnabled(true);
    }

    public double getTimer() {
        return timer.get();
    }

    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("UPPER_SCALE_HEIGHT", () -> this.UPPER_SCALE_HEIGHT, (height) -> this.UPPER_SCALE_HEIGHT = height);
        builder.addDoubleProperty("LOWER_SCALE_HEIGHT", () -> this.LOWER_SCALE_HEIGHT, (height) -> this.LOWER_SCALE_HEIGHT = height);
        builder.addDoubleProperty("SWITCH_HEIGHT", () -> this.SWITCH_HEIGHT, (height) -> this.SWITCH_HEIGHT = height);
        builder.addDoubleProperty("ZERO_ARM_HEIGHT", () -> this.ZERO_ARM_HEIGHT, (height) -> this.ZERO_ARM_HEIGHT = height);

    }
}
