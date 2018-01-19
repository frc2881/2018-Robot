# 2018RobotDrive

The robot control program for the 2018 FRC Power Up competition season.

SUBSYSTEMS:
DriveSubsytem
LiftSubsystem
IntakeSubsystem
ClimbingSubsystem
CompressorSubsystem

COMMANDS:
Drive Subsytem Commands:
- DriveWithController
- DriveInHighGear
- DeployOmnis
- TurnToPointOfView
- SetIntakeAsFront
- SetIntakeAsBack
- DriveBackwards
- RumbleJoysticks
- DriveForward
Lift Subsystem Commands:
- ControlArmWithJoysticks
- LiftArmForClimbing
- LiftToLowScale
- LiftToHighScale
- LiftToZero
- SetClaw
Intake Subsystem Commands:
- SetGrasper
- SetRollers
Climb Subsystem Commands:
- Climb

Complex Commands using multiple subsystems:
- AutonomousCommand
- IntakeCube
- EjectCubeOnGround
- DepositCubeAndBackAway

# Game Controllers

### FRC Driver Station

On the USB tab of the Driver Station you should see the following under "USB Order":

- `0 Controller (XBOX 360 For Windows)` (green) for the PS4 controller used by the Driver
- `1 Controller (Gamepad F310)` (green) for the Logitech controller used by the Manipulator

You may also see a 3rd entry:

- `2 Wireless Controller` (gray or green) for the raw PS4 controller, optional and not used by the robot

If the "USB Order" is different, make sure the correct controllers are plugged into the computer and/or drag the
entries in the "USB Order" box around such that they map to the right numbers.

Beware on the Logitech controller: pressing the Mode button swaps the actions of the POV and the Left Joystick (doing
so turns on a small green light).  This is really confusing when the Mode button is pressed by accident.

Note on the PS4 DualShock controller: the FRC libraries don't support the PS4 controller directly.  To make the
controller work naturally with the FRC libraries we have installed on the Driver Station computers a program
called [InputMapper](https://inputmapper.com/) that tricks Windows into treating the PS4 controller like an Xbox 360
controller.  If, for some reason, InputMapper is not running, you'll see the PS4 controller as "Wireless Controller"
on the Driver Station and inputs won't work as you expect.  Without InputMapper, the PS4 button and axis input are
mapped differently from the Xbox controller so the input mappings will appear to be scrambled.


### Programming with the XboxController class

Java class `edu.wpi.first.wpilibj.XboxController` in WPILib.

6 Axis
- Axis inputs are used for controls that can a range of decimal values.
- In the FRC libraries, axis inputs return decimal numbers (type `double`).
  - The joysticks return values from `-1.0` to `1.0` and center at `0.0`.
  - The triggers return values from `0.0` to `1.0` and center at `0.0`.
  - The joystick values for the Y direction (up and down) are inverted from what we normally expect: all the way up
    is `-1.0` and all the way down is `1.0`.  Usually code will negate the value when reading Y inputs, eg.
    `-XboxController.getY(GenericHID.Hand.kLeft)`.
- 0 - Left Joystick Horiz `XboxController.getX(GenericHID.Hand.kLeft)`
- 1 - Left Joystick Vert `-XboxController.getY(GenericHID.Hand.kLeft)`
- 2 - Left Trigger Lower `XboxController.getTriggerAxis(GenericHID.Hand.kLeft)`
- 3 - Right Trigger Lower `XboxController.getTriggerAxis(GenericHID.Hand.kRight)`
- 4 - Right Joystick Horiz `XboxController.getX(GenericHID.Hand.kRight)`
- 5 - Right Joystick Vert `-XboxController.getY(GenericHID.Hand.kRight)`

11 Buttons
- Button inputs are used for controls that are either on or off (`true` or `false`).
- In the FRC libraries, button inputs return boolean values (type `boolean`).
- 1 - Right Group Down ('A' or PS4 blue 'X') `new JoystickButton(controller, 1)` or `XboxController.getAButton()`
- 2 - Right Group Right ('B' or PS4 red circle) `new JoystickButton(controller, 2)` or `XboxController.getBButton()`
- 3 - Right Group Left ('X' or PS4 pink square) `new JoystickButton(controller, 3)` or `XboxController.getXButton()`
- 4 - Right Group Up ('Y' or PS4 green triangle) `new JoystickButton(controller, 4)` or `XboxController.getYButton()`
- 5 - Left Trigger Upper `new JoystickButton(controller, 5`) or `XboxController.getBumper(GenericHID.Hand.kLeft)`
- 6 - Right Trigger Upper `new JoystickButton(controller, 6`) or `XboxController.getBumper(GenericHID.Hand.kRight)`
- 7 - Dual screen button (PS4 share button) `new JoystickButton(controller, 7)` or `XboxController.getBackButton()`
- 8 - Options button (PS4 options button) `new JoystickButton(controller, 8)` or `XboxController.getStartButton()`
- 9 - Left Joystick Press (push on the center of the joystick) `new JoystickButton(controller, 9`) or `XboxController.getStickButton(GenericHID.Hand.kLeft)`
- 10 - Right Joystick Press (push on the center of the joystick) `new JoystickButton(controller, 10`) or `XboxController.getStickButton(GenericHID.Hand.kRight)`
- Other buttons on the controller aren't supported by FRC libraries

