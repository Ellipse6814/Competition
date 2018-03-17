package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class Drive1 extends Command {
	private Joystick leftController;
	private Joystick rightController;
	private double rev=1.0;


	public Drive1(Joystick leftController, Joystick rightController, AHRS ahrs) {//
		this.leftController = leftController;
		this.rightController = rightController;
		RobotMap.leftBackMotor.setExpiration(0.1);
		RobotMap.leftFrontMotor.setExpiration(0.1);
		RobotMap.rightBackMotor.setExpiration(0.1);
		RobotMap.rightFrontMotor.setExpiration(0.1);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void execute() {
//		Rev();
		
//		double l = leftController.getRawAxis(1);
//		double r = leftController.getRawAxis(3);
//		double p = (l + r) / 2;
//		double t = l - r;
//		double p = leftController.getRawAxis(1);
//		double t = leftController.getRawAxis(0);
//		if (leftController.getRawButton(5)) { // slow down to 60%
//			p *= .6;
//			t *= .3;
//		}
//		if (leftController.getRawButton(6)) { // go straight
//			t = 0;
//		}
//		p *= .7;
//		t *= .6;
		double dir = leftController.getPOV();
		double l=0, r=0;
		if (dir == 0) {
			l=1;
			r=1;
		}else if (dir == 90) {
			l=-.8;
			r=.8;
		}else if (dir == 180) {
			l=-1;
			r=-1;
		}else if (dir == 270) {
			l=.8;
			r=-.8;
		}else if (dir == 360) {
			l=1;
			r=1;
		}
		l*=.6;
		r*=.6;
//		arcadeDrive(p, t);
		ctrlMotors(-l,-r);
	}
	
	private void Rev() {
//		if (leftController.getRawButton(4)) {
//			rev = 1.0;
//		}else if (leftController.getRawButton(2)) {
//			rev = -1.0;
//		}
	}

	private void arcadeDrive(double power, double turn) {
		double leftPower = power - turn;
		double rightPower = power + turn;
		ctrlMotors(leftPower, rightPower);
	}

	private void ctrlMotors(double l, double r) {
//		System.out.println(l+" "+r);
		RobotMap.driveFrontBot.tankDrive(l*rev, r*rev);
		RobotMap.driveBackBot.tankDrive(l*rev, r*rev);
	}

	@Override
	protected void end() {

	}

}
