package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.utils.AmpMonitor;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;

/**
 * Asks intake subsystem if sensor is tripped in front of grasper.
 */
public class CubeLoaded extends Command {
    private static final double TURN_OFF_CURRENT = 6.0; //amps

    private final AmpMonitor ampMonitor = new AmpMonitor(TURN_OFF_CURRENT, Robot.intakeSubsystem::getMotorCurrent);
    private boolean monitoringAmps;
    private double delay;

    public CubeLoaded() {
        requires(Robot.intakeSubsystem);
        delay = .200;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.intakeSubsystem.resetTimer();
        monitoringAmps = false;
    }

    @Override
    protected void execute() {
        //start the intake rollers
        Robot.intakeSubsystem.rollers(Robot.intakeSubsystem.EJECT_SPEED);
        // Let the rollers get started then start monitoring the current used by the motor.
        if (!monitoringAmps && Robot.intakeSubsystem.getTimer() >= delay) {
            ampMonitor.reset();
            monitoringAmps = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return monitoringAmps && ampMonitor.checkTriggered();
    }

    @Override
    protected void end() {
        Robot.intakeSubsystem.rollers(0);
    }
}
