package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * When the command is called, change the driver subsystem to run
 * in high gear, when button is released will reset to low gear.
 */
public class DriveInLowGear extends Command {
    public DriveInLowGear() {
        requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("Low Gear has Started");
        //Turn the piston to true to set it to low gear
        Robot.driveSubsystem.lowGear();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double left = -Robot.oi.driver.getY(GenericHID.Hand.kLeft);
        double right = -Robot.oi.driver.getY(GenericHID.Hand.kRight);
        Robot.driveSubsystem.tankDrive(left, right, true);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("Low Gear has Finished");
    }
}
