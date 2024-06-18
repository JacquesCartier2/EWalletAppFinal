import java.util.ArrayList;

public class EWalletApp {
	//this is the app class, has the GUI and create one object of your expense calculator class. The expense calculator class is the implementation of the Expenser interface 
	private ArrayList<User> AllUsers = new ArrayList<User>();
	
	public void CreateUser(String username, String password) {
		
		User user = new User(username, password);
		AllUsers.add(user);
	}
	
	

}
