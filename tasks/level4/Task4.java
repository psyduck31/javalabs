import java.util.*;


public class Task4 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.printf("Start: ");
		double start = Double.parseDouble(input.nextLine());
		System.out.printf("End: ");
		double end = Double.parseDouble(input.nextLine());
		System.out.printf("Salary: ");
		double salary = Double.parseDouble(input.nextLine());
		System.out.printf("Salary for overtime: ");
		double overtimeSalary = Double.parseDouble(input.nextLine());
		System.out.println(overtime(start, end, salary, overtimeSalary));
	}


	public static String overtime(double start, double end, double salary, double overtimeSalary) {
		String money = "$";
		if (end > 17) return money + String.valueOf((17 - start) * salary + (end - 17) * salary * overtimeSalary);
		return money + String.valueOf((end-start) * salary); 
	}
}