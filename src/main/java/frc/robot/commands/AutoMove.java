package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.utilities.PID;

public class AutoMove extends CommandBase {

    private double x;
    private double y;
    private Rotation2d rot;

    private double moveX;
    private double moveY;
    private double moveRot;

	private final double xPIDkP = 1;
	private final double xPIDkI = 1;
	private final double xPIDkD = 1;

	private final double yPIDkP = 1;
	private final double yPIDkI = 1;
	private final double yPIDkD = 1;

	private final double rotPIDkP = 1;
	private final double rotPIDkI = 1;
    private final double rotPIDkD = 1;
    
    private boolean finished;

	private PID xPID; // Based off side to side alignment with target
	private PID yPID; // Based off distance to target (negative is forwards)
	private PID rotPID; // Based off rotation alignment with target

    private Pose2d initialPosition;
    private Pose2d targetPosition;
    private Pose2d currentPosition;

    // Input in cm
    public AutoMove(double x, double y, Rotation2d rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
    }

    @Override
    public void initialize() {
        initialPosition = Robot.Container.driveTrain.getOdometry();
        targetPosition = new Pose2d(initialPosition.getTranslation().getX() + this.x, initialPosition.getTranslation().getY() + this.y, initialPosition.getRotation().plus(this.rot));

        xPID = new PID(xPIDkP, xPIDkI, xPIDkD, targetPosition.getTranslation().getX());
        yPID = new PID(yPIDkP, yPIDkI, yPIDkD, targetPosition.getTranslation().getY());
        rotPID = new PID(rotPIDkP, rotPIDkI, rotPIDkD, targetPosition.getRotation().getDegrees());

        this.finished = false;
    }

    @Override
    public void execute() {
        currentPosition = Robot.Container.driveTrain.getOdometry();

        if (currentPosition == targetPosition) {
            this.finished = true;;
        } else {
            moveX = xPID.getOutput(currentPosition.getTranslation().getX());
            moveY = yPID.getOutput(currentPosition.getTranslation().getY());
            moveX = rotPID.getOutput(currentPosition.getRotation().getDegrees());

            Robot.Container.driveTrain.cappedMecanumDrive(moveX, moveY, moveRot, 0.6);
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("AutoMove instruction finished.  Interrupted: " + interrupted);
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

}