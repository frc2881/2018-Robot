package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

public class WaitUntilNavXCalibrated extends Command {
    @Override
    protected void initialize() {
        Robot.log("Wait Until NavX Calibration has started");
    }

    @Override
    protected boolean isFinished() {
        return Robot.driveSubsystem.isNavXReady();
    }

    @Override
    protected void end() {
        Robot.log("Wait Until NavX Calibration has finished");
    }
}
