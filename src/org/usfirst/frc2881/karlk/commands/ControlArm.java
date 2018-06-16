package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.OI;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

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
        Robot.log("Control arm has started");

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double speed = -Robot.oi.manipulator.getY(GenericHID.Hand.kRight);
        Robot.liftSubsystem.setArmMotorSpeed(OI.squareInput(OI.applyDeadband(speed)));
        if (-Robot.oi.manipulator.getY(GenericHID.Hand.kRight) > 0.35){
            new SetGrasper(IntakeSubsystem.GrasperState.OPEN).start();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.log("Control arm has ended");
    }

}

