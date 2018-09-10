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

    private boolean testMode;
    private double leftDistance;
    private double rightDistance;
    private int i;

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //y value is swapped on controller, so we need to add a "-" to adjust for that.
        if (Robot.oi.driver.getPOV() == -1 && Robot.oi.manipulator.getPOV() == -1){
            testMode = false;
            i = 0;
        }

        if ((Robot.oi.driver.getPOV() == 0 || Robot.oi.manipulator.getPOV() == 0)) {
            boolean success = false;

            if (!testMode){
                leftDistance = Robot.driveSubsystem.getLeftDistance();
                rightDistance = Robot.driveSubsystem.getRightDistance();
                i = 0;
                Robot.log("Currently in test mode");
                testMode = true;
            }

            if (Math.abs(Robot.oi.driver.getY(GenericHID.Hand.kLeft)) < 0.06 &&
                    Math.abs(Robot.oi.driver.getY(GenericHID.Hand.kRight)) < 0.06) {
                i = 0;
                leftDistance = Robot.driveSubsystem.getLeftDistance();
                rightDistance = Robot.driveSubsystem.getRightDistance();
            }

            while (i < 25 && testMode) {
                i++;
            }

            if(i > 25) {
                success = Robot.driveSubsystem.getLeftDistance() - leftDistance > 0.1 &&
                        -Robot.oi.driver.getY(GenericHID.Hand.kLeft) > 0.06 ||
                        Robot.driveSubsystem.getLeftDistance() - leftDistance < -0.1 &&
                                -Robot.oi.driver.getY(GenericHID.Hand.kLeft) < -0.06 ||

                        Robot.driveSubsystem.getRightDistance() - rightDistance > 0.1 &&
                                -Robot.oi.driver.getY(GenericHID.Hand.kRight) > 0.06 ||
                        Robot.driveSubsystem.getRightDistance() - rightDistance < -0.1 &&
                                -Robot.oi.driver.getY(GenericHID.Hand.kRight) < -0.06;
            }

            if (success){
                new RumbleYes(Robot.oi.driver).start();
                new RumbleYes(Robot.oi.manipulator).start();
            }
            else {
                Robot.log("Error: ¡ENCHUFA EL CODIFICADOR! (plug in the encoder)");
            }

            i++;
        }


        double left = -Robot.oi.driver.getY(GenericHID.Hand.kLeft);
        double right = -Robot.oi.driver.getY(GenericHID.Hand.kRight);
        Robot.driveSubsystem.tankDrive(left, right, false);



    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.log("Tele-op tank drive has ended");
    }
}
