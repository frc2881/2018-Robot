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
public class SwitchStartRSwitchL extends AbstractAutoCommand {
    private final SwitchPosition side;

    public SwitchStartRSwitchL(SwitchPosition side){
        this.side = side;

        addSequential(new TurnToHeading(270, true));

        addSequential(new ConditionalCommand(new DriveForward(190.44 / 12), new DriveForward(234.565/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new TurnToHeading(0, true));

        addSequential(new ConditionalCommand(new DriveForward(101.0/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });
        addSequential(new ConditionalCommand(new TurnToHeading(90, true)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });

        addSequential(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, false));

        addSequential(new ConditionalCommand(new DriveForward(35.0/12), new DriveForward(23.625/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time

}
