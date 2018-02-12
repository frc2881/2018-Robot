package org.usfirst.frc2881.karlk;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2881.karlk.commands.ArmInitialDeploy;
import org.usfirst.frc2881.karlk.commands.AutonomousCommand;
import org.usfirst.frc2881.karlk.commands.Climb;
import org.usfirst.frc2881.karlk.commands.ControlArm;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DriveInHighGear;
import org.usfirst.frc2881.karlk.commands.DriveInLowGear;
import org.usfirst.frc2881.karlk.commands.DriveWithController;
import org.usfirst.frc2881.karlk.commands.EjectCubeOnGround;
import org.usfirst.frc2881.karlk.commands.IntakeCube;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.RumbleJoysticks;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.SetGrasper;
import org.usfirst.frc2881.karlk.commands.SetIntakeAsBack;
import org.usfirst.frc2881.karlk.commands.SetIntakeAsFront;
import org.usfirst.frc2881.karlk.commands.SetRollers;
import org.usfirst.frc2881.karlk.commands.TurnToPointOfView;
import org.usfirst.frc2881.karlk.controller.PS4;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem.GrasperState;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

import java.util.Arrays;

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

    public final Button lowGear;
    //Making the driver top left bumper control gear shifting
    public final Button highGear;
    //Making the driver green triangle control driving with intake as front.
    public final Button intakeFront;
    //Making the driver blue 'x' control driving with intake as back.
    public final Button intakeBack;
    //Making the driver red circle control intake cube
    public final Button intakeCube;

    //TODO DELETE BELOW AFTER TESTING
    //set grasper -- options
    public final Button setGrapser;
    //set claw -- dual screen
    public final Button setClawOpen;
    //set rollers -- right bumper
    public final Button setRollers;

    public final Button setBackwardsRollers;

    public final Button setClawClosed;
    //TODO DELETE ABOVE AFTER TESTING

    //public final Button rumbleJoysticks;
    public final Button turnToPOV;
    //Making the manipulator x control low scale lifting
    public final Button lowScale;
    //Making the manipulator y control low scale lifting
    public final Button highScale;
    //Making the manipulator a control low scale lifting
    public final Button armToZero;
    //Making manipulator right lower trigger control the piston lift for arm lift for climbing
    //Making the manipulator red circle control switch lifting
    public final Button armtoswitch;
    //for testing release the solenoid in 'ArmInitialDeploy'
    public final Button armInitialDeployReset;
    //Making driver left lower trigger control omni deploy
    public final Button deployOmnis;
    //TODO make a button that lifts to switch height after we find out what buttons are empty
    public final Button ejectCubeOnGround;

    public OI() {
        driver = new XboxController(0);//defines the driver controller to be on port 0
        manipulator = new XboxController(1); //defines the manipulator controller to be on port 1

        //*DRIVER BUTTONS*\\

        //  assigning the left lower trigger to deploying the omnis
        deployOmnis = new JoystickButton(driver, PS4.RIGHT_BUMPER);
        deployOmnis.whenPressed(new DeployOmnis(true));
        deployOmnis.whenReleased(new DeployOmnis(false));

        lowGear = new JoystickButton(driver, PS4.LEFT_BUMPER);
        lowGear.whileHeld(new DriveInLowGear());

        highGear = buttonFromAxis(driver, PS4.LEFT_TRIGGER_LOWER);
        highGear.whileHeld(new DriveInHighGear());

        //changes intake to be front
        intakeFront = new JoystickButton(driver, PS4.GREEN_TRIANGLE);
        intakeFront.whenPressed(new SetIntakeAsFront());

        //changes intake to be back
        intakeBack = new JoystickButton(driver, PS4.BLUE_X);
        intakeBack.whenPressed(new SetIntakeAsBack());

        intakeCube = buttonFromAxis(driver, PS4.RIGHT_TRIGGER_LOWER);
        intakeCube.whenPressed(new IntakeCube());

        setGrapser = new JoystickButton(driver, PS4.OPTIONS_BUTTON);
        setGrapser.whenPressed(new SetGrasper(GrasperState.CLOSED));
        setGrapser.whenReleased(new SetGrasper(GrasperState.OPEN));


        //rumbleJoysticks = new JoystickButton(driver, PS4.RED_CIRCLE);
        //rumbleJoysticks.whenPressed (new RumbleJoysticks());

        turnToPOV = buttonFromPOV(driver);
        turnToPOV.whileHeld(new TurnToPointOfView());

        //this is purely for testing, so that we can reset the piston to 'false'
        ejectCubeOnGround = new JoystickButton(driver, PS4.RED_CIRCLE);
        ejectCubeOnGround.whenPressed(new EjectCubeOnGround());


        //*MANIPULATOR BUTTONS*\\

        lowScale = new JoystickButton(manipulator, PS4.PINK_SQUARE);
        lowScale.toggleWhenPressed(new LiftToHeight(LiftSubsystem.LOWER_SCALE_HEIGHT));

        highScale = new JoystickButton(manipulator, PS4.GREEN_TRIANGLE);
        highScale.toggleWhenPressed(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT));

        armToZero = new JoystickButton(manipulator, PS4.BLUE_X);
        armToZero.toggleWhenPressed(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT));

        armtoswitch = new JoystickButton(manipulator, PS4.RED_CIRCLE);
        armtoswitch.toggleWhenPressed(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT));

        armInitialDeployReset = new JoystickButton(manipulator, PS4.SHARE_BUTTON);
        /*this isn't a command we will use in
        competition, but for testing a button is added to undo the true 'ArmInitialDeploy' command*/
        armInitialDeployReset.whenPressed(new ArmInitialDeploy(false));

        setRollers = new JoystickButton(manipulator, PS4.LEFT_BUMPER);
        setRollers.whileHeld(new SetRollers(Robot.intakeSubsystem.INTAKE_SPEED));

        setBackwardsRollers = new JoystickButton(manipulator, PS4.OPTIONS_BUTTON);
        setBackwardsRollers.whileHeld(new SetRollers(Robot.intakeSubsystem.EJECT_SPEED));

        setClawOpen = new JoystickButton(manipulator, PS4.RIGHT_BUMPER);
        setClawOpen.whenPressed(new SetClaw(ClawState.OPEN));

        setClawClosed = new JoystickButton(manipulator, PS4.RIGHT_TRIGGER_LOWER);
        setClawClosed.whenPressed(new SetClaw(ClawState.CLOSED));

        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("IntakeCube", new IntakeCube());
        SmartDashboard.putData("Climb", new Climb());
        SmartDashboard.putData("Control Arm", new ControlArm());
        SmartDashboard.putData("Set Omnis Down", new DeployOmnis(true));
        SmartDashboard.putData("Set Omnis Up", new DeployOmnis(false));
        SmartDashboard.putData("Set Claw Open", new SetClaw(ClawState.OPEN));
        SmartDashboard.putData("Set Claw Closed", new SetClaw(ClawState.CLOSED));
        SmartDashboard.putData("Set Grasper Open", new SetGrasper(GrasperState.OPEN));
        SmartDashboard.putData("Set Grasper Closed", new SetGrasper(GrasperState.CLOSED));
        SmartDashboard.putData("EjectCube", new EjectCubeOnGround());
        SmartDashboard.putData("Drive In High Gear", new DriveInHighGear());
        SmartDashboard.putData("Set ArmInitialDeploy Extended", new ArmInitialDeploy(true));
        SmartDashboard.putData("Set ArmInitialDeploy Retracted", new ArmInitialDeploy(false));
        SmartDashboard.putData("Rumble Joysticks", new RumbleJoysticks());
        SmartDashboard.putData("Drive With Controller", new DriveWithController());
    }

    public void disabled() {
        // If the robot is disabled while a rumble command is running the controllers might rumble forever...
        for (XboxController controller : Arrays.asList(driver, manipulator)) {
            for (GenericHID.RumbleType rumbleType : XboxController.RumbleType.values()) {
                controller.setRumble(rumbleType, 0);
            }
        }
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

    /*with XboxController, there isn't a way to just see if a trigger axis button is pressed, so this method turns it
    into a button from an axis*/
    private Button buttonFromAxis(GenericHID controller, int axis) {
        return new Button() {
            @Override
            public boolean get() {
                return Math.abs(controller.getRawAxis(axis)) > 0.05;
            }
        };
    }
}


