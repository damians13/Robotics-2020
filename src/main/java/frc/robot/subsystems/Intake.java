package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Intake extends SubsystemBase {

    private VictorSPX intakeMotor;
    private DoubleSolenoid solenoid;
    private boolean toggle;

    public Intake() {
        intakeMotor = new VictorSPX(10);
        //                                  moduleNumber, forwardChannel, reverseChannel
        solenoid = new DoubleSolenoid(13, 0, 0);
        toggle = false;
    }

    @Override
    public void periodic() {
        // Get button input
        if (Robot.Container.driverController.getAButtonPressed()) {
            toggle = true;
        } else if (Robot.Container.driverController.getAButtonReleased()) {
            toggle = false;
        }

        // Set motor speed based on input
        if (toggle) {
            intakeMotor.set(ControlMode.PercentOutput, Constants.ControlConstants.INTAKE_SPEED);
        } else {
            intakeMotor.set(ControlMode.PercentOutput, 0);
        }
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