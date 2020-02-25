package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;

public class Indexing extends SubsystemBase {

    private TalonSRX leftMotor;
    private TalonSRX rightMotor;
    private CANCoder leftEncoder;
    private CANCoder rightEncoder;
    // need encoders too

    private boolean spinning;
    private PID indexingPID;

    private final double kP = 1;
    private final double kI = 1;
    private final double kD = 1;
    private final double target = 100; // RPM

    public Indexing() {
        leftMotor = new TalonSRX(8);
        rightMotor = new TalonSRX(9);
        leftEncoder = new CANCoder(8);
        rightEncoder = new CANCoder(9);

        indexingPID = new PID(kP, kI, kD, target);
    }

    @Override
    public void periodic() {
        if (spinning) {
            // Not sure if I have to use the miscutils function with the encodersit, test this
            leftMotor.set(ControlMode.PercentOutput, indexingPID.getOutput(leftEncoder.getVelocity()));
            rightMotor.set(ControlMode.PercentOutput, -indexingPID.getOutput(rightEncoder.getVelocity()));
        } else {
            leftMotor.set(ControlMode.PercentOutput, 0);
            rightMotor.set(ControlMode.PercentOutput, 0);
        }
    }


    /**
     * These methods return true or false as a check to see if the shooter spins up when we don't want it to
     */

    public boolean start() {
        if (!spinning) {
            spinning = true;
            return true;
        } else {
            return false;
        }

    }

    public boolean stop() {
        if (spinning) {
            spinning = false;
            return true;
        } else {
            return false;
        }
    }
}