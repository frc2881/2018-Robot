package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.CrossLineScaleCommands.CrossLineScale;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class ScaleSwitchN extends AbstractAutoCommand {

    public ScaleSwitchN(String gameData, StartingLocation start) {

        addSequential(new CrossLineScale(gameData, start));

        addSequential(new SetClaw(ClawState.OPEN));

        addSequential(new DriveForward(-45.0 / 12));
    }


    // Called just before this Command runs the first time

}
