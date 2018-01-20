package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command should only run for the end of the game.
 * Extends the arm used for climbing on the tower.
 */
public class LiftArmForClimbing extends Command {
    public LiftArmForClimbing() {
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }/// we have recognized the button for this, but that is all... the command and subsystem still needs to be done.
    //ask mentor which subsystem to use

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }


}
