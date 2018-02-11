package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import org.usfirst.frc2881.karlk.RobotMap;

/**
 * This handles the grasper wall and the rollers
 * that are used to intake the cube at the ground level.
 */
public class IntakeSubsystem extends Subsystem implements SendableWithChildren {
    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final PowerDistributionPanel pdp = RobotMap.otherPowerDistributionPanel;
    private final Solenoid grasper = add(RobotMap.intakeSubsystemGrasper);
    private final AnalogInput intakeDetectorShortIR = add(RobotMap.intakeSubsystemIntakeDetectorShortIR);
    private final AnalogInput intakeDetectorLongIR = add(RobotMap.intakeSubsystemIntakeDetectorLongIR);
    private final SpeedController intakeRollerLeft = add(RobotMap.intakeSubsystemIntakeRollerLeft);
    private final SpeedController intakeRollerRight = add(RobotMap.intakeSubsystemIntakeRollerRight);
    private final SpeedControllerGroup intakeRollerGroup = add(RobotMap.intakeSubsystemIntakeRollerGroup);
    private final double thresholdDetectedIR = 0.91;//volts (linear regression for inches is too unreliable)
    private final double thresholdLoadedIR = 2.8;//volts (linear regression for inches is too unreliable)

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
            intakeRollerGroup.set(0.5);
        } else {
            intakeRollerGroup.set(-0.5);
        }
    }

    //Stops the rollers (put at the end of the command)
    public void stopRollers() {
        intakeRollerGroup.set(0);
    }

    public void setGrasper(boolean deploy) {
        grasper.set(deploy);
    }

    //has true/false option to test each sensor individually
    public boolean cubeDetected() {
        return (intakeDetectorShortIR.pidGet() <= thresholdDetectedIR &&
            intakeDetectorLongIR.pidGet() <= thresholdDetectedIR);
            //TODO is there a way to use the exact number for linReg?
    }

    public void loadCube(){
        if ((intakeDetectorLongIR.pidGet() + 0.15) < intakeDetectorShortIR.pidGet()) {
            intakeRollerLeft.set((((intakeDetectorShortIR.pidGet() - intakeDetectorLongIR.pidGet()) /
                    intakeDetectorLongIR.pidGet())+1)*0.5);
            intakeRollerRight.set(0.5);
        }
        else if ((intakeDetectorLongIR.pidGet() - 0.15) > intakeDetectorShortIR.pidGet()) {
            intakeRollerRight.set((((intakeDetectorLongIR.pidGet() - intakeDetectorShortIR.pidGet()) /
                    intakeDetectorShortIR.pidGet())+1)*0.5);
            intakeRollerLeft.set(0.5);
        }
        else{
            intakeRollerGroup.set(0.5);
        }
    }
    public boolean cubeLoaded(){
        return (intakeDetectorShortIR.pidGet() <= thresholdLoadedIR);
    }
}

