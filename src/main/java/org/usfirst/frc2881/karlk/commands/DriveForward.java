package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Move the robot forward the specified amount
 */
public class DriveForward extends Command {
    private double speedSole = 0.58;
    private double distance;

    public DriveForward(double distance) {
        super(computeTimeout(distance));
        requires(Robot.driveSubsystem);
        this.distance = distance;
    }

    private static double computeTimeout(double distance) {
        //Give the robot 1 second to get started then assume it travels at 3 ft/second (in practice it's faster)
        return 1.0 + (Math.abs(distance) / 3.0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Make a call to the subsystem to use a PID loop controller in the subsystem
        //to set the heading based on the angle passed into the method.
        Robot.log("Autonomous driving " + distance + " ft: " + Robot.driveSubsystem.getLocation());
        Robot.driveSubsystem.initializeDriveForward(distance, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Calls to the subsystem to update the angle if controller value has changed
        double speed = Robot.driveSubsystem.getStraightSpeed();

        //So DriveForward won't time out (it goes backwards fast enough to make a difference)
        if(Math.abs(speed) < speedSole) {
            speed = Math.copySign(speedSole, speed);
        }

        double rotate = Robot.driveSubsystem.getRotateToAngleRate();
        Robot.driveSubsystem.autonomousArcadeDrive(speed, rotate);
        //Robot.driveSubsystem.arcadeDrive(speed,speed);
        //Robot.log("set speed to " + speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (isTimedOut()) {
            Robot.log("Drive forward timed out:" + Robot.driveSubsystem.getLocation());
            return true;
        }
        //asking the PID loop have we reached our position
        return Robot.driveSubsystem.isFinishedDriveForward();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //call the drive subsystem to make sure the PID loop is disabled
        Robot.driveSubsystem.endDriveForward();
        Robot.log("Drive Forward has ended: " + Robot.driveSubsystem.getLocation());
    }

    //This method allows us to make changes to the property this.distance in Shuffleboard
    //It is called automatically when you call SmartDashboard.putData() in OI.java.
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Drive Forward Distance", () -> distance, (distance) -> this.distance = distance);
    }
}
