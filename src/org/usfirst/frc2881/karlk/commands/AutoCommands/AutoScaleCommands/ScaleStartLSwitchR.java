package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

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
public class ScaleStartLSwitchR extends CommandGroup {
    private final String gameData;

    public ScaleStartLSwitchR(String gameData){
        this.gameData = gameData;



        addSequential(new ConditionalCommand(new DriveForward(114.25 / 12)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'L';
            }
        });
        addSequential(new ConditionalCommand(new DriveForward(204.875 / 12)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'L';
            }
        });

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(0));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addParallel(new ConditionalCommand(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'L';
            }
        });
        addSequential(new ConditionalCommand(new DriveForward(38/12)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'L';
            }
        });

        addSequential(new ConditionalCommand(new DriveForward(85/12)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.DOWN)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new ConditionalCommand(new TurnToHeading(270)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.UP)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addParallel(new ConditionalCommand(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });
        addSequential(new ConditionalCommand(new DriveForward(40/12)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
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
