package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ColourWheel extends SubsystemBase {

    private VictorSPX motor;
    public DoubleSolenoid solenoid;

    public ColourWheel() {
        motor = new VictorSPX(12);
        solenoid = new DoubleSolenoid(13, 3, 2);
    }

    @Override
    public void periodic() {

    }

    public void setMotorSpeed(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
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
}