package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private VictorSPX intakeMotor;
    private DoubleSolenoid solenoid;
    private boolean spinning;

    public Intake() {
        intakeMotor = new VictorSPX(10);
        //                                  moduleNumber, forwardChannel, reverseChannel
        solenoid = new DoubleSolenoid(13, 0, 1);
        this.spinning = false;
    }

    @Override
    public void periodic() {
        // Set motor speed based on input
        if (this.spinning) {
            intakeMotor.set(ControlMode.PercentOutput, Constants.ControlConstants.INTAKE_SPEED);
        } else {
            intakeMotor.set(ControlMode.PercentOutput, 0);
        }
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

    public String getStatus() {
        if (this.spinning) {
            return "Spinning";
        } else {
            return "Not spinning";
        }
    }

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

    public Value getSolenoid() {
        return this.solenoid.get();
    }

}