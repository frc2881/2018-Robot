package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.Robot;

/**
 * This command runs the winch that is used for climbing at the end of the game.
 * It is the default command for the ClimbingSubsystem.
 */
public class Climb extends Command {
    public Climb() {
        requires(Robot.climbingSubsystem);
    }
    private double speed;
    @Override
    protected void initialize() {
        //Prints in the driver station
        System.out.println("Climb Command has started");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        this.speed = Robot.oi.manipulator.getTriggerAxis(GenericHID.Hand.kLeft);
        Robot.climbingSubsystem.climb(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        //Prints in the driver station
        System.out.println("Climb Command has finished");
    }
    //This method allows us to make changes to the property this.speed in Shuffleboard
    //It is called automatically when you call SmartDashboard.putData() in OI.java.
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Climb Motor Speed", () -> speed, (speed) -> this.speed= speed);
    }
}
