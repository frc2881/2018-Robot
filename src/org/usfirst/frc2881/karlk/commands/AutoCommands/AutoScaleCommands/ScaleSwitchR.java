package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.AutoSwitchCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartCSwitchR;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartLSwitchR;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartRSwitchR;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.SetGrasper;
import org.usfirst.frc2881.karlk.commands.SetRollers;
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
public class ScaleSwitchR extends AbstractAutoCommand {

    public ScaleSwitchR(String gameData, SwitchPosition side, StartingLocation start, AutoStrategy strategy){

        addSequential(new AutoSwitchCommand(start, gameData, side, strategy));

        addSequential(new ConditionalCommand(new DriveForward((136.465 - 17.8)/12), new DriveForward((55.965- 17.8)/12)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new TurnToHeading(0, true));

        addSequential(new DriveForward((95.265 - 14.0 - 17.8)/12)); //(324)

        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));

        addSequential(new ConditionalCommand(new TurnToHeading(270, true), new TurnToHeading(90, true)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, false));
        addSequential(new DriveForward((38.785 - 14.0)/12)); // goes an inch under the scale
        addSequential(new SetClaw(ClawState.OPEN));

        addSequential(new DriveForward((-38.785 - 14.0)/12));

        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, false));
    }


    // Called just before this Command runs the first time

}
