package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

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
        return ((Robot.liftSubsystem.isLimitSwitchTriggered() || Robot.liftSubsystem.isSpeedReallySmall()) &&
                Robot.liftSubsystem.getTimer() >= 1);

    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.liftSubsystem.resetArmEncoder();
    }

}

