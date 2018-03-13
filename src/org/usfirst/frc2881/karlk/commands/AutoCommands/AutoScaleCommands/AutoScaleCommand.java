package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.CrossLineLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoScaleCommand extends AbstractAutoCommand {

    public AutoScaleCommand(StartingLocation start, String gameData, AutoOptions auto,
                            SwitchPosition side, AutoStrategy strategy) {
        super("AutoScale" + start + "position");

        boolean switchOnLeft = gameData.charAt(0) == 'L';
        boolean switchOnRight = !switchOnLeft;

        addSequential(new ConditionalCommand(new ScaleSwitchL(gameData, side, start, strategy)) {
            @Override
            protected boolean condition() {
                return (auto == AutoOptions.BOTH && switchOnLeft);
            }
        });

        addSequential(new ConditionalCommand(new ScaleSwitchN(gameData, start, strategy)) {
            @Override
            protected boolean condition() {
                return auto == AutoOptions.SCALE;
            }
        });

        addSequential(new ConditionalCommand(new ScaleSwitchR(gameData, side , start, strategy)) {
            @Override
            protected boolean condition() {
                return (auto == AutoOptions.BOTH && switchOnRight);
            }
        });

    }


    // Called just before this Command runs the first time

}
