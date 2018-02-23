package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
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
public class SwitchStartCSwitchL extends CommandGroup {
    private final DriveSubsystem.SwitchPosition side;

    public SwitchStartCSwitchL(DriveSubsystem.SwitchPosition side){
        this.side = side;

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(270));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addSequential(new ConditionalCommand(new DriveForward(81.94 / 12), new DriveForward(126.065/12)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(0));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addParallel(new ConditionalCommand(new DriveForward(85/12)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.SIDE;
            }
        });

        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.DOWN)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.SIDE;
            }
        });

        addSequential(new ConditionalCommand(new TurnToHeading(90)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.SIDE;
            }
        });

        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.UP)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.SIDE;
            }
        });

        addParallel(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false));

        addSequential(new ConditionalCommand(new DriveForward(38/12), new DriveForward(26.125/12)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });

        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
