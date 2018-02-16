package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.RobotMap;

public class WaitUntilNavXCalibrated extends Command {
    @Override
    protected void initialize() {
        System.out.println("Wait Until NavX Calibration has started");
    }

    @Override
    protected boolean isFinished() {
        return !RobotMap.driveSubsystemNavX.isConnected() || !RobotMap.driveSubsystemNavX.isCalibrating();
    }

    @Override
    protected void end() {
        System.out.println("Wait Until NavX Calibration has finished");
    }
}
