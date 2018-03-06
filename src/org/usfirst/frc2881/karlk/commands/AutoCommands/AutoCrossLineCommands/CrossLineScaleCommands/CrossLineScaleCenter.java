package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.CrossLineScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
class CrossLineScaleCenter extends AbstractAutoCommand {

    CrossLineScaleCenter(String gameData, AutoStrategy strategy){

        addSequential(new ConditionalCommand(new TurnToHeading(270, true), new TurnToHeading(90, true)) {
            @Override
            protected boolean condition() {
                return strategy == AutoStrategy.SAFE_AUTO_LEFT || (strategy == AutoStrategy.OVERRIDE && gameData.charAt(1) == 'L');
            }
        });
        addSequential(new ConditionalCommand(new DriveForward(126.065/12), new DriveForward(112.065/12)) {
            @Override
            protected boolean condition() {
                return strategy == AutoStrategy.SAFE_AUTO_LEFT || strategy == AutoStrategy.OVERRIDE && gameData.charAt(1) == 'L';
            }
        });
        addSequential(new TurnToHeading(0, true));
        addSequential(new DriveForward(257.0/12));
    }


    // Called just before this Command runs the first time

}
