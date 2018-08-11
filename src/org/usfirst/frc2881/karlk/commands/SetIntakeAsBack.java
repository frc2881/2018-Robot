package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Swap the perspective of the driver so that the Intake is in the back of the Robot
 */
public class SetIntakeAsBack extends InstantCommand {

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveSubsystem.setIntakeLocation(DriveSubsystem.IntakeLocation.BACK);
        //Prints in the driver station
        Robot.log("Intake has been set to BACK.");
        Robot.log("Meaning the BACK of the robot is now the FRONT");
    }

}
