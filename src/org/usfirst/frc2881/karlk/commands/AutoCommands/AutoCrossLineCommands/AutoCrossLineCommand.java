package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCrossLineCommands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AbstractAutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.CrossLineLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.DriveForward;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoCrossLineCommand extends AbstractAutoCommand {

    private final StartingLocation start;
    private final CrossLineLocation side;

    public AutoCrossLineCommand(StartingLocation start, CrossLineLocation side, AutoOptions auto) {
        super("AutoCrossLine" + start + "position");
        this.start = start;
        this.side = side;

        addSequential(new ConditionalCommand(new DriveForward(56.0/12)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.LEFT || start == StartingLocation.RIGHT;
            }
        });

        addSequential(new ConditionalCommand(new CrossLineCenter(side)) {
            @Override
            protected boolean condition() {
                return start == StartingLocation.CENTER;
            }
        });

    }


    // Called just before this Command runs the first time
}
