package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Asks intake subsystem if sensor is tripped in front of grasper.
 */
public class CubeDetected extends Command {

    public CubeDetected() {
        requires(Robot.intakeSubsystem);
    }

    @Override
    protected void execute() {
        Robot.intakeSubsystem.cubeDetected();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.intakeSubsystem.cubeDetected();
    }

    @Override
    protected void end() {
    }
}
