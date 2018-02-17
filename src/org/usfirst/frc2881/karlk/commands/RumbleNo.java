package org.usfirst.frc2881.karlk.commands;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc2881.karlk.Robot;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.usfirst.frc2881.karlk.RobotMap;


public class RumbleNo extends TimedCommand {
    private final XboxController controller;

    public RumbleNo(XboxController controller) {
        super(.6);
        requires(Robot.lightsSubsystem);
        this.controller = controller;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        controller.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double time = timeSinceInitialized();

        RobotMap.otherFancyLights.set(Robot.lightsSubsystem.orange);
        if(time <= 0.2) {
            controller.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
        }
        else if(time > 0.2 && time < 0.4) {
            controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        }
        else if(time > 0.4 && time <= 0.6) {
            controller.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
        }
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
    }
}

