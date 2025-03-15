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
		try
		{
			statement = connection.createStatement();
			statement.execute("INSERT INTO USERS VALUES ('" + _user.getUserName() + "','" + _user.getPwd() +  "')");
			statement.close();
			return true;
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
			return false;
		}
	}
	
	//add an income to the DB. 
	public boolean AddIncome(Wage _income, User _user) {
		try
		{
			statement = connection.createStatement();
			statement.execute("INSERT INTO INCOMES (SOURCE,AMOUNT,MONTH,USER_NAME) VALUES ('" + _income.getSource() + "'," + _income.getAmount() + ",'" + _income.getMonth() + "','" + _user.getUserName() + "')");
			statement.close();
			return true;
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
			return false;
		}
	}
	
	//add an income to the DB. 
	public boolean AddExpense(Expense _expense, User _user) {
		try
		{
			statement = connection.createStatement();
			statement.execute("INSERT INTO EXPENSES (SOURCE,AMOUNT,YEARLYFREQ,USER_NAME) VALUES ('" + _expense.getSource() + "'," + _expense.getAmount() + "," + _expense.getYearlyfrequency() + ",'" + _user.getUserName() + "')");
			statement.close();
			return true;
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
			return false;
		}
	}
	
	//returns an arraylist of all users (and their income/expenses) stored in the database. Returns null if errors occur. 
	public ArrayList<User> GetAllData(){
		ArrayList<User> returnList = new ArrayList<User>();
		ResultSet results;
		
		//get the users
		try {
			statement = connection.createStatement(); //create a statement from the DB connection. 
			
			results = statement.executeQuery("SELECT * FROM \"APP\".\"USERS\""); //results will contain a list of DB entries.
			
			//add users from results into returnList.
			while(results.next())
			{
				returnList.add(new User(results.getString("NAME"), results.getString("PASSWORD")));
			}
		}
		catch (SQLException sqlExcept){
			sqlExcept.printStackTrace();
			return null;
		}
		
		//get the incomes.
		try {
			statement = connection.createStatement(); //create a statement from the DB connection. 
			
			results = statement.executeQuery("SELECT * FROM \"APP\".\"INCOMES\""); //results will contain a list of DB entries.
			
			//add users from results into returnList.
			while(results.next())
			{
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
			return null;
		}
		
		//get the expenses.
		try {
			statement = connection.createStatement(); //create a statement from the DB connection. 
			
			results = statement.executeQuery("SELECT * FROM \"APP\".\"EXPENSES\""); //results will contain a list of DB entries.
			
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
			return null;
		}
		
		return returnList;
	}
	
	//returns true is a user is already in the database false otherwise. Also returns false if an error occurs. 
	public boolean CheckForUser(String _username) {
		try {
			statement = connection.createStatement(); //create a statement from the DB connection. 
			
			ResultSet results = statement.executeQuery("SELECT * FROM \"APP\".\"USERS\" WHERE NAME = '" + _username + "'"); //results will contain a list of DB entries.
			
			if(results.next() == true) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException sqlExcept){
			sqlExcept.printStackTrace();
			return false;
		}
	}
	
	//returns true is an expense is already in the database false otherwise. Also returns false if an error occurs. 
	public boolean CheckForExpense(int _id) {
		try {
			statement = connection.createStatement(); //create a statement from the DB connection. 
			
			ResultSet results = statement.executeQuery("SELECT * FROM \"APP\".\"EXPENSES\" WHERE EXPENSEID = " + _id); //results will contain a list of DB entries.
			
			if(results.next() == true) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException sqlExcept){
			sqlExcept.printStackTrace();
			return false;
		}
	}
	
	//returns true is an income is already in the database false otherwise. Also returns false if an error occurs. 
	public boolean CheckForIncome(int _id) {
		try {
			statement = connection.createStatement(); //create a statement from the DB connection. 
			
			ResultSet results = statement.executeQuery("SELECT * FROM \"APP\".\"INCOMES\" WHERE INCOMEID = " + _id); //results will contain a list of DB entries.
			
			if(results.next() == true) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException sqlExcept){
			sqlExcept.printStackTrace();
			return false;
		}
	}
	
	//IDs are automatically generated in the DB, so when we add an income or expense through code we have no way of knowing what the ID is. Since multiple entries can have the same data except for the id,
	//we must get all DB entries matching the other data, then we look through each one and if the id from the DB entry does not match an existing object, that id will be given to the object passed into the function.
	
	//see above comment. Returns -1 if an error occurs or id not found. 
	public int GetAvailableExpenseID(Expense _expense, User _user) {
		try {
			statement = connection.createStatement(); //create a statement from the DB connection. 
			
			ResultSet results = statement.executeQuery("SELECT * FROM \"APP\".\"EXPENSES\" WHERE SOURCE = '" + _expense.source + "' AND AMOUNT = " + _expense.amount + " AND YEARLYFREQ = " + _expense.yearlyfrequency + " AND USER_NAME = '" + _user.username + "'"); //results will contain a list of DB entries.
			
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
		catch (SQLException sqlExcept){
			sqlExcept.printStackTrace();
			return -1;
		}
	}
	
	//see above comment. Returns -1 if an error occurs or id not found. 
	public int GetAvailableIncomeID(Wage _income, User _user) {
		try {
			statement = connection.createStatement(); //create a statement from the DB connection. 
			
			ResultSet results = statement.executeQuery("SELECT * FROM \"APP\".\"INCOMES\" WHERE SOURCE = '" + _income.source + "' AND AMOUNT = " + _income.amount + " AND MONTH = '" + _income.Month + "' AND USER_NAME = '" + _user.username + "'"); //results will contain a list of DB entries.
			
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
		catch (SQLException sqlExcept){
			sqlExcept.printStackTrace();
			return -1;
		}
	}
	
	//create tables to store data for User, Expense, and Income. This should only be used to set up embedded DBs for local testing. 
	public void DebugCreateTables() {
		try
		{
			//"GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" is derby syntax for auto-increment. 
			statement = connection.createStatement();
			statement.execute("CREATE TABLE Users (Name varchar(255) PRIMARY KEY, Password varchar(255) NOT NULL)");
			statement.close();
			System.out.println("Users table created. ");
			
			statement = connection.createStatement();
			statement.execute("CREATE TABLE Expenses (ExpenseID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Source varchar(255), Amount double NOT NULL, YearlyFreq int NOT NULL, User_Name varchar(255) NOT NULL)");
			statement.close();
			System.out.println("Expenses table created. ");
			
			statement = connection.createStatement();
			statement.execute("CREATE TABLE Incomes (IncomeID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Source varchar(255), Amount double NOT NULL, Month varchar(255), User_Name varchar(255) NOT NULL)");
			statement.close();
			System.out.println("Incomes table created. ");
			
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
		}
	}
	
	//drop a table from the database. This should only be used to set up embedded DBs for local testing. 
	public void DebugDropTable(String _tableName) {
		try
		{
			statement = connection.createStatement();
			statement.execute("DROP TABLE " + _tableName);
			statement.close();
			System.out.println(_tableName + " dropped. ");
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
		}
	}
	
	public Database() {
		
	}
}
