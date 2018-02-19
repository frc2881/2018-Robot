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
public class SwitchStartRSwitchR extends CommandGroup {
    private final DriveSubsystem.SwitchPosition side;

    public SwitchStartRSwitchR(DriveSubsystem.SwitchPosition side){
        this.side = side;

        //Start at (-115.5, 25)
        //Turn East (90°)
        //Go forwards (-28.75, 83)
        //Lines up with middle of plate
        //Turn north (90°)
        //Go forwards (-28.75, 121)
        //Lift arm to switch as well
        //Front bumper lined up with plate
        //Release claw

        //Start at (-115.5, 25)
        //Run RobotPrep
        //Go forwards 64” (-115.5, 83)
        //Middle of corridor
        //Turn East (90°)
        //Go forwards (89.375, 83)
        //Middle of side corridor
        //Turn north (90°)
        //Go forwards (89.375, 168)
        //Lines up with middle of side of switch
        //Turn west (90°)
        //Go forwards (65.75, 168)
        //Lift arm to switch as well
        //Front bumper lined up with plate
        addSequential(new ConditionalCommand(new DriveForward(85 / 12)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.SIDE;
            }
        });
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(270));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));
        addParallel(new ConditionalCommand(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.SIDE;
            }
        });
        addSequential(new ConditionalCommand(new DriveForward(86.75/ 12), new DriveForward(49.75/12)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });
        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.DOWN)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });
        addSequential(new ConditionalCommand(new TurnToHeading(0)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });
        addSequential(new ConditionalCommand(new DeployOmnis(DriveSubsystem.OmnisState.UP)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });
        addParallel(new ConditionalCommand(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.SwitchPosition.FRONT;
            }
        });
        addSequential(new ConditionalCommand(new DriveForward(38/12)) {
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
