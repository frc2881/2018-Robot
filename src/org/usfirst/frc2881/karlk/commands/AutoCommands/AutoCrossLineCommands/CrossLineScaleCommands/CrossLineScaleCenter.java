package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.CrossLineScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutonomousRobotFinish;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
class CrossLineScaleCenter extends AbstractAutoCommand {

    CrossLineScaleCenter(String gameData, AutoStrategy strategy){

        double angle = Math.atan2(36.0, 100.0);

        addSequential(new ConditionalCommand(new TurnToHeading(270, true), new TurnToHeading(90, true)) {
            @Override
            protected boolean condition() {
                return strategy == AutoStrategy.SAFE_AUTO_LEFT || (strategy == AutoStrategy.OVERRIDE && gameData.charAt(1) == 'L');
            }
        });

        addSequential(new ConditionalCommand(new DriveForward((99.565 - 6 - 26.4)/12), new DriveForward((90.565 - 5.4 - 27.8)/12)) {
            @Override
            protected boolean condition() {
                return strategy == AutoStrategy.SAFE_AUTO_LEFT || strategy == AutoStrategy.OVERRIDE && gameData.charAt(1) == 'L';
            }
        });

        addSequential(new TurnToHeading(0, true));

        addSequential(new ConditionalCommand(new DriveForward((141.0 - 5.4 + 2)/12), new DriveForward((141.0 - 6)/12)) {
            @Override
            protected boolean condition() {
                return strategy == AutoStrategy.SAFE_AUTO_LEFT || strategy == AutoStrategy.OVERRIDE && gameData.charAt(1) == 'L';
            }
        });

        addSequential(new ConditionalCommand(new TurnToHeading(-angle * 180/Math.PI, true),
                new TurnToHeading(angle * 180/Math.PI, true)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new AutonomousRobotFinish());

        addSequential(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT - 0.17, false));

        addSequential(new DriveForward(100.0 / Math.cos(angle) / 12));
    }


    // Called just before this Command runs the first time

}
