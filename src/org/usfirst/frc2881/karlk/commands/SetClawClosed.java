package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Sets the claw to open or closed.  The claw is on the Arm.
 */
public class SetClawClosed extends TimedCommand {
    public SetClawClosed() {
        super(.16);
        requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true/extended
        Robot.liftSubsystem.setClaw(ClawState.CLOSED);
    }

    @Override
    protected void end() {
        if (Robot.liftSubsystem.cubeInClaw()) {
            new RumbleYes(Robot.oi.manipulator).start();
        } else {
            new RumbleNo(Robot.oi.manipulator).start();
        }

        System.out.println("Set Claw Closed has ended.");
    }
}

