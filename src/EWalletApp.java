import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EWalletApp extends JFrame{
	//this is the app class, has the GUI and create one object of your expense calculator class. The expense calculator class is the implementation of the Expenser interface 
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private ArrayList<User> AllUsers = new ArrayList<User>();
	private JTextField txtUserName;
	private JTextField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EWalletApp frame = new EWalletApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EWalletApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome User");
		lblNewLabel.setBounds(33, 21, 138, 37);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Login");
		lblNewLabel_1.setBounds(299, 67, 76, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(220, 189, 76, 13);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Resgister");
		btnNewButton.setBounds(279, 316, 96, 28);
		contentPane.add(btnNewButton);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnLogin.setBounds(279, 270, 96, 28);
		contentPane.add(btnLogin);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(220, 125, 214, 19);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(220, 212, 214, 19);
		contentPane.add(txtPassword);
		
		JLabel lblNewLabel_2_1 = new JLabel("User Name");
		lblNewLabel_2_1.setBounds(221, 102, 76, 13);
		contentPane.add(lblNewLabel_2_1);

		
	}
	
	public void CreateUser(String username, String password) {
		
		User user = new User(username, password);
		AllUsers.add(user);
	}
}
