package org.usfirst.frc2881.karlk.commands;

import org.usfirst.frc2881.karlk.Robot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Swap the perspective of the driver so that the Intake is in the back of the Robot
 */
public class SetIntakeAsBack extends Command {
    public SetIntakeAsBack() {
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveSubsystem.setIntakeLocation(DriveSubsystem.IntakeLocation.BACK);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
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
