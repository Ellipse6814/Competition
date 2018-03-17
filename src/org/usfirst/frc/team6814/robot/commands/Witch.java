package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class Witch extends Command {
	private Joystick thirdController;

	public Witch(Joystick thirdController) {//
		this.thirdController = thirdController;
		// no expiration RobotMap.elevator.setExpiration()
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void execute() {
		double power = thirdController.getY(); // raw data
//		System.out.println(-power);
		RobotMap.witch.set(-power);
	}
	@Override
	protected void end() {

	}

}
