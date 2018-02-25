package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class SwitchStartLSwitchR extends AbstractAutoCommand {
    private final SwitchPosition side;

    public SwitchStartLSwitchR(SwitchPosition side){
        this.side = side;

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(90));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addSequential(new ConditionalCommand(new DriveForward(190.44 / 12), new DriveForward(234.565/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(0));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addSequential(new ConditionalCommand(new DriveForward(85/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });
        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.DOWN)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });

        addSequential(new ConditionalCommand(new TurnToHeading(270)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });

        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.UP)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });

        addParallel(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false));

        addSequential(new ConditionalCommand(new DriveForward(38/12), new DriveForward(26.125/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new WaitForChildren());
        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time

}
