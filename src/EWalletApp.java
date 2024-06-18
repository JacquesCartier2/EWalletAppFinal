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
import javax.swing.JDesktopPane;
import javax.swing.JList;

public class EWalletApp extends JFrame {
	// this is the app class, has the GUI and create one object of your expense
	// calculator class. The expense calculator class is the implementation of the
	// Expenser interface

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private ArrayList<User> AllUsers = new ArrayList<User>();
	private String CurrentUser = "";

	private JTextField txtUserName;
	private JTextField txtPassword;
	private JTextField txtExpenceSource;
	private JTextField txtExpenseAmount;
	private JTextField txtExpenseYearlyFrequency;
	private JTextField txtIncomeSource;
	private JTextField txtIncomeAmount;
	private JTextField txtIncomeMonth;

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

		ExpenseCalulator expenserCalulator = new ExpenseCalulator();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1112, 780);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome User");
		lblNewLabel.setVisible(false);
		lblNewLabel.setBounds(23, 10, 138, 37);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(580, 31, 508, 364);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblRegister = new JLabel("Register");
		lblRegister.setBounds(219, 44, 76, 13);
		panel.add(lblRegister);

		JLabel lblNewLabel_2_1 = new JLabel("User Name");
		lblNewLabel_2_1.setBounds(137, 81, 76, 13);
		panel.add(lblNewLabel_2_1);

		txtUserName = new JTextField();
		txtUserName.setBounds(137, 104, 214, 19);
		panel.add(txtUserName);
		txtUserName.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(137, 168, 76, 13);
		panel.add(lblNewLabel_2);

		txtPassword = new JTextField();
		txtPassword.setBounds(137, 191, 214, 19);
		panel.add(txtPassword);
		txtPassword.setColumns(10);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(199, 249, 96, 28);
		panel.add(btnLogin);

		JButton btnRegister = new JButton("Resgister");
		btnRegister.setBounds(199, 293, 96, 28);
		panel.add(btnRegister);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(219, 61, 76, 13);
		panel.add(lblLogin);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(43, 31, 508, 364);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton btnFinacialReport = new JButton("My Financial Report");
		btnFinacialReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFinacialReport.setBounds(280, 42, 149, 31);
		panel_1.add(btnFinacialReport);

		JList LstCurrency = new JList();
		LstCurrency.setBounds(63, 37, 149, 36);
		panel_1.add(LstCurrency);

		JLabel lblNewLabel_1 = new JLabel("Currency in use:");
		lblNewLabel_1.setBounds(63, 14, 108, 13);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("Add Expense:");
		lblNewLabel_3.setBounds(78, 145, 108, 13);
		panel_1.add(lblNewLabel_3);

		JLabel lblNewLabel_3_1 = new JLabel("Add Income:");
		lblNewLabel_3_1.setBounds(335, 145, 108, 13);
		panel_1.add(lblNewLabel_3_1);

		JLabel lblNewLabel_4 = new JLabel("Source:");
		lblNewLabel_4.setBounds(22, 168, 84, 13);
		panel_1.add(lblNewLabel_4);

		txtExpenceSource = new JTextField();
		txtExpenceSource.setBounds(116, 165, 96, 19);
		panel_1.add(txtExpenceSource);
		txtExpenceSource.setColumns(10);

		JLabel lblNewLabel_4_1 = new JLabel("Amount:         $");
		lblNewLabel_4_1.setBounds(22, 221, 96, 13);
		panel_1.add(lblNewLabel_4_1);

		txtExpenseAmount = new JTextField();
		txtExpenseAmount.setColumns(10);
		txtExpenseAmount.setBounds(116, 218, 96, 19);
		panel_1.add(txtExpenseAmount);

		JLabel lblNewLabel_4_2 = new JLabel("Yearly Frequency:");
		lblNewLabel_4_2.setBounds(22, 266, 96, 13);
		panel_1.add(lblNewLabel_4_2);

		txtExpenseYearlyFrequency = new JTextField();
		txtExpenseYearlyFrequency.setColumns(10);
		txtExpenseYearlyFrequency.setBounds(116, 263, 96, 19);
		panel_1.add(txtExpenseYearlyFrequency);

		JLabel lblNewLabel_4_3 = new JLabel("Source:");
		lblNewLabel_4_3.setBounds(280, 168, 84, 13);
		panel_1.add(lblNewLabel_4_3);

		txtIncomeSource = new JTextField();
		txtIncomeSource.setColumns(10);
		txtIncomeSource.setBounds(374, 165, 96, 19);
		panel_1.add(txtIncomeSource);

		txtIncomeAmount = new JTextField();
		txtIncomeAmount.setColumns(10);
		txtIncomeAmount.setBounds(374, 218, 96, 19);
		panel_1.add(txtIncomeAmount);

		JLabel lblNewLabel_4_1_1 = new JLabel("Amount:         $");
		lblNewLabel_4_1_1.setBounds(280, 221, 96, 13);
		panel_1.add(lblNewLabel_4_1_1);

		JLabel lblNewLabel_4_2_1 = new JLabel("Month:");
		lblNewLabel_4_2_1.setBounds(280, 266, 96, 13);
		panel_1.add(lblNewLabel_4_2_1);

		txtIncomeMonth = new JTextField();
		txtIncomeMonth.setColumns(10);
		txtIncomeMonth.setBounds(374, 263, 96, 19);
		panel_1.add(txtIncomeMonth);

		JButton btnAddExpense = new JButton("Add");
		btnAddExpense.setBounds(71, 289, 85, 21);
		panel_1.add(btnAddExpense);

		JButton btnIncome = new JButton("Add");
		btnIncome.setBounds(335, 289, 85, 21);
		panel_1.add(btnIncome);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(43, 411, 508, 364);
		contentPane.add(panel_1_1);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

	}

	public void CreateUser(String username, String password) {

		User user = new User(username, password);
		AllUsers.add(user);
	}

	public User getUserObject(String username) {
		
		for (int i = 0; i < AllUsers.size(); i++) {
			
			User currentUser = AllUsers.get(i);
			if (currentUser.username == username) {
				
				return currentUser;
			}
		}
		return null;
	}
