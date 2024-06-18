import java.util.ArrayList;

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
	    System.out.println("DETAILED REPORT");

	    System.out.println("\nIncome:");
	    for (Wage income : incomeTransactions) {
	        System.out.println("- " + income.getSource() + ": $" + income.getAmount() + " (" + income.getMonth() + ")");
	    }

	    System.out.println("\nExpenses:");
	    for (Expense expense : expenseTransactions) {
	        System.out.println("- " + expense.getSource() + ": $" + expense.getAmount() + " (Frequency: " + expense.getYearlyfrequency() + ")");
	    }

	    // Print summary info
	    System.out.println("\nSUMMARY");
	    System.out.println("Total Income: $" + totalIncome);
	    System.out.println("Total Expenses: $" + totalExpense);
	    System.out.println("Net Balance: $" + netBalance);
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
