import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import java.time.LocalDate;

public class ExpenseCalulator implements Expenser {
	public Database database = null;
	public String filePath; //the filepath where new files are created. 
	public String reportType;
	public String kindOfReport;
	public EWalletApp gui;

	@Override
	public void addExpense(User user, String source, double amount, int yearlyfrequency) {
		Expense expense = new Expense(source, amount, yearlyfrequency);
		
		database.AddExpense(expense, user);
		int id = database.GetAvailableExpenseID(expense, user);
		expense.setID(id);
		user.addExpense(expense);
	}

	@Override
	public void addMonthlyIncome(User user, String source, double amount, String month) {
		Wage income = new Wage(source, amount, month);
		
		database.AddIncome(income, user);
		int id = database.GetAvailableIncomeID(income, user);
		income.setID(id);
		user.addWage(income);
	}

	@Override
	public void PrintFullreport(User user) {
		double totalIncome = 0;
		double totalExpense = 0;
		ArrayList<Wage> incomeTransactions = new ArrayList<>();
		ArrayList<Expense> expenseTransactions = new ArrayList<>();

		// Process transactions for the given user
		for (Wage wage : user.getWages()) {
			totalIncome += wage.getAmount();
			incomeTransactions.add(wage);
		}
		for (Expense expense : user.getExpenses()) {
			double annualExpense = expense.getAmount() * expense.getYearlyfrequency();
			totalExpense += annualExpense;
			expenseTransactions.add(expense);
		}

		// Calculate summary info
		double netBalance = totalIncome - totalExpense;

		// Print detailed report
		StringBuilder report = new StringBuilder();
		report.append("DETAILED REPORT\n");

		report.append("\nIncome:\n");
		for (Wage income : incomeTransactions) {
			report.append("- " + income.getSource() + ": $" + income.getAmount() + " (" + income.getMonth()
					+ ")\n");
		}

		report.append("\nExpenses:\n");
		for (Expense expense : expenseTransactions) {
			report.append("- " + expense.getSource() + ": $" + expense.getAmount() + " (Frequency: "
					+ expense.getYearlyfrequency() + ")\n");
		}

		// Print summary info
		report.append("\nSUMMARY\n");
		report.append("Total Income: $" + totalIncome + "\n");
		report.append("Total Expenses: $" + totalExpense + "\n");
		report.append("Net Balance: $" + netBalance + "\n");

		JOptionPane.showMessageDialog(null, report.toString());
	}

	public void exportReportToCSV(User user) throws IOException {
		FileWriter csvWriter = new FileWriter(user.getUserName() + "_report.csv");
		csvWriter.append("Type, Source, Amount, Frequency/Month\n");

		for (Wage wage : user.getWages()) {
			csvWriter.append("Income," + wage.getSource() + "," + wage.getAmount() + "," + wage.getMonth() + "\n");
		}
		for (Expense expense : user.getExpenses()) {
			csvWriter.append("Expense," + expense.getSource() + "," + expense.getAmount() + "," + expense.getYearlyfrequency() + "\n");
		}
		csvWriter.flush();
		csvWriter.close();
	}

	@Override
	// As a user I would like to view a detailed report of all expenses, and summary
	// information for expenses
	public void PrintExpensereport() {
		
		User userAtHand = EWalletApp.getUserObject();
		EWalletApp.reportListModel.clear();
		double totalExpenses = 0;

		String rep1 = ("Creating Expense report...");
		EWalletApp.reportListModel.addElement(rep1);
		String lineBreak = "";
		EWalletApp.reportListModel.addElement(lineBreak);

		int i;
		// print expenses
		String rep2 = ("Expenses:");
		EWalletApp.reportListModel.addElement(rep2);
		for (i = 0; i < userAtHand.getExpenses().size(); i++) {
			String rep3 = ("Source: " + userAtHand.getExpenses().get(i).source + " Amount: "
					+ userAtHand.getExpenses().get(i).amount + " Frequency (per year): "
					+ userAtHand.getExpenses().get(i).yearlyfrequency);
			EWalletApp.reportListModel.addElement(rep3);
			// collect total expenses
			totalExpenses = totalExpenses
					+ ((userAtHand.getExpenses().get(i).amount) * (userAtHand.getExpenses().get(i).yearlyfrequency));
		}
		System.out.println();

		String rep20 = ("Total Expenses: " + totalExpenses);
		EWalletApp.reportListModel.addElement(rep20);
	}

