package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class Turn90 extends Command{
	private boolean exit = false;
	private double wantedAngle;
	private double spd = .8;
	private AHRS ahrs;

	public Turn90(AHRS ahrs) {//
		this.ahrs = ahrs;
		resetGYRO();
	}
	
	@Override
	protected void execute() {
		if (wantedAngle<-2) {
			ctrlMotors(-spd);
		}else if (wantedAngle>2){
			ctrlMotors(spd);
		}else {
			exit = true;
		}
		spd -=0.00;
		if (spd <=0) {
			spd=0.5;
		}
		System.out.println(spd);
	}
	
	private void ctrlMotors(double turn) { //-:left; +:right
		RobotMap.driveFrontBot.tankDrive((-turn) * -1, (turn) * -1);
		RobotMap.driveBackBot.tankDrive((-turn) * -1, (turn) * -1);
	}
	
	@Override
	protected boolean isFinished() {
		return exit;
	}
	
	@Override
	protected void end() {

	}
	
	private void resetGYRO() {
		wantedAngle = ahrs.getAngle()+90;
//		System.out.println(wangt);
	}
	
}
