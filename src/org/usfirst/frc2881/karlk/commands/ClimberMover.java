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

    private final boolean forward;
    private boolean done;

    public ClimberMover(boolean forward) {
        requires(Robot.climbingSubsystem);
        this.forward = forward;
    }

    @Override
    protected void initialize() {
        //Prints in the driver station
        Robot.climbingSubsystem.resetCurrentMovingAverage();
        Robot.log("Climb Moving Command has started");
        done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (!done) {
            Robot.climbingSubsystem.moveClimber(forward);
            if (Robot.climbingSubsystem.isMoveClimberFinished()){
                new RumbleYes(Robot.oi.manipulator).start();
                done = true;
            }
        }
        else {
            Robot.climbingSubsystem.stopClimber();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        //Prints in the driver station
        Robot.climbingSubsystem.stopClimber();
        Robot.log("Climb Command has finished");
    }
}