	@Override
	//As  a user I would like to view a detailed report of all income, and summary information for income
	public void PrintIncomereport() {
		
		User userAtHand = EWalletApp.getUserObject();
		
		String incomeInfo; 		// Used to store Source, amount, Month
	
		// Clears Current List information and prints updates
		EWalletApp.reportListModel.clear();
		
		// Gets information for Report Income
		for (Wage wage: userAtHand.getWages()) {
			incomeInfo = "Source: " + wage.source + "    Amount: " + wage.amount + "    Month: " + wage.Month;
			EWalletApp.reportListModel.addElement(incomeInfo);
		}
	}

	@Override
	//As  a user I would like to view a detailed report of income of a certain type, and summary information for income
	public void PrintIncomereportbyTpe(){
		
		User userAtHand = EWalletApp.getUserObject();
		
		String incomeInfo; 		// Used to store Source, amount, Month
		String type; 			// User input from filter text field

		EWalletApp.reportListModel.clear();
		
		type = EWalletApp.filterInput.getText();
		
		// Gets filtered information for Report Income
		for (Wage wage: userAtHand.getWages()) {
			if (wage.source.equals(type)) {
				incomeInfo = "Source: " + wage.source + "    Amount: " + wage.amount + "    Month: " + wage.Month;
				EWalletApp.reportListModel.addElement(incomeInfo);
			}
		}		
	}

	@Override
	public void PrintExpensebyType() {
		
		User userAtHand = EWalletApp.getUserObject();
		
		EWalletApp.reportListModel.clear();
		ArrayList <String>expenseTypes = new ArrayList<String>();
		ArrayList <Double>expenseTypeTotals = new ArrayList<Double>();
		
		String rep1 = ("Creating Expense By Type report...");
		EWalletApp.reportListModel.addElement(rep1);
		
		int i;
		//print expenses
		String rep2 = ("Expenses By Type:");
		EWalletApp.reportListModel.addElement(rep2);
		for (i=0; i<userAtHand.getExpenses().size(); i++) {		
			//check if the source is already a recorded type
			if (expenseTypes.contains(userAtHand.getExpenses().get(i).source.toUpperCase())) {
				//get the index of the type in the type list
				int index = expenseTypes.indexOf(userAtHand.getExpenses().get(i).source.toUpperCase());
				
				//add the amount to the totals by types list in the same index spot
				double newExpenseTotal = expenseTypeTotals.get(index) + ((userAtHand.getExpenses().get(i).amount) * (userAtHand.getExpenses().get(i).yearlyfrequency));
				expenseTypeTotals.set(index, newExpenseTotal);
				
			}
			//if the type isn't in the list add the source to the type list and the amount to the total by type list
			else {
				expenseTypes.add(userAtHand.getExpenses().get(i).source.toUpperCase());
				expenseTypeTotals.add((userAtHand.getExpenses().get(i).amount) * (userAtHand.getExpenses().get(i).yearlyfrequency));
			}
		}
		System.out.println();
		
		System.out.println("Summary: ");
		for (i=0; i<expenseTypes.size(); i++) {
			String rep4 = ("Expense Type: " + expenseTypes.get(i) + " Total Expenses: " + expenseTypeTotals.get(i));
			EWalletApp.reportListModel.addElement(rep4);
		}
		System.out.println();
	}

	public void IOError(String error) {
		gui.PopupMessage("IO Error: " + error);
	}
	
