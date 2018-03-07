package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.CrossLineScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.DriveForward;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class CrossLineScale extends AbstractAutoCommand {

    public CrossLineScale(String gameData, StartingLocation start, AutoStrategy strategy){

        addSequential(new ConditionalCommand(new CrossLineScaleCenter(gameData, strategy)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.CENTER && ((
                        (strategy == AutoStrategy.SAFE_AUTO_RIGHT && gameData.charAt(1) == 'R') ||
                                (strategy == AutoStrategy.SAFE_AUTO_LEFT && gameData.charAt(1) == 'L')) ||
                        strategy == AutoStrategy.OVERRIDE);
            }
        });

        addSequential(new ConditionalCommand(new DriveForward(241.0/12)) {
            @Override
            protected boolean condition() {
                return (strategy == AutoStrategy.SAFE_AUTO_LEFT && gameData.charAt(1) == 'L') ||
                        (strategy == AutoStrategy.SAFE_AUTO_RIGHT && gameData.charAt(1) == 'R') ||
                        (strategy == AutoStrategy.OVERRIDE &&
                                (start == StartingLocation.LEFT && gameData.charAt(1) == 'L' ||
                                        start == StartingLocation.RIGHT && gameData.charAt(1) == 'R'));
            }
        });

        addSequential(new ConditionalCommand(new CrossLineScaleSide(start)) {
            @Override
            protected boolean condition() {
                return (strategy == AutoStrategy.OVERRIDE &&
                        (start == StartingLocation.LEFT && gameData.charAt(1) != 'L' ||
                                start == StartingLocation.RIGHT && gameData.charAt(1) != 'R'));
            }
        });

    }


    // Called just before this Command runs the first time

}
