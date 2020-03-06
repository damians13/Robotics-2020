package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.utilities.PID;
import frc.robot.utilities.MiscUtils;

public class DriveTrainMecanum extends SubsystemBase {
    // https://www.desmos.com/calculator/mjjpxtbf11
    public CANSparkMax frontLeft;
    public CANSparkMax frontRight;
    public CANSparkMax backLeft;
    public CANSparkMax backRight;

    private MecanumDriveKinematics driveKinematics;
    private MecanumDriveOdometry driveOdometry;
    private MecanumDriveWheelSpeeds wheelSpeeds;

    private boolean gyroCompensate;

    public Pose2d currentOdometry;

    private RobotContainer container; // This classes reference to the RobotContainer

    double joyX = 0;
    double joyY = 0;
    double rotation = 0;
    double angle = 0;
    double frontLeftPower = 0;
    double frontRightPower = 0;
    double backLeftPower = 0;
    double backRightPower = 0;
    double totalGyroDifference = 0;

    private double totalMeasuredRotation;

    private static final double kP = 0.006;
    private static final double kI = 0.001;
    private static final double kD = 0;
    private static final double target = 0;
// 0.007 for kp and ki
    private PID gyroPID = new PID(kP, kI, kD, target);

    public DriveTrainMecanum(RobotContainer container) {
        this.container = container;

        frontLeft = new CANSparkMax(3, MotorType.kBrushless);
        frontRight = new CANSparkMax(2, MotorType.kBrushless);
        backLeft = new CANSparkMax(4, MotorType.kBrushless);
        backRight = new CANSparkMax(1, MotorType.kBrushless);

        gyroCompensate = true;

        currentOdometry = new Pose2d(new Translation2d(0, 0), Rotation2d.fromDegrees(0));

        totalMeasuredRotation = 0;

        frontRight.setInverted(true);
        backRight.setInverted(true);
    }

    public void startGyroComp() {
        this.gyroCompensate = true;
    }
    public void stopGyroComp() {
        this.gyroCompensate = false;
    }

    // Needs to be passed a sensors object for some weird reason, otherwise it gives a null reference exception
    public void initOdometry(Sensors sensors) {
        driveKinematics = new MecanumDriveKinematics(Constants.ControlConstants.frontLeftLocation, Constants.ControlConstants.frontRightLocation, Constants.ControlConstants.backLeftLocation, Constants.ControlConstants.backLeftLocation);
        System.out.println(sensors.getGyroZ());
        driveOdometry = new MecanumDriveOdometry(driveKinematics, Rotation2d.fromDegrees(totalMeasuredRotation), new Pose2d(0, 0, Rotation2d.fromDegrees(0)));
        wheelSpeeds = new MecanumDriveWheelSpeeds(
            // Front left
            MiscUtils.encoderToSpeed(sensors.getEncoderResolution(Sensors._Encoder.FRONT_LEFT), sensors.getEncoderSpeed(Sensors._Encoder.FRONT_LEFT)),
            // Front right
            MiscUtils.encoderToSpeed(sensors.getEncoderResolution(Sensors._Encoder.FRONT_RIGHT), sensors.getEncoderSpeed(Sensors._Encoder.FRONT_RIGHT)),
            // Back left
            MiscUtils.encoderToSpeed(sensors.getEncoderResolution(Sensors._Encoder.BACK_LEFT), sensors.getEncoderSpeed(Sensors._Encoder.BACK_LEFT)),
            // Back right
            MiscUtils.encoderToSpeed(sensors.getEncoderResolution(Sensors._Encoder.BACK_RIGHT), sensors.getEncoderSpeed(Sensors._Encoder.BACK_RIGHT)));
    }

    public void updateOdometry(Sensors sensors) {
        wheelSpeeds = new MecanumDriveWheelSpeeds(
            // Front left
            MiscUtils.encoderToSpeed(sensors.getEncoderResolution(Sensors._Encoder.FRONT_LEFT), sensors.getEncoderSpeed(Sensors._Encoder.FRONT_LEFT)),
            // Front right
            MiscUtils.encoderToSpeed(sensors.getEncoderResolution(Sensors._Encoder.FRONT_RIGHT), sensors.getEncoderSpeed(Sensors._Encoder.FRONT_RIGHT)),
            // Back left
            MiscUtils.encoderToSpeed(sensors.getEncoderResolution(Sensors._Encoder.BACK_LEFT), sensors.getEncoderSpeed(Sensors._Encoder.BACK_LEFT)),
            // Back right
            MiscUtils.encoderToSpeed(sensors.getEncoderResolution(Sensors._Encoder.BACK_RIGHT), sensors.getEncoderSpeed(Sensors._Encoder.BACK_RIGHT)));

        driveOdometry.update(Rotation2d.fromDegrees(totalMeasuredRotation), wheelSpeeds);
    }

    public Pose2d getOdometry() {
        Pose2d currentOdometry = driveOdometry.getPoseMeters();
        return new Pose2d(new Translation2d(currentOdometry.getTranslation().getX() * Constants.UnitConversions.ODOMETRY_TO_M, currentOdometry.getTranslation().getY() * Constants.UnitConversions.ODOMETRY_TO_M), currentOdometry.getRotation());
    }

