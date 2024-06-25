import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class EWalletApp extends JFrame {
	// this is the app class, has the GUI and create one object of your expense
	// calculator class. The expense calculator class is the implementation of the
	// Expenser interface

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private ArrayList<User> AllUsers = new ArrayList<User>();
	private String CurrentUser = "";
	
	private JTextField txtLoginUserName;
	private JTextField txtLoginPassword;
	private JTextField txtExpenceSource;
	private JTextField txtExpenseAmount;
	private JTextField txtExpenseYearlyFrequency;
	private JTextField txtIncomeSource;
	private JTextField txtIncomeAmount;
	private JTextField txtIncomeMonth;
	private JTextField txtRegisterUserName;
	private JTextField txtRegisterPassword;
	private JTextField txtBalance;
	private JTextField txtMonthlySavings;

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
		setBounds(100, 100, 1143, 863);
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
		
		JLabel lblNewLabel_2_1 = new JLabel("User Name");
		lblNewLabel_2_1.setBounds(137, 81, 76, 13);
		panel.add(lblNewLabel_2_1);
		
		txtLoginUserName = new JTextField();
		txtLoginUserName.setBounds(137, 104, 214, 19);
		panel.add(txtLoginUserName);
		txtLoginUserName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(137, 168, 76, 13);
		panel.add(lblNewLabel_2);
		
		txtLoginPassword = new JTextField();
		txtLoginPassword.setBounds(137, 191, 214, 19);
		panel.add(txtLoginPassword);
		txtLoginPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				for (int i = 0; i < AllUsers.size(); i++) {
					
					User currentUser = AllUsers.get(i);
					if (currentUser.username.equals(txtLoginUserName.getText())) {
						
						CurrentUser = txtLoginUserName.getText();
						
						//Login will display main jPanel in the future
					}
					
					System.out.println("Current user" + CurrentUser);
				}
			}
		});
		btnLogin.setBounds(199, 249, 96, 28);
		panel.add(btnLogin);
		
		JButton btnToRegisterSreen = new JButton("Resgister");
		btnToRegisterSreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnToRegisterSreen.setBounds(199, 293, 96, 28);
		panel.add(btnToRegisterSreen);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(219, 44, 76, 13);
		panel.add(lblLogin);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(43, 31, 508, 364);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton btnFinacialReport = new JButton("My Financial Report");
		btnFinacialReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = getUserObject();
				if(user != null){
					expenserCalulator.PrintFullreport(user);

				} else {
					JOptionPane.showMessageDialog(null, "User Not Found!");

				}
			}
		});
		btnFinacialReport.setBounds(280, 42, 149, 31);
		panel_1.add(btnFinacialReport);

		JButton btnExportCSV = new JButton("Export CSV");
		btnExportCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = getUserObject();
				if (user != null) {
					try {
						expenserCalulator.exportReportToCSV(user);
						JOptionPane.showMessageDialog(null, "Report exported as CSV!");
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null, "Error exporting report: " + ex.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "User not found!");
				}
			}
		});
		btnExportCSV.setBounds(210, 331, 149, 31);
		panel_1.add(btnExportCSV);

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
		btnAddExpense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String source = txtExpenceSource.getText();
				Double amount = Double.parseDouble(txtExpenseAmount.getText());
				int Yearly = Integer.parseInt(txtExpenseYearlyFrequency.getText());
				
				expenserCalulator.addExpense(getUserObject(), source, amount, Yearly);
				
				System.out.println(getUserObject().toString());
				
				UpdateBalance();
			}
		});
		btnAddExpense.setBounds(71, 289, 85, 21);
		panel_1.add(btnAddExpense);

		JButton btnIncome = new JButton("Add");
		btnIncome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String source = txtIncomeSource.getText();
				double amount = Double.parseDouble(txtIncomeAmount.getText());
				String month = txtIncomeMonth.getText();
				
				expenserCalulator.addMonthlyIncome(getUserObject(), source, amount, month);
				
				System.out.println(getUserObject().toString());
				
				UpdateBalance();
			}
		});
		btnIncome.setBounds(335, 289, 85, 21);
		panel_1.add(btnIncome);
		
		JLabel lblNewLabel_5 = new JLabel("Balance:");
		lblNewLabel_5.setBounds(63, 83, 45, 13);
		panel_1.add(lblNewLabel_5);
		
		txtBalance = new JTextField();
		txtBalance.setEditable(false);
		txtBalance.setBounds(63, 97, 96, 19);
		panel_1.add(txtBalance);
		txtBalance.setColumns(10);
		
		txtMonthlySavings = new JTextField();
		txtMonthlySavings.setEditable(false);
		txtMonthlySavings.setColumns(10);
		txtMonthlySavings.setBounds(280, 97, 96, 19);
		panel_1.add(txtMonthlySavings);
		
		JLabel lblNewLabel_5_1 = new JLabel("Monthly Savings:");
		lblNewLabel_5_1.setBounds(280, 83, 96, 13);
		panel_1.add(lblNewLabel_5_1);


		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(43, 411, 508, 364);
		contentPane.add(panel_1_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(580, 411, 508, 364);
		contentPane.add(panel_2);
		
		JLabel lblRegister_1 = new JLabel("Register");
		lblRegister_1.setBounds(219, 44, 76, 13);
		panel_2.add(lblRegister_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("User Name");
		lblNewLabel_2_1_1.setBounds(137, 81, 76, 13);
		panel_2.add(lblNewLabel_2_1_1);
		
		txtRegisterUserName = new JTextField();
		txtRegisterUserName.setColumns(10);
		txtRegisterUserName.setBounds(137, 104, 214, 19);
		panel_2.add(txtRegisterUserName);
		
		JLabel lblNewLabel_2_2 = new JLabel("Password");
		lblNewLabel_2_2.setBounds(137, 168, 76, 13);
		panel_2.add(lblNewLabel_2_2);
		
		txtRegisterPassword = new JTextField();
		txtRegisterPassword.setColumns(10);
		txtRegisterPassword.setBounds(137, 191, 214, 19);
		panel_2.add(txtRegisterPassword);
		
		JButton btnRegister = new JButton("Resgister");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateUser(txtRegisterUserName.getText(), txtRegisterPassword.getText());
	
			}
		});
		btnRegister.setBounds(199, 243, 96, 28);
		panel_2.add(btnRegister);
		
	}
	
	
	public void CreateUser(String username, String password) {

		User user = new User(username, password);
		AllUsers.add(user);
		
		System.out.println("User added:/n Username: " + user.getUserName() + "/n Password: " + user.getPwd());
		
		System.out.println(AllUsers.toString());
	}
	
	public User getUserObject() {
		
		for (int i = 0; i < AllUsers.size(); i++) {
			
			User currentUser = AllUsers.get(i);
			if (currentUser.username.equals(CurrentUser)) {
				
				return currentUser;
			}
		}
		return null;
	}
	
	public void UpdateUserObject(User object) {
		
		for (int i = 0; i < AllUsers.size(); i++) {
			
			User currentUser = AllUsers.get(i);
			if (currentUser.username.equals(CurrentUser)) {
				
				AllUsers.set(i, object);
			}
		}
	}
	
	public void UpdateBalance() {
		
	    double expensesTotal = 0;
	    double incomeTotal = 0;

	    for (int i = 0; i < AllUsers.size(); i++) {
	        User currentUser = AllUsers.get(i);
	        if (currentUser.username.equals(CurrentUser)) {
	            ArrayList<Expense> listOfExpense = currentUser.getExpenses();
	            if (listOfExpense != null) {
	                for (int expenseNum = 0; expenseNum < listOfExpense.size(); expenseNum++) {
	                    Expense currentExpense = listOfExpense.get(expenseNum);
	                    expensesTotal += currentExpense.amount;
	                }
	            }

	            ArrayList<Wage> listOfIncome = currentUser.getWages();
	            if (listOfIncome != null) {
	                for (int incomeNum = 0; incomeNum < listOfIncome.size(); incomeNum++) {
	                    Wage currentIncome = listOfIncome.get(incomeNum);
	                    incomeTotal += currentIncome.getAmount();
	                }
	            }

	            txtBalance.setText(String.valueOf(incomeTotal - expensesTotal));
	            break; // Exit the loop once the current user is found
	        }
	    }
	}

	
}
