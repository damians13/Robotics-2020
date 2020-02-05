package frc.robot.utilities;

public class PID {

	private double kP, kI, kD, output, currentError, totalError, deltaError, previousError, target;

	public PID(double kP, double kI, double kD, double target) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.target = target;
		this.totalError = 0;
		this.previousError = 0;
	}

	public void resetTarget(double target) {
		this.target = target;
		this.totalError = 0;
		this.previousError = 0;
	}

	public double getOutput(double input) {
		this.currentError = this.target - input;
		this.totalError += this.currentError / 50;
		this.deltaError = this.previousError - this.currentError;

		this.output = this.kP * this.currentError + this.kI * this.totalError + this.kD * this.deltaError * 50;
		this.previousError = this.currentError;

		return this.output;
	}
}
