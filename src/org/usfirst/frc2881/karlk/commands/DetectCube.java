package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc2881.karlk.OI;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem.GrasperState;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

import java.util.function.Supplier;

/**
 * This command performs a series of actions needed
 * to intake a cube.  Make sure the lift arm is down,
 * the claw is open before turning on the rollers and
 * closing the grasper, then closing the claw and
 * releasing the grasper once the sensor that indicates
 * a cube is loaded is triggered.
 */
public class DetectCube extends CommandGroup {
    private final Supplier<OI.TriggerButtons> function;

    public DetectCube(Supplier<OI.TriggerButtons> function) {
        super("IntakeCube" + function);
        this.function = function;
        /*
        1. make sure grasper is open
        2. make sure arm is down, claw is open
        3. turn rollers on
        4. check digital sensor if cube is in position
        5. once sensor is tripped, close the grasper
        6. check for current spike on rollers
        7. when current spikes, stop rollers, close claw
                n.b. current plan is to keep claw and grasper both closed when robot transports cube
        8. Rumble Joysticks
        */
        addSequential(new SetGrasper(GrasperState.OPEN));
        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, false));
        addSequential(new SetClaw(ClawState.OPEN));
        addSequential(new WaitUntilCubeDetected(function));
        addSequential(new SetRollers(IntakeSubsystem.INTAKE_SPEED));
        addSequential(new SetGrasper(GrasperState.CLOSED));
        addSequential(new CubeLoaded());
        addSequential(new SetClaw(ClawState.CLOSED));
        addSequential(new RumbleJoysticks());
    }

    @Override
    protected void end() {
        System.out.print("Intake Cube has ended");
    }
}
