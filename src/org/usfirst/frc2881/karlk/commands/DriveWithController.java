package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

/* This command tells assigns the joysticks to tank drive.
 * It is the default command for the DriveSubsystem.
 */
public class DriveWithController extends Command {
    public DriveWithController() {
        requires(Robot.driveSubsystem);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //y value is swapped on controller, so we need to add a "-" to adjust for that.
        double left = -Robot.oi.driver.getY(GenericHID.Hand.kLeft);
        double right = -Robot.oi.driver.getY(GenericHID.Hand.kRight);
        Robot.driveSubsystem.tankDrive(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

}
