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

    public TurnToHeading(double angle) {
        requires(Robot.driveSubsystem);
        this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Make a call to the subsystem to use a PID loop controller in the subsystem
        //to set the heading based on the angle passed into the method.
        System.out.println("autonomous turning to " + angle);
        Robot.driveSubsystem.initializeTurnToHeading(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Calls to the subsystem to update the angle if controller value has changed
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
        System.out.println("Turn to Heading has finished");
        //call the drive subsystem to make sure the PID loop is disabled
        Robot.driveSubsystem.endTurnToHeading();
    }

}
