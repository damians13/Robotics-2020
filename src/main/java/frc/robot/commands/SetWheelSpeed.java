package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SetWheelSpeed extends CommandBase {

    private double xSpeed;
    private double ySpeed;
    private double rotation;

    public SetWheelSpeed(double xSpeed, double ySpeed, double rotation) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rotation = rotation;
    }
    
    @Override
    public void execute() {
        Robot.Container.driveTrain.mecanumDrive(this.xSpeed, this.ySpeed, this.rotation);
    }
    
    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Wheels set to X: " + this.xSpeed + ", Y: " + this.ySpeed + ", ROT: " + this.rotation);
        } else {
            System.out.println("Interrupted while setting wheels to X: " + this.xSpeed + ", Y: " + this.ySpeed + ", ROT: " + this.rotation);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}