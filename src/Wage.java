
public class Wage {
	String source;
	double amount;
	String Month;
	
	public Wage(String source, double amount, String month) {
		super();
		this.source = source;
		this.amount = amount;
		Month = month;
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

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}
}
