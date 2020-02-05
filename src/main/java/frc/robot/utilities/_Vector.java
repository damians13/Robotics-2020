package frc.robot.utilities;

public class _Vector {

    private double _mag;
    private double _bearing;
    private double _refAngle;
    private double _x;
    private double _y;

    public _Vector(double mag, double bearing) {
        this._mag = mag;
        this._bearing = bearing % 360;
        this._refAngle = MiscUtils.bearingToRefAngle(this._bearing);
        this._x = findX(this._mag, this._refAngle, this._bearing);
        this._y = findY(this._mag, this._refAngle, this._bearing);
    }

    public double getMag() {
        return this._mag;
    }

    public double getBearing() {
        return this._bearing;
    }

    public double getRefAngle() {
        return this._refAngle;
    }

    public double getX() {
        return this._x;
    }

    public double getY() {
        return this._y;
    }

    // Redefines the vector based on a given true bearing and magnitude
    public void setBearingAndMag(double bearing, double mag) {
        this._bearing = bearing;
        this._mag = mag;
        this._refAngle = MiscUtils.bearingToRefAngle(this._bearing);
        this._x = findX(this._mag, this._refAngle, this._bearing);
        this._y = findY(this._mag, this._refAngle, this._bearing);
    }

    // Redefines the vector based on a given x and y value
    public void setXandY(double x, double y) {
        this._x = x;
        this._y = y;
        this._mag = findMag(this._x, this._y);
        this._refAngle = findAngle(this._x, this._y);
        this._bearing = MiscUtils.refAngleToBearing(this._x, this._y, this._refAngle);
    }

    private double findX(double mag, double refAngle, double bearing) {
        if (bearing < 180)                      // X should be positive
            return mag * Math.cos(refAngle);    // Return positive
        else                                    // X should be negative
            return -(mag * Math.cos(refAngle)); // Return negative
    }

    private double findY(double mag, double refAngle, double bearing) {
        if (bearing < 90 || bearing > 270)      // Y should be positive
            return mag * Math.sin(refAngle);    // Return positive
        else                                    // Y should be negative
            return -(mag * Math.sin(refAngle)); // Return negative
    }

    private double findMag(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    private double findAngle(double x, double y) {
        return Math.abs(Math.tan(y / x));
    }
}