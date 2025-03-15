package service;
import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import database.Database;
import gui.EWalletApp;
import model.Expense;
import model.User;
import model.Wage;

import java.time.LocalDate;

public class ExpenseCalulator {
	public Database database = null;
	public String filePath; //the filepath where new files are created. 
	public String reportType;
	public String kindOfReport;
	public EWalletApp gui;

	public void addExpense(User user, String source, double amount, int yearlyfrequency) {
		Expense expense = new Expense(source, amount, yearlyfrequency);
		
		database.AddExpense(expense, user);
		int id = database.GetAvailableExpenseID(expense, user);
		expense.setID(id);
		user.addExpense(expense);
	}

	public void addMonthlyIncome(User user, String source, double amount, String month) {
		Wage income = new Wage(source, amount, month);
		
		database.AddIncome(income, user);
		int id = database.GetAvailableIncomeID(income, user);
		income.setID(id);
		user.addWage(income);
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

	public void PrintExpensebyType() {
		
		User userAtHand = EWalletApp.getUserObject();
		
		EWalletApp.reportListModel.clear();
		ArrayList <String>expenseTypes = new ArrayList<String>();
		ArrayList <Double>expenseTypeTotals = new ArrayList<Double>();
		
		String expenseInfo;
		String type;
		type = EWalletApp.filterInput.getText();
		
		for (Expense expense: userAtHand.getExpenses()) {
			if (expense.source.equals(type)) {
				expenseInfo = "Source: " + expense.source + "    Amount: " + expense.amount + "    Yearly Frequency: " + expense.yearlyfrequency;
				EWalletApp.reportListModel.addElement(expenseInfo);
			}
		}
	}

	public void IOError(String error) {
		gui.PopupMessage("IO Error: " + error);
	}
	
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


//	public boolean loadExpenseFile(String filePath) {
//		File loadedFile = null;
//		User userAtHand = null;
//		for(User user : gui.AllUsers) {
//			if(user.username.equals(gui.CurrentUser)){
//				userAtHand = user;
//				break;
//			}
//		}
//		
//		try {
//			loadedFile = new File(filePath);
//		}
//		catch(Exception E) {
//			IOError("file not found. ");
//		}
//		
//		try {
//			ArrayList<Expense> expenses = new ArrayList<Expense>(); //data read from the file will be stored here and added to userAtHand if no problems occur. 
//			
//			BufferedReader br = new BufferedReader(new FileReader(loadedFile));
//			String line = "";
//			String source;
//			double amount;
//			int yearlyFrequency;
//			int lineNumber = 1; //used to keep track of which line is being read. 
//			
//			br.readLine(); //first line contains data field names and should be skipped. 
//			
//			while ((line = br.readLine()) != null) {   //go through each line in the file
//				lineNumber++;
//				
//				//if a line is completely empty, ignore it.
//				if(line.equals("")) {
//					continue;
//				}
//				
//				String[] splitLine = line.split(",");   // use comma as separator to split the line into parts.
//				
//				if(splitLine.length < 3) {
//					IOError("invalid data on line " + lineNumber + ", 3 data points are required for each line. ");
//					br.close();
//					return false;
//				}
//				
//				//first data point is source. 
//				source = splitLine[0];
//				
//				//second data point is amount, must be parse-able as double. 
//				try {
//					amount = Double.parseDouble(splitLine[1]);
//				}
//				catch(Exception E) {
//					IOError("invalid data on line " + lineNumber + ", second data point must be a number. ");
//					br.close();
//					return false;
//				}
//				
//				//third data point is yearly frequency, must be parse-able as int. 
//				try {
//					yearlyFrequency = Integer.parseInt(splitLine[2]);
//				}
//				catch(Exception E) {
//					IOError("invalid data on line " + lineNumber + ", third data point must be an integer. ");
//					br.close();
//					return false;
//				}
//				
//				//if all three data points work, add a new expense.
//				Expense exp = new Expense(source, amount, yearlyFrequency);
//				expenses.add(exp);
//				database.AddExpense(exp, userAtHand);
//			}
//			
//			br.close();
//			
//			//if no problems occurred while reading the data, add everything from expenses to the userAtHand's Spending list. 
//			for(Expense exp : expenses) {
//				userAtHand.addExpenseList(exp);
//			}
//			
//			// Update Monthly Savings 
//			FinancialCalculator.updateMonthlySavings(userAtHand);
//			
//			return true;
//		}
//		catch(Exception E){
//			IOError(E.toString());
//			return false;
//		}
//	}

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
				Wage inc = new Wage(source, amount, month);
				incomes.add(inc);
				database.AddIncome(inc, userAtHand);
			}
			
			br.close();
			
			//if no problems occurred while reading the data, add everything from expenses to the userAtHand's Spending list. 
			for(Wage inc : incomes) {
				userAtHand.addIncomeList(inc);
			}
			// Updates Monthly savings
			FinancialCalculator.updateMonthlySavings(userAtHand);
			
			return true;
		}
		catch(Exception E){
			IOError(E.toString());
			return false;
		}
	}

	public int whenCanIBuy(String itemname, double price, User user) {
				
		if (user.monthlysavings < 0) {
			return Integer.MAX_VALUE; 	// Indicates that savings is not possible with current montly savings
		}
		
		return (int) Math.ceil(price / user.monthlysavings);
	}
}
// 520 Lines