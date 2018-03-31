package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem.OmnisState;

/**
 * Deploys the omnis to aid in turning
 */
public class DeployOmnis extends InstantCommand {
    private final OmnisState state;

    public DeployOmnis(OmnisState state) {
        super("DeployOmnis" + (state == OmnisState.DOWN ? "Down" : "Up"));
        requires(Robot.driveSubsystem);
        this.state = state;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true/extended
        Robot.driveSubsystem.dropOmniPancakePiston(state);
        Robot.log("Omni wheels are " + (state == OmnisState.DOWN ? "Down" : "Up"));
    }

}
