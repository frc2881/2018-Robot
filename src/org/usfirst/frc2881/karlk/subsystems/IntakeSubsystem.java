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
import org.usfirst.frc2881.karlk.OI;
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
    private final Ultrasonic intakeDetectorUltrasonic = add(RobotMap.intakeSubsystemIntakeDetectorUltrasonic);
    private final AnalogInput intakeDetectorIR = add(RobotMap.intakeSubsystemIntakeDetectorIR);
    private final SpeedController intakeRollerLeft = add(RobotMap.intakeSubsystemIntakeRollerLeft);
    private final SpeedController intakeRollerRight = add(RobotMap.intakeSubsystemIntakeRollerRight);
    private final SpeedControllerGroup intakeRollerGroup = add(RobotMap.intakeSubsystemIntakeRollerGroup);
    private final int intakeRollerLeftPdpChannel = RobotMap.INTAKE_SUBSYSTEM_INTAKE_ROLLER_LEFT_PDP_CHANNEL;
    private final int intakeRollerRightPdpChannel = RobotMap.INTAKE_SUBSYSTEM_INTAKE_ROLLER_RIGHT_PDP_CHANNEL;
    private final Timer timer = new Timer();
    private final double thresholdUltrasonic = 6;//inches
    private final double thresholdIR = 1.65;//volts

    public final double EJECT_SPEED = -.4;
    public final double INTAKE_SPEED = .5;



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

    public void resetTimer() {
        timer.reset();
        timer.start();
    }

    public double getTimer() {
        return timer.get();
    }

    //Sets the rollers forwards if roll is true and backwards if roll is false
    public void rollers(double speed) {
            intakeRollerGroup.set(speed);
        }


    //Stops the rollers (put at the end of the command)
    public void stopRollers() {
        intakeRollerGroup.set(0);
    }

    public void setGrasper(boolean deploy) {
        grasper.set(deploy);
    }

    public double getMotorCurrent() {
        if (pdp.getCurrent(intakeRollerLeftPdpChannel) > pdp.getCurrent(intakeRollerRightPdpChannel)) {
            return pdp.getCurrent(intakeRollerLeftPdpChannel);
        } else {
            return pdp.getCurrent(intakeRollerRightPdpChannel);
        }
    }

    //has true/false option to test each sensor individually
    public boolean cubeDetected(boolean ultrasonic) {
        if ((ultrasonic == true && intakeDetectorUltrasonic.getRangeInches() <= thresholdUltrasonic) ||
                (ultrasonic == false && intakeDetectorIR.pidGet() <= thresholdIR)) {
            return true;
        }
        return false;
    }
}

