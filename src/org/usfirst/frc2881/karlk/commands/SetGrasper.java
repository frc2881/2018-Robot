package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem.GrasperState;

/**
 * Sets the grasper to open or closed.
 * The grasper is on the robot chassis and never
 * moves.  The claw must be open before the grasper closes.
 */
public class SetGrasper extends InstantCommand {
    private final GrasperState state;

    public SetGrasper(GrasperState state) {
        super("SetGrasper" + (state == GrasperState.OPEN ? "Open" : "Closed"));
       // requires(Robot.intakeSubsystem); We don't need this because we need the rollers to run at the same time as the grasper.
        this.state = state;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true/extended
        Robot.intakeSubsystem.setGrasper(state);
    }

    @Override
    protected void end() {
        System.out.println("Set Graspers has ended: " + state);
    }
}
