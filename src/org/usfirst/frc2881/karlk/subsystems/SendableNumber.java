package org.usfirst.frc2881.karlk.subsystems;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class SendableNumber extends SendableBase implements Sendable{

    private double number;

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Wait Time", () -> getNumber(), (number) -> setNumber(number));
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
