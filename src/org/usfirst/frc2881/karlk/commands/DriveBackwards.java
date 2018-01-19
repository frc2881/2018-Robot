package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Move the robot backward the specified amount
 * this class doesn't match the specs, need to work on it.
 */

public class DriveBackwards extends Command {
    public DriveBackwards() {
        requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double left = Robot.oi.driver.getY(GenericHID.Hand.kLeft) * -1;
        double right = Robot.oi.driver.getY(GenericHID.Hand.kRight) * -1;
        Robot.driveSubsystem.tankDrive(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }


}
