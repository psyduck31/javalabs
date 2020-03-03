public class Point2d {
	private double xCoord;
	private double yCoord;


	public Point2d(double x, double y) {
		this.xCoord = x;
		this.yCoord = y;
	}


	public Point2d() {
		this.xCoord = 0;
		this.yCoord = 0;
	}


	public double getX() {
		return xCoord;
	}


	public double getY() {
		return yCoord;
	}


	public void setX(double x) {
		xCoord = x;
	}


	public void setY(double y) {
		yCoord = y;
	}
}