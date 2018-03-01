package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartCSwitchL;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartLSwitchL;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartRSwitchL;
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
public class ScaleSwitchL extends AbstractAutoCommand {
    private final String gameData;
    private final SwitchPosition side;
    private final StartingLocation start;

    public ScaleSwitchL(String gameData, SwitchPosition side, StartingLocation start){
        this.gameData = gameData;
        this.side = side;
        this.start = start;

        //DONE

        addSequential(new ConditionalCommand(new SwitchStartLSwitchL(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartCSwitchL(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.CENTER;
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartRSwitchL(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.RIGHT;
            }
        });

        addParallel(new LiftToHeight(0, false));

        addSequential(new ConditionalCommand(new DriveForward(-38.0/12), new DriveForward(-26.125/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.DOWN)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new ConditionalCommand(new TurnToHeading(270)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.UP)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new ConditionalCommand(new DriveForward(60.675/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(0));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addSequential(new ConditionalCommand(new DriveForward(145.735/12), new DriveForward(60.735/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });// (89.375, 228.735)

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(90));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addSequential(new DriveForward(49.125/12));//(40.25)

        addParallel(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(180));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addSequential(new DriveForward(9.735/12));//cube is two inches inside front bumper

        //INTAKE CUBE
        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));
        addParallel(new SetRollers(IntakeSubsystem.INTAKE_SPEED), 4);
        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.CLOSED));
        addSequential(new WaitCommand(1.5));
        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.CLOSED));
        addSequential(new WaitCommand(1.5));
        addSequential(new SetClaw(LiftSubsystem.ClawState.CLOSED));

        addSequential(new DriveForward(-9.735/12));

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new ConditionalCommand(new TurnToHeading(90), new TurnToHeading(270)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addSequential(new ConditionalCommand(new DriveForward(136.465/12), new DriveForward(55.965/12)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(0));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));

        addSequential(new DriveForward(95.265/12)); //(324)

        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));



        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new ConditionalCommand(new TurnToHeading(270), new TurnToHeading(90)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));
        addSequential(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, false));
        addSequential(new DriveForward(38.785/12)); // goes an inch under the scale
        addSequential(new SetClaw(ClawState.OPEN));

        addSequential(new DriveForward(-38.785/12));

        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, false));
    }


    // Called just before this Command runs the first time

}
