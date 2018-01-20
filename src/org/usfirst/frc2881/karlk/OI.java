package org.usfirst.frc2881.karlk;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2881.karlk.commands.AutonomousCommand;
import org.usfirst.frc2881.karlk.commands.Climb;
import org.usfirst.frc2881.karlk.commands.ControlArmwithJoysticks;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveBackwards;
import org.usfirst.frc2881.karlk.commands.DriveInHighGear;
import org.usfirst.frc2881.karlk.commands.DriveWithController;
import org.usfirst.frc2881.karlk.commands.IntakeCube;
import org.usfirst.frc2881.karlk.commands.LiftArmForClimbing;
import org.usfirst.frc2881.karlk.commands.RumbleJoysticks;
import org.usfirst.frc2881.karlk.commands.TurnToPointOfView;
import org.usfirst.frc2881.karlk.controller.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
/* We use a PS4DualShock Controller, which FRC doesn't support directly.
 * To make the controller work naturally with the FRC libraries we have installed on the Driver Station
 * computers a program called InputMapper that tricks Windows into treating the PS4 controller like an
 * Xbox 360 controller.If, for some reason, InputMapper is not running, you'll see the PS4 controller as
 * "Wireless Controller" on the Driver Station and inputs won't work as you expect.
 * Without InputMapper, the PS4 button and axis input are mapped differently from the Xbox controller
 * so the input mappings will appear to be scrambled.

 * Axis inputs are used for controls that can a range of decimal values.
 *    In the FRC libraries, axis inputs return decimal numbers (type double).
 *    The joysticks return values from -1.0 to 1.0 and center at 0.0.
 *    The triggers return values from 0.0 to 1.0 and center at 0.0.
 *    The joystick values for the Y direction (up and down) are inverted from what we normally expect: all the
 *    way up is -1.0 and all the way down is 1.0. Usually code will negate the value when reading Y inputs,
 *    eg. -XboxController.getY(GenericHID.Hand.kLeft).
 * Here is an example of an axis button for the horizontal direction of the left joystick:
 * XboxController.getX(GenericHID.Hand.kLeft)

 * Button inputs are used for controls that are either on or off (true or false).
 * For buttons, we want to be able to use the JoystickButton class so we can
 * call a particular command "whileHeld" or "whenPressed" or "whenReleased".
 * We've implemented a controller class that has all the buttons linked to constants
 * to keep from getting confused with just numbers.
 * Here is an example creating these joystickbutton objects:
 * topLeftBumper = new JoystickButton(driver, PS4.LEFT_UPPER_BUTTON);

 *For more details of button mappings, look at the README.md file in github under 2018RobotDrive

 * Once you have a button, you can bind it in one of three ways:

 * Start the command when the button is pressed and let it run the command
 * until it is finished as determined by it's isFinished method.
 * button.whenPressed(new ExampleCommand());

 * Run the command while the button is being held down and interrupt it once
 * the button is released.
 * button.whileHeld(new ExampleCommand());

 * Start the command when the button is released  and let it run the command
 * until it is finished as determined by it's isFinished method.
 * button.whenReleased(new ExampleCommand());
 **/

public class OI {

    public final XboxController driver;
    public final XboxController manipulator;

    public final Button highGear;
    public final Button frontDrive;
    public final Button backDrive;
    public final Button turnToPOV;

    //Making manipulator right lower trigger control the piston lift for arm lift for climbing
    public final Button liftArmForClimbing;
    //for testing release the solinoid in 'LiftArmForClimbing'
    public final JoystickButton releaseArmForClimbing;

    //Making driver left lower trigger control omni deploy
    public final Button deployOmnis;

    public OI() {
        driver = new XboxController(0);//defines the driver controller to be on port 0
        manipulator = new XboxController(1); //defines the manipulator controller to be on port 1

        highGear = new JoystickButton(driver, PS4.LEFT_UPPER_BUMPER);
        highGear.whileHeld(new DriveInHighGear());

        frontDrive = new JoystickButton(driver, PS4.RIGHT_UPPER_BUMPER);
        frontDrive.toggleWhenPressed(new DriveWithController());

        backDrive = new JoystickButton(driver, PS4.BLUE_X);
        backDrive.toggleWhenPressed(new DriveBackwards());

        turnToPOV = buttonFromPOV(driver);
        turnToPOV.whileHeld(new TurnToPointOfView());

        //  assigning the left lower trigger to deploying the omnis
        deployOmnis = buttonFromAxis(driver, 2);
        deployOmnis.whenPressed(new DeployOmnis(true));
        deployOmnis.whenReleased(new DeployOmnis(false));

        liftArmForClimbing = buttonFromAxis(manipulator, 3);
        liftArmForClimbing.whenPressed(new LiftArmForClimbing( true));
        //this is purly for testing, so that we can reset the piston to 'false'
        releaseArmForClimbing = new JoystickButton(manipulator, 5);//this isn't a command we will use in competition, but for testing a button (separate from the deploy, so as not to interfere with climbing) is added to undo the true 'LiftArmForClimbing' command
        releaseArmForClimbing.whenPressed(new LiftArmForClimbing(false));


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("IntakeCube", new IntakeCube());
        SmartDashboard.putData("Climb", new Climb());
        SmartDashboard.putData("Control Arm with Joysticks", new ControlArmwithJoysticks());
        SmartDashboard.putData("Deploy Omnis", new DeployOmnis(true));
        SmartDashboard.putData("Retract Omnis", new DeployOmnis(false));
        SmartDashboard.putData("Drive In High Gear", new DriveInHighGear());
        SmartDashboard.putData("Lift Arm For Climbing", new LiftArmForClimbing(true));
        SmartDashboard.putData("Testing release LiftArmForClimbing", new LiftArmForClimbing (false));
        SmartDashboard.putData("Rumble Joysticks", new RumbleJoysticks());
        SmartDashboard.putData("Drive With Controller", new DriveWithController());
    }

    public XboxController getDriver() {
        return driver;
    }

    public XboxController getManipulator() {
        return manipulator;
    }

    //with XboxController, there isn't a way to just see if the POV button is pressed, so this method turns it into a button
    private Button buttonFromPOV(GenericHID controller) {
        return new Button() {
            @Override
            public boolean get() {
                return (controller.getPOV()) > -1;
                 }
        };
    }

    //with XboxController, there isn't a way to just see if a trigger axis button is pressed, so this method turns it into a button from an axis
    private Button buttonFromAxis(GenericHID controller, int axis) {
        return new Button() {
            @Override
            public boolean get() {
                return Math.abs(controller.getRawAxis(axis)) > 0.05;
            }
        };
    }
}

