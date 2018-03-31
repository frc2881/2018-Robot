package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;

public class WaitUntilNavXCalibrated extends Command {
    @Override
    protected void initialize() {
        Robot.log("Wait Until NavX Calibration has started");
    }

    @Override
    protected boolean isFinished() {
        return !RobotMap.driveSubsystemNavX.isConnected() || !RobotMap.driveSubsystemNavX.isCalibrating();
    }

    @Override
    protected void end() {
        Robot.log("Wait Until NavX Calibration has finished");
    }
}
