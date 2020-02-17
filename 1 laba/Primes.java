public class Primes {
	public static void main(String[] args) {
		for (int i = 2; i < 100; i++) {
			boolean result = isPrime(i);
			if (result == true) 
				System.out.printf("Number %d %b\n", i, result);
		}
	}


	public static boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0 && n != i)
				return false;
		}
		return true;
	}
}