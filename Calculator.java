
import java.util.Scanner;

public class Calculator {

	public static void main(String[] args) {
		int num1, num2;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Number 1");
		num1 = s.nextInt();
		System.out.println("Enter Number 2");
		num2 = s.nextInt();
		add(num1, num2);
		sub(num1, num2);
		mul(num1, num2);
		div(num1, num2);
	}

	public static void add(int num1, int num2) {
		System.out.println(num1 + num2);
	}

	public static void sub(int a, int b) {
		System.out.println(a - b);
	}

	public static void mul(int a, int b) {
		System.out.println(a * b);
	}

	public static void div(int a, int b) {
		System.out.println(a / b);
	}
}
