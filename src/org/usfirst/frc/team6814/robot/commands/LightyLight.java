package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class LightyLight extends Command{
	
	public LightyLight() {
		//nothing to do.
	}
	
	@Override
	protected void execute() {
		RobotMap.lightStrip.set(RobotMap.light);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		System.out.println("Light Strip: PROCESS TERMINATED");
	}
}
