package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Read the current heading from the NavX and
 * turn the robot to match the NavX heading ...
 * details of how to implement are here:
 * https://github.com/kauailabs/navxmxp/blob/master/roborio/java/navXMXP_Java_RotateToAngle_Tank/src/org/usfirst/frc/team2465/robot/Robot.java
 */
public class TurnToHeading extends Command {

    private final double angle;
    private final boolean omnis;
    private double direction;

    public TurnToHeading(double angle, boolean omnis) {
        requires(Robot.driveSubsystem);
        if (angle>180){
            angle -= 360;
        }
        this.angle = angle;
        this.omnis = omnis;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Make a call to the subsystem to use a PID loop controller in the subsystem
        //to set the heading based on the angle passed into the method.
        Robot.log("Turn to Heading " + angle + " has started: " + Robot.driveSubsystem.getLocation());

            Robot.driveSubsystem.initializeTurnToHeading(angle);
            direction = Robot.driveSubsystem.getTurnToHeadingError();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Calls to the subsystem to update the angle if controller value has changed
        double rotateToAngleRate = Robot.driveSubsystem.getRotateToAngleRate();
        if (direction >= 0) {
            Robot.driveSubsystem.autonomousRotate(rotateToAngleRate, 0);
        } else {
            Robot.driveSubsystem.autonomousRotate(0, -rotateToAngleRate);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //asking the pid loop have we reached our position
        return Robot.driveSubsystem.isFinishedTurnToHeading();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //call the drive subsystem to make sure the PID loop is disabled

             Robot.driveSubsystem.endTurnToHeading();

        Robot.log("Turn to Heading has finished: " + Robot.driveSubsystem.getLocation());
    }

}
