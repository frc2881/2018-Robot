package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.OI;
import org.usfirst.frc2881.karlk.Robot;

import java.util.function.Supplier;

/**
 * Asks intake subsystem if sensor is tripped in front of grasper.
 */
public class WaitUntilCubeDetected extends Command {

    private final Supplier<OI.TriggerButtons> function;

    public WaitUntilCubeDetected(Supplier<OI.TriggerButtons> function) {
        this.function = function;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (Robot.intakeSubsystem.cubeDetected() || function.get() == OI.TriggerButtons.INTAKE_CUBE_OVERRIDE);
    }

    @Override
    protected void end() {
    }
}
