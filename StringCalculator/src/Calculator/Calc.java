package Calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Calc {

	public int add(String numbers) {
		if (numbers.isEmpty()) {
			return 0;
		}

		// Check if the input string starts with a custom delimiter definition
		if (numbers.startsWith("//")) {
			// Find the end index of the delimiter definition
			int delimiterEndIndex = numbers.indexOf("\n");

			// Extract the delimiter(s)
			String delimiterSection = numbers.substring(2, delimiterEndIndex);

			// Extract the numbers part
			String numberStr = numbers.substring(delimiterEndIndex + 1);

			// Split the numbers part using the custom delimiter(s)
			String[] delimiters = parseDelimiters(delimiterSection);
			String regex = buildRegex(delimiters);
			String[] numArray = numberStr.split(regex);

			// Remove empty strings
			List<String> cleanedList = new ArrayList<>();
			for (String num : numArray) {
				if (!num.isEmpty()) {
					cleanedList.add(num);
				}
			}

			// Convert List to array
			String[] resultArray = cleanedList.toArray(new String[0]);

			// Calculate the sum of the numbers
			int sum = 0;
			List<Integer> negatives = new ArrayList<>();
			for (String num : resultArray) {
				int n = Integer.parseInt(num);
				if (n < 0) {
					negatives.add(n);
				} else if (n <= 1000) {
					sum += n;
				}
			}
			if (!negatives.isEmpty()) {
				throw new IllegalArgumentException("Negatives not allowed: " + negatives);
			}
			return sum;
		} else {
			// Split the input string by commas and new lines
			String[] numArray = numbers.split("[,\n]");

			// Calculate the sum of the numbers
			int sum = 0;
			List<Integer> negatives = new ArrayList<>();
			for (String num : numArray) {
				int n = Integer.parseInt(num);
				if (n < 0) {
					negatives.add(n);
				} else if (n <= 1000) {
					sum += n;
				}
			}
			if (!negatives.isEmpty()) {
				throw new IllegalArgumentException("Negatives not allowed: " + negatives);
			}
			return sum;
		}
	}

	// Method to parse the custom delimiters
	private String[] parseDelimiters(String delimiterSection) {
		if (delimiterSection.isEmpty()) {
			return new String[] {};
		}
		return delimiterSection.split("\\]\\[|\\[|\\]");
	}

	// Method to build a regex pattern from multiple delimiters
	private String buildRegex(String[] delimiters) {
		StringBuilder regexBuilder = new StringBuilder();
		regexBuilder.append("(");
		for (String delimiter : delimiters) {
			if (!delimiter.isEmpty()) {
				regexBuilder.append(Pattern.quote(delimiter)).append("|");
			}
		}
		if (regexBuilder.charAt(regexBuilder.length() - 1) == '|') {
			regexBuilder.deleteCharAt(regexBuilder.length() - 1); // Remove the last unnecessary "|"
		}
		regexBuilder.append(")");
		return regexBuilder.toString();
	}

	public static void main(String[] args) {
		Calc calculator = new Calc();

		// Test cases
		try {

			System.out.println(calculator.add("")); // Expected output: 0
			System.out.println(calculator.add("1")); // Expected output: 1
			System.out.println(calculator.add("1,2")); // Expected output: 3
			System.out.println(calculator.add("1\n2,3")); // Expected output: 6
			System.out.println(calculator.add("//;\n1;2")); // Expected output: 3
			System.out.println(calculator.add("1001,2")); // Expected output: 2
			System.out.println(calculator.add("//[|||]\n1|||2|||3")); // Expected output: 6
			System.out.println(calculator.add("//[|][%]\n1|2%3")); // Expected output: 6
			// System.out.println(calculator.add("-1,2")); // Expected output: Throws exception
			// System.out.println(calculator.add("2,-4,3,-5")); // Expected output: Throws exception

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}
