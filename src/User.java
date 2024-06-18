import java.util.ArrayList;

public class User {
	private ArrayList <Currency>currencyRates;
	private ArrayList <Wage>Income;  // user income sources that user can record or view or search by type or month 
	private ArrayList <Expense>Spending; //user's expenses 

	String username;
	String pwd;
	//current total income - total 
	double balance;
	// possible monthly savings, calculated using monthly income (most recent) assuming the data we have is for one year, and monthly and biweekly expenses, here you can assume yearly expenses that are recorded have already been paid. 
	double monthlysavings;	
	//should add constructor(s)
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

	public void addWage(Wage wage){
		Income.add(wage);
	}

	public void addExpense(Expense expense){
		Spending.add(expense);
	}
}
