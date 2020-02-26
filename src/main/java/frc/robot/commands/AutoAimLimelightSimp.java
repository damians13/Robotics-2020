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

    private static final double kP = 0.01;
    private static final double kI = 0.001;
    private static final double kD = 0;

    @Override
    public void initialize() {
        goalMax = 0.5;
        goalMin = -0.5;

        aimPID = new PID(kP, kI, kD, 0);
    }

    @Override
    public void execute() {
        System.out.println("Running");

        if (!Robot.Container.sensors.limelightHasTarget()) {
            System.out.println("No target for limelight aim!");
        } else {
            //if ()

            Robot.Container.driveTrain.cappedMecanumDrive(0, 0, -aimPID.getOutput(Robot.Container.sensors.getLimelightTX()), 0.6);
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Limelight aimed.");
        }
    }
}