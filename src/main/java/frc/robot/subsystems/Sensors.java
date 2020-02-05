package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.utilities.LiDAR;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.I2C;

@SuppressWarnings("unused")
public class Sensors extends SubsystemBase {

    private AHRS navx;
    private AnalogInput irSensor1;
    private ColorSensorV3 colourSensor;
    private NetworkTable limelight;
    private LiDAR lidar;

    private RobotContainer container; // This classes reference to the RobotContainer

    private static CANEncoder frontRightEncoder;
    private static CANEncoder frontLeftEncoder;
    private static CANEncoder backRightEncoder;
    private static CANEncoder backLeftEncoder;

    public static enum _Encoder {
        FRONT_RIGHT, FRONT_LEFT, BACK_RIGHT, BACK_LEFT
    }

    public static enum Colour {
        BLUE, GREEN, RED, YELLOW
    }

    public Sensors(RobotContainer container) {
        this.container = container;

        navx = new AHRS(SPI.Port.kMXP, (byte) 50);
        irSensor1 = new AnalogInput(0); // Initialize with the channel number the sensor is plugged into
        colourSensor = new ColorSensorV3(I2C.Port.kOnboard);
        limelight = NetworkTableInstance.getDefault().getTable("limelight");
        lidar = new LiDAR(Port.kMXP);

        frontRightEncoder = new CANEncoder(this.container.driveTrain.frontRight);
        frontLeftEncoder = new CANEncoder(this.container.driveTrain.frontLeft);
        backRightEncoder = new CANEncoder(this.container.driveTrain.backRight);
        backLeftEncoder = new CANEncoder(this.container.driveTrain.backLeft);

        this.container.driveTrain.initOdometry(this);
    }

    public double getGyroX() {
        return navx.getRawGyroX();
    }

    @Override
    public void periodic() {
    }

    // We could just use 4096 for this, but this method will ensure accuracy if one changes
    public double getEncoderRotations(_Encoder encoder) {
        switch(encoder) {
            case FRONT_RIGHT:
                return frontRightEncoder.getPosition();
            case FRONT_LEFT:
                return frontLeftEncoder.getPosition();
            case BACK_RIGHT:
                return backRightEncoder.getPosition();
            case BACK_LEFT:
                return backLeftEncoder.getPosition();
            default:
                System.out.println("Problem getting encoder position.");
                return 0;
        }
    }

    public double getGyroY() {
        return navx.getRawGyroY();
    }

    public double getGyroZ() {
        return navx.getRawGyroZ();
    }

    public double getIrVoltage() {
        double sensorVoltage = irSensor1.getVoltage();
        if (sensorVoltage > 4 || sensorVoltage < 0) { // Eliminate corrupt data
            sensorVoltage = 0;
        }
        return sensorVoltage;
    }

    public int getEncoderResolution(_Encoder encoder) {
        switch(encoder) {
            case FRONT_RIGHT:
                return frontRightEncoder.getCountsPerRevolution();
            case FRONT_LEFT:
                return frontLeftEncoder.getCountsPerRevolution();
            case BACK_RIGHT:
                return backRightEncoder.getCountsPerRevolution();
            case BACK_LEFT:
                return backLeftEncoder.getCountsPerRevolution();
            default:
                System.out.println("Problem getting encoder resolution.");
                return 0;
        }
    }

    public int getColourSensorRed() {
        return colourSensor.getRed();
    }

    public int getColourSensorGreen() {
        return colourSensor.getGreen();
    }

    public int getColourSensorBlue() {
        return colourSensor.getBlue();
    }

    public int getColourSensorIR() {
        return colourSensor.getIR();
    }

    public int getColourSensorDistance() {
        return colourSensor.getProximity();
    }

    public void turnOffTheDamnLimelightLED() {
        limelight.getEntry("ledMode").setNumber(1);
    }

    public void turnOnLimelightLED() {
        limelight.getEntry("ledMode").setNumber(0);
    }

    public int getLimelightLED() {
        return (int) limelight.getEntry("ledMode").getNumber(0);
    }
    
    public double[] getLimelight3dPosition() { // Results of a 3D position solution, 6 numbers: Translation (x,y,y) Rotation(pitch,yaw,roll)
        return limelight.getEntry("camtran").getDoubleArray(new double[] {});
    }

    public double getLimelightTA() { // Returns the area of the target (0% to 100%)
        return limelight.getEntry("ta").getDouble(0);
    }

    // The next two methods are used to get the X and Y coordinates of the limelight detected corners
    public double[] getLimelightCornersX() {
        return limelight.getEntry("tcornx").getDoubleArray(new double[] {});
    }

    public double[] getLimelightCornersY() {
        return limelight.getEntry("tcornx").getDoubleArray(new double[] {});
    }

    public double getLimelightTHor() { // Horizontal sidelength of the rough bounding box (0 - 320 pixels)
        return limelight.getEntry("thor").getDouble(0);
    }

    public int getLimelightTL() { // Returns the latency of the limelight pipeline
        return (int) limelight.getEntry("tl").getDouble(0) + 11;
    }

    public double getLimelightTLong() { // Sidelength of longest side of the fitted bounding box (pixels)
        return limelight.getEntry("tlong").getDouble(0);
    }
    
    public double getLimelightPipeline() { // 	Vertical sidelength of the rough bounding box (0 - 320 pixels)
        return limelight.getEntry("getpipe").getDouble(0);
    }

    public double getLimelightTS() { // Returns the skew/rotation (-90 degrees to 0 degrees)
        return limelight.getEntry("ts").getDouble(0);
    }

    public double getLimelightTShort() { // Sidelength of shortest side of the fitted bounding box (pixels)
        return limelight.getEntry("tshort").getDouble(0);
    }

    public int getLimelightTV() { // Returns the number of targets found (0 or 1)
        return (int) limelight.getEntry("tv").getDouble(0);
    }

    public boolean limelightHasTarget() {
        return ((int) limelight.getEntry("tv").getDouble(0)) == 1;
    }

    public double getLimelightTVert() { // 	Vertical sidelength of the rough bounding box (0 - 320 pixels)
        return limelight.getEntry("tvert").getDouble(0);
    }

    public double getLimelightTX() {
        return limelight.getEntry("tx").getDouble(0);
    }

    public double getLimelightTY() {
        return limelight.getEntry("ty").getDouble(0);
    }

    public double getLidarDistance() {
        return lidar.getDistance();
    }
    
}