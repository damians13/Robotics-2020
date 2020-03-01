package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class TogglePneumatic extends CommandBase {

    private boolean finished;

    public static enum Solenoids {
        COLOUR_WHEEL, INTAKE, CLIMB
    }

    public TogglePneumatic(Solenoids solenoid) {
        this.finished = false;

        switch (solenoid) {
            case COLOUR_WHEEL:
                if (Robot.Container.colourWheel.getSolenoid() == Value.kForward) { // Forward
                    Robot.Container.colourWheel.setPistonState(Constants.SolenoidStates.DOWN);
                } else { // Reverse and off
                    Robot.Container.colourWheel.setPistonState(Constants.SolenoidStates.UP);
                }
                this.finished = true;
                break;
            case INTAKE:
                if (Robot.Container.intake.getSolenoid() == Value.kForward) { // Forward
                    Robot.Container.intake.setPistonState(Constants.SolenoidStates.DOWN);
                } else { // Reverse and off
                    Robot.Container.intake.setPistonState(Constants.SolenoidStates.UP);
                }
                this.finished = true;
                break;
            case CLIMB:
                if (Robot.Container.climb.getSolenoid() == Value.kForward) { // Forward
                    Robot.Container.climb.setPistonState(Constants.SolenoidStates.DOWN);
                } else { // Reverse and off
                    Robot.Container.climb.setPistonState(Constants.SolenoidStates.UP);
                }
                this.finished = true;
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }
}