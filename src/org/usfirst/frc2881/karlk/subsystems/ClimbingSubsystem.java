package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.Climb;

/**
 * This subsystem handles the winch
 * the robot needs to climb.  It should be disabled until
 * the end of the match.  It does NOT manage the arm
 * extension also needed at the end.
 */
public class ClimbingSubsystem extends Subsystem implements SendableWithChildren {
    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final SpeedController winch = add(RobotMap.climbingSubsystemWinch);
    private final Solenoid liftArmForClimbing = add(RobotMap.climbingSubsystemExtender);

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Climb());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void climb(double speed) {
        //This method sets the speed to the number specified in the trigger, as long as speed value is positive
        if (speed >= 0) {
            winch.set(speed);
        } else {
            // we are assuming that we cannot run the winch backward (because last year the winch was a ratchet)
            winch.stopMotor();
        }
    }

    public void liftArmForClimbing (boolean deploy) {
        liftArmForClimbing.set(deploy);
    }

}

