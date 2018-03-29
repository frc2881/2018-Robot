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

        final boolean startCenter = start == StartingLocation.CENTER;
        final boolean startLeft = start == StartingLocation.LEFT;
        final boolean startRight = start == StartingLocation.RIGHT;

        final boolean safeRight = strategy == AutoStrategy.SAFE_AUTO_RIGHT;
        final boolean safeLeft = strategy == AutoStrategy.SAFE_AUTO_LEFT;
        final boolean override = strategy == AutoStrategy.OVERRIDE;

        final boolean scaleRight = gameData.charAt(1) == 'R';
        final boolean scaleLeft = gameData.charAt(1) == 'L';


        if (startCenter && ((safeRight && scaleRight) || (safeLeft && scaleLeft) || override)){
            addSequential(new CrossLineScaleCenter(gameData, strategy));
        }
        else if ((safeLeft && scaleLeft) || (safeRight && scaleRight) ||
                (override && ((scaleRight && startRight) || (scaleLeft && startLeft)))) {
            addSequential(new CrossLineStraight(start, strategy));
        }
        else if (override && (startLeft && scaleRight || startRight && scaleLeft)){
            addSequential(new CrossLineScaleSide(start));
        }

    }


    // Called just before this Command runs the first time

}
