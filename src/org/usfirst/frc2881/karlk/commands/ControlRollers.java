package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.utils.AmpMonitor;

public class ControlRollers extends Command {

    private AmpMonitor ampMonitor = new AmpMonitor(10.0, () -> Robot.intakeSubsystem.getHighestRollerCurrent());
    private boolean monitoringAmps;
    private double speedCap;
    private double joystickValue;
    private double programTime;
    private boolean joystickLeft;
    private boolean joystickRight;

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
        if (!ampMonitor.isTriggered() && (programTime == 0 || timeSinceInitialized() > 500 ||
                joystickValue - Robot.oi.manipulator.getY(GenericHID.Hand.kLeft) >= 0.25)) {

            joystickLeft = Math.abs(Robot.oi.manipulator.getY(GenericHID.Hand.kLeft)) <= 0.15 &&
                    Robot.oi.manipulator.getX(GenericHID.Hand.kLeft) <= -0.15;
            joystickRight = Math.abs(Robot.oi.manipulator.getY(GenericHID.Hand.kLeft)) <= 0.15 &&
                    Robot.oi.manipulator.getX(GenericHID.Hand.kLeft) >= 0.15;

            if (joystickLeft || joystickRight) {
                System.out.println("turning");
                double turnSpeed = -Robot.oi.manipulator.getX(GenericHID.Hand.kLeft);
                Robot.intakeSubsystem.turnRollers(turnSpeed);
            }

            else {
                double straightSpeed = Robot.oi.manipulator.getY(GenericHID.Hand.kLeft);
                Robot.intakeSubsystem.rollers(straightSpeed);
                if (straightSpeed < 0){
                    new SetClaw(LiftSubsystem.ClawState.OPEN).start();
                }
            }
        }

        else {
            Robot.intakeSubsystem.rollers(speedCap);
        }

        if (!monitoringAmps && timeSinceInitialized() > 200){
            ampMonitor.reset();
            monitoringAmps = true;
        }

        if (monitoringAmps && ampMonitor.isTriggered()) {
            Robot.log("Roller current limit exceeded");

            speedCap = 0.2;
            joystickValue = Robot.oi.manipulator.getY(GenericHID.Hand.kLeft);
            programTime = timeSinceInitialized();

            if (Math.abs(Robot.oi.manipulator.getX(GenericHID.Hand.kLeft)) <= 0.15 &&
                    Robot.oi.manipulator.getY(GenericHID.Hand.kLeft) > 0) {
                Robot.intakeSubsystem.rollers(-speedCap);
            }

            else if (Math.abs(Robot.oi.manipulator.getX(GenericHID.Hand.kLeft)) <= 0.15 &&
                    Robot.oi.manipulator.getY(GenericHID.Hand.kLeft) < 0 ){
                Robot.intakeSubsystem.rollers(speedCap);
            }

            else if (joystickLeft && Robot.oi.manipulator.getX(GenericHID.Hand.kLeft) < 0 ){
                Robot.intakeSubsystem.turnRollers(speedCap);
            }

            else if (joystickRight && Robot.oi.manipulator.getY(GenericHID.Hand.kLeft) > 0 ){
                Robot.intakeSubsystem.turnRollers(-speedCap);
            }

            new RumbleNo(Robot.oi.manipulator).start();
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

