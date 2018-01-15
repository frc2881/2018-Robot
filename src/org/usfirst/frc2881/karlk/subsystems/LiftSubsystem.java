package org.usfirst.frc2881.karlk.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import org.usfirst.frc2881.karlk.RobotMap;
import org.usfirst.frc2881.karlk.commands.ControlArmwithJoysticks;

import java.time.chrono.ThaiBuddhistDate;

/**
 * This handles the arm and the claw at the end
 * of the arm that is used to deliver cubes to the
 * switch and the scale.
 */
public class LiftSubsystem extends PIDSubsystem implements SendableWithChildren {
    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final WPI_TalonSRX armMotor = add(RobotMap.liftSubsystemArmMotor);
    private final Encoder armEncoder = add(RobotMap.liftSubsystemArmEncoder);
    private final DigitalInput armTop = add(RobotMap.liftSubsystemArmTop);
    private final DigitalInput armBottom = add(RobotMap.liftSubsystemArmBottom);
    private final Solenoid claw = add(RobotMap.liftSubsystemClaw);

    // Initialize your subsystem here
    public LiftSubsystem() {
        super("LiftSubsystem", 1.0, 0.0, 0.0);
        /*This makes a call to the PIDSubsystem constructor
        PIDSubsystem(double p, double i, double d)
        that instantiates a PIDSubsystem that will use the given p, i and d values.*/
        setAbsoluteTolerance(0.2);  //Set the absolute error which is considered tolerable for use with OnTarget.
        getPIDController().setContinuous(false);
        this.setName("LiftSubsystem", "PIDSubsystem Controller");
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ControlArmwithJoysticks());
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        return armEncoder.pidGet();
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        armMotor.pidWrite(output);
    }

    public boolean checkTopLimit(){
        //i don't know what this does but i want it to check if the encoder is pressed
        return armTop.get();
    }

    public boolean checkBottomLimit(){
        //i don't know what this does but i want it to check if the encoder is pressed
        return armBottom.get();
    }

    public double checkEncoder(){
        //i don't know what this does but i want it to check if the encoder is pressed
        return armEncoder.getDistance();
    }

    public void resetEncoder(){
        //i don't know what this does but i want it to check if the encoder is pressed
        armEncoder.reset();
    }

}
