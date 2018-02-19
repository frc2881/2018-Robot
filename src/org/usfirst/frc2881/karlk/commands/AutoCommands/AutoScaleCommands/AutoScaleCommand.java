package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoSwitchCommands.SwitchStartLSwitchR;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class AutoScaleCommand extends CommandGroup {

    private final DriveSubsystem.StartingLocation start;
    private final String gameData;
    private final DriveSubsystem.AutoOptions auto;

    public AutoScaleCommand(DriveSubsystem.StartingLocation start, String gameData, DriveSubsystem.AutoOptions auto) {
        super("AutoSwitch" + start + "position");
        this.start = start;
        this.gameData = gameData;
        this.auto = auto;

        if(gameData.length() > 0)
        {
            if(gameData.charAt(0) == 'L')
            {
                //Put left auto code here
            } else {
                //Put right auto code here
            }
        }

        addSequential(new ConditionalCommand(new ScaleStartL(gameData)) {
            @Override
            protected boolean condition() {
                return start == DriveSubsystem.StartingLocation.LEFT;
            }
        });

    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
