package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;

import java.time.Instant;

import static org.usfirst.frc2881.karlk.RobotMap.driveSubsystemDropOmniPancake;

/**
 * Deploys the omnis to aid in turning
 */
public class DeployOmnis extends InstantCommand {
    private boolean deploy;

    public DeployOmnis(boolean deploy) {
        requires(Robot.driveSubsystem);
        this.deploy = deploy;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true/extended
        Robot.driveSubsystem.dropOmniPancakePiston(this.deploy);
    }

// Called repeatedly when this Command is scheduled to run
}
