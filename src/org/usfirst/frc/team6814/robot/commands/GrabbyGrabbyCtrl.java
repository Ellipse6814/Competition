package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class GrabbyGrabbyCtrl extends Command {
	private Joystick rightStick;
	private boolean lastAction = false; // [true:Fwd|false:Rev]every time solenoid status CHANGES, it will log the
										// current status (it doesn't log a billion times a second)

	public GrabbyGrabbyCtrl(Joystick rightJoystick) {
		this.rightStick = rightJoystick;
	}

	@Override
	protected void execute() {
		if (this.rightStick.getRawButton(9)||this.rightStick.getRawButton(10)||this.rightStick.getRawButton(3)||this.rightStick.getRawButton(1)) {
			RobotMap.solenoid.set(DoubleSolenoid.Value.kReverse);
			if (RobotMap.whoHasPwrOverLight==1) { //if I have power
				setLight(.57);
			}
			if (!lastAction) { //I have power!
				lastAction = true;
				setPower();
			}
		} else {
			RobotMap.solenoid.set(DoubleSolenoid.Value.kForward);
			if (RobotMap.whoHasPwrOverLight==1) {
				setLight(.59);
			}
			if (lastAction) {
				lastAction = false;
				setPower();
			}
		}
	}
	
	protected void setPower() {
		RobotMap.whoHasPwrOverLight = 1;
	}
	
	protected void setLight(double a) {
		RobotMap.light = a;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		RobotMap.solenoid.set(DoubleSolenoid.Value.kOff);
		System.out.println("Intake: PROCESS TERMINATED");
	}
}
