package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class SwitchSafeSide extends AbstractAutoCommand {

    public SwitchSafeSide(StartingLocation start){

        addSequential(new ConditionalCommand(new TurnToHeading(45, true), new TurnToHeading(-45, true)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new DriveForward(91 / Math.cos(Math.PI/4) /12));

        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time

}
