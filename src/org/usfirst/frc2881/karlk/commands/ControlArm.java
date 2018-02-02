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
        double speed = -Robot.oi.manipulator.getY(GenericHID.Hand.kRight);
        double distance = RobotMap.liftSubsystemArmEncoder.getDistance();
        double min = -1;
        double max = 1;
        if (distance <= 0) {
            min = 0;
            max = 1;
        } else if (distance >= 7) {
            min = -1;
            max = 0;
        } else if (distance <= 2) {
            min = distance * (-.4) - .2;
        } else if (distance >= 5) {
            max = distance * (-.4) + 3;
        } else {
            min = -1;
            max = 1;
        }
        if (speed < min) {
            speed = min;
        }
        if (speed > max) {
            speed = max;
        }
        Robot.liftSubsystem.armControl(speed);
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

