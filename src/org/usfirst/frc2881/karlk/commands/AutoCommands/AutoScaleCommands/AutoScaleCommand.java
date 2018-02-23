package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
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

    public AutoScaleCommand(DriveSubsystem.StartingLocation start, String gameData, DriveSubsystem.AutoOptions auto,
                            DriveSubsystem.SwitchPosition side) {
        super("AutoSwitch" + start + "position");
        this.start = start;
        this.gameData = gameData;
        this.auto = auto;

        addSequential(new ConditionalCommand(new ScaleSwitchL(gameData, side, start)) {
            @Override
            protected boolean condition() {
                return (auto == DriveSubsystem.AutoOptions.BOTH && gameData.charAt(0) == 'L');
            }
        });

        addSequential(new ConditionalCommand(new ScaleSwitchN(gameData, start)) {
            @Override
            protected boolean condition() {
                return auto == DriveSubsystem.AutoOptions.SCALE;
            }
        });

        addSequential(new ConditionalCommand(new ScaleSwitchR(gameData, side, start)) {
            @Override
            protected boolean condition() {
                return (auto == DriveSubsystem.AutoOptions.BOTH && gameData.charAt(0) == 'R');
            }
        });


    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
