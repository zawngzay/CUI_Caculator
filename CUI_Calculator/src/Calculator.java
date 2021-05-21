import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Calculator {
	public static void main(String args[]) {
		String[] oper = getOperators();
		String exp = null;
		String result = null;
		System.out.println("Start Calculator Application");
		do {
			try {
				System.out.print("Input expression : ");
				Scanner scn = new Scanner(System.in);
				exp = scn.nextLine();
				if (exp.isEmpty()) {
					break;
				}

				String[] expArr = exp.split(" ");
				List<String> expList = Arrays.asList(expArr);

				boolean hasError = checkInputErrors(expList);

				if (hasError) {
					String errorMessge = "Invalid expression.";
					System.out.println(errorMessge);
				}
				if (!hasError) {
					if (expList.size() > 0) {
						boolean isFistIsChar = isOperator(expList.get(0));
						if (isFistIsChar) {
							List<String> resultList = new ArrayList<String>();
							resultList.add(result);
							resultList.addAll(expList);
							expList = resultList;
						}
					}
					while (expList.size() != 1) {
						boolean hasParen = hasParentheses(expList);
						if (hasParen) {
							int first = 0;
							int last = 0;
							for (int i = 0; i < expList.size(); i++) {
								if (expList.get(i).equals("(")) {
									first = i;
								}
								if (expList.get(i).equals(")")) {
									last = i;
									break;
								}
							}
							List<String> subList1 = expList.subList(0, first);
							List<String> subList2 = expList.subList(last + 1, expList.size());
							List<String> parenSubList = expList.subList(first + 1, last);
							parenSubList = getNumericalExpWithoutPar(oper, parenSubList);
							List<String> combineList = new ArrayList<String>();
							combineList.addAll(subList1);
							combineList.addAll(parenSubList);
							combineList.addAll(subList2);
							expList = combineList;
						} else {

							expList = getNumericalExpWithoutPar(oper, expList);

						}
					}

					result = expList.get(0);
					boolean checkResultBig = isBig(result);
					boolean checkResultSmall = isSmall(result);
					if (checkResultBig) {
						System.out.println("The calculation result is too big.");
					} else if (checkResultSmall) {
						System.out.println("The calculation result is too small.");
					} else {
						if (Double.parseDouble(result) % 1 == 0) {
							int res = (int) Double.parseDouble(result);
							System.out.println("Result: " + res);
						} else {
							System.out.println("Result:  " + result);
						}

					}
				}
			} catch (NullPointerException e) {
				System.out.println("This numerical expression is not calculatable");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid expression.");
			} catch (NumberFormatException e) {
				System.out.println("Invalid expression.");
			} catch (IllegalArgumentException nfe) {
				System.out.println("Invalid expression.");
			}

		} while (!exp.isEmpty());

	}

	public static List<String> getNumericalExpWithoutPar(String[] oper, List<String> expList) {

		for (int j = 0; j < oper.length; j++) {
			if (expList.size() != 1) {
				for (int i = 0; i < expList.size(); i++) {
					if (expList.get(i).equals(oper[j])) {
						int firstIndex = i - 1;
						int lastIndex = i + 1;
						double result;
						double num1 = Double.parseDouble(expList.get(i - 1));
						double num2 = Double.parseDouble(expList.get(i + 1));
						if (num2 == 0 && oper[j] == "/") {
							return null;
						}
						if (isOperator(expList.get(i + 1)) || oper[j].isEmpty()) {
							return null;
						}
						if (!isOperator(expList.get(i))) {
							return null;
						}
						result = calculate(num1, num2, oper[j]);
						List<String> subList1 = expList.subList(0, firstIndex);
						List<String> subList2 = expList.subList(lastIndex + 1, expList.size());
						List<String> all = new ArrayList<String>();
						all.addAll(subList1);
						all.add(result + "");
						all.addAll(subList2);
						expList = all;
						if (expList.size() == 2) {
							for (String st : expList) {
								List<String> operList = Arrays.asList(oper);
								if (!operList.contains(st)) {
									return null;
								}
							}
						}
					}
				}

			}

		}

		return expList;
	}

	public static boolean hasParentheses(List<String> arr) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).equals("(")) {
				return true;
			}
		}
		return false;
	}

	public static String[] getOperators() {
		String[] operators = { "^", "/", "*", "-", "+" };
		return operators;
	}

	public static double calculate(double num1, double num2, String operator) {
		double calVal = 0;
		switch (operator) {
		case "^":
			calVal = pow(num1, num2);
			break;
		case "/":
			calVal = num1 / num2;
			break;
		case "*":
			calVal = num1 * num2;
			break;
		case "+":
			calVal = num1 + num2;
			break;
		case "-":
			calVal = num1 - num2;
			break;
		default:
			calVal = 0;
			break;
		}
		return calVal;
	}

	public static boolean checkInputErrors(List<String> expList) {
		boolean hasError = false;
		for (String ch : expList) {
			boolean isError = isOperator(ch);

			if (!isError) {
				isError = isParenthesis(ch);

				if (!isError) {
					try {
						Double.parseDouble(ch);
						isError = true;
					} catch (Exception e) {
						isError = false;
					}
				}
			}
			if (!isError) {
				hasError = true;
				break;
			}
		}
		return hasError;
	}

	public static boolean isParenthesis(String st) {

		if (st.equals("(") || st.equals(")")) {
			return true;
		}
		return false;
	}

	public static boolean isOperator(String firstChar) {
		boolean isFirstCharOper = false;
		switch (firstChar) {
		case "^":
			isFirstCharOper = true;
			break;
		case "/":
			isFirstCharOper = true;
			break;
		case "*":
			isFirstCharOper = true;
			break;
		case "+":
			isFirstCharOper = true;
			break;
		case "-":
			isFirstCharOper = true;
			break;
		default:
			isFirstCharOper = false;
			break;
		}
		return isFirstCharOper;
	}

	public static boolean isBig(String result) {

		if (Double.parseDouble(result) > pow(10, 10)) {
			return true;
		}
		return false;
	}

	public static boolean isSmall(String result) {

		if (Double.parseDouble(result) < -2147483647) {

			return true;
		}
		return false;
	}

	@SuppressWarnings("null")
	public static double pow(double num1, double num2) {
		double result = 1;
		if (num2 < 0) {
			return (Double) null;
		}
		while (num2 != 0) {
			if (num1 < 0) {
				result *= num1;

			} else {
				result *= num1;
			}
			--num2;
		}
		return result;

	}

}
