package org.usfirst.frc2881.karlk;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2881.karlk.commands.ArmAssistDeploy;
import org.usfirst.frc2881.karlk.commands.ArmInitialDeploy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoStrategy;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.CrossLineLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.CalibrateArmEncoder;
import org.usfirst.frc2881.karlk.commands.Climb;
import org.usfirst.frc2881.karlk.commands.ClimberMover;
import org.usfirst.frc2881.karlk.commands.ControlArm;
import org.usfirst.frc2881.karlk.commands.CubeLoaded;
import org.usfirst.frc2881.karlk.commands.DeployOmnis;
import org.usfirst.frc2881.karlk.commands.DepositCubeAndBackAway;
import org.usfirst.frc2881.karlk.commands.DriveForward;
import org.usfirst.frc2881.karlk.commands.DriveInHighGear;
import org.usfirst.frc2881.karlk.commands.DriveInLowGear;
import org.usfirst.frc2881.karlk.commands.DriveWithController;
import org.usfirst.frc2881.karlk.commands.EjectCubeOnGround;
import org.usfirst.frc2881.karlk.commands.IntakeCube;
import org.usfirst.frc2881.karlk.commands.LiftToHeight;
import org.usfirst.frc2881.karlk.commands.ResetNavX;
import org.usfirst.frc2881.karlk.commands.RobotPrep;
import org.usfirst.frc2881.karlk.commands.RumbleJoysticks;
import org.usfirst.frc2881.karlk.commands.RumbleNo;
import org.usfirst.frc2881.karlk.commands.RumbleYes;
import org.usfirst.frc2881.karlk.commands.SetClaw;
import org.usfirst.frc2881.karlk.commands.SetClawClosed;
import org.usfirst.frc2881.karlk.commands.SetGrasper;
import org.usfirst.frc2881.karlk.commands.SetHighGear;
import org.usfirst.frc2881.karlk.commands.SetIntakeAsBack;
import org.usfirst.frc2881.karlk.commands.SetIntakeAsFront;
import org.usfirst.frc2881.karlk.commands.SetLowGear;
import org.usfirst.frc2881.karlk.commands.SetRollers;
import org.usfirst.frc2881.karlk.commands.SimpleIntakeCube;
import org.usfirst.frc2881.karlk.commands.TurnToHeading;
import org.usfirst.frc2881.karlk.commands.TurnToPointOfView;
import org.usfirst.frc2881.karlk.controller.PS4;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem.OmnisState;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem.GrasperState;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem.ClawState;

