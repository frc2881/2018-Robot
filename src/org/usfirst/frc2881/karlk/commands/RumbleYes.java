package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;


public class RumbleYes extends TimedCommand {

    private final XboxController controller;

    public RumbleYes(XboxController controller) {
        super(.1);
        requires(Robot.lightsSubsystem);
        this.controller = controller;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        controller.setRumble(GenericHID.RumbleType.kRightRumble, 1);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        RobotMap.otherFancyLights.set(Robot.lightsSubsystem.green);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        controller.setRumble(GenericHID.RumbleType.kRightRumble, 0);
    }
}
