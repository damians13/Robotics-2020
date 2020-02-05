package frc.robot.utilities;

public class LimelightCorners {

	int cornerCount;

	Corner topLeft;
	Corner bottomLeft;
	Corner bottomRight;
	Corner topRight;

	public LimelightCorners(double[] xs, double[] ys) {
		updateCorners(xs, ys);
	}

	public void updateCorners(double[] xs, double[] ys) {
		cornerCount = xs.length;
		if (cornerCount == 4) { // 4 corners
			topLeft = new Corner(xs[0], ys[0]);
			bottomLeft = new Corner(xs[3], ys[3]);
			bottomRight = new Corner(xs[2], ys[2]);
			topRight = new Corner(xs[1], ys[1]);
		} else if (cornerCount == 3) { // 3 corners
			topLeft = new Corner(xs[2], ys[2]);
			bottomLeft = null; // This is normally missing if there is 3 reported corners
			bottomRight = new Corner(xs[1], ys[1]);
			topRight = new Corner(xs[0], ys[0]);
		} else { // Unexpected target or no target
			topLeft = null;
			bottomLeft = null;
			bottomRight = null;
			topRight = null;
		}
	}

    /*******************************
     * This method is NOT complete *
     *******************************/
	public double computeArea() {
		if (this.cornerCount == 4) {
			double topDist = Math.sqrt(Math.pow(this.topLeft.x - this.topRight.x, 2) + Math.pow(this.topLeft.y - this.topRight.y, 2));
			double bottomDist = Math.sqrt(Math.pow(this.bottomLeft.x - this.bottomRight.x, 2) + Math.pow(this.bottomLeft.y - this.bottomRight.y, 2));
			double leftDist = Math.sqrt(Math.pow(this.topLeft.x - this.bottomLeft.x, 2) + Math.pow(this.topLeft.y - this.bottomLeft.y, 2));
			double rightDist = Math.sqrt(Math.pow(this.topRight.x - this.bottomRight.x, 2) + Math.pow(this.topRight.y - this.bottomRight.y, 2));

			double averageLength = (topDist + bottomDist) / 2;
			double averageWidth = (rightDist + leftDist) / 2;

			return averageLength * averageWidth;
		} else if (this.cornerCount == 3) {
			double topYDist = this.topLeft.y - this.topRight.y;
			double topRawDist = Math.sqrt(Math.pow(this.topLeft.x - this.topRight.x, 2) + Math.pow(topYDist, 2));
			double rightXDist = this.bottomRight.x - this.topRight.x;
			double rightRawDist = Math.sqrt(Math.pow(rightXDist, 2) + Math.pow(this.bottomRight.y - this.topRight.y, 2));
			
			double topAngle = Math.asin(topYDist / topRawDist);
			double rightsideAngle = Math.asin(rightXDist / rightRawDist);
			double topRightAngle = topAngle + 90 - rightsideAngle;
            double bottomRightAngle = topAngle + 90 + rightsideAngle;
            
            // TO-DO: It is returning this for now to get rid of the yellow lines, this method is NOT complete
            return topRightAngle * bottomRightAngle * 0;
		} else {
			return 0.0;
		}

	}

	// This method will return a ratio of (top left corner height) / (top right corner height)
	// This is used to align the bot at a specific angle relative to the target (for automatic shooting from the trench)
	public double computeAngleRatio() {
		return this.topLeft.y / this.topRight.y;
	}

	private class Corner {
		double x;
		double y;
		public Corner(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}