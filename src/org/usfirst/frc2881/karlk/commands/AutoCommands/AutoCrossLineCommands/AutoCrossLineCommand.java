package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands.ScaleStartL;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoCrossLineCommand extends CommandGroup {

    private final DriveSubsystem.StartingLocation start;
    private final DriveSubsystem.CrossLineLocation side;

    public AutoCrossLineCommand(DriveSubsystem.StartingLocation start, DriveSubsystem.CrossLineLocation side) {
        super("AutoCrossLine" + start + "position");
        this.start = start;
        this.side = side;

        addSequential(new ConditionalCommand(new DriveForward(56/12)) {
            @Override
            protected boolean condition() {
                return start == DriveSubsystem.StartingLocation.LEFT || start == DriveSubsystem.StartingLocation.RIGHT;
            }
        });

        addSequential(new ConditionalCommand(new CrossLineCenter(side)) {
            @Override
            protected boolean condition() {
                return start == DriveSubsystem.StartingLocation.CENTER;
            }
        });

    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
