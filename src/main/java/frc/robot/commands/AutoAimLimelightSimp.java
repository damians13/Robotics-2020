package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private boolean autoSetHeight;

    private static final double target = -2.65;
    //private static final double target = 0;
    //private static final double kP = 0.0280012;
    //private static final double kI = 0.0002;
    private static final double kD = 0; 
    private static final double kP = 0.0258;
    private static final double kI = 0.0002;

    public AutoAimLimelightSimp(boolean autoSetHeight) {
        this.autoSetHeight = autoSetHeight;
    }

    @Override
    public void initialize() {
        this.finished = false;
        goalMax = 0.8;
        goalMin = -0.8;

        Robot.Container.driveTrain.stopGyroComp();

        aimPID = new PID(kP, kI, kD, target);
    }

    @Override
    public void execute() {
        System.out.println("Running");
        if (!Robot.Container.sensors.limelightHasTarget()) {
            System.out.println("No target for limelight aim!");
            this.finished = true;
        } else {
            double tx = Robot.Container.sensors.getLimelightTX();
            
            if (autoSetHeight) {
                Robot.Container.shooter.autoAdjustShooter();
            }

            if (tx >= goalMin + target && tx <= goalMax + target) {
                this.finished = true;
            } else {
                Robot.Container.driveTrain.cappedMecanumDrive(0, 0, -aimPID.getOutput(tx), 0.1);
                SmartDashboard.putNumber("-aimPID Output", -aimPID.getOutput(tx));
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            System.out.println("Limelight aimed.");
        }
        Robot.Container.driveTrain.startGyroComp();
        //Robot.Container.sensors.turnOffTheDamnLimelightLED();
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }
}