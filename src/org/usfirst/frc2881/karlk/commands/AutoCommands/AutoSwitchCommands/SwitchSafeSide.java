package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import com.sun.tools.javadoc.Start;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class SwitchSafeSide extends AbstractAutoCommand {

    public SwitchSafeSide(StartingLocation start){

        double distanceToSwitch = 94.0;
        double angle = Math.atan2(22.0, distanceToSwitch);
        double angle2 = Math.atan2(-22.0, distanceToSwitch);

        addSequential(new ConditionalCommand(new TurnToHeading(angle2 * 180/Math.PI, true),
                              new TurnToHeading(angle * 180/Math.PI, true)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
                }
        });

        addSequential(new ConditionalCommand(new DriveForward((distanceToSwitch/Math.cos(angle2) - 17.8) / 12),
                new DriveForward((distanceToSwitch/Math.cos(angle2) - 16.8) / 12)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new ConditionalCommand(new TurnToHeading(270, true), new TurnToHeading(90, true)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new LiftToHeight(3.7-0.17, false));

        addSequential(new ConditionalCommand(new DriveForward((18.06 - 14.0) / 12),
                new DriveForward((18.06 - 15.5) / 12)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time

}
