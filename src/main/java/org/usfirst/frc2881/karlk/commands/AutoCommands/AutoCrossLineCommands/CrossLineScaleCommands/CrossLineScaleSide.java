package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands.CrossLineScaleCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutonomousRobotFinish;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class CrossLineScaleSide extends AbstractAutoCommand {

    public CrossLineScaleSide(StartingLocation start){

        addSequential(new ConditionalCommand(new DriveForward((165.0)/12), new DriveForward((165.0 + 1)/12)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new ConditionalCommand(new TurnToHeading(90, true), new TurnToHeading(270, true)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new ConditionalCommand(new DriveForward((209.94 - 14.0 - 27.8)/12), new DriveForward((209.94 - 6 - 26.4)/12)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new TurnToHeading(0, true));

        addSequential(new AutonomousRobotFinish());

        addSequential(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, false));

        addSequential(new ConditionalCommand(new DriveForward((52.65 - 6)/12), new DriveForward((52.65 - 14.0)/12)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

    }


    // Called just before this Command runs the first time

}
