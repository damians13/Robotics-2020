package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class DetectButtonPress extends CommandBase {

    private double button;
    private Controller controller;
    private boolean finished;

    public enum Controller {
        DRIVER, SECONDARY
    }

    public DetectButtonPress(Controller controller, double button) {
        this.button = button;
        this.controller = controller;
        this.finished = false;
    }

    /**
     * I hate the execute method of this class
     * There is definitely a better way to do this, but I don't have time
     */

    @Override
    public void execute() {
        switch (this.controller) {
            case DRIVER:
                if (button == Constants.ControllerConstants.Xbox_A_Button) {
                    this.finished = Robot.Container.driverController.getAButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_B_Button) {
                    this.finished = Robot.Container.driverController.getBButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_X_Button) {
                    this.finished = Robot.Container.driverController.getXButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_Y_Button) {
                    this.finished = Robot.Container.driverController.getYButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_Right_Bumper) {
                    this.finished = Robot.Container.driverController.getBumperPressed(Hand.kRight);

                } else if (button == Constants.ControllerConstants.Xbox_Left_Bumper) {
                    this.finished = Robot.Container.driverController.getBumperPressed(Hand.kLeft);

                } else if (button == Constants.ControllerConstants.Xbox_Back_Button) {
                    this.finished = Robot.Container.driverController.getBackButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_Start_Button) {
                    this.finished = Robot.Container.driverController.getStartButtonPressed();

                } else {
                    System.out.println("ERROR: Unknown button press detected!");
                    this.finished = false;
                }
                break;
            case SECONDARY:
                if (button == Constants.ControllerConstants.Xbox_A_Button) {
                    this.finished = Robot.Container.secondaryController.getAButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_B_Button) {
                    this.finished = Robot.Container.secondaryController.getBButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_X_Button) {
                    this.finished = Robot.Container.secondaryController.getXButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_Y_Button) {
                    this.finished = Robot.Container.secondaryController.getYButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_Right_Bumper) {
                    this.finished = Robot.Container.secondaryController.getBumperPressed(Hand.kRight);

                } else if (button == Constants.ControllerConstants.Xbox_Left_Bumper) {
                    this.finished = Robot.Container.secondaryController.getBumperPressed(Hand.kLeft);

                } else if (button == Constants.ControllerConstants.Xbox_Back_Button) {
                    this.finished = Robot.Container.secondaryController.getBackButtonPressed();

                } else if (button == Constants.ControllerConstants.Xbox_Start_Button) {
                    this.finished = Robot.Container.secondaryController.getStartButtonPressed();

                } else {
                    System.out.println("ERROR: Unknown button press detected!");
                    this.finished = false;
                }
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }
}