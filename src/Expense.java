//adding a comment for testing - 
public class Expense {
	String source;
	double amount;
	int yearlyfrequency; //1 for 1 time or once a year, 12 for monthly or or 24 for biweekly
	private int ID;
	
	public Expense(String source, double amount, int yearlyfrequency) {
		super();
		this.source = source;
		this.amount = amount;
		this.yearlyfrequency = yearlyfrequency;
	}
	
	public Expense(String source, double amount, int yearlyfrequency, int id) {
		super();
		this.source = source;
		this.amount = amount;
		this.yearlyfrequency = yearlyfrequency;
		this.ID = id;
	}

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
	
	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		this.ID = id;
	}
	
	@Override
    public String toString() {
        return "Expense [source=" + source + ", amount=" + amount + ", yearlyfrequency=" + yearlyfrequency + ", id=" + ID + "]";
    }
}
