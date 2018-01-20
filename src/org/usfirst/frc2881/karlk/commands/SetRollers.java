package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/**
 *
 */
public class SetRollers extends Command {
    private boolean roll;

    public SetRollers (boolean roll){
        requires (Robot.driveSubsystem);
        this.roll = roll;
    }
    public SetRollers() {
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.intakeSubsystem.(roll);



       // private final SpeedController intakeRollerLeft = add(RobotMap.intakeSubsystemIntakeRollerLeft);
       // private final SpeedController intakeRollerRight = add(RobotMap.intakeSubsystemIntakeRollerRight);


        //public class DeployOmnis extends InstantCommand {
        //    private boolean deploy;
        //
        //    public DeployOmnis(boolean deploy) {
        //        requires(Robot.driveSubsystem);
        //        this.deploy = deploy;
        //    }
    }

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
