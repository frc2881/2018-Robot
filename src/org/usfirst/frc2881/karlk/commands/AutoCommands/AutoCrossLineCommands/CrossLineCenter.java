package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.CrossLineLocation;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class CrossLineCenter extends AbstractAutoCommand {
    private final CrossLineLocation side;


    public CrossLineCenter(CrossLineLocation side){
        this.side = side;

        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new ConditionalCommand(new TurnToHeading(270), new TurnToHeading(90)) {
            @Override
            protected boolean condition() {
                return side == CrossLineLocation.LEFT;
            }
        });
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));
        addSequential(new ConditionalCommand(new DriveForward(126.065/12), new DriveForward(112.065/12)) {
            @Override
            protected boolean condition() {
                return side == CrossLineLocation.LEFT;
            }
        });
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.DOWN));
        addSequential(new TurnToHeading(0));
        addSequential(new DeployOmnis(DriveSubsystem.OmnisState.UP));
        addSequential(new DriveForward(56.00/12));
    }


    // Called just before this Command runs the first time

}
