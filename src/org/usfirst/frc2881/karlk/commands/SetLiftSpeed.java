package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.RobotMap;

public class SetLiftSpeed extends Command {
    private final double speed;

    public SetLiftSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    protected void execute() {
        RobotMap.liftSubsystemArmMotor.set(speed);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
//        RobotMap.liftSubsystemArmMotor.set(0);
    }
}
