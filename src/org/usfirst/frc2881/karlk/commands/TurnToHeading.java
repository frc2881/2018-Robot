package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Read the current heading from the NavX and
 * turn the robot to match the NavX heading ...
 * details of how to implement are here:
 * https://github.com/kauailabs/navxmxp/blob/master/roborio/java/navXMXP_Java_RotateToAngle_Tank/src/org/usfirst/frc/team2465/robot/Robot.java
 */
public class TurnToHeading extends Command {

    private double angle;
    private boolean omnis;

    public TurnToHeading(double angle, boolean omnis) {
        requires(Robot.driveSubsystem);
        if (angle > 180) {
            angle -= 360;
        }
        this.angle = angle;
        this.omnis = omnis;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Make a call to the subsystem to use a PID loop controller in the subsystem
        //to set the heading based on the angle passed into the method.
        System.out.println("Turn to Heading " + angle + " has started: " + Robot.driveSubsystem.getLocation());
        if (omnis) {
            Robot.driveSubsystem.dropOmniPancakePiston(DriveSubsystem.OmnisState.DOWN);
        }
        Robot.driveSubsystem.initializeDriveForward(0, angle, omnis);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Calls to the subsystem to update the angle if controller value has changed
        double speed = Robot.driveSubsystem.getStraightSpeed();
        double rotate = Robot.driveSubsystem.getRotateToAngleRate();
        Robot.driveSubsystem.autonomousArcadeDrive(speed, rotate);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //asking the pid loop have we reached our position
        return Robot.driveSubsystem.isFinishedDriveForward(omnis);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //call the drive subsystem to make sure the PID loop is disabled
        Robot.driveSubsystem.endDriveForward(omnis);
        if (omnis) {
            Robot.driveSubsystem.dropOmniPancakePiston(DriveSubsystem.OmnisState.UP);
        }
        System.out.println("Turn to Heading has finished: " + Robot.driveSubsystem.getLocation());
    }

    @Override
    //This method allows us to make changes to the property this.angle in Shuffleboard
    //It is called automatically when you call SmartDashboard.putData() in OI.java.
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("POV Angle", () -> angle, (angle) -> this.angle = angle);
    }

}
