package org.usfirst.frc2881.karlk.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Read the current heading from the NavX and
 * turn the robot to match the NavX heading ...
 * details of how to implement are here:
 * https://github.com/kauailabs/navxmxp/blob/master/roborio/java/navXMXP_Java_RotateToAngle_Tank/src/org/usfirst/frc/team2465/robot/Robot.java
 */
public class TurnToPointOfView extends Command implements PIDOutput {

    static final double kP = 0.03;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;

    PIDController turnPOV;
    AHRS ahrs;
    double rotateToAngleRate;



    public TurnToPointOfView() {
        requires(Robot.driveSubsystem);
        try {
            /***********************************************************************
             * navX-MXP:
             * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.
             * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
             *
             * navX-Micro:
             * - Communication via I2C (RoboRIO MXP or Onboard) and USB.
             * - See http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
             *
             * Multiple navX-model devices on a single robot are supported.
             ************************************************************************/
            ahrs = new AHRS(SPI.Port.kMXP);
        }

        catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }

        turnPOV = new PIDController(kP, kI, kD, kF, ahrs, this);
        turnPOV.setInputRange(-180,  180);
        turnPOV.setOutputRange(-1.0, 1.0);
        turnPOV.setAbsoluteTolerance(5);
        turnPOV.setContinuous(true);
        turnPOV.disable();

        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        LiveWindow.addActuator("DriveSystem", "RotateController", turnPOV);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //depending on whether we need to turn or not, one or the other would be used
        turnPOV.setSetpoint(45);
        rotateToAngleRate = 0;
        turnPOV.enable();

        turnPOV.setSetpoint(ahrs.getYaw());
        rotateToAngleRate = 0;
        turnPOV.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        if (rotateToAngleRate != 0) {
            //TODO figure out which one or both to use depending on what the button does
            double leftStickValue = rotateToAngleRate;
            double rightStickValue = rotateToAngleRate;
            Robot.driveSubsystem.tankDrive(leftStickValue, rightStickValue);
        }

        else {
            //makes the robot drive straight. maybe make this happen after it turns to the correct angle
            double magnitude = (Robot.oi.driver.getY(GenericHID.Hand.kLeft) + Robot.oi.driver.getY(GenericHID.Hand.kRight)) / 2;
            double left = magnitude + rotateToAngleRate;
            double right = magnitude - rotateToAngleRate;
            Robot.driveSubsystem.tankDrive(left, right);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if (rotateToAngleRate !=0){
            return true;
        }
        else {
            return false;
        }
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        turnPOV.disable();
    }

    @Override
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }
}
