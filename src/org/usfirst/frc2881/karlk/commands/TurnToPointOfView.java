package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Read the current heading from the NavX and
 * turn the robot to match the NavX heading ...
 * details of how to implement are here:
 * https://github.com/kauailabs/navxmxp/blob/master/roborio/java/navXMXP_Java_RotateToAngle_Tank/src/org/usfirst/frc/team2465/robot/Robot.java
 */
public class TurnToPointOfView extends Command {
    public TurnToPointOfView() {
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
    }
}
