package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Deploys the omnis to aid in turning
 */
public class ExtendRollers extends InstantCommand {

    private boolean state; //true is extended, false is retracted

    public ExtendRollers() {
        requires(Robot.intakeSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true/extended
        Robot.intakeSubsystem.setRollerExtensionPiston(state);

        state = !Robot.intakeSubsystem.getRollerState();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.log("Rollers are " + (state ? "extended" : "retracted"));
    }

}
