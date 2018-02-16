package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
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
              /*
      1) Open Grasper
      2) Lift claw
      2) Set rollers
      3) Close Grasper
      3) Open claw

         */
        addSequential(new SetGrasper(GrasperState.OPEN),1.0);
        addSequential(new LiftToHeight(.25)); //in feet
        addParallel(new SetRollers(Robot.intakeSubsystem.EJECT_SPEED), 1.0);//This will set the motor to run backwards to eject the cube
        addParallel(new SetGrasper(GrasperState.CLOSED),1.0);
        addSequential(new SetClaw(ClawState.OPEN));
    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.print("Eject Cube On Ground has ended");
    }
}
