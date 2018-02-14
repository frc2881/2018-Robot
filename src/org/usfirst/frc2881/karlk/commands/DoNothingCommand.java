package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Pick this command in Shuffleboard to do nothing in Autonomous mode.
 */
public class DoNothingCommand extends InstantCommand {
    @Override
    protected void end() {
        System.out.print("Do Nothing has ended");
    }
}
