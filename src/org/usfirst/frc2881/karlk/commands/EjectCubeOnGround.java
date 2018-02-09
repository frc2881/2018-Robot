package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class EjectCubeOnGround extends CommandGroup {
    public EjectCubeOnGround() {
        /*
        1) Release Claw
        2)setRollers
        3) Release Grasper
        4)Please add on... or correct...
         */
        addSequential(new SetClaw(false));
        addSequential(new setRollers());
        addSequential(new SetGrasper(false));
        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT));
        addSequential(new RumbleJoysticks());
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.print("Cube Eject has ended");
    }
}
