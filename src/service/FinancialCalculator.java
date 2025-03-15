package service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Expense;
import model.User;
import model.Wage;

public class FinancialCalculator {
    public static double updateMonthlySavings(User user) {
        double monthlyExpenses = 0;
        double monthlyIncome = 0;
        double monthlySavings;

        // Calculate monthly expenses
        ArrayList<Expense> listOfExpense = user.getExpenses();
        if (listOfExpense != null) {
            for (Expense currentExpense : listOfExpense) {
                monthlyExpenses += (currentExpense.getAmount() * currentExpense.getYearlyfrequency()) / 12;
            }
        }

        // Calculate monthly income for the current month
        ArrayList<Wage> listOfIncome = user.getWages();
        if (listOfIncome != null) {
            for (Wage currentIncome : listOfIncome) {
                LocalDate currentDate = LocalDate.now();
                String currentMonth = currentDate.getMonth().toString();

                if (currentMonth.equalsIgnoreCase(currentIncome.getMonth())) {
                    monthlyIncome += currentIncome.getAmount();
                }
            }
        }

        // Calculate monthly savings
        monthlySavings = monthlyIncome - monthlyExpenses;

        // Round to 2 decimal places for currency representation
        BigDecimal bd = new BigDecimal(Double.toString(monthlySavings));
        monthlySavings = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();

        // Update user's monthly savings
        user.monthlysavings = monthlySavings;

        return monthlySavings;
    }
}