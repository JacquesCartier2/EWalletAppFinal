
public class Wage {
	String source;
	double amount;
	String Month;
	
<<<<<<< HEAD
	//should add contructor(s)
	public Wage(String source, double amount, String Month){
        this.source = source;
        this.amount = amount;
        this.Month = Month;
    }

    public String getSource(){
        return source;
    }

    public double getAmount(){
        return amount;
    }

    public String getMonth(){
        return Month;
    }
=======
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

>>>>>>> 9ce906e195c8446cb0cf76266775f11eb1d6d836
}
