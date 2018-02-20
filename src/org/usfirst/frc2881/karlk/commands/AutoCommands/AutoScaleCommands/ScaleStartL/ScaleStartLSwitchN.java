package org.usfirst.frc2881.karlk.commands.AutoCommands.AutoScaleCommands.ScaleStartL;

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
public class ScaleStartLSwitchN extends CommandGroup {
    private final String gameData;
    private final DriveSubsystem.SwitchPosition side;

    public ScaleStartLSwitchN(String gameData, DriveSubsystem.SwitchPosition side){
        this.gameData = gameData;
        this.side = side;


        //ALL OF THE FOLLOWING ONLY HAPPENS WHEN WE GO TO FRONT OF SWITCH


    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
