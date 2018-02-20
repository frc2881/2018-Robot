package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.Robot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Move the robot forward the specified amount
 */
public class DriveForward extends Command {
    private double distance;
    private PrintStream log;

    public DriveForward(double distance) {
        requires(Robot.driveSubsystem);
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Make a call to the subsystem to use a PID loop controller in the subsystem
        //to set the heading based on the angle passed into the method.
        System.out.println("Autonomous driving to " + distance);

        try {
            log = new PrintStream(new FileOutputStream("/home/lvuser/drive-capture.tsv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Robot.driveSubsystem.logHeader(log);

        Robot.driveSubsystem.initializeDriveForward(distance);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Calls to the subsystem to update the angle if controller value has changed
        double speed = Robot.driveSubsystem.getStraightSpeed();
        Robot.driveSubsystem.autonomousArcadeDrive(speed, 0);
        //Robot.driveSubsystem.arcadeDrive(speed,speed);
        //System.out.println("set speed to " + speed);
        Robot.driveSubsystem.log(log, timeSinceInitialized());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //asking the PID loop have we reached our position
        return Robot.driveSubsystem.isFinishedDriveForward();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        new FinishLogging(log, timeSinceInitialized()).start();
        //call the drive subsystem to make sure the PID loop is disabled
        Robot.driveSubsystem.endDriveForward();
        new RumbleYes(Robot.oi.driver).start();
        System.out.println("Drive Forward has ended");
    }

    //This method allows us to make changes to the property this.distance in Shuffleboard
    //It is called automatically when you call SmartDashboard.putData() in OI.java.
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Drive Forward Distance", () -> distance, (distance) -> this.distance = distance);
    }
}
