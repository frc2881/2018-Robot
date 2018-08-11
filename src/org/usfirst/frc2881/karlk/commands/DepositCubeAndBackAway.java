package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Releases the claw, lifts the arm up away from the cube and drives the robot back
 * so that a cube is neatly deposited and the robot reset for moving.
 * We don't move arm down here in case the manipulator wants to use the arm
 * to nudge the cube.
 */
public class DepositCubeAndBackAway extends CommandGroup {
    public DepositCubeAndBackAway() {
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
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
        Robot.log("Deposit Cube and back away has finished");
    }
}
