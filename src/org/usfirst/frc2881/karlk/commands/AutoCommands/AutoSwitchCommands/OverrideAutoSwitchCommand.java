package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class OverrideAutoSwitchCommand extends AbstractAutoCommand {

    public OverrideAutoSwitchCommand(String gameData) {

        super("AutoSwitch" + "center" + "position");

        addSequential(new ConditionalCommand(new SwitchStartCSwitchR()) {
            @Override
            protected boolean condition() {
                return gameData.charAt(0) == 'R';
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartCSwitchL()) {
            @Override
            protected boolean condition() {
                return gameData.charAt(0) == 'L';
            }
        });
    }


    // Called just before this Command runs the first time

}
