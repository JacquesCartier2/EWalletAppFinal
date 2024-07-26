import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.time.LocalDate;

public class ExpenseCalulator implements Expenser {
	public Database database = null;
	
	@Override
	public void addExpense(User user, String source, double amount, int yearlyfrequency) {
		Expense expense = new Expense(source, amount, yearlyfrequency);
		
		database.AddExpense(expense, user);
		int id = database.GetAvailableExpenseID(expense, user);
		expense.setID(id);
		user.addExpense(expense);
	}

	@Override
	public void addMonthlyIncome(User user, String source, double amount, String month) {
		Wage income = new Wage(source, amount, month);
		
		database.AddIncome(income, user);
		int id = database.GetAvailableIncomeID(income, user);
		income.setID(id);
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
			report.append("- " + income.getSource() + ": $" + income.getAmount() + " (" + income.getMonth()
					+ ")\n");
		}

		report.append("\nExpenses:\n");
		for (Expense expense : expenseTransactions) {
			report.append("- " + expense.getSource() + ": $" + expense.getAmount() + " (Frequency: "
					+ expense.getYearlyfrequency() + ")\n");
		}

		// Print summary info
		report.append("\nSUMMARY\n");
		report.append("Total Income: $" + totalIncome + "\n");
		report.append("Total Expenses: $" + totalExpense + "\n");
		report.append("Net Balance: $" + netBalance + "\n");

		JOptionPane.showMessageDialog(null, report.toString());
	}

	public void exportReportToCSV(User user) throws IOException {
		FileWriter csvWriter = new FileWriter(user.getUserName() + "_report.csv");
		csvWriter.append("Type, Source, Amount, Frequency/Month\n");

		for (Wage wage : user.getWages()) {
			csvWriter.append("Income," + wage.getSource() + "," + wage.getAmount() + "," + wage.getMonth() + "\n");
		}
		for (Expense expense : user.getExpenses()) {
			csvWriter.append("Expense," + expense.getSource() + "," + expense.getAmount() + "," + expense.getYearlyfrequency() + "\n");
		}
		csvWriter.flush();
		csvWriter.close();
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
	public Currency convertForeignCurrency(Currency C, String name) {

		// Convert to Canadian Dollar CAD
		final double CADRATE = 1.37;
		final double USDRATE = .73;

		if (name.equals("USD")) {
			C.setRate(CADRATE);
		}

		if (name.equals("CAD")) {
			C.setRate(USDRATE);
		}
		// need to use printf to limit decmail places

		return C;

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

	public double updateMonthlySavings(User user) {
		
		double monthlyExpenses = 0;
		double monthlyIncome = 0;
		
		ArrayList<Expense> listOfExpense = user.getExpenses();
        if (listOfExpense != null) {
            for (int expenseNum = 0; expenseNum < listOfExpense.size(); expenseNum++) {
                Expense currentExpense = listOfExpense.get(expenseNum);
                monthlyExpenses += (currentExpense.amount * currentExpense.yearlyfrequency) / 12;
            }
        }

        ArrayList<Wage> listOfIncome = user.getWages();
        if (listOfIncome != null) {
            for (int incomeNum = 0; incomeNum < listOfIncome.size(); incomeNum++) {
                Wage currentIncome = listOfIncome.get(incomeNum);
                
                LocalDate currentDate = LocalDate.now();
                String currentMonth = currentDate.getMonth().toString();
                
                System.out.println(currentMonth);
                
                if (currentMonth.equals(currentIncome.Month)) {
                	
                	monthlyIncome += currentIncome.getAmount();
                }  
            }
        }
		
		return monthlyIncome - monthlyExpenses;
	}

}