    // Run every time the scheduler runs (50hz)
    @Override
    public void periodic() {
        double currentGyroInput = this.container.sensors.getGyroZ() / 50;

        if (Math.abs(currentGyroInput) > 0.1) {
            totalMeasuredRotation += currentGyroInput;
        }
        
        updateOdometry(Robot.Container.sensors);

		joyX = Robot.Container.driverControllerAxisValue(Constants.ControllerConstants.Xbox_Left_X_Axis);
		joyY = -Robot.Container.driverControllerAxisValue(Constants.ControllerConstants.Xbox_Left_Y_Axis);
        rotation = Constants.ControlConstants.ROTATION_MULT * (Robot.Container.driverControllerAxisValue(Constants.ControllerConstants.Xbox_Right_Trigger) - Robot.Container.driverControllerAxisValue(Constants.ControllerConstants.Xbox_Left_Trigger));

        mecanumDrive(joyX, joyY, rotation);
    }

    public void cappedMecanumDrive(double xInput, double yInput, double rotInput, double capSpeed) {
        double xSpeed = xInput;
        double ySpeed = yInput;
        double rotSpeed = rotInput;

        // Cap speeds
        if (Math.abs(xSpeed) > capSpeed) {
            xSpeed *= capSpeed / Math.abs(xSpeed);
        }
        if (Math.abs(ySpeed) > capSpeed) {
            ySpeed *= capSpeed / Math.abs(ySpeed);
        }
        if (Math.abs(rotSpeed) > capSpeed) {
            rotSpeed *= capSpeed / Math.abs(rotSpeed);
        }

        this.mecanumDrive(xSpeed, ySpeed, rotSpeed);
    }

    public void mecanumDrive(double joystickX, double joystickY, double rotation) {
        // Deadband inputs
        if (Math.abs(rotation) < Constants.ControlConstants.ROTATION_DEADBAND)
            rotation = 0;
        if (Math.abs(joystickX) < Constants.ControlConstants.JOY_DEADBAND)
            joystickX = 0;
        if (Math.abs(joystickY) < Constants.ControlConstants.JOY_DEADBAND)
            joystickY = 0;

        if (gyroCompensate) {
            // Gyro comp
            if (Math.abs(rotation) >= Constants.ControlConstants.GYRO_TOGGLE) {
                totalGyroDifference = 0;
                gyroPID.resetTarget(0);
            } else {
                totalGyroDifference += this.container.sensors.getGyroZ() / 50;

                if (totalGyroDifference < Constants.ControlConstants.GYRO_DEADBAND) {
                    rotation = 0;
                } else {
                    rotation = -totalGyroDifference / 10;
                }

                if (Math.abs(rotation) > Constants.ControlConstants.MAX_TURN_SPEED)
                    rotation *= Constants.ControlConstants.MAX_TURN_SPEED / Math.abs(rotation);

                rotation *= gyroPID.getOutput(this.container.sensors.getGyroZ());
            }
        }

        // Cap rotation to ControlConstants value
        if (Math.abs(rotation) > Constants.ControlConstants.MAX_TURN_SPEED)
            rotation *= Constants.ControlConstants.MAX_TURN_SPEED / Math.abs(rotation);

        // Calculate speed for each wheel
        frontRightPower = joystickY - joystickX - rotation;
        frontLeftPower = joystickY + joystickX + rotation;
        backLeftPower = joystickY - joystickX + rotation;
        backRightPower = joystickY + joystickX - rotation;

        // Cap speeds to ControlConstants values
        for (double speed : new double[] { frontLeftPower, frontRightPower, backLeftPower, backRightPower }) {
            if (speed > Constants.ControlConstants.MAX_ROBOT_SPEED) {
                speed = Constants.ControlConstants.MAX_ROBOT_SPEED;
            } else if (speed < Constants.ControlConstants.MAX_ROBOT_SPEED) {
                speed = Constants.ControlConstants.MAX_ROBOT_SPEED;
            }
        }
        // *********************************************
        // * Same thing as above? Maybe remove this... *
        // *********************************************
        if (Math.abs(frontLeftPower) > Constants.ControlConstants.MAX_ROBOT_SPEED)
            frontLeftPower *= Constants.ControlConstants.MAX_ROBOT_SPEED / Math.abs(frontLeftPower);
        if (Math.abs(frontRightPower) > Constants.ControlConstants.MAX_ROBOT_SPEED)
            frontRightPower *= Constants.ControlConstants.MAX_ROBOT_SPEED / Math.abs(frontRightPower);
        if (Math.abs(backLeftPower) > Constants.ControlConstants.MAX_ROBOT_SPEED)
            backLeftPower *= Constants.ControlConstants.MAX_ROBOT_SPEED / Math.abs(backLeftPower);
        if (Math.abs(backRightPower) > Constants.ControlConstants.MAX_ROBOT_SPEED)
            backRightPower *= Constants.ControlConstants.MAX_ROBOT_SPEED / Math.abs(backRightPower);

        // Power the motors
        frontLeft.set(frontLeftPower);
        frontRight.set(frontRightPower);
        backLeft.set(backLeftPower);
        backRight.set(backRightPower);
    }

    public void stop() {
        frontLeft.set(0);
        frontRight.set(0);
        backLeft.set(0);
        backRight.set(0);
    }
}