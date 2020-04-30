public class Task4 {
	public static void main(String[] args) {
		System.out.println(checkPerfect(args[0]));
	}


	public static String checkPerfect(String s) {
		if (s.length() <= 2) {
			return "Incompatible.";
		}
		if (s.charAt(0) == s.charAt(s.length() - 1)) {
			return "Two's a pair.";
		}
		char first = s.charAt(0);
		char last = s.charAt(s.length() - 1);
		char[] chars = s.toCharArray();
		chars[0] = last;
		chars[s.length() - 1] = first;
		return String.valueOf(chars);
	}
}