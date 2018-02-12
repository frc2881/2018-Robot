package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;

/**
 * This command runs the arm.
 * It is the default command for the LiftSubsystem.
 */
public class CalibrateArmEncoder extends Command {
    public CalibrateArmEncoder() {
        requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.liftSubsystem.startTimer();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //sets motor speed to -0.3
        Robot.liftSubsystem.setMotorForCalibration();
    }

    @Override
    protected boolean isFinished() {
        if (Robot.liftSubsystem.getTimer() < 1) {
            return false;
        }
        if (Robot.liftSubsystem.isLimitSwitchTriggered()) {
            System.out.println("Limit switch ended calibration");
            return true;
        }
        else if (Robot.liftSubsystem.isSpeedReallySmall()) {
            System.out.println("Speed ended calibration: " + RobotMap.liftSubsystemArmEncoder.getRate());
            return true;
        }
        return false;

    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.liftSubsystem.resetArmEncoder();
    }

}

