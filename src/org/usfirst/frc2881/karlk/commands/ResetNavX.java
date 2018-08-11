package org.usfirst.frc2881.karlk.commands;

import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;

public class ResetNavX extends WaitUntilNavXCalibrated {

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.log("Reset NavX has started");
        RobotMap.driveSubsystemNavX.reset();
    }

    // Called once after isFinished returns true
    @Override
    protected void end(){
        Robot.log("Reset NavX has finished");
    }

}

