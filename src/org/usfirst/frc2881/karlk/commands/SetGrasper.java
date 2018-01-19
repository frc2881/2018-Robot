package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the grasper to open or closed.
 * The grasper is on the robot chassis and never
 * moves.  The claw must be open before the grasper closes.
 */
public class SetGrasper extends Command {
    public SetGrasper() {
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
    }
}
