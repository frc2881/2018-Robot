package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Swap the perspective of the driver so that the Intake is in the back of the Robot
 */
public class SetIntakeAsBack extends InstantCommand {
    public SetIntakeAsBack() {
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveSubsystem.setIntakeLocation(DriveSubsystem.IntakeLocation.BACK);

    }


}
