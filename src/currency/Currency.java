package currency;

public class Currency {
	public double rate;
	public String name;

	public Currency(double rate, String name) {
		super();
		this.rate = rate;
		this.name = name;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