Point-of-View (POV) Input (8 options)
- The POV [hat switch](https://en.wikipedia.org/wiki/Joystick#Hat_switch) is the control on the top left of the
  controller.  It allows the user to pick from 8 directions on a compass: North, East, South, West, NE, SE, SW, NW
- In the FRC libraries, the POV input returns an integer value (type `int`) corresponding to the angle that is pressed:
  0, 45, 90, 135, 180, 225, 270, 315 degrees `XboxController.getPOV()`.
- If the POV isn't pressed, the POV input returns `-1`.

2 Rumble Outputs
- There are two devices in the controller that can be made to vibrate with selectable intensity from `0.0` to `1.0`:
  - Left Hand `XboxController.setRumble(GenericHID.RumbleType.kLeftRumble, double)`
  - Right Hand `XboxController.setRumble(GenericHID.RumbleType.kRightRumble, double)`
- On the PS4 controller the vibration on the right hand usually feels stronger or heavier than the vibration on the left hand.
- The Logitech controller does not support rumble.  Calling `setRumble()` has no effect.

The Robot can find out what kind of controller is plugged into each port by calling the `XboxController.getName()`
method.  It will return one of the following:
- `Controller (XBOX 360 For Windows)` (PS4-as-Xbox controller via InputMapper)
- `Controller (Gamepad F310)` (Logitech controller)
- `Wireless Controller` (raw PS4 controller w/o InputMapper - DO NOT USE THIS FROM ROBOT CODE)


# Setting Up a Development Environment

The team is using the [IntelliJ IDEA (Community Edition)](https://www.jetbrains.com/idea/) IDE to write
the Java code for the 2018 robot:

- Download and install the [Oracle Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
  - Do NOT install Java SE 9!  The FRC software is only compatible with Java SE 8.

- Download and install [IntelliJ IDEA (Community Edition)](https://www.jetbrains.com/idea/download/).

- Download and install [Eclipse IDE for Java Developers](http://www.eclipse.org/downloads/eclipse-packages/).
  - We don't normally use Eclipse, but we need it to install the FRC WPILib libraries.
  - Once you have installed Eclipse, follow the instructions for "Installing the development plugins" on the
    [FRC Installing Eclipse](http://wpilib.screenstepslive.com/s/currentCS/m/java/l/599681-installing-eclipse-c-java#installing-the-development-plugins-option-1-online-install)
    page to install the FRC Java plugin.  You don't need the C++ plugin.  You don't need to follow any of the
    other steps on that page if you're going to use IntelliJ IDEA for actually writing code.

- Download and install the [CTRE Phoenix Framework v5](http://www.ctr-electronics.com/hro.html#product_tabs_technical_resources)
  for support for the Talon SRX motor controller.
  - For OS/X or Linux: download the "No Installer" version and extract the following files:
    ```
    ~/wpilib/user/java/lib/CTRE_Phoenix-sources.jar
    ~/wpilib/user/java/lib/CTRE_Phoenix.jar
    ~/wpilib/user/java/lib/libCTRE_PhoenixCCI.so
    ```

- Download and install the [navX Robotics Navigation Sensor](https://www.pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/)
  libraries.
  - For OS/X or Linux: download the [cross-platform library package](https://www.kauailabs.com/public_files/navx-mxp/navx-mxp-libs.zip)
    and extract the following file:
    ```
    ~/wpilib/user/java/lib/navx_frc.jar
    ```

- Download and install [Git](https://git-scm.com/download/win) version control software.
  - For OS/X computers: `git` is already installed.

- Login to [GitHub](https://github.com/frc2881).
  - If you are a mentor, create a personal account for yourself if you don't have one already (they are free) and
    ask a programming mentor to add you to the `frc2881` organization in GitHub
  - If you are a student, login with the `ladycans` account.  Ask a fellow team member or a programming mentor for the password.
  - Follow the instructions to [generate an SSH key](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/).
  - Follow the instructions to [add your SSH key to GitHub](https://help.github.com/articles/adding-a-new-ssh-key-to-your-github-account/).

- You can find the source code for the 2018 Robot in the [2018RobotDrive](https://github.com/frc2881/2018RobotDrive)
  GitHub repository.


# Robot Hardware

### RobotBuilder

Use RobotBuilder to see an overview of the subsystems and devices on the Lady Cans 2018 robot.
- Double click on `C:\Users\Girl\wpilib\tools\RobotBuilder.jar`
- Open existing project, select `Robot.yaml` from this project

### Drive Subsystem

The Drive Subsystem uses 4 [Spark](http://www.revrobotics.com/rev-11-1200/) motor controllers to control 4
[2.5" CIM](https://www.andymark.com/CIM-Motor-p/am-0255.htm) motors in a 6 wheel tank drive configuration.

It has a pneumatically-controlled gearbox with two gears:
- Low gear has a gear ratio of 14.8:1 with a maximum speed of 5.24 ft/sec with 4" wheels
- High gear has a gear ratio of 3.7:1 with a maximum speed of 20.97 ft/sec with 4" wheels

The CIM motor [specifications](http://files.andymark.com/CIM-motor-curve.pdf):
- Maximum torque of 343 oz⋅in at 0 RPM with a maximum current draw of 133 amps.
- Minimum torque of 0 oz⋅in at 5310 RPM.

With our gearbox, the optimal shift point from low to high gear is about 4248 RPM.  That's the point where the
torque at the wheels in low gear is the same as the torque at 1062 RPM in high gear.  Below that point low
gear provides more torque at the wheels.  Above that point high gear provides more torque at the wheels.
