package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem.GrasperState;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

/**
 * This command performs a series of actions needed
 * to intake a cube.  Make sure the lift arm is down,
 * the claw is open before turning on the rollers and
 * closing the grasper, then closing the claw and
 * releasing the grasper once the sensor that indicates
 * a cube is loaded is triggered.
 */
public class IntakeCubeOverride extends CommandGroup {

    public IntakeCubeOverride(){
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
        addSequential(new ConditionalCommand(new SetGrasper(GrasperState.OPEN)) {
            protected boolean condition() {
                return !Robot.intakeSubsystem.getGrasper();
            }
        });

        /* reenable after tested addSequential(new ConditionalCommand(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT)) {
            protected boolean condition() {
                return Math.abs(Robot.liftSubsystem.checkEncoder()) <= 0.05;
            }
        }); */

        addSequential(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT));

        /* add this back later after sensors are fixed addSequential(new ConditionalCommand(new SetClaw(ClawState.OPEN)) {
               protected boolean condition() {
                   return !Robot.liftSubsystem.getClaw();
               }
          }); */

        addSequential(new SetClaw(LiftSubsystem.ClawState.OPEN));

        addSequential(new ConditionalCommand(new SetRollers(Robot.intakeSubsystem.INTAKE_SPEED)) {
            protected boolean condition() {
                return !Robot.intakeSubsystem.getRollers();
            }
        });

        addSequential(new SetGrasper(GrasperState.CLOSED));
        addSequential(new WaitCommand(1.5));
        addSequential(new SetClaw(LiftSubsystem.ClawState.CLOSED));
        addSequential(new RumbleJoysticks());
    }

    @Override
    protected void end() {
        System.out.print("Open Grasper has ended");
    }
}
