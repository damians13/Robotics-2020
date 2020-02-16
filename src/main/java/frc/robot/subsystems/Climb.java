package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb extends SubsystemBase {

    public enum ExtenderArmStates {
        UP, DOWN, OFF;
    }

    private VictorSP smallWinch;
    private CANSparkMax bigWinch;

    private DoubleSolenoid solenoid;

    public Climb() {
        smallWinch = new VictorSP(7000); // lol
        bigWinch = new CANSparkMax(7, MotorType.kBrushless);

        //                            forwardChannel, reverseChannel
        solenoid = new DoubleSolenoid(0, 0);
    }

    @Override
    public void periodic() {

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