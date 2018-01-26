package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 *
 */
public class SetRollers extends Command {
    private boolean roll;

    public SetRollers(boolean roll) {
        requires(Robot.intakeSubsystem);
        this.roll = roll;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.intakeSubsystem.rollers(roll);


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
        Robot.intakeSubsystem.stopRollers();
    }
}
