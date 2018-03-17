package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class Elevator extends Command {
	private Joystick leftController;

	public Elevator(Joystick rightController) {//
		this.leftController = rightController;
		// no expiration RobotMap.elevator.setExpiration()
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void execute() {
		double power = leftController.getRawAxis(2) - leftController.getRawAxis(3); // raw data
//		System.out.println(-power);
		RobotMap.elevator.set(power);
		if (!leftController.getRawButton(2)) {
			RobotMap.witch.set(power);
		}
	}
	@Override
	protected void end() {

	}

}