	@Override
	// As a user I would like to choose a report and export it as an external file (any type is fine preferences are csv or JSON)
	public void exportReport(String reportTitle) {
		//stores the path and name of the file to be created.
		String fullFilepath = filePath + reportTitle + ".csv";
		User userAtHand = null;
		for(User user : gui.AllUsers) {
			if(user.username.equals(gui.CurrentUser)){
				userAtHand = user;
				break;
			}
		}
		
		//create the file in the destination. 
		File file = new File(fullFilepath);
		try {
			if(file.createNewFile() == false) {
				System.out.println("File " + reportTitle + ".csv has been overwritten. "); 
			}
		}
		catch(IOException e) {
			IOError(e.toString());
			return;
		}
		
		//write the data to file.
		try {
			FileWriter writer = new FileWriter(file);
			
			if(kindOfReport.equals("Income")) {
				writer.write("source,amount,month\n");
				for(Wage income : userAtHand.getWages()) {
					writer.write(income.source + "," + income.amount + "," + income.Month + "\n");
				}
			}
			else if(kindOfReport.equals("IncomeByType")) {
				writer.write("source,amount,month\n");
				for(Wage income : userAtHand.getWages()) {
					if(income.source.equals(reportType)) {
						writer.write(income.source + "," + income.amount + "," + income.Month + "\n");
					}
				}
			}
			else if(kindOfReport.equals("Expense")) {
				writer.write("source,amount,yearly_frequency\n");
				for(Expense spending : userAtHand.getExpenses()) {
					writer.write(spending.source + "," + spending.amount + "," + spending.yearlyfrequency + "\n");
				}
			}
			else if(kindOfReport.equals("ExpenseByType")) {
				writer.write("source,amount,yearly_frequency\n");
				for(Expense spending : userAtHand.getExpenses()) {
					if(spending.source.equals(reportType)) {
						writer.write(spending.source + "," + spending.amount + "," + spending.yearlyfrequency + "\n");
					}
				}
			}
			
			writer.close();
		}
		catch(IOException e) {
			IOError(e.toString());
			return;
		}
	}

	@Override
	public Currency convertForeignCurrency(Currency C, String name) {

		// Convert to Canadian Dollar CAD
		final double CADRATE = 1.37;
		final double USDRATE = .73;

		if (name.equals("USD")) {
			C.setRate(CADRATE);
		}

		if (name.equals("CAD")) {
			C.setRate(USDRATE);
		}
		// need to use printf to limit decmail places

		return C;

	}

	@Override
	public boolean loadExpenseFile(String filePath) {
		File loadedFile = null;
		User userAtHand = null;
		for(User user : gui.AllUsers) {
			if(user.username.equals(gui.CurrentUser)){
				userAtHand = user;
				break;
			}
		}
		
		try {
			loadedFile = new File(filePath);
		}
		catch(Exception E) {
			IOError("file not found. ");
		}
		
		try {
			ArrayList<Expense> expenses = new ArrayList<Expense>(); //data read from the file will be stored here and added to userAtHand if no problems occur. 
			
			BufferedReader br = new BufferedReader(new FileReader(loadedFile));
			String line = "";
			String source;
			double amount;
			int yearlyFrequency;
			int lineNumber = 1; //used to keep track of which line is being read. 
			
			br.readLine(); //first line contains data field names and should be skipped. 
			
			while ((line = br.readLine()) != null) {   //go through each line in the file
				lineNumber++;
				
				//if a line is completely empty, ignore it.
				if(line.equals("")) {
					continue;
				}
				
				String[] splitLine = line.split(",");   // use comma as separator to split the line into parts.
				
				if(splitLine.length < 3) {
					IOError("invalid data on line " + lineNumber + ", 3 data points are required for each line. ");
					br.close();
					return false;
				}
				
				//first data point is source. 
				source = splitLine[0];
				
				//second data point is amount, must be parse-able as double. 
				try {
					amount = Double.parseDouble(splitLine[1]);
				}
				catch(Exception E) {
					IOError("invalid data on line " + lineNumber + ", second data point must be a number. ");
					br.close();
					return false;
				}
				
				//third data point is yearly frequency, must be parse-able as int. 
				try {
					yearlyFrequency = Integer.parseInt(splitLine[2]);
				}
				catch(Exception E) {
					IOError("invalid data on line " + lineNumber + ", third data point must be an integer. ");
					br.close();
					return false;
				}
				
				//if all three data points work, add a new expense.
				expenses.add(new Expense(source, amount, yearlyFrequency));
			}
			
			br.close();
			
			//if no problems occurred while reading the data, add everything from expenses to the userAtHand's Spending list. 
			for(Expense exp : expenses) {
				userAtHand.addExpenseList(exp);
			}
			
			// Update Monthly Savings 
			updateMonthlySavings(userAtHand);
			
			return true;
		}
		catch(Exception E){
			IOError(E.toString());
			return false;
		}
	}

