package service;
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
}