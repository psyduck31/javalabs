public class Point3d {
	private double X; private double Y; private double Z;


	public Point3d(double x, double y, double z) {
		X = x; Y = y; Z = z;
	}


	public Point3d() {
		this(0,0,0);
	}


	public double getX() {
		return X;
	}


	public double getY() {
		return Y;
	}


	public double getZ() {
		return Z;
	}


	public void setX(double value) {
		X = value;
	}


	public void setY(double value) {
		Y = value;
	}


	public void setZ(double value) {
		Z = value;
	}


	public double distanceTo(Point3d ex) {
		return Math.abs(Math.sqrt(Math.pow(X-ex.getX(), 2) + Math.pow(Y-ex.getY(), 2) + Math.pow(Z-ex.getZ(), 2)));
	}
}