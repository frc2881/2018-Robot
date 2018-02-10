package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;

import static edu.wpi.first.wpilibj.Timer.getMatchTime;

public class WaitUntilEndGame extends Command {

    @Override
    protected boolean isFinished() {
        return getMatchTime() <= 30;
    }
}
