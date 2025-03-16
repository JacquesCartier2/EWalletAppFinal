package service;

import database.Database;
import gui.EWalletApp;
import model.Expense;
import model.User;
import model.Wage;

public class ExpenseCalulator {
	public Database database = null;
	public String filePath; // the filepath where new files are created.

	public EWalletApp gui;

	public void addExpense(User user, String source, double amount, int yearlyfrequency) {
		Expense expense = new Expense(source, amount, yearlyfrequency);

		database.AddExpense(expense, user);
		int id = database.GetAvailableExpenseID(expense, user);
		expense.setID(id);
		user.addExpense(expense);
	}

	public void addMonthlyIncome(User user, String source, double amount, String month) {
		Wage income = new Wage(source, amount, month);

		database.AddIncome(income, user);
		int id = database.GetAvailableIncomeID(income, user);
		income.setID(id);
		user.addWage(income);
	}

	public int whenCanIBuy(String itemname, double price, User user) {

		if (user.monthlysavings < 0) {
			return Integer.MAX_VALUE; // Indicates that savings is not possible with current montly savings
		}

		return (int) Math.ceil(price / user.monthlysavings);
	}
}