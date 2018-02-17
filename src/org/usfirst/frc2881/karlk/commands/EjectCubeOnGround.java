package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem.GrasperState;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

/**
 * Release claw on lift subsystem, release grasper
 * run rollers backwards on intake subsystem so
 * that cube is ejected from the robot at the ground level
 */
public class EjectCubeOnGround extends CommandGroup {

    public EjectCubeOnGround() {
        super("EjectCubeOnGround");
        //requires(Robot.intakeSubsystem);
              /*
      1) Open Grasper
      2) Lift claw
      2) Set rollers
      3) Close Grasper
      3) Open claw

         */

        addSequential(new PrintCommand("Eject Cube is working if you read this."));
        addSequential(new SetGrasper(GrasperState.OPEN));
        addSequential(new LiftToHeight(1.1)); //in feet
        addSequential(new SetGrasper(GrasperState.CLOSED));
        addSequential(new WaitCommand(0.3));
        addParallel(new SetRollers(IntakeSubsystem.EJECT_SPEED), 1.0);//This will set the motor to run backwards to eject the cube
        addSequential(new SetClaw(ClawState.OPEN));
        addSequential(new WaitForChildren());
    }


    // Called just before this Command runs the first time
    @Override
    protected void end() {
        System.out.println("Eject Cube On Ground has ended");
    }
}
