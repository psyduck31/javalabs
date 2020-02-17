public class Main {
	public static void main(String[] args) {
		double[] Args = new double[9];
		for (int i = 0; i < args.length; i++) {
			//System.out.printf("%s", Args[i].getClass().getName());
			Args[i] = Double.parseDouble(args[i]);
		}
		Point3d firstP = new Point3d(Args[0], Args[1], Args[2]);
		Point3d secondP = new Point3d(Args[3], Args[4], Args[5]);
		Point3d thirdP = new Point3d(Args[6], Args[7], Args[8]);
		System.out.printf("[firstP]: X:%s, Y:%s, Z:%s\n", firstP.getX(), firstP.getY(), firstP.getZ());
		System.out.printf("[secondP]: X:%s, Y:%s, Z:%s\n", secondP.getX(), secondP.getY(), secondP.getZ());
		System.out.printf("[thirdP]: X:%s, Y:%s, Z:%s\n", thirdP.getX(), thirdP.getY(), thirdP.getZ());
		double result = computeArea(firstP, secondP, thirdP);
		System.out.printf("Result: %.2f", result);
	}


	public static double computeArea(Point3d f, Point3d s, Point3d t) {
		double a = f.distanceTo(s);
		double b = f.distanceTo(t); 
		double c = s.distanceTo(t);
		double perimeter = (a + b + c) / 2;
		return Math.sqrt(perimeter * (perimeter - b) * (perimeter - c) * (perimeter - a));
	}

}