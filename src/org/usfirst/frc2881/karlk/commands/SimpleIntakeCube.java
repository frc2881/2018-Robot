package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem.GrasperState;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

public class SimpleIntakeCube extends CommandGroup {

    public SimpleIntakeCube() {
        addSequential(new SetClaw(ClawState.OPEN));
        addParallel(new SetRollers(-IntakeSubsystem.INTAKE_SPEED));
        addSequential(new SetGrasper(GrasperState.CLOSED));
        addSequential(new WaitForeverCommand());
    }

    @Override
    protected void initialize() {
        Robot.log("Simple Cube Intake has started");
    }

    @Override
    protected void end() {
        Robot.log("Simple Cube Intake has ended");
    }

}
