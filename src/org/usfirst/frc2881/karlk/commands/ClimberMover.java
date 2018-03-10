package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.OI;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.utils.AmpMonitor;

/**
 * This command runs the winch that is used for climbing at the end of the game.
 * It is the default command for the ClimbingSubsystem.
 */
public class ClimberMover extends Command {

    boolean forward;

    public ClimberMover(boolean forward) {
        requires(Robot.climbingSubsystem);
        this.forward = forward;
    }

    @Override
    protected void initialize() {
        //Prints in the driver station
        System.out.println("Climb Moving Command has started");

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.climbingSubsystem.moveClimber(forward);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.climbingSubsystem.isMoveClimberFinished();
    }

    @Override
    protected void end() {
        //Prints in the driver station
        Robot.climbingSubsystem.stopClimber();
        System.out.println("Climb Command has finished");
    }
}
