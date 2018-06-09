package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Sets the claw to open or closed.  The claw is on the Arm.
 */
public class SetClaw extends InstantCommand {
    private final ClawState state;

    public SetClaw(ClawState state) {
        super("SetClaw" + (state == ClawState.OPEN ? "Open" : "Closed"));
        requires(Robot.liftSubsystem);
        this.state = state;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true/extended
        Robot.liftSubsystem.setClaw(state);
        if (state == ClawState.OPEN){
            new SetGrasper(IntakeSubsystem.GrasperState.CLOSED);
        }
    }

    @Override
    protected void end() {
        Robot.log("Set Claw has ended: " + state);
    }
}
