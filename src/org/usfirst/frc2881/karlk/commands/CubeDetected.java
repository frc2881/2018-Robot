package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Asks intake subsystem if sensor is tripped in front of grasper.
 */
public class CubeDetected extends Command {
    private final boolean ultrasonic;

    public CubeDetected(boolean ultrasonic) {
        requires(Robot.intakeSubsystem);
        this.ultrasonic = ultrasonic;
    }

    @Override
    protected void execute() {
        Robot.intakeSubsystem.cubeDetected(ultrasonic);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.intakeSubsystem.cubeDetected(ultrasonic);
    }

    @Override
    protected void end() {
    }
}
