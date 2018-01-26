package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc2881.karlk.RobotMap;

/**
 * This handles the grasper wall and the rollers
 * that are used to intake the cube at the ground level.
 */
public class IntakeSubsystem extends Subsystem implements SendableWithChildren {
    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final Solenoid intakeDeploy = add(RobotMap.intakeSubsystemIntakeDeploy);
    private final Solenoid grasper = add(RobotMap.intakeSubsystemGrasper);
    private final DigitalInput intakeDetector = add(RobotMap.intakeSubsystemIntakeDetector);
    private final SpeedController intakeRollerLeft = add(RobotMap.intakeSubsystemIntakeRollerLeft);
    private final SpeedController intakeRollerRight = add(RobotMap.intakeSubsystemIntakeRollerRight);
    private final SpeedControllerGroup intakeRollerGroup = add(RobotMap.intakeSubsystemIntakeRollerGroup);

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void grasper(boolean grasp) {
        grasper.set(grasp);
    }

    //Opens grasper (put at the end of the command)
    public void openGrasper() {
        grasper.set(false);
    }

    //Sets the rollers forwards if roll is true and backwards if roll is false
    public void rollers(boolean roll) {
        if (roll) {
            intakeRollerGroup.set(1);
        } else {
            intakeRollerGroup.set(-1);
        }


    }

    //Stops the rollers (put at the end of the command)
    public void stopRollers() {
        intakeRollerGroup.set(0);
    }

    public void setGrasper(boolean deploy) {
        grasper.set(deploy);
    }
}