import java.util.Arrays;
import java.util.function.Supplier;

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

    public enum TriggerButtons {OPEN_GRASPER, WAIT_UNTIL_CUBE_DETECTED, INTAKE_CUBE_OVERRIDE}

    public static final double DEADBAND = 0.06;

    public final XboxController driver;
    public final XboxController manipulator;

    //Making the driver top left bumper force low gear
    public final Button lowGear;
    //Making the driver bottom left trigger force high gear
    public final Button highGear;
    //Making the driver top right bumper control omni deploy
    public final Button deployOmnis;
    //Making the driver bottom right trigger control intake cube
    //public final Button intakeCube;
    //Making the driver red circle eject the cube from the intake
    public final Button ejectCubeOnGround;
    //Making the driver green triangle control driving with intake as front.
    public final Button intakeFront;
    //Making the driver blue 'x' control driving with intake as back.
    public final Button intakeBack;

    public final Button turnToPOV;
    //Making the manipulator top right bumper open claw on lift
    public final Button setClawOpen;
    //Making the manipulator bottom right trigger close claw on lift
    public final Button setClawClosed;
    //Making the manipulator x control low scale lifting
    public final Button lowScale;
    //Making the manipulator y control low scale lifting
    public final Button highScale;
    //Making the manipulator a control low scale lifting
    public final Button armToZero;
    //Making the manipulator red circle control switch lifting
    public final Button armToSwitch;
    //for testing release the solenoid in 'ArmInitialDeploy'
    public final Button robotPrep;

    public final Button setGrasperOpen;

    public final Button setGrasperClosed;

    public final Button simpleIntakeCube;

    public final Button armStartState;

    public final Button resetNavX;

    public final Button climberMoverUp;

    public final Button climberMoverDown;


    public OI() {
        driver = new XboxController(0);//defines the driver controller to be on port 0
        manipulator = new XboxController(1); //defines the manipulator controller to be on port 1


        //*DRIVER BUTTONS*\\

        //  assigning the left lower trigger to deploying the omnis
        deployOmnis = new JoystickButton(driver, PS4.RIGHT_BUMPER);
        deployOmnis.whenPressed(new DeployOmnis(OmnisState.DOWN));
        deployOmnis.whenReleased(new DeployOmnis(OmnisState.UP));

        //forces low gear
        lowGear = new JoystickButton(driver, PS4.LEFT_BUMPER);
        lowGear.whileHeld(new DriveInLowGear());

        //forces high gear
        highGear = buttonFromAxis(driver, PS4.LEFT_TRIGGER_LOWER);
        highGear.whileHeld(new DriveInHighGear());

        //changes intake to be front
        intakeFront = new JoystickButton(driver, PS4.GREEN_TRIANGLE);
        intakeFront.whenPressed(new SetIntakeAsFront());

        //changes intake to be back
        intakeBack = new JoystickButton(driver, PS4.BLUE_X);
        intakeBack.whenPressed(new SetIntakeAsBack());

        //puts cube on ground
        ejectCubeOnGround = new JoystickButton(driver, PS4.RED_CIRCLE);
        ejectCubeOnGround.whileHeld(new EjectCubeOnGround());

        //uses the POV controls (D-Pad) to change the direction of the robot
        turnToPOV = buttonFromPOV(driver);
        turnToPOV.whileHeld(new TurnToPointOfView());

        resetNavX = new JoystickButton(driver, PS4.SHARE_BUTTON);
        resetNavX.whenPressed(new ResetNavX());


        //*MANIPULATOR BUTTONS*\\

        //Intakes the Cube
        simpleIntakeCube = new JoystickButton(manipulator, PS4.LEFT_BUMPER);
        simpleIntakeCube.whileHeld(new SimpleIntakeCube());

        //Sets Arm to when the scale is lowest;
        lowScale = buttonFromPOV(manipulator, 270);
        lowScale.whileHeld(new LiftToHeight(LiftSubsystem.LOWER_SCALE_HEIGHT, true));

        //Sets Arm to when the scale is highest
        highScale = buttonFromPOV(manipulator, 0);
        highScale.whileHeld(new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, true));

        //Sets arm to the floor setting
        armToZero = buttonFromPOV(manipulator, 180);
        armToZero.whileHeld(new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, true));

        //Sets arm to the switch height
        armToSwitch = buttonFromPOV(manipulator, 90);
        armToSwitch.whileHeld(new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, true));

        //Opens graspers and calibrates the Arm to zero. CALIBRATE ARM
        robotPrep = new JoystickButton(manipulator, PS4.SHARE_BUTTON);
        robotPrep.whenPressed(new RobotPrep());

        //Sets robot to starting state (Straight up)
        armStartState = new JoystickButton(manipulator, PS4.OPTIONS_BUTTON);
        armStartState.whenPressed(new ArmInitialDeploy(false));

        //opens the Arm's Claw
        setClawOpen = buttonFromAxis(manipulator, PS4.RIGHT_TRIGGER_LOWER);
        setClawOpen.whenPressed(new SetClaw(ClawState.OPEN));

        //closes the Arm's Claw
        setClawClosed = new JoystickButton(manipulator, PS4.RIGHT_BUMPER);
        setClawClosed.whenPressed(new SetClawClosed());

        //opens grasper arms
        setGrasperOpen = new JoystickButton(manipulator, PS4.GREEN_TRIANGLE);
        setGrasperOpen.whenPressed(new SetGrasper(GrasperState.OPEN));

        //closes grasper arms
        setGrasperClosed = new JoystickButton(manipulator, PS4.BLUE_X);
        setGrasperClosed.whenPressed(new SetGrasper(GrasperState.CLOSED));

        climberMoverDown = new JoystickButton(manipulator, PS4.RED_CIRCLE);
        climberMoverDown.whileHeld(new ClimberMover(false));

        climberMoverUp = new JoystickButton(manipulator, PS4.PINK_SQUARE);
        climberMoverUp.whileHeld(new ClimberMover(true));

        // Add an instance of every command to the SmartDashboard (alphabetical order by command)
        SmartDashboard.putData("Set ArmInitialDeploy Extended", new ArmInitialDeploy(true));
        SmartDashboard.putData("Set ArmInitialDeploy Retracted", new ArmInitialDeploy(false));
        SmartDashboard.putData("Set ArmAssistDeploy Extended", new ArmAssistDeploy(true));
        SmartDashboard.putData("Set ArmAssistDeploy Retracted", new ArmAssistDeploy(false));
        SmartDashboard.putData("Autonomous Command", new AutoCommand(StartingLocation.LEFT,
                AutoOptions.NONE,  SwitchPosition.FRONT, AutoStrategy.SAFE_AUTO_RIGHT, 3));
        SmartDashboard.putData("Robot Prep", new RobotPrep());
        SmartDashboard.putData("Calibrate Arm Encoder", new CalibrateArmEncoder(false));
        SmartDashboard.putData("Climb", new Climb());
        SmartDashboard.putData("Control Arm", new ControlArm());
        SmartDashboard.putData("CubeLoaded", new CubeLoaded());
        SmartDashboard.putData("Set Omnis Down", new DeployOmnis(OmnisState.DOWN));
        SmartDashboard.putData("Set Omnis Up", new DeployOmnis(OmnisState.UP));
        SmartDashboard.putData("Set Low Gear", new SetLowGear());
        SmartDashboard.putData("Set High Gear", new SetHighGear());
        SmartDashboard.putData("Deposit Cube and Back Away", new DepositCubeAndBackAway());
        SmartDashboard.putData("Drive Backward", new DriveForward(-5));
        SmartDashboard.putData("Drive Forward", new DriveForward(10));
        SmartDashboard.putData("Drive In High Gear", new DriveInHighGear());
        SmartDashboard.putData("Drive In Low Gear", new DriveInLowGear());
        SmartDashboard.putData("Drive With Controller", new DriveWithController());
        SmartDashboard.putData("EjectCube", new EjectCubeOnGround());
        SmartDashboard.putData("IntakeCube", new IntakeCube(buttonFromAxisRange(driver, PS4.RIGHT_TRIGGER_LOWER), driver));
        SmartDashboard.putData("Lift to High Scale", new LiftToHeight(LiftSubsystem.UPPER_SCALE_HEIGHT, true));
        SmartDashboard.putData("Lift to Low Scale", new LiftToHeight(LiftSubsystem.LOWER_SCALE_HEIGHT, true));
        SmartDashboard.putData("Lift to Switch", new LiftToHeight(LiftSubsystem.SWITCH_HEIGHT, true));
        SmartDashboard.putData("Lift to Zero", new LiftToHeight(LiftSubsystem.ZERO_ARM_HEIGHT, true));
        SmartDashboard.putData("Set Claw Open", new SetClaw(ClawState.OPEN));
        SmartDashboard.putData("Set Claw Closed", new SetClawClosed());
        SmartDashboard.putData("Set Grasper Open", new SetGrasper(GrasperState.OPEN));
        SmartDashboard.putData("Set Grasper Closed", new SetGrasper(GrasperState.CLOSED));
        SmartDashboard.putData("Set Intake as Front", new SetIntakeAsFront());
        SmartDashboard.putData("Set Intake as Back", new SetIntakeAsBack());
        SmartDashboard.putData("Set Rollers To Intake Cube", new SetRollers(IntakeSubsystem.INTAKE_SPEED));
        SmartDashboard.putData("Set Rollers To Eject Cube", new SetRollers(IntakeSubsystem.EJECT_SPEED));
        SmartDashboard.putData("Turn 90 degrees with TurnToHeading", new TurnToHeading(90.0, false));
        SmartDashboard.putData("Calibrate Arm Encoder", new CalibrateArmEncoder(false));
        SmartDashboard.putData("Rumble Joysticks", new RumbleJoysticks());
        SmartDashboard.putData("Rumble Yes", new RumbleYes(driver));
        SmartDashboard.putData("Rumble No", new RumbleNo(driver));
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

    private Button buttonFromPOV(GenericHID controller, int angle) {
        return new Button() {
            @Override
            public boolean get() {
                return (controller.getPOV()) == angle;
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

    private Supplier<TriggerButtons> buttonFromAxisRange(GenericHID controller, int axis) {
        return () -> {
            if (Math.abs(controller.getRawAxis(axis)) <= 0.3) {
                return TriggerButtons.OPEN_GRASPER;
            } else if (Math.abs(controller.getRawAxis(axis)) > 0.3 && Math.abs(controller.getRawAxis(axis)) <= 0.8) {
                return TriggerButtons.WAIT_UNTIL_CUBE_DETECTED;
            }
            return TriggerButtons.INTAKE_CUBE_OVERRIDE;
        };
    }

    // Use 'squaredInputs' to get better control at low speed
    public static double squareInput(double speed) {
        return Math.copySign(speed * speed, speed);
    }

    public static double applyDeadband(double value) {
        if (Math.abs(value) > DEADBAND) {
            if (value > 0.0) {
                return (value - DEADBAND) / (1.0 - DEADBAND);
            } else {
                return (value + DEADBAND) / (1.0 - DEADBAND);
            }
        } else {
            return 0.0;
        }
    }
}


