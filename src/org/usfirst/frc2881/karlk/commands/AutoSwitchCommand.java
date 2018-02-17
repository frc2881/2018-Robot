package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem.GrasperState;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoSwitchCommand extends CommandGroup {

    private final DriveSubsystem.StartingLocation start;
    private final DriveSubsystem.SwitchPosition side;

    public AutoSwitchCommand(DriveSubsystem.StartingLocation start , DriveSubsystem.SwitchPosition side) {
        super("AutoSwitch" + start + "position" + side + "of switch");
        this.start = start;
        this.side = side;

        addSequential(new SetClaw(ClawState.CLOSED));
        addSequential(new DriveForward(64/12 - 1.5));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(55));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));
        addSequential(new DriveForward(114.25/12));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(22));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));
        addParallel(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false));
        addSequential(new DriveForward(40/12));
        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
