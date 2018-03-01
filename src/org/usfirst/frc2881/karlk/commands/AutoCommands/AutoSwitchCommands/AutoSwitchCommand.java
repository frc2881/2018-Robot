package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.AutoCommands.OverrideAuto;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoSwitchCommand extends AbstractAutoCommand {

    public AutoSwitchCommand(StartingLocation start, String gameData, SwitchPosition side, AutoStrategy strategy) {

        addSequential(new ConditionalCommand(new SafeAutoSwitchCommand(start, gameData, side, strategy)) {
            @Override
            protected boolean condition() {
                return strategy == AutoStrategy.SAFE_AUTO_LEFT || strategy == AutoStrategy.SAFE_AUTO_RIGHT;
            }
        });

        addSequential(new ConditionalCommand(new OverrideAutoSwitchCommand(start, gameData, side)) {
            @Override
            protected boolean condition() {
                return strategy == AutoStrategy.OVERRIDE;
            }
        });

    }


    // Called just before this Command runs the first time

}
