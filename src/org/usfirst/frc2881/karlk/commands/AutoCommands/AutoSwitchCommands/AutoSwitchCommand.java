package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.AutoCommands.OverrideAuto;
import org.usfirst.frc2881.karlk.commands.AutonomousRobotFinish;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoSwitchCommand extends AbstractAutoCommand {

    public AutoSwitchCommand(StartingLocation start, String gameData, SwitchPosition side, AutoStrategy strategy) {

        //3.4
        //3.7
        //0.17
        if (strategy == AutoStrategy.SAFE_AUTO_LEFT || strategy == AutoStrategy.SAFE_AUTO_RIGHT) {
            addSequential(new SafeAutoSwitchCommand(start, gameData, side, strategy));
        }

        if (strategy == AutoStrategy.OVERRIDE) {
            addSequential(new OverrideAutoSwitchCommand(start, gameData, side));
        }
        addSequential(new ConditionalCommand(new DriveForward(-35.0/12), new DriveForward(-19.125/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new AutonomousRobotFinish());

        //addSequential(new SwitchCubeIntake(side, gameData));
    }


    // Called just before this Command runs the first time

}
