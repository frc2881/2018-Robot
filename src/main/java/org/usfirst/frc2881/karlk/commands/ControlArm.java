package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.OI;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;

/**
 * This command runs the arm.
 * It is the default command for the LiftSubsystem.
 */
public class ControlArm extends Command {
    public ControlArm() {
        requires(Robot.liftSubsystem);
    }

    private boolean testMode;
    private double armHeight;
    private int i;

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.log("Control arm has started");

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.oi.driver.getPOV() == -1 && Robot.oi.manipulator.getPOV() == -1){
            testMode = false;
            i = 0;
        }

        if ((Robot.oi.driver.getPOV() == 0 || Robot.oi.manipulator.getPOV() == 0)) {
            boolean success = false;

            if (!testMode){
                armHeight = Robot.liftSubsystem.getArmHeight();
                i = 0;
                Robot.log("Currently in test mode");
                testMode = true;
            }

            if (Math.abs(Robot.oi.manipulator.getY(GenericHID.Hand.kRight)) < 0.06) {
                i = 0;
                armHeight = Robot.liftSubsystem.getArmHeight();
            }

            while (i < 25 && testMode) {
                i++;
            }

            if(i > 25) {
                success = Robot.liftSubsystem.getArmHeight() - armHeight > 0.1 &&
                        -Robot.oi.manipulator.getY(GenericHID.Hand.kRight) > 0.06 ||
                        Robot.liftSubsystem.getArmHeight() - armHeight < -0.1 &&
                                -Robot.oi.manipulator.getY(GenericHID.Hand.kRight) < -0.06;
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
        double speed = -Robot.oi.manipulator.getY(GenericHID.Hand.kRight);
        Robot.liftSubsystem.setArmMotorSpeed(OI.squareInput(OI.applyDeadband(speed)));
        if (-Robot.oi.manipulator.getY(GenericHID.Hand.kRight) > 0.35){
            new SetGrasper(IntakeSubsystem.GrasperState.OPEN).start();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.log("Control arm has ended");
    }

}

