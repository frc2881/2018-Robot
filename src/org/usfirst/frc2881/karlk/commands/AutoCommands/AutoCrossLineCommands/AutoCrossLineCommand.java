package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutonomousRobotFinish;
import org.usfirst.frc2881.karlk.commands.DriveForward;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoCrossLineCommand extends AbstractAutoCommand {

    public AutoCrossLineCommand(StartingLocation start, AutoStrategy strategy) {
        super("AutoCrossLine" + start + "position");

        addSequential(new ConditionalCommand(new DriveForward((56.0 + 17.0)/12)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT || start == StartingLocation.RIGHT;
            }
        });

        addSequential(new ConditionalCommand(new CrossLineCenter(strategy)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.CENTER;
            }
        });

    }


    // Called just before this Command runs the first time
}
