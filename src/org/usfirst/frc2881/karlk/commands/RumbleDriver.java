package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.OI;
import org.usfirst.frc2881.karlk.Robot;

//Rumbles the Driver controller at the beginning of the match
public class RumbleDriver extends Command {

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("RumbleDriver has started");
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kRightRumble, .7);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        double leftY = -Robot.oi.driver.getY(GenericHID.Hand.kLeft);
        double leftX = -Robot.oi.driver.getX(GenericHID.Hand.kLeft);
        double rightY = -Robot.oi.driver.getY(GenericHID.Hand.kRight);
        double rightX = -Robot.oi.driver.getX(GenericHID.Hand.kRight);

        if (Math.abs(leftY) > OI.DEADBAND) return true;
        if (Math.abs(rightY) > OI.DEADBAND) return true;
        if (Math.abs(leftX) > OI.DEADBAND) return true;
        if (Math.abs(rightX) > OI.DEADBAND) return true;
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kRightRumble, 0);
        System.out.println("RumbleDriver has finished");
    }
}
