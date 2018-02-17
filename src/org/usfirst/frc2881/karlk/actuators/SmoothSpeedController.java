package org.usfirst.frc2881.karlk.actuators;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Smooths out rapid changes to requested motor speeds to reduce motor loads, maximum current draw and
 * stress on the physical robot components.
 */
public class SmoothSpeedController extends SendableBase implements SpeedController {
    private final SpeedController motor;
    private double stallSpeed;
    private double maxIncrease;
    private double maxDecrease;
    private double speed;

    /**
     * Wraps the specified speed controller such that an input change from 0 to 1 is smoothed linearly over
     * the specified number of seconds.
     *
     * @param motor            the speed controller for the physical motor or {@code SpeedControllerGroup}
     * @param stallSpeed       the minimum speed at which the motor starts to move on the robot
     * @param zeroToOneSeconds the minimum time it should take for input to change from 0.0 to 1.0 or,
     *                         if decelerating, from 1.0 to 0.0.
     */
    public SmoothSpeedController(SpeedController motor,
                                 double stallSpeed,
                                 double zeroToOneSeconds) {
        this(motor, stallSpeed, zeroToOneSeconds, zeroToOneSeconds);
    }

    /**
     * Use this constructor to accelerate and decelerate at different maximum rates.
     */
    public SmoothSpeedController(SpeedController motor,
                                 double stallSpeed,
                                 double zeroToOneSeconds,
                                 double oneToZeroSeconds) {
        setName(((Sendable) motor).getName() + " Smooth");
        setSubsystem(((Sendable) motor).getSubsystem());
        this.motor = motor;
        this.stallSpeed = stallSpeed;
        this.maxIncrease = TimedRobot.DEFAULT_PERIOD / zeroToOneSeconds;
        this.maxDecrease = TimedRobot.DEFAULT_PERIOD / oneToZeroSeconds;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Speed Controller");
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("Value", this::get, this::set);
        builder.addDoubleProperty("StallSpeed", () -> stallSpeed, (stallSpeed) -> this.stallSpeed = stallSpeed);
        builder.addDoubleProperty("MaxIncrease", () -> maxIncrease, (maxIncrease) -> this.maxIncrease = maxIncrease);
        builder.addDoubleProperty("MaxDecrease", () -> maxDecrease, (maxDecrease) -> this.maxDecrease = maxDecrease);
        builder.addDoubleProperty("Actual", motor::get, null);
    }

    @Override
    public double get() {
        return this.speed;
    }

    @Override
    public void set(double speed) {
        this.speed = smooth(this.speed, speed);
        motor.set(scale(this.speed));
    }

    private double smooth(double oldSpeed, double speed) {
        double sign = 1.0;
        if (speed < -0.01) {
            sign = -1.0;
            speed = -speed;
            oldSpeed = -oldSpeed;
        }
        if (oldSpeed < -maxIncrease) {
            // Old speed is very negative but speed is positive, slow down before reversing direction
            speed = Math.min(oldSpeed + maxDecrease, 0);
        } else {
            // Constraint max movement in either direction
            double max = oldSpeed + maxIncrease;
            double min = oldSpeed - maxDecrease;
            if (speed > max) {
                speed = max;
            } else if (speed < min) {
                speed = min;
            }
        }
        speed *= sign;
        return speed;
    }

    private double scale(double speed) {
        if (Math.abs(speed) < 0.001) {
            speed = 0;
        } else if (Math.abs(speed) > 0.999) {
            speed = Math.signum(speed);
        } else {
            speed = Math.copySign(stallSpeed, speed) + speed * (1.0 - stallSpeed);
        }
        return speed;
    }

    @Override
    public void stopMotor() {
        this.speed = 0;
        motor.stopMotor();
    }

    @Override
    public void disable() {
        this.speed = 0;
        motor.disable();
    }

    @Override
    public void setInverted(boolean inverted) {
        motor.setInverted(inverted);
    }

    @Override
    public boolean getInverted() {
        return motor.getInverted();
    }

    @Override
    public void pidWrite(double output) {
        set(output);
    }
}
