package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.CrossLineLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.SwitchPosition;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoScaleCommand extends AbstractAutoCommand {

    private final StartingLocation start;
    private final String gameData;
    private final AutoOptions auto;

    public AutoScaleCommand(StartingLocation start, String gameData, AutoOptions auto,
                            SwitchPosition switchSide, CrossLineLocation lineSide) {
        super("AutoScale" + start + "position");
        this.start = start;
        this.gameData = gameData;
        this.auto = auto;

        addSequential(new ConditionalCommand(new ScaleSwitchL(gameData, switchSide, start)) {
            @Override
            protected boolean condition() {
                return (auto == AutoOptions.BOTH && gameData.charAt(0) == 'L');
            }
        });

        addSequential(new ConditionalCommand(new ScaleSwitchN(gameData, start, lineSide)) {
            @Override
            protected boolean condition() {
                return auto == AutoOptions.SCALE;
            }
        });

        addSequential(new ConditionalCommand(new ScaleSwitchR(gameData, switchSide, start)) {
            @Override
            protected boolean condition() {
                return (auto == AutoOptions.BOTH && gameData.charAt(0) == 'R');
            }
        });


    }


    // Called just before this Command runs the first time

}
