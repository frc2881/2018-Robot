package org.usfirst.frc2881.karlk.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Asks intake subsystem if sensor is tripped in front of grasper.
 */
public class CubeLoaded extends Command{

    public CubeLoaded() {requires(Robot.intakeSubsystem); }

    @Override
    protected void execute() {
        if (Robot.intakeSubsystem.cubeDetected()){
            this.isFinished();
        }
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
