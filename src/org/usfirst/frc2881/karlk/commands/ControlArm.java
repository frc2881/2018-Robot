package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;

/**
 * This command runs the arm.
 * It is the default command for the LiftSubsystem.
 */
public class ControlArm extends Command {
    public ControlArm() {
        requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.liftSubsystem.liftSafety();
    }
    // TODO maybe set limits for this later??


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

