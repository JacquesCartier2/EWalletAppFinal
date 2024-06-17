import java.util.ArrayList;

public class ExpenseCalulator implements Expenser {
	
	ArrayList<User> Users = new ArrayList<User>();
	

	@Override
	public void addExpense(Expense Ex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMonthlyIncome(Wage W) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PrintFullreport() {
		// TODO Auto-generated method stub
		double totalIncome = 0;
		double totalExpense = 0;
		ArrayList<Transaction> incomeTransactions = new ArrayList<>();
		ArrayList<Transaction> expenseTransactions = new ArrayList<>();
		
		// Process transactions for all users
		for(User User : Users){
			for(Transaction transaction : User.getTransactions()){
				if(transaction.getType().equalsIgnoreCase("income")){
					totalIncome += transaction.getAmount();
					incomeTransactions.add(transaction);
				} else if(transaction.getType().equalsIgnoreCase("expenses")){
					totalExpense += transaction.getAmount();
					expenseTransactions.add(transaction);
				}
			}
		}

		// Calculate summary info
		double netBalalnce = totalIncome - totalExpense;
		
		// Print detailed report
		System.out.println("DETAILED REPORT");

		System.out.println("\nIncome:");
		for(Transaction income : incomeTransactions){
			System.out.println("- " + income.getDescription() + ": $" + income.getAmount());
		}

		System.out.println("\nExpenses:");
		for(Transaction expense : expenseTransactions){
			System.out.println("- " + expense.getDescription() + ": $" + expense.getAmount());
		}

		// Print summary info
		System.out.println("\nSUMMARY");
		System.out.println("Total Income: $" + totalIncome);
		System.out.println("Total Expenses: $" + totalExpense);
		System.out.println("Net Balance: $" + netBalalnce);
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
