import java.awt.geom.Rectangle2D;

public class Tricorn extends FractalGenerator {
	
	public static final int MAX_ITERATIONS = 100;
	
	public Tricorn() {
		
	}
	
	@Override 
	public void getInitialRange(Rectangle2D.Double range) {
		range.x = -2;
		range.y = -2;
		range.width = 4;
		range.height = 4;
	}
	
	@Override
	public int numIterations(double x, double y) {
		int iterations = 0;
		double real = 0;
		double supposed = 0;
		
		while (iterations < MAX_ITERATIONS && real * real + supposed * supposed < 4) {
			double r = real * real - supposed * supposed + x;
			double s = -2 * real * supposed + y;
			real = r;
			supposed = s;
			iterations += 1;
		}
		
		if (iterations == MAX_ITERATIONS) return -1;
		return iterations;
	}
	
	@Override
	public String toString() {
		return "Tricorn";
	}
}