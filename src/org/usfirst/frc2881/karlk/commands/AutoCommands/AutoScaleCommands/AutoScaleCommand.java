package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoScaleCommand extends AbstractAutoCommand {

    public AutoScaleCommand(StartingLocation start, String gameData, AutoOptions auto) {
        super("AutoScale" + start + "position");

        boolean switchOnLeft = gameData.charAt(0) == 'L';
        boolean switchOnRight = !switchOnLeft;

        addSequential(new ConditionalCommand(new ScaleSwitchN(gameData, start)) {
            @Override
            protected boolean condition() {
                return auto == AutoOptions.PRIORITY_SWITCH || auto == AutoOptions.PRIORITY_SCALE;
            }
        });

        addSequential(new DriveForward(-24.0/12));

        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, false));

    }


    // Called just before this Command runs the first time

}
