package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 * This command should only run for the end of the game.
 * Extends the arm used for climbing on the tower.
 * It uses the climbing subsystem, not the lift subsystem, because it only needs one, and the climbing subsystem
 * already has its solenoid in it
 */
public class ArmAssistDeploy extends Command {
    private boolean deploy;

    public ArmAssistDeploy(boolean deploy) {
        super("ArmAssistDeploy" + (deploy ? "Extended" : "Retracted"));
        requires(Robot.liftSubsystem);
        this.deploy = deploy;
    }

    @Override
    protected void initialize() {
        Robot.log("Arm Assist Deploy has started");
    }

    @Override
    protected boolean isFinished() {
        // Don't trust pressure readings *right* after the robot starts..?
        return timeSinceInitialized() > 0.2 && Robot.compressorSubsystem.hasEnoughPressureForArmDeploy();
    }

    // Called just before this Command runs the first time
    @Override
    protected void end() {
        //turning the piston to true as soon as the 'button' is pressed
        Robot.liftSubsystem.armAssistDeploy(deploy);
        Robot.log("Arm Assist Deploy has ended: " + deploy);
    }

}
