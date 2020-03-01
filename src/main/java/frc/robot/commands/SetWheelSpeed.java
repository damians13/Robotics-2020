package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SetWheelSpeed extends CommandBase {

    private double xSpeed;
    private double ySpeed;
    private double rotation;

    private boolean finished;

    public SetWheelSpeed(double xSpeed, double ySpeed, double rotation) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rotation = rotation;

        this.finished = false;
    }
    
    @Override
    public void execute() {
        Robot.Container.driveTrain.mecanumDrive(xSpeed, ySpeed, rotation);
        this.finished = true;
    }
    
    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Wheels set to X: " + xSpeed + ", Y: " + ySpeed + ", ROT: " + rotation);
        } else {
            System.out.println("Interrupted while setting wheels to X: " + xSpeed + ", Y: " + ySpeed + ", ROT: " + rotation);
        }
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

}