package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleLimelightLED extends CommandBase {
	private double currentLimelightState;
	private boolean ran;

    public ToggleLimelightLED() {
		this.ran = false;
    }

	@Override
	public void initialize() {
		this.currentLimelightState = Robot.Container.sensors.getLimelightLED();
	}

	@Override
	public void execute() {
		if (this.currentLimelightState == 0) { // 0 is on
			Robot.Container.sensors.turnOffTheDamnLimelightLED();
		} else if (this.currentLimelightState == 1) { // 1 is off
			Robot.Container.sensors.turnOnLimelightLED();
		}
	}

	@Override
	public void end(boolean interrupted) {
		System.out.println("Toggling limelight LED");
	}

	@Override
	public boolean isFinished() {
		return ran;
	}
}