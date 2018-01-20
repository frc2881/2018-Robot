package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;

import static org.usfirst.frc2881.karlk.RobotMap.climbingSubsystemExtender;

/**
 * This command should only run for the end of the game.
 * Extends the arm used for climbing on the tower.
 * it uses the climbing subsystem, not the lift subsystem, because eit only needs one, and the climbing subsystem already has its solenoid in it
 */

public class LiftArmForClimbing extends InstantCommand {
    private boolean deploy;

    public LiftArmForClimbing(boolean deploy) {
        requires(Robot.climbingSubsystem);
        this.deploy = deploy;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        DriverStation.reportWarning("lift climbing piston is running " + this.deploy, false);

        //turning the piston to true as soon as the 'button' is pressed
        Robot.climbingSubsystem.liftArmForClimbing(this.deploy);
        //for testing: set to false
        Robot.climbingSubsystem.releaseArmForClimbing(this.deploy);

    }

}
