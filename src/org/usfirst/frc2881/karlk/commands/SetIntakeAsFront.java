package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Swap the perspective of the driver so that the Intake is in the front of the Robot
 */
public class SetIntakeAsFront extends InstantCommand {

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveSubsystem.setIntakeLocation(DriveSubsystem.IntakeLocation.FRONT);

    }

}
