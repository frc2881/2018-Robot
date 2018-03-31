package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;

public class SetLowGear extends InstantCommand {

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //this turns the piston to false
        Robot.driveSubsystem.lowGear();
    }

    @Override
    protected void end() {
        Robot.log("Set Low Gear has ended");
    }
}
