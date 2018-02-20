package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import org.usfirst.frc2881.karlk.Robot;

import java.io.PrintStream;

public class FinishLogging extends TimedCommand {
    private final PrintStream log;
    private final double start;

    public FinishLogging(PrintStream log, double start) {
        super(1);
        this.log = log;
        this.start = start;
    }

    @Override
    protected void execute() {
        Robot.driveSubsystem.log(log, start + timeSinceInitialized());
    }

    @Override
    protected void end() {
        log.close();
    }
}
