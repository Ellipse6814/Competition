package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command {
	private Joystick leftController;
	private Joystick rightController;
	private double fAngle;
	private boolean ForceStraight = false;
	private boolean straight = false;
	private boolean forwards = true;
	private int turn = -1;
	// private double turnSpd = 0.6;
	private int lastturn = -1;
	private int wantedTurn = 0;
	private int lastStatus = 0; // 1:forward straught; 2:backward straight; 3: turning whatever
	private AHRS ahrs;
	private int count = 0;
	private double GYROturnSpd = 0.6;
	private double rev = 1.0;

	public Drive(Joystick leftController, Joystick rightController, AHRS ahrs) {//
		this.leftController = leftController;
		this.rightController = rightController;
		RobotMap.leftBackMotor.setExpiration(0.1);
		RobotMap.leftFrontMotor.setExpiration(0.1);
		RobotMap.rightBackMotor.setExpiration(0.1);
		RobotMap.rightFrontMotor.setExpiration(0.1);
		this.ahrs = ahrs;
		resetGYRO();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void execute() {
		// System.out.println(RobotMap.driveEnc.get());
		// 1 rotation=6pi=18.84...
		// 79 is one foot, I think.
		
		double rl = leftController.getRawAxis(1); // raw data
		double rr = leftController.getRawAxis(3);
		double l = (rl + rr) / 2;
		double r = rl - rr;
		// double l = rl;
		// double r = rr;
//		if (count > 20) {
//			count = 0;
//
//		}
		// System.out.println(ahrs.getAngle()-fAngle);
//		count++;
		// System.out.println(this.leftController.getPOV());
		GetRevBut();
//		turn90();
		Forcestriaght(l, r);
		if (!ForceStraight) {
			checkStraight(l, r);
			// ellipseDrive(leftController.getRawAxis(1),leftController.getRawAxis(5));
			if (l > -0.1 && l < 0.1 && Math.abs(r) > 0.5) {
				rotate(r);
			} else {
				arcadeDrive(l, r);
				// cheesyDrive(l, r);
			}
			// System.out.println(ahrs.getAngle());
		} else {
			setLight(-0.03);// force straight light
		}

	}

	private void GetRevBut() {
		if (leftController.getRawButton(4)) {
			rev = 1.0;
		} else if (leftController.getRawButton(2)) {
			rev = -1.0;
		}
	}

	private void turn90() {
		lastturn = turn;
		turn = this.leftController.getPOV();
		if (!(turn == -1) && lastturn == -1) {
			resetGYRO();
			wantedTurn = 10;
			if (turn > 180) {
				turn = 180 - turn;
			}
			fAngle += turn;
			// System.out.println("Setting turn" + turn + " angle: " + fAngle);
		}
	}

	private double getBigger(double a, double b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

	private void Forcestriaght(double l, double r) {
		// System.out.println(leftController.getRawButton(1));
		if (leftController.getRawButton(6) && !(ForceStraight)) { // start going straight
			resetGYRO();
			straight = true;
			ForceStraight = true;
			// System.out.println("F straight");
		} else if (!(leftController.getRawButton(6)) && ForceStraight) { // ended going straight
			straight = false;
			ForceStraight = false;
			// System.out.println("F turning");
		}
		if (ForceStraight && !((lastStatus == 1 && (l >= 0 && r >= 0)) || (lastStatus == 2 && (l < 0 && r < 0)))) {
			resetGYRO();
			// System.out.println("F change direction -> reset");
		}
		if (leftController.getRawButton(5)) {
			l *= 0.6;
		}
		ctrlMotors(l, l);
	}

	private void rotate(double spd) {
		ctrlMotors(-spd, spd);
	}

	private void checkStraight(double l, double r) {
		double straightAngle = 0.6;
		// if (doubleJoystick) {
		if (Math.abs(l - r) <= straightAngle && l >= 0 && r >= 0 && !(lastStatus == 1)) { // start going straight
			resetGYRO();
			lastStatus = 1;
			straight = true;
			setLight(-0.05);
			// System.out.println("forwards");
		} else if (Math.abs(l - r) <= straightAngle && l < 0 && r < 0 && !(lastStatus == 2)) { // start going straight
			resetGYRO();
			lastStatus = 2;
			straight = true;
			setLight(-0.07);
			// System.out.println("backwards");
		} else {
			lastStatus = 3;
			straight = false;
			setLight(-0.09);
			// System.out.println("turing");
		}
	}

	private void cheesyDrive(double power, double turn) {
		double leftPower = power - turn;
		double rightPower = power + turn;
		ctrlMotors(leftPower, rightPower);
	}

	private void arcadeDrive(double power, double turn) {
		if (leftController.getRawButton(5)) {
			power *= 0.6;
			turn *= 0.3;
		}
		double leftPower = power + power * turn;
		double rightPower = power - power * turn;
		ctrlMotors(leftPower, rightPower);
	}

	private void EllipseDrive(double left, double right) {
		double power = (left + right) / 2;
		double turn = left - right;
		double leftPower = power + power * turn;
		double rightPower = power - power * turn;
		ctrlMotors(leftPower, rightPower);
	}

	private double DistanceSince(double prevEncVal) {
		double EncVal = RobotMap.encoder.get();
		final double encFormula = 6*Math.PI/128;
		double res = encFormula*EncVal;
		
		return res;
		// 1rotation=128=6pi ft 6pi/128 = outp/inp
		
	}
	
	
	private void ctrlMotors(double l, double r) {
		RobotMap.driveFrontBot.tankDrive((l + GYROl(l, r)) * rev, (r + GYROr(l, r)) * rev);
		RobotMap.driveBackBot.tankDrive((l + GYROl(l, r)) * rev, (r + GYROr(l, r)) * rev);
	}

	private double GYROl(double l, double r) {
		double turnSpd = 0.6;
		if (wantedTurn > 0) {
			if (ahrs.getAngle() - fAngle > 1) {
				// System.out.println(">2");
				wantedTurn = 10;
				return turnSpd;
			} else if (ahrs.getAngle() - fAngle < -1) {
				// System.out.println("<-2");
				wantedTurn = 10;
				return -turnSpd;
			} else {
				wantedTurn -= 1;
				// System.out.println("Turn finished.");
			}
		} else if (straight)
			if (ahrs.getAngle() - fAngle > 1) {
				return -0.1;
			}
		return 0.0;
	}

	private double GYROr(double l, double r) {
		double turnSpd = 0.6;
		if (wantedTurn > 0) {
			if (ahrs.getAngle() - fAngle > 1) {
				// System.out.println(">2");
				wantedTurn = 100;
				return -turnSpd;
			} else if (ahrs.getAngle() - fAngle < -1) {
				// System.out.println("<-2");
				wantedTurn = 100;
				return turnSpd;
			} else {
				wantedTurn -= 1;
				// System.out.println("Turn finished.");
			}
		} else if (straight)
			if (ahrs.getAngle() - fAngle < -1) {
				return -0.1;
			}
		return 0.0;
	}

	private void resetGYRO() {
		fAngle = ahrs.getAngle();
		// System.out.println(fAngle);
	}

	private void setPower() {
		RobotMap.whoHasPwrOverLight = 0;
	}

	private void setLight(double a) {
		RobotMap.light = a;
	}

	@Override
	protected void end() {

	}

}
