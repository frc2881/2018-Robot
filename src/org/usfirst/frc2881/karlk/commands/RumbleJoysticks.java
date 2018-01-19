package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Rumbles the joysticks of the controller passed in as an argument.
 */
public class RumbleJoysticks extends Command {
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // Rumbles things!!
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kRightRumble, .7);
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kLeftRumble, .7);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        // Stops de rumbles!!
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kRightRumble, 0);
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
    }
}
