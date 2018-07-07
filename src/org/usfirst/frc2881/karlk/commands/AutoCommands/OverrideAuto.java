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

import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.AutoSwitchCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutonomousRobotFinish;
import org.usfirst.frc2881.karlk.commands.DriveForward;

/**
 *
 */
public class OverrideAuto extends AbstractAutoCommand {

    OverrideAuto(String gameData) {

        addSequential(new DriveForward((46.0 - 26.4) / 12));
        addSequential(new AutonomousRobotFinish());

        addSequential(new AutoSwitchCommand(StartingLocation.CENTER, gameData));
        }
    }
