package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Read the current heading from the NavX and
 * turn the robot to match the NavX heading ...
 * details of how to implement are here:
 * https://github.com/kauailabs/navxmxp/blob/master/roborio/java/navXMXP_Java_RotateToAngle_Tank/src/org/usfirst/frc/team2465/robot/Robot.java
 */
public class TurnToPointOfView extends Command {

    private boolean omnis;

    public TurnToPointOfView() {
        requires(Robot.driveSubsystem);
        this.omnis = Robot.driveSubsystem.getOmnisState();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        int angle = getDriverPOVAngle();
        System.out.println("Turn to POV has started: " + angle);
        //Make a call to the subsystem to use a PID loop controller in the subsystem
        //to set the heading based on the HAT controller.
        if (omnis){
            Robot.driveSubsystem.initializeTurnToHeadingOmnis(angle);
        }
        else{Robot.driveSubsystem.initializeTurnToHeading(angle);}
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Calls to the subsystem to update the angle if controller value has changed
        double rotateToAngleRate = Robot.driveSubsystem.getRotateToAngleRate();
        Robot.driveSubsystem.autonomousRotate(rotateToAngleRate, -rotateToAngleRate);
        if (omnis) {
            Robot.driveSubsystem.changeHeadingTurnToHeadingOmnis(getDriverPOVAngle());
        }
        else{Robot.driveSubsystem.changeHeadingTurnToHeading(getDriverPOVAngle());}
    }

    //returns an integer angle based on what the driver controller reads
    private int getDriverPOVAngle() {
        int angle = Robot.oi.driver.getPOV();
        if (angle > 180) {
            angle = angle - 360;
        }
        return angle;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //asking the pid loop have we reached our position
        if (omnis){
            return Robot.driveSubsystem.isFinishedTurnToHeadingOmnis();
        }
        else{return Robot.driveSubsystem.isFinishedTurnToHeading();}
    }

    @Override
    protected void interrupted() {
        //call the drive subsystem to make sure the PID loop is disabled
        if (omnis){
            Robot.driveSubsystem.endTurnToHeadingOmnis();
        }
        else {Robot.driveSubsystem.endTurnToHeading();}
        System.out.println("Turn to POV was interrupted");
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //call the drive subsystem to make sure the PID loop is disabled
        if (omnis){
            Robot.driveSubsystem.endTurnToHeadingOmnis();
        }
        else {Robot.driveSubsystem.endTurnToHeading();}
        new RumbleYes(Robot.oi.driver).start();
        System.out.println("Turn to POV has finished");
    }

}
