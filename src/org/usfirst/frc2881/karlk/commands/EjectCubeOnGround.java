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
        super("EjectCubeOnGround" );
        requires(Robot.intakeSubsystem);

        /*
       1) Arm at 0 height
       2) Claw releases
       3) Rollers 'eject'
       4) Grasper releases
       5) Rumble Joysticks
       6) etc.
         */
        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT));
            addSequential(new SetClaw(false));
            addSequential(new SetRollers(Robot.intakeSubsystem.EJECT_SPEED));//This will set the motor to a slow speed backwards to eject the cube
            addSequential(new SetGrasper(false));
            addSequential(new RumbleJoysticks());

        }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.print("Eject Cube On Ground has ended");
    }
}
