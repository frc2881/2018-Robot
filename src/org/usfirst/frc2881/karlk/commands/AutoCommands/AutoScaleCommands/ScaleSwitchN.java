package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartLSwitchL;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartRSwitchL;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.SetGrasper;
import org.usfirst.frc2881.karlk.commands.SetRollers;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class ScaleSwitchN extends CommandGroup {
    private final String gameData;
    private final DriveSubsystem.StartingLocation start;

    public ScaleSwitchN(String gameData, DriveSubsystem.StartingLocation start){
        this.gameData = gameData;
        this.start = start;

        addParallel(new LiftToHeight(0, false));

        addSequential(new DriveForward(241/12));

        addSequential(new SetGrasper(IntakeSubsystem.GrasperState.OPEN));

        addParallel(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, false));

        addSequential(new ConditionalCommand(new TurnToHeading(270), new TurnToHeading(90)) {
            @Override
            protected boolean condition() {
                return gameData.charAt(1) == 'R';
            }
        });

        addSequential(new DriveForward(26.38/12)); // front bumper goes an inch under the scale

        addSequential(new SetClaw(ClawState.OPEN));

        addSequential(new DriveForward(-38.785/12));

        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, false));
    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
