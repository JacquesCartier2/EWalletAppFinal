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
>>>>>>> f631f9d2614bb009343dcbb4bf077e6351f16b08
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
	
	
>>>>>>> f631f9d2614bb009343dcbb4bf077e6351f16b08
}
