package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;

class WaitForeverCommand extends Command {
    @Override
    protected void initialize() {
        Robot.log("Wait Forever has started");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.log("Wait Forever has finished");
    }
}
