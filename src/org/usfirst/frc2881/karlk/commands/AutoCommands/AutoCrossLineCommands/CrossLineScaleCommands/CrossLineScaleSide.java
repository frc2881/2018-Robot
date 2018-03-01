package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.CrossLineScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.CrossLineLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class CrossLineScaleSide extends AbstractAutoCommand {

    public CrossLineScaleSide(StartingLocation start){

        addSequential(new DriveForward(145.735/12));
        addSequential(new ConditionalCommand(new TurnToHeading(90, true), new TurnToHeading(270, true)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.RIGHT;
            }
        });
        addSequential(new DriveForward(234.565/12));
        addSequential(new TurnToHeading(0, true));
        addSequential(new DriveForward(95.265/12));
    }


    // Called just before this Command runs the first time

}
