
public class Wage {
	String source;
	double amount;
	String Month;
	
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
}
