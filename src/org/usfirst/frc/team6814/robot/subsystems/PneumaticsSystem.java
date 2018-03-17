package org.usfirst.frc.team6814.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.Compressor;

import org.usfirst.frc.team6814.robot.RobotMap;

public class PneumaticsSystem extends Subsystem{ 
	public void initDefaultCommand() {
		
	}
	
	public PneumaticsSystem() {
		RobotMap.compressor.start();
	}
	
	public void forward() {
		try {
			RobotMap.solenoid.set(DoubleSolenoid.Value.kForward);
		}catch(Exception e) {
			//System.out.println("FAILED TO SET SOLENOID TO FORWARD");
		}
	}
	
	public void reverse() {
		RobotMap.solenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void off() {
		RobotMap.solenoid.set(DoubleSolenoid.Value.kOff);
	}
}
