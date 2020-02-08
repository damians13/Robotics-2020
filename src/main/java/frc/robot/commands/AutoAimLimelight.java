package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.utilities.MiscUtils;
import frc.robot.utilities.PID;

public class AutoAimLimelight extends CommandBase {

	/**
	 * y is forward/back
	 * x is side to side
	 */

	private final double xTarget = 0.0; // In pixels
	private final double distTarget = 100.0; // In centimeters
	private final double rotTarget = 0.0; // In degrees

	private final double xPIDkP = 0.0;
	private final double xPIDkI = 0.0;
	private final double xPIDkD = 0.0;

	private final double yPIDkP = 0.0;
	private final double yPIDkI = 0.0;
	private final double yPIDkD = 0.0;

	private final double rotPIDkP = 0.0;
	private final double rotPIDkI = 0.0;
	private final double rotPIDkD = 0.0;

	private PID xPID; // Based off side to side alignment with target
	private PID yPID; // Based off distance to target (negative is forwards)
	private PID rotPID; // Based off rotation alignment with target

	private double driveInputX;
	private double driveInputY;
	private double driveInputAngle;

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		if (Robot.Container.driveTrain == null) {
			System.out.println("Failed to AutoAimLimelight, Robot.Container.driveTrain == null");
			this.end(false);
		}
		if (Robot.Container.sensors == null) {
			System.out.println("Failed to AutoAimLimelight, Robot.Container.sensors == null");
			this.end(false);
		}

		xPID = new PID(xPIDkP, xPIDkI, xPIDkD, xTarget);
		yPID = new PID(yPIDkP, yPIDkI, yPIDkD, distTarget);
		rotPID = new PID(rotPIDkP, rotPIDkI, rotPIDkD, rotTarget);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (Robot.Container.sensors.limelightHasTarget()) {

			driveInputX = xPID.getOutput(Robot.Container.sensors.getLimelightTX());
			driveInputY = yPID.getOutput(Robot.Container.sensors.getLidarDistance()); // get distance to target
			driveInputAngle = rotPID.getOutput(MiscUtils.limelightPointsLeftOverRight(Robot.Container.sensors.getLimelightCornersX(), Robot.Container.sensors.getLimelightCornersY()));

			Robot.Container.driveTrain.cappedMecanumDrive(driveInputX, driveInputY, driveInputAngle, 0.6);
		} else {
			System.out.println("Failed to AutoAimLimelight, limelightHasTarget() == false");
			this.end(false);
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		System.out.println("Ending AutoAimLimelight...");
	}
}