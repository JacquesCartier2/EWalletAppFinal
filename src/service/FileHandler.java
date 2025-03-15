package service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Expense;

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
}
