/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrainMecanum;
import frc.robot.Constants;

public class _MecanumDrive extends CommandBase {
 
    double joyX = 0;
    double joyY = 0;
	double rotation = 0;

	private DriveTrainMecanum subsystem;

	public _MecanumDrive(DriveTrainMecanum sub) {
		subsystem = sub;

		System.out.println("*** Mecanum drive initialized.");
		addRequirements(subsystem);
	}
	
	@Override
	public void execute() {
		joyX = Robot.Container.driverControllerAxisValue(Constants.ControllerConstants.Xbox_Right_X_Axis);
		joyY = -Robot.Container.driverControllerAxisValue(Constants.ControllerConstants.Xbox_Right_Y_Axis);
		rotation = Robot.Container.driverControllerAxisValue(Constants.ControllerConstants.Xbox_Right_Trigger) - Robot.Container.driverControllerAxisValue(Constants.ControllerConstants.Xbox_Left_Trigger);

		subsystem.mecanumDrive(joyX, joyY, rotation);
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
}