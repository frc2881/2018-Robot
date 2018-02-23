package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartCSwitchR;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartLSwitchL;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartLSwitchR;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartRSwitchL;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartRSwitchR;
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
public class ScaleSwitchR extends CommandGroup {
    private final String gameData;
    private final DriveSubsystem.SwitchPosition side;
    private final DriveSubsystem.StartingLocation start;

    public ScaleSwitchR(String gameData, DriveSubsystem.SwitchPosition side, DriveSubsystem.StartingLocation start){
        this.gameData = gameData;
        this.side = side;
        this.start = start;

        addSequential(new ConditionalCommand(new SwitchStartCSwitchR(side)) {
            @Override
            protected boolean condition() {
                return start == DriveSubsystem.StartingLocation.CENTER;
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartRSwitchR(side)) {
            @Override
            protected boolean condition() {
                return start == DriveSubsystem.StartingLocation.RIGHT;
            }
        });

        addSequential(new ConditionalCommand(new SwitchStartLSwitchR(side)) {
            @Override
            protected boolean condition() {
                return start == DriveSubsystem.StartingLocation.LEFT;
            }
        });

        addParallel(new LiftToHeight(0, false));

        addSequential(new ConditionalCommand(new DriveForward(-38/12), new DriveForward(-26.125/12)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });

        addSequential(new ConditionalCommand(new TurnToHeading(90)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });

        addSequential(new ConditionalCommand(new DriveForward(60.675/12)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });

        addSequential(new TurnToHeading(0));

        addSequential(new ConditionalCommand(new DriveForward(145.735/12), new DriveForward(60.735/12)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });// (89.375, 228.735)

        addSequential(new TurnToHeading(270));

        addSequential(new DriveForward(49.125/12));//(40.25)

        addParallel(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));

        addSequential(new TurnToHeading(180));

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

        addSequential(new ConditionalCommand(new TurnToHeading(90), new TurnToHeading(270)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new ConditionalCommand(new DriveForward(136.465/12), new DriveForward(55.965/12)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new TurnToHeading(0));

        addSequential(new DriveForward(95.265/12)); //(324)

        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));

        addParallel(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, false));

        addSequential(new ConditionalCommand(new TurnToHeading(270), new TurnToHeading(90)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new DriveForward(38.785/12)); // goes an inch under the scale

        addSequential(new SetClaw(ClawState.OPEN));

        addSequential(new DriveForward(-38.785/12));

        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, false));
    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
