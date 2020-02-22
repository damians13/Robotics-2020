package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.PID;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Indexing extends SubsystemBase {

    private TalonSRX leftMotor;
    private TalonSRX rightMotor;
    // need encoders too

    private boolean spinning;
    private PID indexingPID;

    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;
    private final double target = 400; // RPM

    public Indexing() {
        leftMotor = new TalonSRX(10);
        leftMotor = new TalonSRX(11);

        indexingPID = new PID(kP, kI, kD, target);
    }

    @Override
    public void periodic() {
        if (spinning) {
            // Not sure if I have to use the miscutils function with the encodersit, test this
            leftMotor.set(MiscUtils.encoderToSpeed(leftEncoder.getCountsPerRevolution(), shooterPID.getOutput(leftEncoder.getVelocity())));
            rightMotor.set(-MiscUtils.encoderToSpeed(rightEncoder.getCountsPerRevolution(), shooterPID.getOutput(rightEncoder.getVelocity())));
        } else {
            leftMotor.set(0);
            rightMotor.set(0);
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