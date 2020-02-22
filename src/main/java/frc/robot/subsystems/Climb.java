package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.MiscUtils;
import frc.robot.utilities.PID;

public class Climb extends SubsystemBase {

    public enum ExtenderArmStates {
        UP, DOWN, OFF;
    }

    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;

    private PID holdExtenderArm;

    private VictorSP smallWinch;
    private CANSparkMax bigWinch;
    private CANEncoder bigWinchEncoder;
    private DoubleSolenoid solenoid;

    public Climb() {
        holdExtenderArm = new PID(kP, kI, kD, 0);

        smallWinch = new VictorSP(7000); // lol
        bigWinch = new CANSparkMax(7, MotorType.kBrushless);
        bigWinchEncoder = new CANEncoder(bigWinch);
        //               forwardChannel, reverseChannel
        solenoid = new DoubleSolenoid(0, 0);
    }

    @Override
    public void periodic() {

    }

    public double getWinchEncoder() {
        return MiscUtils.encoderToSpeed(bigWinchEncoder.getCountsPerRevolution(), bigWinchEncoder.getVelocity());
    }

    public void setSmallWinchSpeed(double speed) {
        smallWinch.set(speed);
    }

    public void setSmallWinchHold() {
        smallWinch.set(holdExtenderArm.getOutput(1));
    }

    public void setExtenderArmState(ExtenderArmStates state) {
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
}