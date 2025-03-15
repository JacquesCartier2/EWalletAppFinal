package service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import database.Database;
import model.Expense;
import model.User;

public class FileHandler {
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
}
