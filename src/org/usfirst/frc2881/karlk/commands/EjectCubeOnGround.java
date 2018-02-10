package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class EjectCubeOnGround extends CommandGroup {

    public EjectCubeOnGround() {
        super("EdjectCubeOnGround" );
        requires(Robot.intakeSubsystem);

        /*
        1) Release Claw
        2)setRollers
        3) Release Grasper
        4)Please add on... or correct...
         */
        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT));
            addSequential(new SetClaw(false));
            addSequential(new SetRollers(Robot.intakeSubsystem.EJECT_SPEED));//This will set the motor to a slow speed backwards to eject the cube
            addSequential(new SetGrasper(false));
            addSequential(new RumbleJoysticks());

        }

    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.print("Eject Cube On Ground has ended");
    }
}
