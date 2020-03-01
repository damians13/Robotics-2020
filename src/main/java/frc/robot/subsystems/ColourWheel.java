package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class ColourWheel extends SubsystemBase {

    private VictorSPX motor;
    public DoubleSolenoid solenoid;

    private double blue;
    private double red;
    private double green;

    public final int threshold = 500;
    public final int greenThreshold = 400;
    public final int yellowThreshold = 800;

    private String previousColour;

    //                                          R,   G,    B
    private static final int[] blueValues = {600, 1800, 1800};
    private static final int[] redValues = {1400, 1000, 400};
    private static final int[] greenValues = {400, 1200, 600};
    private static final int[] yellowValues = {1600, 3000, 400};
    private static final int[] whiteValues = {2300, 4000, 2200};
    
    public ColourWheel() {
        motor = new VictorSPX(12);
        solenoid = new DoubleSolenoid(13, 3, 2);

        previousColour = "No input yet";
    }

    @Override
    public void periodic() {
        blue = Robot.Container.sensors.getColourSensorBlue();
        red = Robot.Container.sensors.getColourSensorRed();
        green = Robot.Container.sensors.getColourSensorGreen();

        SmartDashboard.putNumber("Colour sensor blue", blue);
        SmartDashboard.putNumber("Colour sensor red", red);
        SmartDashboard.putNumber("Colour sensor green", green);

        SmartDashboard.putString("Detected colour", this.determineColour());
        SmartDashboard.putString("Previous detected colour", this.previousColour);
    }

    public Value getSolenoid() {
        return this.solenoid.get();
    }

    public String determineColour() {
        // Check blue
        if (threshold(blue, blueValues[2], threshold) && threshold(red, blueValues[0], threshold) && threshold(green, blueValues[1], threshold)) {
            previousColour = "Blue";
            return "Blue";
        }
        // Check red
        else if (threshold(blue, redValues[2], threshold) && threshold(red, redValues[0], threshold) && threshold(green, redValues[1], threshold)) {
            previousColour = "Red";
            return "Red";
        }
        // Check green
        else if (threshold(blue, greenValues[2], greenThreshold) && threshold(red, greenValues[0], greenThreshold) && threshold(green, greenValues[1], greenThreshold)) {
            previousColour = "Green";
            return "Green";
        }
        // Check yellow
        else if (threshold(blue, yellowValues[2], yellowThreshold) && threshold(red, yellowValues[0], yellowThreshold) && threshold(green, yellowValues[1], yellowThreshold)) {
            previousColour = "Yellow";
            return "Yellow";
        }
        // Check white
        else if (threshold(blue, whiteValues[2], threshold) && threshold(red, whiteValues[0], threshold) && threshold(green, whiteValues[1], threshold)) {
            return "White";
        } else {
            return "No colour detected";
        }
    }

    private boolean threshold(double value1, double value2, double _threshold) {
        return Math.abs(value1 - value2) <= _threshold;
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