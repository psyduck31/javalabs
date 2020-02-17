public class Palindrome {
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			boolean result = isPalindrome(s);
			System.out.printf("%s - %b\n", s, result);
		}
	}


	public static String reverseString(String str) {
		String result = "";
		for (int i = str.length() - 1; i >= 0; i--) {
			result += str.charAt(i);
		}
		return result;
	}


	public static boolean isPalindrome(String s) {
		boolean result = (s.equals(reverseString(s))) ? true : false;
		return result;
	}
}