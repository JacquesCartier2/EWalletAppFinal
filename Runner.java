import java.util.ArrayList;

public class Runner {

    public static void main(String[] args){
        
        ExpenseCalulator calculator = new ExpenseCalulator();

        User user1 = new User("Bob", "hgjg");
        user1.addWage(new Wage("Salary", 50000, "October"));
        user1.addExpense(new Expense("food", 500, 12));

        calculator.Users.add(user1);

        calculator.PrintFullreport();
    }
}
