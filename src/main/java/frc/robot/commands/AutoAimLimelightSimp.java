package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.utilities.PID;

/**
 * Simp version of what could've been a really cool file
 */

public class AutoAimLimelightSimp extends CommandBase {

    private double goalMax;
    private double goalMin;

    private PID aimPID;

    private boolean finished;

    private static final double kP = 0.022;
    private static final double kI = 0.005;
    private static final double kD = 0;

    @Override
    public void initialize() {
        goalMax = 0.5;
        goalMin = -0.5;

        Robot.Container.driveTrain.stopGyroComp();

        aimPID = new PID(kP, kI, kD, 0);

        this.finished = false;
    }

    @Override
    public void execute() {
        System.out.println("Running");
        if (!Robot.Container.sensors.limelightHasTarget()) {
            System.out.println("No target for limelight aim!");
        } else {
            double tx = Robot.Container.sensors.getLimelightTX();
            if (tx >= goalMin && tx <= goalMax) {
                this.finished = true;
            } else {
                Robot.Container.driveTrain.cappedMecanumDrive(0, 0, -aimPID.getOutput(tx), 0.25);
                System.out.println(-aimPID.getOutput(tx));
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Limelight aimed.");
        }
        Robot.Container.driveTrain.startGyroComp();
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }
}