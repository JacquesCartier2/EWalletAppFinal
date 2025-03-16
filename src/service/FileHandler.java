package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import database.Database;
import model.Expense;
import model.User;
import model.Wage;

public class FileHandler {
	public static String filePath;
	public static String kindOfReport;
	public static String reportType;
	public static String exportReport;;

	public static ArrayList<Expense> loadExpenses(String filePath) throws IOException {
		ArrayList<Expense> expenses = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		br.readLine(); // Skip header

		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			String source = data[0];
			double amount = Double.parseDouble(data[1]);
			int yearlyFrequency = Integer.parseInt(data[2]);
			expenses.add(new Expense(source, amount, yearlyFrequency));
		}
		br.close();
		return expenses;
	}

	public static boolean loadExpenseFile(String filePath, User userAtHand, Database database) {
		File loadedFile;

		// Validate the file path
		try {
			loadedFile = new File(filePath);
			if (!loadedFile.exists() || !loadedFile.isFile()) {
				System.err.println("Error: File not found or invalid file path.");
				return false;
			}
		} catch (Exception e) {
			System.err.println("Unexpected error while validating the file: " + e.getMessage());
			return false;
		}

		// Read and parse the file
		try (BufferedReader br = new BufferedReader(new FileReader(loadedFile))) {
			ArrayList<Expense> expenses = new ArrayList<>();
			String line;
			int lineNumber = 0;

			br.readLine(); // Skip header
			while ((line = br.readLine()) != null) {
				lineNumber++;

				// Skip empty lines
				if (line.trim().isEmpty()) {
					continue;
				}

				try {
					// Split line into parts
					String[] splitLine = line.split(",");
					if (splitLine.length < 3) {
						System.err.println("Error on line " + lineNumber + ": Insufficient data (3 fields required).");
						return false;
					}

					String source = splitLine[0];
					double amount = Double.parseDouble(splitLine[1]);
					int yearlyFrequency = Integer.parseInt(splitLine[2]);

					// Add new expense
					Expense expense = new Expense(source, amount, yearlyFrequency);
					expenses.add(expense);
					database.AddExpense(expense, userAtHand);

				} catch (NumberFormatException nfe) {
					System.err.println("Error on line " + lineNumber + ": Invalid number format.");
					return false;
				}
			}

			// Add expenses to user's list
			for (Expense expense : expenses) {
				userAtHand.addExpenseList(expense);
			}

			// Update monthly savings
			FinancialCalculator.updateMonthlySavings(userAtHand);

			return true;

		} catch (IOException ioe) {
			System.err.println("Error while reading the file: " + ioe.getMessage());
			return false;
		} catch (Exception e) {
			System.err.println("Unexpected error occurred: " + e.getMessage());
			return false;
		}

	}

	public static boolean loadIncomeFile(String filePath, User userAtHand, Database database) {
		File loadedFile;

		// Validate the file path
		try {
			loadedFile = new File(filePath);
			if (!loadedFile.exists() || !loadedFile.isFile()) {
				System.err.println("Error: File not found or invalid file path.");
				return false;
			}
		} catch (Exception e) {
			System.err.println("Unexpected error while validating the file: " + e.getMessage());
			return false;
		}

		try {
			ArrayList<Wage> incomes = new ArrayList<>();

			BufferedReader br = new BufferedReader(new FileReader(loadedFile));
			String line;
			int lineNumber = 1;

			br.readLine(); // Skip header

			while ((line = br.readLine()) != null) {
				lineNumber++;

				if (line.trim().isEmpty()) {
					continue;
				}

				String[] splitLine = line.split(",");
				if (splitLine.length < 3) {
					System.err.println("Error on line " + lineNumber + ": Insufficient data (3 fields required).");
					br.close();
					return false;
				}

				String source = splitLine[0];
				double amount;
				try {
					amount = Double.parseDouble(splitLine[1]);
				} catch (NumberFormatException e) {
					System.err.println("Error on line " + lineNumber + ": Invalid number format.");
					br.close();
					return false;
				}

				String month = splitLine[2];

				Wage inc = new Wage(source, amount, month);
				incomes.add(inc);
				database.AddIncome(inc, userAtHand);
			}

			br.close();

			for (Wage inc : incomes) {
				userAtHand.addIncomeList(inc);
			}

			FinancialCalculator.updateMonthlySavings(userAtHand);

			return true;
		} catch (IOException ioe) {
			System.err.println("Error while reading the file: " + ioe.getMessage());
			return false;
		} catch (Exception e) {
			System.err.println("Unexpected error occurred: " + e.getMessage());
			return false;
		}
	}

	public void exportReportToCSV(User user) throws IOException {
		FileWriter csvWriter = new FileWriter(user.getUserName() + "_report.csv");
		csvWriter.append("Type, Source, Amount, Frequency/Month\n");

		for (Wage wage : user.getWages()) {
			csvWriter.append("Income," + wage.getSource() + "," + wage.getAmount() + "," + wage.getMonth() + "\n");
		}
		for (Expense expense : user.getExpenses()) {
			csvWriter.append("Expense," + expense.getSource() + "," + expense.getAmount() + ","
					+ expense.getYearlyfrequency() + "\n");
		}
		csvWriter.flush();
		csvWriter.close();
	}

	public static void exportReport(String reportTitle, User userAtHand) {
		// stores the path and name of the file to be created.
		String fullFilepath = filePath + reportTitle + ".csv";

		// create the file in the destination.
		File file = new File(fullFilepath);
		try {
			if (file.createNewFile() == false) {
				System.out.println("File " + reportTitle + ".csv has been overwritten. ");
			}
		} catch (IOException e) {
			throw new Error("Error creating file: " + e.getMessage());
		}

		// write the data to file.
		try {
			FileWriter writer = new FileWriter(file);

			if (kindOfReport.equals("Income")) {
				writer.write("source,amount,month\n");
				for (Wage income : userAtHand.getWages()) {
					writer.write(income.source + "," + income.amount + "," + income.Month + "\n");
				}
			} else if (kindOfReport.equals("IncomeByType")) {
				writer.write("source,amount,month\n");
				for (Wage income : userAtHand.getWages()) {
					if (income.source.equals(reportType)) {
						writer.write(income.source + "," + income.amount + "," + income.Month + "\n");
					}
				}
			} else if (kindOfReport.equals("Expense")) {
				writer.write("source,amount,yearly_frequency\n");
				for (Expense spending : userAtHand.getExpenses()) {
					writer.write(spending.source + "," + spending.amount + "," + spending.yearlyfrequency + "\n");
				}
			} else if (kindOfReport.equals("ExpenseByType")) {
				writer.write("source,amount,yearly_frequency\n");
				for (Expense spending : userAtHand.getExpenses()) {
					if (spending.source.equals(reportType)) {
						writer.write(spending.source + "," + spending.amount + "," + spending.yearlyfrequency + "\n");
					}
				}
			}

			writer.close();
		} catch (IOException e) {
			throw new Error("Error writing to file: " + e.getMessage());
		}
	}
}
