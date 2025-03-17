package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Expense;
import model.User;
import model.Wage;

//this class contains all code used to interact with the database. 
public class Database {
	//private String connectionLink = "jdbc:derby:F:\\Program Files\\Apache Derby DB\\Databases\\SENG210_Final;create=false";
	private static String connectionLink="jdbc:derby:C:/Users/elgu4/SENG210_Final"; //create=false";
	private static Connection connection = null; 
	private static Statement statement = null;

	//steps for setting up a local DB for testing:
	// 1. right click the project in eclipse and select "properties", then navigate to "java build path>libraries" and add your local derby.jar to the classpath as an external jar, deleting any existing derby.jars from the classpath.
	// 2. create an embedded derby DB, remember to disconnect from it before running any code, and that only one instance of the program may be running at a time. 
	// 3. replace the connectionLink variable with the connection link to your newly created DB.
	// 4. in EWalletApp, just after "database.connect();" in initializeLoginPanel, copy and paste "database.DebugCreateTables();".
	// 5. run the program, then close it. If no errors occured, the database will now have the correct tables. 
	// 6. delete the "database.DebugCreateTables();" line. 

	//open a connection to the database. Returns true if connection succeeds false otherwise. 

	@SuppressWarnings("deprecation")
	public boolean Connect() {
		try
		{
			//Ensure that the correct driver class is present. 
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

			//Get a connection.
			connection = DriverManager.getConnection(connectionLink); 

			System.out.println("DB connection successful. ");
			return true;
		}
		catch (Exception except)
		{
			except.printStackTrace();
			return false;
		}
	}

	//add a user to the DB. 
	public boolean AddUser(User _user) {
		String sql = "INSERT INTO USERS VALUES ('" + _user.getUserName() + "','" + _user.getPwd() +  "')";
		return executeInsertSqlStatement(sql);
	}

	//add an income to the DB. 
	public boolean AddIncome(Wage _income, User _user) {
		String sql = "INSERT INTO INCOMES (SOURCE,AMOUNT,MONTH,USER_NAME) VALUES ('" + _income.getSource() + "'," + _income.getAmount() + ",'" + _income.getMonth() + "','" + _user.getUserName() + "')";
		return executeInsertSqlStatement(sql);
	}

	//add an income to the DB. 
	public boolean AddExpense(Expense _expense, User _user) {
		String sql = "INSERT INTO EXPENSES (SOURCE,AMOUNT,YEARLYFREQ,USER_NAME) VALUES ('" + _expense.getSource() + "'," + _expense.getAmount() + "," + _expense.getYearlyfrequency() + ",'" + _user.getUserName() + "')";
		return executeInsertSqlStatement(sql);
	}

	//returns an arraylist of all users (and their income/expenses) stored in the database. Returns null if errors occur. 
	public ArrayList<User> GetAllData(){
		ArrayList<User> returnList = new ArrayList<User>();
		getUsers(returnList);

		getIncomes(returnList);

		getExpenses(returnList);

		return returnList;
	}

	//see above comment. Returns -1 if an error occurs or id not found. 
	public int GetAvailableExpenseID(Expense _expense, User _user) throws SQLException {
		String sql = "SELECT * FROM \"APP\".\"EXPENSES\" WHERE SOURCE = '" + _expense.source + "' AND AMOUNT = " + _expense.amount + " AND YEARLYFREQ = " + _expense.yearlyfrequency + " AND USER_NAME = '" + _user.username + "'";
		ResultSet results = executeSelectSqlStatement(sql);		

		//there may be multiple expenses identical except for id in the DB, so look through all expenses in the user to see if the current ID matches any of them. 
		//if there is a match, that means that the current id is already in use so you should move on to the next DB entry's ID to see if it is available in the live code. 
		int currentID;
		boolean IDAvailable = true;
		while(results.next()) {
			IDAvailable = true;
			currentID = results.getInt("EXPENSEID");

			for(Expense exp : _user.getExpenses()) {
				if(exp.getID() == currentID) {
					IDAvailable = false;
					break;
				}
			}

			if(IDAvailable) {
				return currentID;
			}
		}
		return -1;
	}

	//see above comment. Returns -1 if an error occurs or id not found. 
	public int GetAvailableIncomeID(Wage _income, User _user) throws SQLException {
		String sql = "SELECT * FROM \"APP\".\"INCOMES\" WHERE SOURCE = '" + _income.source + "' AND AMOUNT = " + _income.amount + " AND MONTH = '" + _income.Month + "' AND USER_NAME = '" + _user.username + "'";
		ResultSet results = executeSelectSqlStatement(sql);

		//there may be multiple expenses identical except for id in the DB, so look through all expenses in the user to see if the current ID matches any of them. 
		//if there is a match, that means that the current id is already in use so you should move on to the next DB entry's ID to see if it is available in the live code. 
		int currentID;
		boolean IDAvailable = true;
		while(results.next()) {
			IDAvailable = true;
			currentID = results.getInt("INCOMEID");

			for(Wage inc : _user.getWages()) {
				if(inc.getID() == currentID) {
					IDAvailable = false;
					break;
				}
			}

			if(IDAvailable) {
				return currentID;
			}
		}
		return -1;	
	}	

	private void getUsers(ArrayList<User> returnList) {
		ResultSet results;
		try {
			//get the users
			results = executeSelectSqlStatement("SELECT * FROM \"APP\".\"USERS\""); //results will contain a list of DB entries.

			//add users from results into returnList.
			while(results.next())
			{
				returnList.add(new User(results.getString("NAME"), results.getString("PASSWORD")));
			}
		}
		catch (SQLException sqlExcept){
			sqlExcept.printStackTrace();
		}
	}

	private void getIncomes(ArrayList<User> returnList) {
		ResultSet results;
		try {
			//get the incomes.
			results = executeSelectSqlStatement("SELECT * FROM \"APP\".\"INCOMES\""); //results will contain a list of DB entries.

			//add users from results into returnList.
			while(results.next()) {
				//find the user with a matching username.
				for(User user : returnList) {
					if(user.username.equals(results.getString("USER_NAME"))) {
						user.addWage(new Wage(results.getString("SOURCE"), results.getDouble("AMOUNT"), results.getString("MONTH"), results.getInt("INCOMEID")));
						break;
					}
				}
			}
		}
		catch (SQLException sqlExcept){
			sqlExcept.printStackTrace();
		}
	}

	private void getExpenses(ArrayList<User> returnList) {
		ResultSet results;
		try {
			// get the expenses.
			results = executeSelectSqlStatement("SELECT * FROM \"APP\".\"EXPENSES\""); //results will contain a list of DB entries.
			//add users from results into returnList.
			while(results.next())
			{
				//find the user with a matching username.
				for(User user : returnList) {
					if(user.username.equals(results.getString("USER_NAME"))) {
						user.addExpense(new Expense(results.getString("SOURCE"), results.getDouble("AMOUNT"), results.getInt("YEARLYFREQ"), results.getInt("EXPENSEID")));
						break;
					}
				}
			}
		}
		catch (SQLException sqlExcept){
			sqlExcept.printStackTrace();
		}
	}

	private boolean executeInsertSqlStatement(String sql) {
		try {
			statement = connection.createStatement();
			statement.execute(sql);
			statement.close();
			return true;
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
			return false;
		}
	}

	private ResultSet executeSelectSqlStatement(String sql) {
		try {
			statement = connection.createStatement();
			ResultSet results = statement.executeQuery(sql);
			return results;
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
			return null;
		}
	}
}