import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.io.IOException;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.event.ListSelectionEvent;

public class EWalletApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private ArrayList<User> AllUsers = new ArrayList<User>();
    Currency C = new Currency(1.00, "USD");
    private String CurrentUser = "";

    // JTextFields
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

    ExpenseCalulator expenserCalulator = new ExpenseCalulator();

    private JPanel loginPanel;
    private JPanel registerPanel;
    private JPanel mainMenuPanel;
    private JPanel reportPagePanel;
    
    
    // Generates ReportListModel List for JList GUI use 
    // TODO CAN BE MOVED TO ANOTHER CLASS IF NEEDED
    // TODO THIS WILL MOST LIKELY BE REPLACED BY DATABASE 
   	DefaultListModel<String> reportListModel = new DefaultListModel<>();;
   	
   	// Used for File Chooser
 	JFileChooser importFile = new JFileChooser("C:\\",FileSystemView.getFileSystemView());

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
        setBounds(100, 100, 635, 527);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        initializeLoginPanel();
        initializeRegisterPanel();
        initializeMainMenuPanel();
        initializeReportPagePanel();

        // Initially show the login panel
        // showLoginPanel();
     //   showMainMenuPanel();
        showReportPagePanel();

    }

    private void initializeLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setBounds(43, 31, 527, 406);
        contentPane.add(loginPanel);
        loginPanel.setLayout(null);
        JLabel lblLoginUserName = new JLabel("User Name");
        lblLoginUserName.setBounds(137, 81, 76, 13);
        loginPanel.add(lblLoginUserName);

        txtLoginUserName = new JTextField();
        txtLoginUserName.setBounds(137, 104, 214, 19);
        loginPanel.add(txtLoginUserName);
        txtLoginUserName.setColumns(10);

        JLabel lblLoginPassword = new JLabel("Password");
        lblLoginPassword.setBounds(137, 168, 76, 13);
        loginPanel.add(lblLoginPassword);

        txtLoginPassword = new JTextField();
        txtLoginPassword.setBounds(137, 191, 214, 19);
        loginPanel.add(txtLoginPassword);
        txtLoginPassword.setColumns(10);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < AllUsers.size(); i++) {

                    User currentUser = AllUsers.get(i);

                    if (currentUser.username.equals(txtLoginUserName.getText())) {
                        CurrentUser = txtLoginUserName.getText();
                        showMainMenuPanel();
                        break;
                    }
                }
            }
        });
        btnLogin.setBounds(199, 249, 96, 28);
        loginPanel.add(btnLogin);

        JButton btnToRegisterScreen = new JButton("Register");
        btnToRegisterScreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                showRegisterPanel();

            }
        });
        btnToRegisterScreen.setBounds(199, 293, 96, 28);
        loginPanel.add(btnToRegisterScreen);

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setBounds(219, 44, 76, 13);
        loginPanel.add(lblLogin);
    }

    private void initializeRegisterPanel() {
        registerPanel = new JPanel();
        registerPanel.setBounds(43, 31, 527, 406);
        contentPane.add(registerPanel);
        registerPanel.setLayout(null);

        JLabel lblRegister = new JLabel("Register");
        lblRegister.setBounds(219, 44, 76, 13);
        registerPanel.add(lblRegister);

        JLabel lblRegisterUserName = new JLabel("User Name");
        lblRegisterUserName.setBounds(137, 81, 76, 13);
        registerPanel.add(lblRegisterUserName);

        txtRegisterUserName = new JTextField();
        txtRegisterUserName.setBounds(137, 104, 214, 19);
        registerPanel.add(txtRegisterUserName);
        txtRegisterUserName.setColumns(10);

        JLabel lblRegisterPassword = new JLabel("Password");
        lblRegisterPassword.setBounds(137, 168, 76, 13);
        registerPanel.add(lblRegisterPassword);

        txtRegisterPassword = new JTextField();
        txtRegisterPassword.setBounds(137, 191, 214, 19);
        registerPanel.add(txtRegisterPassword);
        txtRegisterPassword.setColumns(10);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                CreateUser(txtRegisterUserName.getText(), txtRegisterPassword.getText());
                showLoginPanel();
            }
        });
        btnRegister.setBounds(199, 243, 96, 28);
        registerPanel.add(btnRegister);
    }

    private void initializeMainMenuPanel() {
        mainMenuPanel = new JPanel();
        mainMenuPanel.setBounds(43, 31, 527, 406);
        contentPane.add(mainMenuPanel);
        mainMenuPanel.setLayout(null);

        JLabel lblBalance = new JLabel("Balance:");
        lblBalance.setBounds(63, 83, 96, 13);
        mainMenuPanel.add(lblBalance);

        txtBalance = new JTextField();
        txtBalance.setEditable(false);
        txtBalance.setBounds(63, 97, 96, 25);
        mainMenuPanel.add(txtBalance);
        txtBalance.setColumns(10);

        JLabel lblMonthlySavings = new JLabel("Monthly Savings:");
        lblMonthlySavings.setBounds(280, 83, 96, 13);
        mainMenuPanel.add(lblMonthlySavings);

        txtMonthlySavings = new JTextField();
        txtMonthlySavings.setEditable(false);
        txtMonthlySavings.setBounds(280, 97, 96, 19);
        mainMenuPanel.add(txtMonthlySavings);
        txtMonthlySavings.setColumns(10);

        JLabel lblAddExpense = new JLabel("Add Expense:");
        lblAddExpense.setBounds(78, 145, 108, 13);
        mainMenuPanel.add(lblAddExpense);

        JLabel lblExpenseSource = new JLabel("Source:");
        lblExpenseSource.setBounds(30, 168, 84, 13);
        mainMenuPanel.add(lblExpenseSource);

        txtExpenceSource = new JTextField();
        txtExpenceSource.setBounds(116, 165, 96, 19);
        mainMenuPanel.add(txtExpenceSource);
        txtExpenceSource.setColumns(10);

        JLabel lblExpenseAmount = new JLabel("Amount:          $");
        lblExpenseAmount.setBounds(30, 221, 96, 13);
        mainMenuPanel.add(lblExpenseAmount);

        txtExpenseAmount = new JTextField();
        txtExpenseAmount.setBounds(116, 218, 96, 19);
        mainMenuPanel.add(txtExpenseAmount);
        txtExpenseAmount.setColumns(10);

        JLabel lblExpenseYearlyFrequency = new JLabel("Yearly Frequency:");
        lblExpenseYearlyFrequency.setBounds(10, 266, 115, 13);
        mainMenuPanel.add(lblExpenseYearlyFrequency);

        txtExpenseYearlyFrequency = new JTextField();
        txtExpenseYearlyFrequency.setBounds(116, 263, 96, 19);
        mainMenuPanel.add(txtExpenseYearlyFrequency);
        txtExpenseYearlyFrequency.setColumns(10);

        JButton btnAddExpense = new JButton("Add");
        btnAddExpense.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String source = txtExpenceSource.getText();
                double amount = Double.parseDouble(txtExpenseAmount.getText());
                int Yearly = Integer.parseInt(txtExpenseYearlyFrequency.getText());

                expenserCalulator.addExpense(getUserObject(), source, amount, Yearly);
                System.out.println(getUserObject().toString());

                UpdateBalance();
                UpdateSavings();
            }
        });
        btnAddExpense.setBounds(71, 289, 85, 21);
        mainMenuPanel.add(btnAddExpense);

        JLabel lblAddIncome = new JLabel("Add Income:");
        lblAddIncome.setBounds(335, 145, 108, 13);
        mainMenuPanel.add(lblAddIncome);

        JLabel lblIncomeSource = new JLabel("Source:");
        lblIncomeSource.setBounds(280, 168, 84, 13);
        mainMenuPanel.add(lblIncomeSource);

        txtIncomeSource = new JTextField();
        txtIncomeSource.setBounds(374, 165, 96, 19);
        mainMenuPanel.add(txtIncomeSource);
        txtIncomeSource.setColumns(10);

        JLabel lblIncomeAmount = new JLabel("Amount:            $");
        lblIncomeAmount.setBounds(280, 221, 96, 13);
        mainMenuPanel.add(lblIncomeAmount);

        txtIncomeAmount = new JTextField();
        txtIncomeAmount.setBounds(374, 218, 96, 19);
        mainMenuPanel.add(txtIncomeAmount);
        txtIncomeAmount.setColumns(10);

        JLabel lblIncomeMonth = new JLabel("Month:");
        lblIncomeMonth.setBounds(280, 266, 96, 13);
        mainMenuPanel.add(lblIncomeMonth);

        txtIncomeMonth = new JTextField();
        txtIncomeMonth.setBounds(374, 263, 96, 19);
        mainMenuPanel.add(txtIncomeMonth);
        txtIncomeMonth.setColumns(10);

        JButton btnAddIncome = new JButton("Add");
        btnAddIncome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String source = txtIncomeSource.getText();
                double amount = Double.parseDouble(txtIncomeAmount.getText());
                String month = txtIncomeMonth.getText();

                expenserCalulator.addMonthlyIncome(getUserObject(), source, amount, month);
                System.out.println(getUserObject().toString());

                UpdateBalance();
                UpdateSavings();
            }
        });
        btnAddIncome.setBounds(335, 289, 85, 21);
        mainMenuPanel.add(btnAddIncome);

        JButton btnFinancialReport = new JButton("My Financial Report");
        btnFinancialReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User user = getUserObject();
                if (user != null) {
                    expenserCalulator.PrintFullreport(user);
                } else {
                    JOptionPane.showMessageDialog(null, "User Not Found!");
                }
            }
        });
        btnFinancialReport.setBounds(280, 42, 149, 31);
        mainMenuPanel.add(btnFinancialReport);

   

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrentUser = "";
                showLoginPanel();
            }
        });
        btnLogout.setBounds(0, 332, 96, 28);
        mainMenuPanel.add(btnLogout);

        String currencyList[] = { "USD", "CAD" };
        JList<String> LstCurrency = new JList<String>(currencyList);
        LstCurrency.setBounds(63, 37, 149, 36);
        LstCurrency.setSelectedIndex(0);
        mainMenuPanel.add(LstCurrency);
        // here
        LstCurrency.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    double inBalAmount = Double.parseDouble(txtBalance.getText());
                    double inSaveAmount = Double.parseDouble(txtMonthlySavings.getText());
                    int outBalAmount;
                    int outSaveAmount;
                    String name = LstCurrency.getSelectedValue();

                    expenserCalulator.convertForeignCurrency(C, name);

                    outBalAmount = (int) (inBalAmount * C.getRate());
                    txtBalance.setText(String.valueOf(outBalAmount));

                    outSaveAmount = (int) (inSaveAmount * C.getRate());
                    txtMonthlySavings.setText(String.valueOf(outSaveAmount));
                }
            }
        });

        JLabel lblNewLabel_1 = new JLabel("Currency in use:");
        lblNewLabel_1.setBounds(63, 14, 108, 13);
        mainMenuPanel.add(lblNewLabel_1);

        // TODO ADDING BUTTON FOR REPORTS (INCOME, EXPENSE, AND SORT BY TYPE) 
        JButton reportsButton = new JButton("Report Page");
        reportsButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                showReportPagePanel();
        	}
        });
        reportsButton.setBounds(100, 332, 125, 28); 
        mainMenuPanel.add(reportsButton);
        
        // TODO ADDING BUTTONS FOR IMPORTING AND EXPORTING REPORTS
        JButton importReportButton = new JButton("Import Report");
        importReportButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null,"This button does nothing, yet! Add file chooser Functionality");        	}
        });
        importReportButton.setBounds(230, 332, 125, 28); 
        mainMenuPanel.add(importReportButton);
     
     // TODO ADDING BUTTONS FOR IMPORTING AND EXPORTING REPORTS
        JButton exportReportButton = new JButton("Export Report");
        exportReportButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null,"This button does nothing, yet! Add file chooser Functionality");
        	}
        });
        exportReportButton.setBounds(360, 332, 125, 28); 
        mainMenuPanel.add(exportReportButton);
        
    
    }
    

    private void initializeReportPagePanel() {
    	reportPagePanel = new JPanel();
    	reportPagePanel.setBounds(43, 31, 550, 500);
    	contentPane.add(reportPagePanel);
    	reportPagePanel.setLayout(null);
    	
    	// Adding JTextfields
    	JTextField filterInput = new JTextField();
    	filterInput.setBounds(260, 0, 125, 20);
    	reportPagePanel.add(filterInput);
    	
    	// Adding JList for Report Information 
		JList<String> reportList = new JList<>(reportListModel);
		reportList.setBounds(25, 30, 450, 400);
		reportPagePanel.add(reportList);

    	// Adding JButtons
    	 JButton incomeReportButton = new JButton("Income Report");
    	 incomeReportButton.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		JOptionPane.showMessageDialog(null,"This button does nothing, yet!");
         		reportListModel.addElement("Hello There, I am testing this");
         	}
         });
    	 incomeReportButton.setBounds(0, 0, 125, 20);
    	 reportPagePanel.add(incomeReportButton);
    	
    	 JButton expenseReportButton = new JButton("Expense Report");
    	 expenseReportButton.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		JOptionPane.showMessageDialog(null,"This button does nothing, yet!");
         	}
         });
    	 expenseReportButton.setBounds(130, 0, 125, 20);
    	 reportPagePanel.add(expenseReportButton);
    	 
    	 JButton filterButton = new JButton("Filter");
    	 filterButton.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		JOptionPane.showMessageDialog(null,"This button does nothing, yet!");
         	}
         });
    	 filterButton.setBounds(390, 0, 100, 20);
    	 reportPagePanel.add(filterButton);
    	 
    	 JButton menuButton = new JButton("Main Menu");
    	 menuButton.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		showMainMenuPanel();
         	}
         });
    	 menuButton.setBounds(0, 435, 100, 20);
    	 reportPagePanel.add(menuButton);
    	


    }
    

    private void showLoginPanel() {
    	loginPanel.setVisible(true);
    	registerPanel.setVisible(false);
        mainMenuPanel.setVisible(false);
        reportPagePanel.setVisible(false);
    }

    private void showRegisterPanel() {
        loginPanel.setVisible(false);
        registerPanel.setVisible(true);
        mainMenuPanel.setVisible(false);
        reportPagePanel.setVisible(false);

    }

    private void showMainMenuPanel() {
        loginPanel.setVisible(false);
        registerPanel.setVisible(false);
        mainMenuPanel.setVisible(true);
        reportPagePanel.setVisible(false);
        UpdateBalance();
        UpdateSavings();
    }
    
    private void showReportPagePanel() {
        loginPanel.setVisible(false);
        registerPanel.setVisible(false);
        mainMenuPanel.setVisible(false);
        reportPagePanel.setVisible(true);
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

    public void UpdateSavings() {

        for (int i = 0; i < AllUsers.size(); i++) {
            User currentUser = AllUsers.get(i);
            if (currentUser.username.equals(CurrentUser)) {

                txtMonthlySavings.setText(String.valueOf(expenserCalulator.updateMonthlySavings(AllUsers.get(i))));
                break; // Exit the loop once the current user is found
            }
        }
    }

}
