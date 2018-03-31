package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class SafeAutoSwitchCommand extends AbstractAutoCommand {

    public SafeAutoSwitchCommand(StartingLocation start, String gameData, SwitchPosition side, AutoStrategy strategy) {

        super("AutoSwitch" + start + "position" + side + "of switch");

        addSequential(new ConditionalCommand(new SwitchSafeSide(start)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT && gameData.charAt(0) == 'L' || start == StartingLocation.RIGHT && gameData.charAt(0) == 'R';
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartCSwitchR()) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.CENTER && gameData.charAt(0) == 'R' && strategy == AutoStrategy.SAFE_AUTO_RIGHT;
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartCSwitchL()) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.CENTER && gameData.charAt(0) == 'L' && strategy == AutoStrategy.SAFE_AUTO_LEFT;
            }
        });

        // Called just before this Command runs the first time
    }
}
