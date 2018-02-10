package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the grasper to open or closed.
 * The grasper is on the robot chassis and never
 * moves.  The claw must be open before the grasper closes.
 */
public class SetGrasper extends InstantCommand {
    private final boolean deploy;

    public SetGrasper(boolean deploy) {
        super("SetGrasper" + (deploy ? "Open" : "Close"));
        requires(Robot.intakeSubsystem);
        this.deploy = deploy;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true/extended
        Robot.intakeSubsystem.setGrasper(this.deploy);
    }

    @Override
    protected void end() {
        System.out.println("Graspers has finished");
    }
}
