package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.AutonomousRobotFinish;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.SetGrasper;
import org.usfirst.frc2881.karlk.commands.SetRollers;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class SwitchCubeIntake extends AbstractAutoCommand {

    public SwitchCubeIntake(SwitchPosition side, String gameData){


        addSequential(new ConditionalCommand(new DriveForward(-38.0/12), new DriveForward(-26.125/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new AutonomousRobotFinish());

        addSequential(new LiftToHeight(0, false));

        addSequential(new ConditionalCommand(new TurnToHeading(90, true)) {
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

        addSequential(new TurnToHeading(0, true));

        addSequential(new ConditionalCommand(new DriveForward(145.735/12), new DriveForward(60.735/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });// (89.375, 228.735)

        addSequential(new ConditionalCommand(new TurnToHeading(270, true), new TurnToHeading(90, true)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(0) == 'R';
            }
        });

        addSequential(new DriveForward(49.125/12));//(40.25)

        addParallel(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));

        addSequential(new TurnToHeading(180, true));

        addSequential(new DriveForward(9.735/12));//cube is two inches inside front bumper

        //INTAKE CUBE
        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));
        addParallel(new SetRollers(IntakeSubsystem.INTAKE_SPEED), 4);
        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.CLOSED));
        addSequential(new WaitCommand(1.5));
        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.CLOSED));
        addSequential(new WaitCommand(1.5));
        addSequential(new SetClaw(ClawState.CLOSED));

        addSequential(new DriveForward(-9.735/12));

        addSequential(new ConditionalCommand(new TurnToHeading(90, true), new TurnToHeading(270, true)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });
    }


    // Called just before this Command runs the first time

}
