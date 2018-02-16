package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Rumbles the joysticks of the controller passed in as an argument.
 */

public class RumbleJoysticks extends TimedCommand {
    public RumbleJoysticks() {
        super(.75);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("Rumbling Joysticks has started");
        // Rumble things CODE
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kRightRumble, .7);
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kLeftRumble, .7);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        // Stop de rumbles CODE
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kRightRumble, 0);
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        System.out.println("Rumbling Joysticks has finished");
    }
}
