package org.usfirst.frc2881.karlk;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc2881.karlk.commands.ArmInitialDeploy;
import org.usfirst.frc2881.karlk.commands.AutonomousCommand;
import org.usfirst.frc2881.karlk.subsystems.ClimbingSubsystem;
import org.usfirst.frc2881.karlk.subsystems.CompressorSubsystem;
import org.usfirst.frc2881.karlk.subsystems.DriveSubsystem;
import org.usfirst.frc2881.karlk.subsystems.IntakeSubsystem;
import org.usfirst.frc2881.karlk.subsystems.LiftSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    public static DriveSubsystem driveSubsystem;
    public static IntakeSubsystem intakeSubsystem;
    public static LiftSubsystem liftSubsystem;
    public static ClimbingSubsystem climbingSubsystem;
    public static CompressorSubsystem compressorSubsystem;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        DriverStation.reportWarning("starting lift piston program", false);

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

        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // Add commands to Autonomous Sendable Chooser
        //If you want more than one option to show up then list them individually here

        chooser.addDefault("Autonomous Command", new AutonomousCommand()); //for subsequent options call "addObject"
        SmartDashboard.putData("Auto mode", chooser);//make sure to add to SmartDashboard


    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
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
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        //deploy the arm for the duration of the match
        new ArmInitialDeploy(true);
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
