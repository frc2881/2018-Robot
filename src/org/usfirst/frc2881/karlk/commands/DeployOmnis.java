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

    private final boolean deploy;

    public DeployOmnis(boolean deploy) {
        super("DeployOmnis" + (deploy ? "Down" : "Up"));
        requires(Robot.driveSubsystem);
        this.deploy = deploy;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true/extended
        Robot.driveSubsystem.dropOmniPancakePiston(this.deploy);
        System.out.println("Omni wheels have deployed");
    }

// Called repeatedly when this Command is scheduled to run
}
