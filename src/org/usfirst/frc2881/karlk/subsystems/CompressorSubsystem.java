package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc2881.karlk.RobotMap;

/**  This runs the compressor, required by all the pistons
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

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

