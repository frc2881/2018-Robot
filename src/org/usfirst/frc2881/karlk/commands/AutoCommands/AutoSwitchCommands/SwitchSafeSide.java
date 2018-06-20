package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutonomousRobotFinish;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class SwitchSafeSide extends AbstractAutoCommand {

    public SwitchSafeSide(StartingLocation start){

        double distanceToSwitch = 103.0;
        double angleRight = Math.atan2(26.0, distanceToSwitch);
        double angleLeft = Math.atan2(-26.0, distanceToSwitch);

        addSequential(new ConditionalCommand(
                new TurnToHeading(angleLeft * 180/Math.PI, true),
                new TurnToHeading(angleRight * 180/Math.PI, true)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
                }
        });

        addSequential(new ConditionalCommand(
                new DriveForward((distanceToSwitch/Math.cos(angleLeft)) / 12),
                new DriveForward((distanceToSwitch/Math.cos(angleRight) + 1) / 12)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new ConditionalCommand(
                new TurnToHeading(90, false),
                new TurnToHeading(270, false)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT;
            }
        });

        addSequential(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false));

        addSequential(new DriveForward(22.0 / 12));

        addSequential(new SetClaw(ClawState.OPEN));

        addSequential(new DriveForward( -22.0/12));

        //addSequential(new AutonomousRobotFinish());

        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, false));
    }


    // Called just before this Command runs the first time

}
