package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc2881.karlk.Robot;

/**
 * Pick this command in Shuffleboard to do nothing in Autonomous mode.
 */
public class DoNothingCommand extends InstantCommand {
    @Override
    protected void end() {
        Robot.log("Do Nothing has ended");
    }
}
