package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class SwitchStartCSwitchR extends AbstractAutoCommand {

    public SwitchStartCSwitchR(){

        addSequential(new TurnToHeading(90, true));

        addSequential(new DriveForward((46.94 - 5.4 - 27.8) / 12));

        addSequential(new TurnToHeading(0, true));

        addSequential(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false));

        //addSequential(new DriveForward((56.0- 15.5)/12));

        addSequential(new DriveForward((60.0 - 6)/12));

        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time

}
