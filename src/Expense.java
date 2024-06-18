//adding a comment for testing - 
public class Expense {
	String source;
	double amount;
	int yearlyfrequency; //1 for 1 time or once a year, 12 for monthly or or 24 for biweekly
<<<<<<< HEAD
	//should add contructor(s)

	public Expense(String source, double amount, int yearlyfrequency){
=======
	
	public Expense(String source, double amount, int yearlyfrequency) {
		super();
>>>>>>> 9ce906e195c8446cb0cf76266775f11eb1d6d836
		this.source = source;
		this.amount = amount;
		this.yearlyfrequency = yearlyfrequency;
	}

<<<<<<< HEAD
	public String getSource(){
		return source;
	}

	public double getAmount(){
		return amount;
	}

	public int getYearlyFrequency(){
		return yearlyfrequency;
	}
=======
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getYearlyfrequency() {
		return yearlyfrequency;
	}

	public void setYearlyfrequency(int yearlyfrequency) {
		this.yearlyfrequency = yearlyfrequency;
	}
	
>>>>>>> 9ce906e195c8446cb0cf76266775f11eb1d6d836
}
