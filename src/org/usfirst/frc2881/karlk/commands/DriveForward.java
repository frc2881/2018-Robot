package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Move the robot forward the specified amount
 */
public class DriveForward extends Command {
    private final double distance;

    public DriveForward(double distance) {
        requires(Robot.driveSubsystem);
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Make a call to the subsystem to use a PID loop controller in the subsystem
        //to set the heading based on the angle passed into the method.
        System.out.println("autonomous turning to " + distance);
        Robot.driveSubsystem.initializeDriveForward(distance);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Calls to the subsystem to update the angle if controller value has changed
        Robot.driveSubsystem.rotate(Robot.driveSubsystem.getStraightSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //asking the PID loop have we reached our position
        return Robot.driveSubsystem.isFinishedDriveForward();
}

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //call the drive subsystem to make sure the PID loop is disabled
        Robot.driveSubsystem.endDriveForward();
    }
}
