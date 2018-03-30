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
public class SwitchStartCSwitchR extends AbstractAutoCommand {
    private final SwitchPosition side;

    public SwitchStartCSwitchR(SwitchPosition side){
        this.side = side;

        addSequential(new TurnToHeading(90, true));

        addSequential(new ConditionalCommand(new DriveForward((53.94 - 14.0 - 17.8) / 12), new DriveForward((114.565 - 14.0 - 17.8)/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });

        addSequential(new TurnToHeading(0, true));

        addSequential(new ConditionalCommand(new DriveForward((94.0 - 14.0- 17.8)/12)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });

        addSequential(new ConditionalCommand(new TurnToHeading(270, true)) {
            @Override
            protected boolean condition() {
                return side == SwitchPosition.SIDE;
            }
        });

        addSequential(new LiftToHeight(3.7-0.17, false));

        addSequential(new ConditionalCommand(new DriveForward((44.0- 14.0)/12), new DriveForward((23.625 - 14.0)/12)) {//TODO CHANGE BACK TO 35 for front
            @Override
            protected boolean condition() {
                return side == SwitchPosition.FRONT;
            }
        });
        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time

}
