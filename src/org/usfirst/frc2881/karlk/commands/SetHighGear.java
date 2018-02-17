package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;

public class SetHighGear extends InstantCommand {

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to true
        Robot.driveSubsystem.highGear();
    }

    @Override
    protected void end() {
        System.out.println("Set High Gear has ended");
    }
}
