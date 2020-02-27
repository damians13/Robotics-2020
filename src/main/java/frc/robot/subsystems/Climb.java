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
        solenoid = new DoubleSolenoid(13, 0, 3);
    }

    @Override
    public void periodic() {
        smallWinch.set(ControlMode.PercentOutput, Robot.Container.secondaryControllerAxisValue(Constants.ControllerConstants.Xbox_Left_Y_Axis));
        //System.out.println("smallWinch controller input: " + Robot.Container.secondaryControllerAxisValue(Constants.ControllerConstants.Xbox_Left_Y_Axis));
        bigWinch.set(Robot.Container.secondaryControllerAxisValue(Constants.ControllerConstants.Xbox_Right_Y_Axis));
    }

    public double getWinchEncoder() {
        return MiscUtils.encoderToSpeed(bigWinchEncoder.getCountsPerRevolution(), bigWinchEncoder.getVelocity());
    }

    public void setSmallWinchSpeed(double speed) {
        smallWinch.set(ControlMode.PercentOutput, speed);
    }

    public void setSmallWinchHold() {
        smallWinch.set(ControlMode.PercentOutput, holdExtenderArm.getOutput(1));
    }

    public void setExtenderArmState(Constants.SolenoidStates state) {
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