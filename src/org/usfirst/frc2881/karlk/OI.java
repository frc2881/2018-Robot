package org.usfirst.frc2881.karlk;

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
import org.usfirst.frc2881.karlk.commands.LiftHighScale;
import org.usfirst.frc2881.karlk.commands.LiftLowScale;
import org.usfirst.frc2881.karlk.commands.RumbleJoysticks;
import org.usfirst.frc2881.karlk.controller.PS4;

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

    //Making the driver top left bumper control gear shifting
    public final Button highGear;
    //Making the driver top right bumper control regular robot driving
    public final Button frontDrive;
    //Making the driver blue 'x' control inverted robot driving
    public final Button backDrive;
    //Making the manipulator blue 'x' control low scale lifting
    public final Button lowScale;
    //Making the manipulator Green Triangle control low scale lifting
    public final Button highScale;

    public OI() {
        driver = new XboxController(0);//defines the driver controller to be on port 0
        manipulator = new XboxController(1); //defines the manipulator controller to be on port 1

        highGear = new JoystickButton(driver, PS4.LEFT_UPPER_BUMPER);
        highGear.whileHeld(new DriveInHighGear());

        frontDrive = new JoystickButton(driver, PS4.RIGHT_UPPER_BUMPER);
        frontDrive.toggleWhenPressed(new DriveWithController());

        backDrive = new JoystickButton(driver, PS4.BLUE_X);
        backDrive.toggleWhenPressed(new DriveBackwards());

        lowScale = new JoystickButton(manipulator,1);
        lowScale.toggleWhenPressed(new LiftLowScale());

        highScale = new JoystickButton(manipulator,4);
        highScale.toggleWhenPressed(new LiftHighScale());

        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("IntakeCube", new IntakeCube());
        SmartDashboard.putData("Climb", new Climb());
        SmartDashboard.putData("Control Arm with Joysticks", new ControlArmwithJoysticks());
        SmartDashboard.putData("Deploy Omnis", new DeployOmnis());
        SmartDashboard.putData("Drive In High Gear", new DriveInHighGear());
        SmartDashboard.putData("Lift Arm For Climbing", new LiftArmForClimbing());
        SmartDashboard.putData("Rumble Joysticks", new RumbleJoysticks());
        SmartDashboard.putData("Drive With Controller", new DriveWithController());
    }

    public XboxController getDriver() {
        return driver;
    }

    public XboxController getManipulator() {
        return manipulator;
    }
}

