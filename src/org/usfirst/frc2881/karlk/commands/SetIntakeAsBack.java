package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;
import edu.wpi.first.wpilibj.command.Command;
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
        System.out.println("intake has been set to BACK.");
        System.out.println("Meaning the BACK of the robot is now the FRONT");
    }

}
