package currency;
public class CurrencyConverter {
	public static void convert(Currency currency, String targetCurrency) {
		final double CADRATE = 1.37;
		final double USDRATE = .73;
		
		 if (targetCurrency.equals("USD")) {
	            currency.setRate(CADRATE);
	        } else if (targetCurrency.equals("CAD")) {
	            currency.setRate(USDRATE);
	        }
	    }
	}
