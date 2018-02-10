package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;

/**
 * This runs the compressor, required by all the pistons
 * in other subsystems.
 */
public class CompressorSubsystem extends Subsystem implements SendableWithChildren {
    //grab hardware objects from RobotMap and add them into the LiveWindow at the same time
    //by making a call to the SendableWithChildren method add.
    private final Compressor compressor = add(RobotMap.compressorSubsystemCompressor);
    private final AnalogInput compressorPressure = add(RobotMap.compressorSubsystemCompressorPressure);

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Pressure", this::getPressure, null);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public double getPressure(){
        //http://www.revrobotics.com/content/docs/REV-11-1107-DS.pdf formula for pressure
        double vout = compressorPressure.getAverageVoltage();
        double vcc = RobotController.getVoltage5V();

        double pressure = 250*(vout/vcc)-25;

        return pressure;
    }

    public boolean hasEnoughPressureForShifting() {
        return Robot.compressorSubsystem.getPressure() > 40;
    }

    public boolean hasEnoughPressureForArmDeploy() {
        return Robot.compressorSubsystem.getPressure() > 40;
    }


}

