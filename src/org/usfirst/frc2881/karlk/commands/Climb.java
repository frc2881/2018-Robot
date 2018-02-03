package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * This command runs the winch that is used for climbing at the end of the game.
 * It is the default command for the ClimbingSubsystem.
 */
public class Climb extends Command {
    public Climb() {
        requires(Robot.climbingSubsystem);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Prints in the driver station
        System.out.println("Climb Command has started");
        double speed = Robot.oi.manipulator.getTriggerAxis(GenericHID.Hand.kLeft);
        Robot.climbingSubsystem.climb(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }
}
