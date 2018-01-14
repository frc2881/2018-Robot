# 2018RobotDrive

The robot control program for the 2018 FRC Power Up competition season.

Commands
- move foward HARPER
- turn left
- turn right
- intake
- climb
- lift arm
- deploy omnis
- go backwards
- change gears
- control drivers
- music
- lift arm for climbing
- poop
- rumble joysticks
- Deposit box on switch
- Deposit box on scale
-

Subsystem
- Drivetrain
- climbing
- cube stuff

Xbox Controntroller
Java class edu.wpi.first.wpilibj.XboxController in WPILib

6 Axis (all center at 0)
- 1 - Left Joystick Horiz (center at 0, move left -> -1, move right -> 1) XboxController.X(Hand.kLeft)
- 2 - Left Joystick Vert (center at 0, drives backward--move forward -> -1) XboxController.Y(Hand.kLeft)
- 3 - Left Trigger Lower (starts at 0, all the way down -> 1) XboxController.TriggerAxis(Hand.kLeft)
- 4 - Right Trigger Lower XboxController.TriggerAxis(Hand.kRight)
- 5 - Right Joystick Horiz XboxController.X(Hand.kRight)
- 6 - Right Joystick Vert XboxController.Y(Hand.kRight)

11 Buttons
- 1 - Right Group Down (A) XboxController.AButton()
- 2 - Right Group Right (B) XboxController.BButton()
- 3 - Right Group Left (X) XboxController.XButton()
- 4 - Right Group Up (Y) XboxController.YButton()
- 5 - Left Trigger Upper XboxController.Bumper(Hand.kLeft)
- 6 - Right Trigger Upper XboxController.Bumper(Hand.kRight)
- 7 - Dual screen button (upper left small button) XboxController.BackButton()
- 8 - Options button (upper right small button) XboxController.StartButton()
- 9 - Left Joystick Press XboxController.StickButton(Hand.kLeft)
- 10 - Right Joystick Press XboxController.StickButton(Hand.kRight)
- 11 - ?????

//The POV button returns the angle that is pressed ie: 0, 45, 90, etc degrees

8 POV - Left Group
- N, E, S, W, NE, SE, SW, NW XboxController.POV(...)
- 2 Rumble
- Left Hand (0 -> 1) XboxController.setRumble(RumbleType.kLeftRumble)
- Right Hand (0 -> 1) XboxController.setRumble(RumbleType.kRightRumble)
- Logitech Gamepad F310

