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
    private double speedCap;

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
        if (!ampMonitor.isTriggered()){
            double speed = -Robot.oi.manipulator.getY(GenericHID.Hand.kLeft);
            Robot.intakeSubsystem.rollers(speed);
        }
        //TODO add monitoring amps cap speed code
        if (!monitoringAmps && timeSinceInitialized() > 200) {
            ampMonitor.reset();
            monitoringAmps = true;
        }

        if (monitoringAmps && ampMonitor.isTriggered()) {
            Robot.log("Roller current limit exceeded");
            speedCap = 0.2;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.log("Control Rollers has ended");
    }

}

