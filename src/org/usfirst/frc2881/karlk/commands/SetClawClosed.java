package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Sets the claw to open or closed.  The claw is on the Arm.
 */
public class SetClawClosed extends TimedCommand {
    public SetClawClosed() {
        super(.25 + .16);  // wait 250ms for rollers to spin up, 160ms for claw to close before checking cubeInClaw()
        requires(Robot.liftSubsystem);
        requires(Robot.intakeSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.intakeSubsystem.rollers(0.7);
    }

    @Override
    protected void execute() {
        if (timeSinceInitialized() > .25) {
            Robot.liftSubsystem.setClaw(ClawState.CLOSED);
        }
    }

    @Override
    protected void end() {
        Robot.intakeSubsystem.rollers(0);
        if (Robot.liftSubsystem.cubeInClaw()) {
            new RumbleYes(Robot.oi.manipulator).start();
        } else {
            new RumbleNo(Robot.oi.manipulator).start();
        }

        Robot.log("Set Claw Closed has ended.");
    }
}

