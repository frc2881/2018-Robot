package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.utils.AmpMonitor;

public class ControlRollers extends Command {

    private AmpMonitor ampMonitor = new AmpMonitor(10.0, () -> Robot.intakeSubsystem.getHighestRollerCurrent());
    private boolean monitoringAmps;

    public ControlRollers() {
        requires(Robot.intakeSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.log("Control Rollers has started");
        monitoringAmps = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double speed = -Robot.oi.manipulator.getY(GenericHID.Hand.kLeft);
        Robot.intakeSubsystem.rollers(speed);
        if (!monitoringAmps && timeSinceInitialized() > 200) {
            ampMonitor.reset();
            monitoringAmps = true;
        }
    }

    @Override
    protected boolean isFinished() {
        if (monitoringAmps && ampMonitor.checkTriggered()){
            Robot.log("Roller current limit exceeded");
            return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.log("Control Rollers has ended");
    }

}

