package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.TWINKLES;

/**  This runs the compressor, required by all the pistons
 * in other subsystems.
 */
public class PrettyLightsSubsystem extends Subsystem implements SendableWithChildren {
    public static final double red_heartbeat = -0.25;
    public static final double blue_heartbeat = -0.23;
    public static final double hotPink = 0.57;
    public static final double green = 0.77;
    public static final double orange = 0.65;

    private final Spark Spark = add(RobotMap.otherFancyLights);

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
         setDefaultCommand(new TWINKLES());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

