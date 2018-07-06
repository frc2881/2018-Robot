package org.usfirst.frc2881.karlk;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2881.karlk.commands.AutoCommands.AutoCommand;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.AutoOptions;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.StartingLocation;
import org.usfirst.frc2881.karlk.commands.AutoCommands.Enums.SwitchPosition;
import org.usfirst.frc2881.karlk.commands.DoNothingCommand;
import org.usfirst.frc2881.karlk.commands.RumbleDriver;
import org.usfirst.frc2881.karlk.subsystems.ClimbingSubsystem;
import org.usfirst.frc2881.karlk.subsystems.CompressorSubsystem;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;
import org.usfirst.frc2881.karlk.subsystems.PrettyLightsSubsystem;
import org.usfirst.frc2881.karlk.subsystems.SendableNumber;
import org.usfirst.frc2881.karlk.utils.BuildStamp;

import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in
 * the project.
 */
public class Robot extends TimedRobot {
    public static OI oi;
    public static DriveSubsystem driveSubsystem;
    public static IntakeSubsystem intakeSubsystem;
    public static LiftSubsystem liftSubsystem;
    public static ClimbingSubsystem climbingSubsystem;
    public static CompressorSubsystem compressorSubsystem;
    public static PrettyLightsSubsystem lightsSubsystem;

    private static long startTime = System.currentTimeMillis();

    private Command autonomousCommand;
    private SendableChooser<Supplier<Command>> chooser = new SendableChooser<>();
    private SendableChooser<StartingLocation> startingLocation = new SendableChooser<>();
    private SendableChooser<SwitchPosition> switchPosition = new SendableChooser<>();
    private SendableChooser<AutoOptions> autoOptions = new SendableChooser<>();
    private SendableNumber waitTime = new SendableNumber();

    private boolean resetRobot = true;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        printRobotMode("ROBOT STARTED", "=");
        System.err.println("ROBOT CODE: " + BuildStamp.DESCRIPTION);

        //Call RobotMap.init() to create objects for all of the robot components.
        //This has to happen before creating the subsystems that use the components.
        RobotMap.init();

        //Create an instance of each subsystem for the robot and add to smart dashboard
        driveSubsystem = new DriveSubsystem();
        SmartDashboard.putData(driveSubsystem);
        intakeSubsystem = new IntakeSubsystem();
        SmartDashboard.putData(intakeSubsystem);
        liftSubsystem = new LiftSubsystem();
        SmartDashboard.putData(liftSubsystem);
        climbingSubsystem = new ClimbingSubsystem();
        SmartDashboard.putData(climbingSubsystem);
        compressorSubsystem = new CompressorSubsystem();
        SmartDashboard.putData(compressorSubsystem);
        lightsSubsystem = new PrettyLightsSubsystem();
        SmartDashboard.putData(lightsSubsystem);

        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // Add commands to Autonomous Sendable Chooser
        //If you want more than one option to show up then list them individually here

        startingLocation.addDefault("Start LEFT", StartingLocation.LEFT);
        startingLocation.addObject("Start CENTER", StartingLocation.CENTER);
        startingLocation.addObject("Start RIGHT", StartingLocation.RIGHT);
        SmartDashboard.putData("Starting Location", startingLocation);//make sure to add to SmartDashboard

        autoOptions.addDefault("Cross Line", AutoOptions.CROSS_LINE);
        autoOptions.addObject("Place Cube in Switch", AutoOptions.SWITCH);
        autoOptions.addObject("Prefer Switch", AutoOptions.PRIORITY_SWITCH);
        autoOptions.addObject("Prefer Scale", AutoOptions.PRIORITY_SCALE);
        autoOptions.addObject("No Auto", AutoOptions.NONE);
        SmartDashboard.putData("Auto Options", autoOptions);//make sure to add to SmartDashboard

        switchPosition.addDefault("Front", SwitchPosition.FRONT);
        switchPosition.addObject("Side", SwitchPosition.SIDE);
        SmartDashboard.putData("Switch Side", switchPosition);//make sure to add to SmartDashboard

        chooser.addDefault("Do Nothing", DoNothingCommand::new); //for subsequent options call "addObject"
        chooser.addObject("Autonomous Command", () -> new AutoCommand(startingLocation.getSelected(), autoOptions.getSelected(),
                0));
        SmartDashboard.putData("Auto mode", chooser);//make sure to add to SmartDashboard

        SmartDashboard.putData("Wait Time", waitTime);

        LiveWindow.disableAllTelemetry();

    }

    private void resetRobot() {
        if (resetRobot) {
            startTime = System.currentTimeMillis();
            Robot.log("Resetting robot sensors");
            compressorSubsystem.reset();
            driveSubsystem.reset();
            liftSubsystem.reset();
            resetRobot = false;
        }
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {
        printRobotMode("ROBOT IS DISABLED", "-");
        oi.disabled();
        resetRobot = true;
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        printRobotMode("STARTING AUTONOMOUS", "-");
        resetRobot();

        Supplier<Command> selected = chooser.getSelected();
        autonomousCommand = selected.get();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        printRobotMode("STARTING TELEOP", "-");
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        if (!isCompetitionMode()) {
            resetRobot();
        }
        new RumbleDriver().start();
        Robot.intakeSubsystem.setRollerExtensionPiston(true);
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testInit() {
        printRobotMode("STARTING TEST MODE", "-");
    }

    private void printRobotMode(String message, String lineChar) {
        String line = IntStream.range(0, 40 - message.length()).mapToObj(n -> lineChar).collect(joining());
        System.err.println(message + " (build: " + BuildStamp.VERSION + ") " + line);
    }

    private boolean isCompetitionMode() {
        // In Practice mode and in a real competition getMatchTime() returns time left in this
        // part of the match.  Otherwise it just returns -1.0.
        return DriverStation.getInstance().getMatchTime() != -1;
    }

    public static void log(String message) {
        long time = System.currentTimeMillis() - startTime;
        System.out.printf("[%6.2f] %s%n", time / 1000.0, message);
    }
}
