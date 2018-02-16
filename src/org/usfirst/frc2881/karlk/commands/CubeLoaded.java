package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.utils.AmpMonitor;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;

/**
 * Asks intake subsystem if sensor is tripped in front of grasper.
 */
public class CubeLoaded extends Command {

    public CubeLoaded() {
        requires(Robot.intakeSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        //start the intake rollers
        Robot.intakeSubsystem.stopRollers();
        Robot.intakeSubsystem.loadCube();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.intakeSubsystem.cubeLoaded();
    }

    @Override
    protected void end() {
        Robot.intakeSubsystem.stopRollers();
    }
}
