package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class CrossLineCenter extends CommandGroup {
    private final DriveSubsystem.CrossLineLocation side;


    public CrossLineCenter(DriveSubsystem.CrossLineLocation side){
        this.side = side;

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new ConditionalCommand(new TurnToHeading(270), new TurnToHeading(90)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.CrossLineLocation.LEFT;
            }
        });
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));
        addSequential(new ConditionalCommand(new DriveForward(122.5/12), new DriveForward(108.5/12)) {
            @Override
            protected boolean condition() {
                return side == DriveSubsystem.CrossLineLocation.LEFT;
            }
        });
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(0));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));
        addSequential(new DriveForward(37/12));
    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
