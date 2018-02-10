package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2881.karlk.Robot;

import static edu.wpi.first.wpilibj.Timer.getMatchTime;

public class EndMatchRumble extends CommandGroup {
    public EndMatchRumble() {
        addSequential(new WaitUntilEndGame());
        addSequential(new RumbleJoysticks());
    }
}
