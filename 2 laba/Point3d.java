public class Point3d extends Point2d {
	//private double x; private double Y; private double Z;
	private double zCoord;

	public Point3d(double x, double y, double z) {
		super(x, y);
		this.zCoord = z;
	}


	public Point3d() {
		super();
		this.zCoord = 0;
	}


	public double getZ() {
		return zCoord;
	}


	public void setZ(double value) {
		zCoord = value;
	}


	public double distanceTo(Point3d ex) {
		return Math.abs(Math.sqrt(Math.pow(getX()-ex.getX(), 2) + Math.pow(getY()-ex.getY(), 2) + Math.pow(zCoord-ex.getZ(), 2)));
	}
}