	@Override
	public boolean loadIncomeFile(String filePath) {
		File loadedFile = null;
		User userAtHand = null;
		for(User user : gui.AllUsers) {
			if(user.username.equals(gui.CurrentUser)){
				userAtHand = user;
				break;
			}
		}
		
		try {
			loadedFile = new File(filePath);
		}
		catch(Exception E) {
			IOError("file not found. ");
		}
		
		try {
			ArrayList<Wage> incomes = new ArrayList<Wage>(); //data read from the file will be stored here and added to userAtHand if no problems occur. 
			
			BufferedReader br = new BufferedReader(new FileReader(loadedFile));
			String line = "";
			String source;
			double amount;
			String month;
			int lineNumber = 1; //used to keep track of which line is being read. 
			
			br.readLine(); //first line contains data field names and should be skipped. 
			
			while ((line = br.readLine()) != null) {   //go through each line in the file
				lineNumber++;
				
				//if a line is completely empty, ignore it.
				if(line.equals("")) {
					continue;
				}
				
				String[] splitLine = line.split(",");   // use comma as separator to split the line into parts.
				
				if(splitLine.length < 3) {
					IOError("invalid data on line " + lineNumber + ", 3 data points are required for each line. ");
					br.close();
					return false;
				}
				
				//first data point is source. 
				source = splitLine[0];
				
				//second data point is amount, must be parse-able as double. 
				try {
					amount = Double.parseDouble(splitLine[1]);
				}
				catch(Exception E) {
					IOError("invalid data on line " + lineNumber + ", second data point must be a number. ");
					br.close();
					return false;
				}
				
				//third data point is month. 
				month = splitLine[2];
				
				//if all three data points work, add a new expense.
				incomes.add(new Wage(source, amount, month));
			}
			
			br.close();
			
			//if no problems occurred while reading the data, add everything from expenses to the userAtHand's Spending list. 
			for(Wage inc : incomes) {
				userAtHand.addIncomeList(inc);
			}
			// Updates Monthly savings
			updateMonthlySavings(userAtHand);
			
			return true;
		}
		catch(Exception E){
			IOError(E.toString());
			return false;
		}
	}

	@Override
	public int whenCanIBuy(String itemname, double price) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double updateMonthlySavings(User user) {
		
		double monthlyExpenses = 0;
		double monthlyIncome = 0;
		
		ArrayList<Expense> listOfExpense = user.getExpenses();
        if (listOfExpense != null) {
            for (int expenseNum = 0; expenseNum < listOfExpense.size(); expenseNum++) {
                Expense currentExpense = listOfExpense.get(expenseNum);
                monthlyExpenses += (currentExpense.amount * currentExpense.yearlyfrequency) / 12;
            }
        }

        ArrayList<Wage> listOfIncome = user.getWages();
        if (listOfIncome != null) {
            for (int incomeNum = 0; incomeNum < listOfIncome.size(); incomeNum++) {
                Wage currentIncome = listOfIncome.get(incomeNum);
                
                LocalDate currentDate = LocalDate.now();
                String currentMonth = currentDate.getMonth().toString();
                
                System.out.println(currentMonth);
                
                if (currentMonth.equals(currentIncome.Month)) {
                	
                	monthlyIncome += currentIncome.getAmount();
                }  
            }
        }
		
		return monthlyIncome - monthlyExpenses;
	}

}
