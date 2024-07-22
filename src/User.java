import java.util.ArrayList;

public class User {
	private ArrayList <Wage>Income;  // user income sources that user can record or view or search by type or month 
	private ArrayList <Expense>Spending; //user's expenses 
	private int ID;

	String username;
	String pwd;
	//current total income - total 
	double balance;
	// possible monthly savings, calculated using monthly income (most recent) assuming the data we have is for one year, and monthly and biweekly expenses, here you can assume yearly expenses that are recorded have already been paid. 
	double monthlysavings;	

	public User(String username,String password){
		this.username = username;
		this.pwd = password;
		this.Income = new ArrayList<>();
		this.Spending = new ArrayList<>();
	}

	public String getUserName(){
		return username;
	}

	public ArrayList<Wage> getWages(){
		return Income;
	}

	public ArrayList<Expense> getExpenses(){
		return Spending;
	}
	

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void addWage(Wage wage){
		Income.add(wage);
	}

	public void addExpense(Expense expense){
		Spending.add(expense);
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User Details:\n");
        sb.append("Username: ").append(username).append("\n");
        sb.append("Balance: ").append(balance).append("\n");
        sb.append("Monthly Savings: ").append(monthlysavings).append("\n");
        sb.append("Income Sources:\n");
        for (Wage wage : Income) {
            sb.append("\t").append(wage.toString()).append("\n");
        }
        sb.append("Expenses:\n");
        for (Expense expense : Spending) {
            sb.append("\t").append(expense.toString()).append("\n");
        }
        return sb.toString();
    }
	
}
