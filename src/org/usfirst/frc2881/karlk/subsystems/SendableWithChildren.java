package org.usfirst.frc2881.karlk.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Use this to extend Subsystem and add useful methods.
 */
interface SendableWithChildren {

    /*  This method can be used to name and add a component of the subsystem
     *  into the LiveWindow.  When you declare a component in the subsystem
     *  file, you should use this to add it at the same time.
     *  Example:
     *      private final Encoder armEncoder = add(RobotMap.liftSubsystemArmEncoder);
     *
     *  The "T" refers to a generic type, so it will return the same type as you sent it.
     *  In the above example, it would return a type Encoder.
     */
    void addChild(Sendable sendable);

    default <T extends Sendable> T add(AnalogInput sendable) {
        addChild(sendable);
        return (T) sendable;

    }

    default <T extends Sendable> T add(Compressor sendable) {
        addChild(sendable);
        return (T) sendable;
    }

    default <T extends Sendable> T add(Spark sendable) {
        addChild(sendable);
        return (T) sendable;

    }

    default <T extends Sendable> T add(Solenoid sendable) {
        addChild(sendable);
        return (T) sendable;

    }

    default <T extends Sendable> T add(Encoder sendable) {
        addChild(sendable);
        return (T) sendable;

    }

    default <T extends Sendable> T add(DifferentialDrive sendable) {
        addChild(sendable);
        return (T) sendable;

    }

    default <T extends Sendable> T add(SpeedControllerGroup sendable) {
        addChild(sendable);
        return (T) sendable;

    }

    default <T extends Sendable> T add(DigitalInput sendable) {
        addChild(sendable);
        return (T) sendable;

    }

    default <T extends Sendable> T add(WPI_TalonSRX sendable) {
        addChild(sendable);
        return (T) sendable;

    }
}
