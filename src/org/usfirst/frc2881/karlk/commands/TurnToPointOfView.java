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
import org.usfirst.frc2881.karlk.RobotMap;

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
    double rotateToAngleRate;

    public TurnToPointOfView() {
        requires(Robot.driveSubsystem);
        turnPOV = new PIDController(kP, kI, kD, kF, RobotMap.driveSubsystemNavX, this);
        turnPOV.setInputRange(-180, 180);
        turnPOV.setOutputRange(-1.0, 1.0);
        turnPOV.setAbsoluteTolerance(5);
        turnPOV.setContinuous(true);
        turnPOV.disable();

        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
        /* tuning of the Turn Controller's P, I and D coefficients.            */
        /* Typically, only the P value needs to be modified.                   */
        turnPOV.setName("DriveSystem", "RotateController");
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //depending on whether we need to turn or not, one or the other would be used
        int angle = Robot.oi.driver.getPOV();
        if (angle > 180) {
            angle = angle - 360;
        }

        turnPOV.setSetpoint(angle);
        rotateToAngleRate = 0;
        turnPOV.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
         Robot.driveSubsystem.rotate(rotateToAngleRate);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return turnPOV.onTarget();
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
