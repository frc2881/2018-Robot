package org.usfirst.frc2881.karlk.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.utils.AmpMonitor;

/**
 * Asks intake subsystem if sensor is tripped in front of grasper.
 */
public class CubeDetected extends Command{
    private static final double TURN_OFF_CURRENT = 18.5; //amps

    private final AmpMonitor ampMonitor = new AmpMonitor(TURN_OFF_CURRENT, Robot.intakeSubsystem::getMotorCurrent);

    public CubeDetected() {requires(Robot.intakeSubsystem); }

    @Override
    protected void execute() {

    }
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }
    @Override
    protected void end() {
    }
}
