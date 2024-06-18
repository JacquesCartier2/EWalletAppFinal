import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ExpenseCalulator implements Expenser {
	
	@Override
	public void addExpense(User user, String source, double amount, int yearlyfrequency) {
		Expense expense = new Expense(source, amount, yearlyfrequency);
		
		user.addExpense(expense);
	}

	@Override
	public void addMonthlyIncome(User user, String source, double amount, String month) {
		Wage income = new Wage(source, amount, month);
		
		user.addWage(income);
	}

	@Override
	public void PrintFullreport(User user) {
		double totalIncome = 0;
		double totalExpense = 0;
		ArrayList<Wage> incomeTransactions = new ArrayList<>();
		ArrayList<Expense> expenseTransactions = new ArrayList<>();

		// Process transactions for the given user
		for (Wage wage : user.getWages()) {
			totalIncome += wage.getAmount();
			incomeTransactions.add(wage);
		}
		for (Expense expense : user.getExpenses()) {
			double annualExpense = expense.getAmount() * expense.getYearlyfrequency();
			totalExpense += annualExpense;
			expenseTransactions.add(expense);
		}

		// Calculate summary info
		double netBalance = totalIncome - totalExpense;

	    // Print detailed report
		StringBuilder report = new StringBuilder();
	    report.append("DETAILED REPORT\n");

	    report.append("\nIncome:\n");
	    for (Wage income : incomeTransactions) {
	        report.append("- " + income.getSource() + ": $" + income.getAmount() + " (" + income.getMonth() + ")\n");
	    }

	    report.append("\nExpenses:\n");
	    for (Expense expense : expenseTransactions) {
	        report.append("- " + expense.getSource() + ": $" + expense.getAmount() + " (Frequency: " + expense.getYearlyfrequency() + ")\n");
	    }

	    // Print summary info
	    report.append("\nSUMMARY\n");
	    report.append("Total Income: $" + totalIncome + "\n");
	    report.append("Total Expenses: $" + totalExpense + "\n");
	    report.append("Net Balance: $" + netBalance + "\n");

		JOptionPane.showMessageDialog(null, report.toString());
	}

	@Override
	public void PrintExpensereport() {
		// TODO Auto-generated method stub

	}

	@Override
	public void PrintIncomereport() {
		// TODO Auto-generated method stu

	}

	@Override
	public void PrintIncomereportbyTpe() {
		// TODO Auto-generated method stub

	}

	@Override
	public void PrintExpensebyType() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exportReport(String reportTitle) {
		// TODO Auto-generated method stub

	}

	@Override
	public Currency convertForeignCurrency(Currency C, double amount) {

		// Convert to Canadian Dollar CAD
		final double CADRATE = 1.37;
		final double USDRATE = 0.73;

		// to Canadian Dollar
		if (C.getName().equals("USD")) {
			C.setRate(USDRATE);
			C.setName("CAD");

		}
		// to US Dollar
		if (C.getName().equals("CAD")) {
			C.setRate(USDRATE);
			C.setName("USD");
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loadExpenseFile(String filePath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadIncomeFile(String filePath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int whenCanIBuy(String itemname, double price) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateMonthlySavings() {
		// TODO Auto-generated method stub

	}

}
