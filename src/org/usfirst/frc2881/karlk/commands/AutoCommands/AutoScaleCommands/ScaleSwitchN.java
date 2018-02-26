package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.AutoCrossLineCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.CrossLineCenter;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.CrossLineLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.StartingLocation;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.SetGrasper;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class ScaleSwitchN extends AbstractAutoCommand {

    public ScaleSwitchN(String gameData, StartingLocation start, CrossLineLocation side, AutoOptions auto){

        addSequential(new AutoCrossLineCommand(start, side, auto));

        addSequential(new DriveForward(167/12));

        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new ConditionalCommand(new TurnToHeading(-91), new TurnToHeading(90)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addSequential(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, false));

        addSequential(new DriveForward(26.38/12)); // front bumper goes an inch under the scale

        addSequential(new SetClaw(ClawState.OPEN));

        addSequential(new DriveForward(-38.785/12));

        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, false));
    }


    // Called just before this Command runs the first time

}
