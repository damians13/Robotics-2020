package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.utilities.PID;

public class Shooter extends SubsystemBase {

    private boolean spinning;
    private PID shooterPID;

    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;
    private CANEncoder leftEncoder;
    private CANEncoder rightEncoder;

    private Servo leftActuator;
    private Servo rightActuator;

    private final double kP = 1;
    private final double kI = 0;
    private final double kD = 0;
    private final double target = 6000; // RPM

    private double height;

    public Shooter() {
        this.spinning = false;
        shooterPID = new PID(kP, kI, kD, target);

        leftMotor = new CANSparkMax(5, MotorType.kBrushless);
        rightMotor = new CANSparkMax(6, MotorType.kBrushless);
        leftEncoder = new CANEncoder(leftMotor);
        rightEncoder = new CANEncoder(rightMotor);

        leftActuator = new Servo(0);
        rightActuator = new Servo(1);

        height = 0.5;
    }

    @Override
    public void periodic() {
        if (this.spinning) {
            // Not sure if I have to use the miscutils function with the spark maxes, test this
            leftMotor.set(-shooterPID.getOutput(leftEncoder.getVelocity()));
            rightMotor.set(shooterPID.getOutput(rightEncoder.getVelocity()));
        } else {
            leftMotor.set(0);
            rightMotor.set(0);
        }

        SmartDashboard.putNumber("Shooter height", this.height);

        SmartDashboard.putNumber("Shooter Speed", rightEncoder.getVelocity());
        SmartDashboard.putNumber("Shooter PID", shooterPID.getOutput(rightEncoder.getVelocity()));
    }

    public void setStartHeight() {
        leftActuator.set(0.5);
        rightActuator.set(0.5);
    }

    /**
     * These methods return true or false as a check to see if the shooter spins up when we don't want it to
     */

    public boolean start() {
        if (!this.spinning) {
            this.spinning = true;
/*
            Robot.Container.sensors.getLidarDistance();

            // insert lidar distance to linear actuator input algorithm here

            leftActuator.set(0.5); // 0.5 for now, eventually just put the output of the algorithm
            rightActuator.set(0.5);
*/
            return true;
        } else {
            return false;
        }

    }

    public boolean stop() {
        if (this.spinning) {
            this.spinning = false;
            return true;
        } else {
            return false;
        }
    }

    // temp methods down here
    public void adjustShooter() {
        leftActuator.set(this.height);
        rightActuator.set(this.height);
    }

    public void increase() {
        height += 0.005;
    }

    public void decrease() {
        height -= 0.005;
    }

    public double tiltFormula() {
        double dy = 229.56 * Math.pow(Robot.Container.sensors.getLimelightTY(), -0.961);
        double dw = 788.67 * Math.pow(Robot.Container.sensors.getLimelightTHor(), -1.013);
        double d = (dw + dy) / 2;
        double tilt = 0.001 * Math.pow(d, 2) - 0.0372 * d + 0.8592;
        return tilt;
    }
}