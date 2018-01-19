package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Automatically moves the arm to the level of the high scale.
 * We are using the PID loop capability in the subsystem rather
 * than using a PID command.
 */
public class LiftToHighScale extends Command {
    public LiftToHighScale() {
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
