package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;

class WaitForeverCommand extends Command {
    @Override
    protected void initialize() {
        System.out.println("Wait Forever has started");
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        System.out.println("Wait Forever has finished");
    }
}
