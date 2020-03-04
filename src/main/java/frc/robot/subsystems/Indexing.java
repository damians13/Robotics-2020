package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.MiscUtils;
import frc.robot.utilities.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Indexing extends SubsystemBase {

    private TalonSRX leftMotor;
    private TalonSRX rightMotor;

    private boolean spinning;
    private PID indexingPID;

    private final double kP = 0.0005;
    private final double kI = 0.0001;
    private final double kD = 0;
    private final double target = 80; // RPM (I think)

    public Indexing() {
        leftMotor = new TalonSRX(8);
        rightMotor = new TalonSRX(9);

        // Last value is timeout in milliseconds
        leftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 25);
        rightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 25);

        indexingPID = new PID(kP, kI, kD, target);
    }

    @Override
    public void periodic() {
        if (spinning) {
            //leftMotor.set(ControlMode.PercentOutput, indexingPID.getOutput(leftMotor.getSelectedSensorVelocity()));
            //rightMotor.set(ControlMode.PercentOutput, -indexingPID.getOutput(rightMotor.getSelectedSensorVelocity()));

            SmartDashboard.putNumber("Left Indexing Encoder Output", leftMotor.getSelectedSensorVelocity());

            leftMotor.set(ControlMode.PercentOutput, 0.16);
            rightMotor.set(ControlMode.PercentOutput, -0.16);
        } else {
            leftMotor.set(ControlMode.PercentOutput, 0);
            rightMotor.set(ControlMode.PercentOutput, 0);
        }
        
        //System.out.println("irSensor: " + irSensor.getValue());
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