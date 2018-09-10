package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoSwitchCommand extends AbstractAutoCommand {

    public AutoSwitchCommand(StartingLocation start, String gameData) {

        //3.4
        //3.7
        //0.17
        if (start == StartingLocation.LEFT || start == StartingLocation.RIGHT) {
            addSequential(new SafeAutoSwitchCommand(start, gameData));
        }

        if (start == StartingLocation.CENTER) {
            addSequential(new OverrideAutoSwitchCommand(gameData));
        }

        /*addSequential(new ConditionalCommand(new DriveForward(-35.0/12), new DriveForward(-19.125/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });*/

        //addSequential(new SwitchCubeIntake(side, gameData));
    }


    // Called just before this Command runs the first time

}
