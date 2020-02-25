package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.PID;

public class Shooter extends SubsystemBase {

    private boolean spinning;
    private PID shooterPID;

    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;
    private CANEncoder leftEncoder;
    private CANEncoder rightEncoder;

    private final double kP = 1;
    private final double kI = 0;
    private final double kD = 0;
    private final double target = 6000; // RPM

    public Shooter() {
        this.spinning = false;
        shooterPID = new PID(kP, kI, kD, target);

        leftMotor = new CANSparkMax(5, MotorType.kBrushless);
        rightMotor = new CANSparkMax(6, MotorType.kBrushless);
        leftEncoder = new CANEncoder(leftMotor);
        rightEncoder = new CANEncoder(rightMotor);
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

        SmartDashboard.putNumber("Shooter Speed", rightEncoder.getVelocity());
        SmartDashboard.putNumber("Shooter PID", shooterPID.getOutput(rightEncoder.getVelocity()));
    }

    /**
     * These methods return true or false as a check to see if the shooter spins up when we don't want it to
     */

    public boolean start() {
        if (!this.spinning) {
            this.spinning = true;
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
}