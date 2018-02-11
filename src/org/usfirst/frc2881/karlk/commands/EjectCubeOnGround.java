package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem.GrasperState;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class EjectCubeOnGround extends CommandGroup {

    public EjectCubeOnGround() {
        super("EjectCubeOnGround");
        requires(Robot.intakeSubsystem);
        //final double ???????????= 0.45;
        /*
       1) Arm at 0 height
       2) Claw releases
       3) Grasper releases
       4) Rollers 'eject'
       6) etc.
         */
        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT));
        addSequential(new SetClaw(ClawState.OPEN));
        addSequential(new SetGrasper(GrasperState.OPEN));
        addParallel(new SetRollers(Robot.intakeSubsystem.EJECT_SPEED));//This will set the motor to a slow speed backwards to eject the cube

    }

    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.print("Eject Cube On Ground has ended");
    }
}
