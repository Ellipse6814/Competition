/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6814.robot;
import org.usfirst.frc.team6814.robot.commands.AutoDrive;
import org.usfirst.frc.team6814.robot.commands.Drive1;
import org.usfirst.frc.team6814.robot.commands.Elevator;
import org.usfirst.frc.team6814.robot.commands.ExampleCommand;
import org.usfirst.frc.team6814.robot.commands.GrabbyGrabbyCtrl;
import org.usfirst.frc.team6814.robot.commands.LightyLight;
import org.usfirst.frc.team6814.robot.commands.Turn90;
import org.usfirst.frc.team6814.robot.subsystems.ExampleSubsystem;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties h file in the
 * project
 */
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static OI m_oi;
	public Drive1 drive;
	public AutoDrive autoDrive;
	public Turn90 turn90;
	public GrabbyGrabbyCtrl grabbygrabby;
	public LightyLight lightylight;
	public Elevator elevator;
//	public Witch witch;
	

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//init encoder
		 RobotMap.encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		 RobotMap.encoder.setMaxPeriod(1);
		 RobotMap.encoder.setMinRate(10);
		 RobotMap.encoder.setDistancePerPulse(5);
		 RobotMap.encoder.setReverseDirection(true);
		 RobotMap.encoder.setSamplesToAverage(7);
		 RobotMap.encoder.reset();
		
		RobotMap.timer.start();
		RobotMap.ahrs = new AHRS(SPI.Port.kMXP); 
		m_oi = new OI();
		grabbygrabby = new GrabbyGrabbyCtrl(m_oi.leftController);
		autoDrive = new AutoDrive();
		drive = new Drive1(m_oi.leftController, m_oi.rightController, RobotMap.ahrs);
		lightylight = new LightyLight();
		elevator = new Elevator(m_oi.leftController);
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		turn90 = new Turn90(RobotMap.ahrs);
		
		

//		witch=new Witch(m_oi.thirdController);
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	
	public void getArcadeConfig() {
		String arcadeConfig = DriverStation.getInstance().getGameSpecificMessage();
		char usableArray[] = arcadeConfig.toCharArray();

		// Autonomous code if both switch and scale on the left
		if ((usableArray[0] == 'L' && usableArray[1] == 'L') || (usableArray[0] == 'l' && usableArray[1] == 'l')) {

		}

		// Autonomous code if both switch and scale are on the right
		else if ((usableArray[0] == 'R' && usableArray[1] == 'R') || (usableArray[0] == 'r' && usableArray[1] == 'r')) {

		}

		// Autonomous code if the home switch is on the left and the scale is on the
		// right
		else if ((usableArray[0] == 'L' && usableArray[1] == 'R') || (usableArray[0] == 'l' && usableArray[1] == 'r')) {

		}

		// Autonomous code if the home switch is on the right and the scale is on the
		// left
		else if ((usableArray[0] == 'R' && usableArray[1] == 'L') || (usableArray[0] == 'r' && usableArray[1] == 'l')) {

		}

	}
	
	@Override
	public void autonomousInit() {
		//m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		autoDrive.start();
		
//		turn90.start();
		
	}

	/**
	 * This function is called periodically during autonomous.
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
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		//Scheduler.getInstance().add(drive);
		drive.start();
		grabbygrabby.start();
		lightylight.start();
		elevator.start();
//		witch.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
