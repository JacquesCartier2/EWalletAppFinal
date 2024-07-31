
public class Wage {
	String source;
	double amount;
	String Month;
	private int ID;
	
	public Wage(String source, double amount, String month) {
		super();
		this.source = source;
		this.amount = amount;
		Month = month;
	}
	public Wage(String source, double amount, String month, int id) {
		super();
		this.source = source;
		this.amount = amount;
		Month = month;
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

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		this.ID = id;
	}
	
	 @Override
	    public String toString() {
	        return "Wage [source=" + source + ", amount=" + amount + ", Month=" + Month + ", id=" + ID + "]";
	    }

}
