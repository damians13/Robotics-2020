package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Intake extends SubsystemBase {

    private VictorSP intakeMotor;
    private boolean toggle;

    public Intake() {
        intakeMotor = new VictorSP(0);
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
            intakeMotor.set(Constants.ControlConstants.INTAKE_SPEED);
        } else {
            intakeMotor.set(0);
        }
    }

}