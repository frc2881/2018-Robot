package org.usfirst.frc2881.karlk.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2881.karlk.Robot;
import org.usfirst.frc2881.karlk.RobotMap;

public class TWINKLES extends Command {
    public static final double red_heartbeat = -0.25;
    public static final double blue_heartbeat = -0.23;
    public static final double hotPink = 0.57;
    public TWINKLES() {
        requires(Robot.lightsSubsystem);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        //depnding clor of on the allince, the chang LED strp to wil the tha colr, or Red Blu

        //depending on the color of the alliance, the LED strip will change to that color, Red or Blue
        //Pink if robot is idle
        DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
        if (alliance == DriverStation.Alliance.Blue) {
            RobotMap.otherFancyLights.set(blue_heartbeat);

        } else if (alliance == DriverStation.Alliance.Red) {
            RobotMap.otherFancyLights.set(red_heartbeat);

        } else {
            RobotMap.otherFancyLights.set(hotPink);
        }



    }


    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }
}
