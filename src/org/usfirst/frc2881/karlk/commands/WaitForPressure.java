package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * This command should only run for the end of the game.
 * Extends the arm used for climbing on the tower.
 * It uses the climbing subsystem, not the lift subsystem, because it only needs one, and the climbing subsystem
 * already has its solenoid in it
 */
public class WaitForPressure extends Command {

    @Override
    protected void initialize() {
        Robot.log("WaitForPressure has started");
    }

    @Override
    protected boolean isFinished() {
        // Don't trust pressure readings *right* after the robot starts..?
        return timeSinceInitialized() > 0.2 && Robot.compressorSubsystem.hasEnoughPressureForArmDeploy();
    }

    // Called just before this Command runs the first time
    @Override
    protected void end() {
        Robot.log("WaitForPressure has finished.");
    }

}
