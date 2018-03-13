// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc2881.karlk.commands.AutoCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.AutoCrossLineCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands.AutoScaleCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.AutoSwitchCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.DriveForward;

/**
 *
 */
public class OverrideAuto extends AbstractAutoCommand {

    OverrideAuto(StartingLocation start, AutoOptions auto,
                 SwitchPosition side, String gameData, AutoStrategy strategy) {

        if (auto == AutoOptions.NONE) {
            return;
        }

        addSequential(new DriveForward(67.0 / 12));

        if (auto == AutoOptions.CROSS_LINE) {
            addSequential(new AutoCrossLineCommand(start, strategy));
        } else if (auto == AutoOptions.SWITCH) {
            addSequential(new AutoSwitchCommand(start, gameData, side, strategy));
        } else if (auto == AutoOptions.SCALE || auto == AutoOptions.BOTH) {
            addSequential(new AutoScaleCommand(start, gameData, auto, side, strategy));
        }
    }
}
