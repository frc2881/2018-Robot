package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.SwitchPosition;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoSwitchCommand extends AbstractAutoCommand {

    private final StartingLocation start;
    private final SwitchPosition side;
    private final String gameData;

    public AutoSwitchCommand(StartingLocation start, String gameData, SwitchPosition side) {
        super("AutoSwitch" + start + "position" + side + "of switch");
        this.start = start;
        this.side = side;
        this.gameData = gameData;

        addSequential(new ConditionalCommand(new SwitchStartLSwitchR(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT && gameData.charAt(0) == 'R';
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartLSwitchL(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT && gameData.charAt(0) == 'L';
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartCSwitchR(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.CENTER && gameData.charAt(0) == 'R';
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartCSwitchL(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.CENTER && gameData.charAt(0) == 'L';
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartRSwitchR(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.RIGHT && gameData.charAt(0) == 'R';
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartRSwitchL(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.RIGHT && gameData.charAt(0) == 'L';
            }
        });

    }


    // Called just before this Command runs the first time

}
