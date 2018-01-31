package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.Sendable;

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

    default <T extends Sendable> T add(T sendable) {
        addChild(sendable);
        return sendable;
    }
}
