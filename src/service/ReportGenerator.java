package service;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.EWalletApp;
import model.Expense;
import model.User;
import model.Wage;

public class ReportGenerator {
    public static void generateExpenseReport(User user) {
        EWalletApp.reportListModel.clear();
        double totalExpenses = 0;

        // Add introductory messages
        EWalletApp.reportListModel.addElement("Creating Expense report...");
        EWalletApp.reportListModel.addElement("");

        // Add expense details
        EWalletApp.reportListModel.addElement("Expenses:");
        for (Expense expense : user.getExpenses()) {
            String expenseInfo = "Source: " + expense.getSource() +
                                 " Amount: " + expense.getAmount() +
                                 " Frequency (per year): " + expense.getYearlyfrequency();
            EWalletApp.reportListModel.addElement(expenseInfo);
            totalExpenses += expense.getAmount() * expense.getYearlyfrequency();
        }

        // Add total expenses
        EWalletApp.reportListModel.addElement("");
        EWalletApp.reportListModel.addElement("Total Expenses: " + totalExpenses);
    }

    public static void generateIncomeReport(User user) {
        EWalletApp.reportListModel.clear();

        // Add income details
        for (Wage wage : user.getWages()) {
            String incomeInfo = "Source: " + wage.getSource() +
                                "    Amount: " + wage.getAmount() +
                                "    Month: " + wage.getMonth();
            EWalletApp.reportListModel.addElement(incomeInfo);
        }
    }
    
    public static void generateFullReport(User user) {
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

        // Build detailed report as a string
        StringBuilder report = new StringBuilder();
        report.append("DETAILED REPORT\n");

        report.append("\nIncome:\n");
        for (Wage income : incomeTransactions) {
            report.append("- ").append(income.getSource()).append(": $")
                  .append(income.getAmount()).append(" (").append(income.getMonth()).append(")\n");
        }

        report.append("\nExpenses:\n");
        for (Expense expense : expenseTransactions) {
            report.append("- ").append(expense.getSource()).append(": $")
                  .append(expense.getAmount()).append(" (Frequency: ").append(expense.getYearlyfrequency()).append(")\n");
        }

        // Append summary info
        report.append("\nSUMMARY\n");
        report.append("Total Income: $").append(totalIncome).append("\n");
        report.append("Total Expenses: $").append(totalExpense).append("\n");
        report.append("Net Balance: $").append(netBalance).append("\n");

        // Display report via JOptionPane
        JOptionPane.showMessageDialog(null, report.toString());
    }
}