package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2881.karlk.Robot;

//Rumbles the Driver controller at the beginning of the match
public class RumbleDriver extends CommandGroup {
    public RumbleDriver() {

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("RumbleDriver initialize");
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kLeftRumble, .7);
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

        if (Math.abs(leftY) > 0.05) return true;
        if (Math.abs(rightY) > 0.05) return true;
        if (Math.abs(leftX) > 0.05) return true;
        if (Math.abs(rightX) > 0.05) return true;
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("RumbleDriver end");
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
    }
}
