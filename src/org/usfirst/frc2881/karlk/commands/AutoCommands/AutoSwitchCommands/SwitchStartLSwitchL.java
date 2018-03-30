package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
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
public class SwitchStartLSwitchL extends AbstractAutoCommand {
    private final SwitchPosition side;

    public SwitchStartLSwitchL(SwitchPosition side){
        this.side = side;

        double distanceToSwitch = 94.0;
        double angle = Math.atan(-22.0 / distanceToSwitch);

        addSequential(new TurnToHeading(angle * 180/Math.PI, true));

        addSequential(new ConditionalCommand(new DriveForward(distanceToSwitch/Math.cos(angle) / 12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });

        addSequential(new TurnToHeading(90, true));

        addSequential(new ConditionalCommand(new DriveForward((34.0 - 17.0)/ 12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });
        addSequential(new ConditionalCommand(new TurnToHeading(0, true)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new LiftToHeight(3.7-0.17, false));

        addSequential(new ConditionalCommand(new DriveForward((44.0 - 14.0)/12), new DriveForward((18.06 - 14.0)/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });
        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time

}
