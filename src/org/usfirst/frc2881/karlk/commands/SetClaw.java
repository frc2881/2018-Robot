package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Sets the claw to open or closed.  The claw is
 * on the Arm.
 */
public class SetClaw extends InstantCommand{
private final boolean deploy;

    public SetClaw(boolean deploy) {
        super("SetClaw" + (deploy ? "Down" : "Up"));
        requires(Robot.liftSubsystem);
        this.deploy = deploy;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true/extended
        Robot.liftSubsystem.setClaw(this.deploy);
    }

    @Override
    protected void end() {
        System.out.println("Set Claw has finished");
    }
}
