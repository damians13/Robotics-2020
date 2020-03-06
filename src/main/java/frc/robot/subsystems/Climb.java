package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.utilities.MiscUtils;
import frc.robot.utilities.PID;

public class Climb extends SubsystemBase {

    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;

    private PID holdExtenderArm;

    private boolean bigWinchSpinning;

    private VictorSPX smallWinch;
    private CANSparkMax bigWinch;
    private CANEncoder bigWinchEncoder;
    private DoubleSolenoid solenoid;

    public Climb() {
        holdExtenderArm = new PID(kP, kI, kD, 0);

        smallWinch = new VictorSPX(11);
        bigWinch = new CANSparkMax(7, MotorType.kBrushless);
        bigWinchEncoder = new CANEncoder(bigWinch);
        //               PCM CAN ID, forwardChannel, reverseChannel
        solenoid = new DoubleSolenoid(13, 7, 6);
    }

    @Override
    public void periodic() {
        if (this.bigWinchSpinning) {
            bigWinch.set(Constants.ControlConstants.BIG_WINCH_SPEED);
        } else {
            bigWinch.set(0);
        }
    }

    public boolean startBigWinch() {
        if (!this.bigWinchSpinning) {
            this.bigWinchSpinning = true;
            return true;
        } else {
            return false;
        }

    }

    public boolean stopBigWinch() {
        if (this.bigWinchSpinning) {
            this.bigWinchSpinning = false;
            return true;
        } else {
            return false;
        }
    }

    public double getWinchEncoder() {
        return MiscUtils.encoderToSpeed(bigWinchEncoder.getCountsPerRevolution(), bigWinchEncoder.getVelocity());
    }

    public void setSmallWinchSpeed(double speed) {
        smallWinch.set(ControlMode.PercentOutput, speed * 0.5);
    }

    public void setSmallWinchHold() {
        smallWinch.set(ControlMode.PercentOutput, holdExtenderArm.getOutput(1));
    }

    public void setPistonState(Constants.SolenoidStates state) {
        switch (state) {
            case UP:
                solenoid.set(Value.kForward);
                break;
            case DOWN:
                solenoid.set(Value.kReverse);
                break;
            case OFF:
                solenoid.set(Value.kOff);
                break;
        }
    }

    public Value getSolenoid() {
        return this.solenoid.get();
    }
}