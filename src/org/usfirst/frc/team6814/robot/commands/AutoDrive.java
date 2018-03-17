package org.usfirst.frc.team6814.robot.commands;

import org.usfirst.frc.team6814.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command {
	private boolean currentState = false;
	private double currentSpeed = -.8;
	private double StartEncPos = 0.0;

	public AutoDrive(){
		StartEncPos = RobotMap.encoder.get();
		System.out.println("auto started");
	}
	
	
	@Override
	protected void execute() {
		RobotMap.driveFrontBot.tankDrive(currentSpeed, currentSpeed);
		RobotMap.driveBackBot.tankDrive(currentSpeed, currentSpeed);
//		System.out.println(DistanceSince(StartEncPos));
		
		if (DistanceSince(StartEncPos) > 12) {
			currentSpeed = 0;
			RobotMap.timer.reset();
		}
	}

	@Override
	protected boolean isFinished() {
		return currentState;
	}

	@Override
	protected void end() {
//		RobotMap.timer.reset();
		System.out.println("auto end");
	}

	private double DistanceSince(double prevEncVal) {
		double EncVal = RobotMap.encoder.get();
		final double encFormula = 6 * Math.PI / 128 / 12;
		double res = encFormula * EncVal;

		return res;
		// 1rotation=128=6pi ft 6pi/128 = outp/inp

	}
}