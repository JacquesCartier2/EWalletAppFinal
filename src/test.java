import java.util.Scanner;

/**
 * test
 */
public class test {

	public static void main(String[] args) {
		double amount;

		Scanner scnr = new Scanner(System.in);
		Currency test = new Currency(1.00, "USD");
		ExpenseCalulator convert = new ExpenseCalulator();

		System.out.println("How much money do you have? ");
		amount = scnr.nextDouble();

		System.out.println("You have " + amount + "USD");

		amount = convert.convertForeignCurrency(test, amount);
		System.out.println("To CAD " + amount);

		amount = convert.convertForeignCurrency(test, amount);
		System.out.printf("Back to USD " + "%.2f", amount);
	}
}
