package activity10;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import activity13.GuiCallPlacement;

/**
 * Main Class for the activities 8 and 10 Start this program here, it will guide
 * you through all steps grafically
 * 
 * @implements minimal definition from PbesAbstract
 * @author benste & Luis
 * 
 */
public class Pbes extends PbesAbstract implements ActionListener {
	/* Some Initial Values */
	private static final long serialVersionUID = 7954557041637449001L;
	private JPanel contentPane;
	JTextField txtSearch;
	private JTextField txtMoney;
	private Integer customerCount;
	private ArrayList<Customer> customers;
	/* private Customer[] customerToImport; //@LUIS - not needed */
	final Color UEMCOLOR = new Color(143, 27, 39);

	/**
	 * Launch the application
	 * 
	 * @author Maren
	 */
	public static void main(String[] args) { // Main Method that launches the
												// application
		Integer maxCustomers = 50;
		/*
		 * Starting from Activity 10 max number is fixed to 50 the following
		 * code is deprecated try { GuiFilter a = new GuiFilter(0); while
		 * (maxCustomers == 0) { maxCustomers = a.getNumber();
		 * Thread.sleep(200); // waits a time in miliseconds }
		 */
		Pbes mainFrame = new Pbes(maxCustomers); // Create new Gui program
													// instance
		mainFrame.setVisible(true); // make Gui visible
		/*
		 * Also part of the old code ... } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
	}// end main method

	/**
	 * Create the frame.
	 * 
	 * @author Maren
	 */
	public Pbes(Integer numberOfCustomers) { // Constrcutor
		super(numberOfCustomers); // super constructor
		setGuiStyles(); // use function for predefined GUI mods
		customers = new ArrayList<Customer>(numberOfCustomers);
		// initialize empty customer array with predefined count
		this.customerCount = 0; // initialize customer count

		// Frame and GUI Setup
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // constant that it
														// exits
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				// TODO this can be used to export before closing
				System.out.println("Safe Shutdown Function Excecuted");// TODO
																		// DEBUG
				System.exit(0);
			} // end embedded function
		});// end of window listener

		setBounds(100, 100, 1000, 500); // set the bounds of the mainframe
		contentPane = new JPanel(); // creation of content Pane
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // set Borders
		setContentPane(contentPane); // set the content pane

		txtSearch = new JTextField(); // Creation of Textfield for Input
		txtSearch.setToolTipText("enter the User ID here"); // set tooltip
		txtSearch.setText("0"); // set Text to value of 0
		txtSearch.setColumns(10); // Clums for Textfield

		txtMoney = new JTextField();// Creation of Textfield for Money
		txtMoney.setToolTipText("enter amount of Money in Cents"); // set
																	// tooltip
		txtMoney.setColumns(10); // set width

		// User Search Button by ID
		JButton btnSearch = new JButton("Search User by ID");
		// creation of new Button and its appearance
		btnSearch.setMnemonic('s'); // set key shortcut
		btnSearch.addActionListener(this); // link action

		// User Call Button by ID
		JButton btnCall = new JButton("Call");
		// creation of new Button and its appearance
		btnCall.setMnemonic('c'); // set key shortcut
		btnCall.addActionListener(this); // link action

		// User Monthly bill Button by ID
		JButton btnMonthlyBill = new JButton("Monthly bill");
		// creation of new Button and its appearance
		btnMonthlyBill.setMnemonic('m'); // set key shortcut
		btnMonthlyBill.addActionListener(this); // link action

		JButton btnPay = new JButton("Pay by User ID");
		btnPay.setSize(40, 40);
		btnPay.setEnabled(false); // Disable additional feature for evaluation
		btnPay.addActionListener(this);

		// Button to add a new customer - same implementation as Search
		JButton btnAddCustomer = new JButton("Add a new Customer");
		btnAddCustomer.setEnabled(true);
		btnAddCustomer.addActionListener(this);

		// Show all customer Button - same implementation as Search
		JButton btnAllCustomer = new JButton("Show all Customers");
		btnAllCustomer.addActionListener(this);
		// Added new Menu Item Style
		JMenuItem menAllCustomer = new JMenuItem("Show all Customers");
		menAllCustomer.addActionListener(this);

		// Button that shows customer revenue - same implementation as Search
		JButton btnCompanyRev = new JButton(
				"Compute Balances & Show Company Revenue");
		btnCompanyRev.addActionListener(this);

		// Button CustomersAboveRate - same implementation as Search
		JButton btnCustomersAboveRate = new JButton("Show Customers Above Rate");
		btnCustomersAboveRate.addActionListener(this);

		// Button CalcBalance - same implementation as Search
		JButton btnCalcBalance = new JButton("Calculate Balance");
		btnCalcBalance.addActionListener(this);
		// Added new Menu Item Style //TODO
		JMenuItem menCalcBalances = new JMenuItem("Calculate Balances");
		menCalcBalances.addActionListener(this);

		// Button CalcRev - same implementation as Search
		JButton btnCalcRev = new JButton("Calculate Revenue");
		btnCalcRev.addActionListener(this);
		// Added new Menu Item Style
		JMenuItem menCalcRev = new JMenuItem("Calculate Revenue");
		menCalcRev.addActionListener(this);

		// Import customer from text - same implementation as Search
		JButton btnCustFromText = new JButton("Import customers");
		btnCustFromText.addActionListener(this);
		// Added new Menu Item Style
		JMenuItem menImportCustomer = new JMenuItem("Import customers from CSV");
		menImportCustomer.addActionListener(this);

		// Export customer to text - same implementation as Search
		JButton btnCustToText = new JButton("Export customers to CSV");
		btnCustToText.addActionListener(this);

		// Added new Menu Item Style
		JMenuItem menExportCustomer = new JMenuItem("Export customers to CSV");
		menExportCustomer.addActionListener(this);
		// Added new Menu Item Style
		JMenuItem menExportCustomerExcel = new JMenuItem(
				"Export customers to Excel2013");
		menExportCustomerExcel.addActionListener(this);

		// User Delete button by ID - same implementation as Search
		JButton btnDelete = new JButton("Delete User by ID");
		btnDelete.setMnemonic('d');
		btnDelete.addActionListener(this);

		// NEW GUI DISTRIBUTION
		setLayout(new BorderLayout());
		// main grid layout panel
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new GridLayout(2, 2));
		contentPane.add(pnlMain, BorderLayout.CENTER);

		// subpanel top-left
		JPanel pnlTopLeft = new JPanel();
		pnlTopLeft.setLayout(new GridLayout(1, 1));
		pnlTopLeft.add(txtSearch);
		pnlMain.add(pnlTopLeft);

		// subpanel top-right
		JPanel pnlTopRight = new JPanel();
		pnlTopRight.setLayout(new GridLayout(1, 3));
		pnlMain.add(pnlTopRight);
		pnlTopRight.add(btnSearch);
		pnlTopRight.add(btnDelete);
		pnlTopRight.add(btnAddCustomer);
		pnlTopRight.add(btnCall);
		pnlTopRight.add(btnMonthlyBill);

		// subpanel bot-left
		JPanel pnlBotLeft = new JPanel();
		pnlBotLeft.setLayout(new GridLayout(1, 1));
		pnlBotLeft.add(txtMoney);
		pnlMain.add(pnlBotLeft);

		// subpanel bot-right
		JPanel pnlBotRight = new JPanel();
		pnlBotRight.setLayout(new GridLayout(1, 3));
		pnlMain.add(pnlBotRight);
		pnlBotRight.add(btnCustomersAboveRate);
		pnlBotRight.add(btnPay);

		// Create the menu bar.
		JMenuBar menuBar = new JMenuBar();
		// menuBar.setBackground(Color.DARK_GRAY);

		JMenu menuData = new JMenu("Data"); // Create the Menu Data
		menuData.add(menImportCustomer);
		menuData.add(menExportCustomer);
		menuData.add(menExportCustomerExcel);
		menuBar.add(menuData); // Add menu to Main Menu

		JMenu menuRevenue = new JMenu("Revenue");
		menuRevenue.add(menCalcBalances);
		menuRevenue.add(menCalcRev);
		menuBar.add(menuRevenue); // Add menu to Main Menu

		JMenu menuAbout = new JMenu("About");
		menuAbout.add(menAllCustomer);
		menuBar.add(menuAbout); // Add menu to Main Menu

		setJMenuBar(menuBar); // DEBUG just simply show the menu
	} // End constructor

	/**
	 * Overwrite Styles of Items for GUI
	 * 
	 * This uses a procedure described here:
	 * http://java-demos.blogspot.com.es/2013
	 * /01/set-selection-background-foreground-for-jmenuitem.html
	 */
	public void setGuiStyles() {
		UIManager.put("MenuBar.background", Color.DARK_GRAY);

		UIManager.put("Menu.foreground", Color.WHITE);
		UIManager.put("Menu.border", new EmptyBorder(10, 10, 10, 10));
		UIManager.put("Menu.selectionBackground", Color.BLACK);
		UIManager.put("Menu.selectionForeground", Color.LIGHT_GRAY);

		UIManager.put("MenuItem.selectionBackground", Color.WHITE);
		UIManager.put("MenuItem.selectionForeground", UEMCOLOR);
		UIManager.put("MenuItem.border", new EmptyBorder(10, 10, 10, 10));

		UIManager.put("Button.background", UEMCOLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.select", Color.DARK_GRAY);
		UIManager.put("Button.border", new EmptyBorder(10, 10, 10, 10));
	}

	/**
	 * method for linking the buttons to actions
	 */
	@Override
	public void actionPerformed(ActionEvent ae) { // ActionList
		String sourceName = ae.getActionCommand(); // get Sender name
		// System.out.println("PBES - performing Action for: " + sourceName);
		// //DEBUG only

		// IF Cases for all Buttons which have actions
		if (sourceName.contains("Search User by ID")) {
			// Conditions for the button search by user ID
			this.onSearchId(ae);
		} else if (sourceName.contains("Add a new Customer")) {
			// condition to react on the add new customer button try to
			// create an empty new customer and add it to our list of customers
			this.onAddCustomer(ae);
		} else if (sourceName.contains("Pay by User ID")) {
			// Conditions for Pay by User ID Button
			this.onPayByUserId(ae);
		} else if (sourceName.contains("Show all Customers")) {
			// Conditions for Show all Customer Button
			this.onShowCustomer(ae);
		} else if (sourceName.contains("Show Customers Above Rate")) {
			// Conditions for show customers rate Button
			this.onCustomerAboveRate(ae);
		} else if (sourceName.contains("Company Revenue")) {
			// Conditions for Revenue Button and balances
			this.onShowAndCalculateCompanyRevenue(ae);
		} else if (sourceName.contains("Calculate Balance")) {
			// Conditions for Calculate Balance
			// function that shows the revenue of each customer
			this.calculateAllBalances();
		} else if (sourceName.contains("Calculate Revenue")) {
			// Conditions for Revenue Button
			// function that shows the company revenue
			this.onRevenue(ae);
		} else if (sourceName.contains("Import customers from CSV")) {
			this.onImportText(ae);
		} else if (sourceName.contains("Export customers to Excel2013")) {
			this.onExportExcel(ae);
		} else if (sourceName.contains("Export customers to CSV")) {
			this.onExportText(ae);
		} else if (sourceName.contains("Delete")) {
			this.onDeleteByUserId(ae);
		} else if (sourceName.contains("Call")) {
			this.onCall(ae);
		} else if (sourceName.contains("Monthly bill")) {
			this.onCalculateMonthlyBill(ae);
		}// END if for all buttons as source
	} // End actionPerformed

	/**
	 * handle the search ID Button
	 * 
	 * @author benste
	 * @param new Customer Object
	 */
	public void onSearchId(ActionEvent ae) {
		Integer searchId = Integer.parseInt(this.txtSearch.getText());
		@SuppressWarnings("unused")
		GuiUserModificator editor = null; // init Modificator
		if (this.getCustomer(searchId) != null) { // check that user exists
			editor = new GuiUserModificator(this,
					(Customer) this.getCustomer(searchId));
			// create a new editor with the user found
		} else {// user not found
			JOptionPane.showMessageDialog(this, "User with ID: " + searchId
					+ " does not exist yet");
			// message when customer does not exist
		} // end checking existance of user
	} // end SearchId()

	/**
	 * 
	 * @param Luis
	 */
	public void onAddCustomer(ActionEvent ae) {
		Customer newCustomer = new Customer("", "",
				Integer.parseInt(this.txtSearch.getText())); // function to
																// add new
																// customer

		if (!(this.addCustomer(newCustomer))) {
			JOptionPane
					.showMessageDialog(
							this,
							"<html>Something went wrong when trying to save a new user <br> you might have tried to exceed your user maximum</html>");
		} else {
			new GuiUserModificator(this, newCustomer); // this will run the
														// Modificator
														// hiding this
														// window until
														// action was
														// successful
		}
	}

	public void onPayByUserId(ActionEvent ae) {
		String userIdText = txtSearch.getText(); // get the text form
		// textfield
		Integer userId = Integer.parseInt(userIdText);
		Customer myCustomer = (Customer) this.getCustomer(userId);
		GuiFilter paymentQuestion = new GuiFilter(2);
		Integer cash = paymentQuestion.getNumber(); // works when luis
		// made getmoney method
		JOptionPane.showMessageDialog(
				this,
				"Your change is:"
						+ Integer.toString(myCustomer.payBalance(cash)));
	}

	public void onShowCustomer(ActionEvent ae) {
		GuiCustomerList list = new GuiCustomerList(
				this.getCustomersAboveRate(Integer.MIN_VALUE),
				"List of all customers"); // opens list of all customers
		list.setVisible(true);
	}

	public void onCustomerAboveRate(ActionEvent ae) {
		Integer rate;
		try {
			rate = Integer.parseInt(txtMoney.getText());
			// get the input text
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"No Valid minimum rate specified , using 0 instead");
			rate = 0;
		} // end try
		GuiCustomerList list = new GuiCustomerList(
				this.getCustomersAboveRate(rate),
				"List of all customers above rate: " + rate);
		// shows list of all customers and rates
		list.setVisible(true);
	}// end onCustomerAboveRate

	public void onRevenue(ActionEvent ae) {
		String revenue = this.getCompanyRevenue().toString();
		System.out.println("Company reveneu big decimal in on revenue is = "+this.getCompanyRevenue());
		JOptionPane.showMessageDialog(this, " Your Revenue is:" + revenue
				+ " EURO");
	} // end OnRevenue
	
	public void onShowAndCalculateCompanyRevenue(ActionEvent ae) {
		// revenue of each customer
		this.calculateAllBalances();
		this.onRevenue(ae);
	} // end onShowAndCalcCompRev

	/**
	 * Import all customers using the default file location of the DataFile
	 */
	public void onImportText(ActionEvent ae) {
		DataFile export = new DataFile(); // create a new file without a
											// customer object
		for (Customer oneCustomer : export.importCustomer()) {
			if (addCustomer(oneCustomer)) {
				// System.out.println("Following Customer added: " +
				// oneCustomer);
			} // end if
		} // end for addin customer
	}// end onImportText()

	/**
	 * Export all customers using the default file location of the DataFile
	 */
	public void onExportText(ActionEvent ae) {
		DataFile export = new DataFile(); // create a new file without a
											// customer object
		export.exportCustomer(this.customers);
		// export the current set of customers
	} // end onExportText

	/**
	 * Export all customers using the default file location of the DataFile
	 */
	public void onExportExcel(ActionEvent ae) {
		DataFile export = new DataFile(); // create a new file without a
											// customer object
		export.exportCustomerExcel2013(this.customers);
		// export the current set of customers
	} // end onExportExcel

	public void onDeleteByUserId(ActionEvent ae) {
		Integer searchId = Integer.parseInt(this.txtSearch.getText()); // get
																		// userID
		this.deleteCustomer(searchId); // delete user
	} // end onDeleteByUserId

	public void onCall(ActionEvent ae) {
		Integer searchId = Integer.parseInt(this.txtSearch.getText()); // getuserID
		@SuppressWarnings("unused")
		GuiCallPlacement editor = null; // init Modificator
		if (this.getCustomer(searchId) != null) { // check that user exists
			editor = new GuiCallPlacement(this,
					(Customer) this.getCustomer(searchId));
			// create a new editor with the user found
		} else {// user not found
			JOptionPane.showMessageDialog(this, "User with ID: " + searchId
					+ " does not exist yet");
			// message when customer does not exist
		} // end checking existance of user
	} // end onCall

	public void onCalculateMonthlyBill(ActionEvent ae) {
		Integer searchId = Integer.parseInt(this.txtSearch.getText());
		if (this.getCustomer(searchId) != null) { // check that user exists
			DataFile bill = new DataFile();
			bill.setFileName("Billing_info");
			bill.exportCustomerBill((Customer) this.getCustomer(searchId));
			JOptionPane.showMessageDialog(this,
					"User Bill has been saved on the Filesystem");
		} else {// user not found
			JOptionPane.showMessageDialog(this, "User with ID: " + searchId
					+ " does not exist yet");
			// message when customer does not exist
		} // end checking existance of user
	} // end onCalculateMonthlyBill

	/**
	 * Method which adds a Customer into the local data storage and also sets
	 * its minimum Balance
	 * 
	 * @param customer
	 * @return
	 */
	public boolean addCustomer(Customer customer) {
		Integer newId;

		// Read minimum Balance and Save to Customer
		DataFile d = new DataFile("MinimumBalance");
		customer.setMinBalance(d.importMinimumBalanceExcel2013());
		// System.out.println("User added to DataStore with min balance "+customer.getMinBalance());

		// as long as the current customer has an id that already exists
		while (null != this.getCustomer(customer.getId())) {
			try { // try to ask user for alternative ID
				newId = Integer
						.parseInt(JOptionPane
								.showInputDialog(this,
										"This ID already exists or has a problematic format please choose a new one"));
			} catch (Exception e) { // reset ID to original choice
				newId = customer.getId(); // in case ID was not valid format
			}
			((Customer) customer).setId(newId); // set a new ID
		}
		this.customers.add((Customer) customer);
		// save customer to empty spot
		this.customerCount++;
		return true;
	} // end addCustomer()

	/**
	 * save a Customer based on it's existing ID do overwrite existing, no
	 * success if didn't exist
	 * 
	 * @author benste
	 * @param currentCustomer
	 *            Customer object to be changed
	 * @return success of overwriting existing item
	 */
	@Override
	public boolean saveCustomer(CustomerAbstract currentCustomer) {
		Integer position = 0; // initialize iteration variable
		for (Customer compareCustomer : this.customers) { // iterate through all
			// customers
			if (compareCustomer == null) {
				// skip empty customers
			} else if (compareCustomer.getId().equals(currentCustomer.getId())) // customer
			{
				this.customers.set(position, (Customer) currentCustomer);
				// overwrite customer at this index
				return true; // stop iterating for more
			} else { // go to next customer if this one didn't match
				position++;
			} // end IF of save or seek for customer
		} // end for
		System.out.println("WARNING: Customer was not found when saving");
		return false; // no more space for more customers
	} // end saveCustomer()

	/**
	 * get a specific customer based on its ID
	 * 
	 * @author benste
	 * @param ID
	 *            of the customer which is requested
	 * @return Customer Object OR NullPointerException
	 */
	@Override
	public CustomerAbstract getCustomer(Integer searchId) {
		for (Customer compareCustomer : this.customers) { // iterate through all
			// customers
			if (compareCustomer != null) {
				if (compareCustomer.getId().equals(searchId))
				// customer is the one
				{
					return compareCustomer;
				} // delete customer
			}// end null checking
		} // end for
		return null;
	} // end getCustomer()

	/**
	 * Delete a Customer based on his ID
	 * 
	 * @param searchId
	 *            Number of the user in the system (not necessarily the index in
	 *            the customers array)
	 * @return whether the customer was successfully deleted
	 * @author benste
	 */
	public boolean deleteCustomer(Integer searchId) {
		for (Customer compareCustomer : this.customers) {
			// iterate through all customers
			if (compareCustomer.getId().equals(searchId))
			// customer is the one we
			{
				JOptionPane.showMessageDialog(this,
						"This user has been deleted: " + compareCustomer);
				return this.customers.remove(compareCustomer);
				// delete customer
			} // end IF of customer found or not
		}// end of iterating through all customers
		return false; // ID was not found not successfull deleted
	} // end deleteCustomer()

	/**
	 * This function will return the cumulative total of all customer balances
	 * 
	 * @author benste
	 * @return Total Balance of all customers
	 */
	@Override
	public BigDecimal getAllCustomerBalance() {
		BigDecimal result = new BigDecimal(0, new MathContext(3,
				RoundingMode.HALF_UP)); // initialize result variable
		for (Customer compareCustomer : this.customers) { // iterate through all
															// customers
			if (compareCustomer != null) // customer exists
			{
				result.add(compareCustomer.getBalance());
			}// end if precondition customer exists
		}// end of iterating through all customers
		return result;
	} // end getAllCustomerBalance()

	@Override
	/**
	 * This function does itterate through the classes array of customers and return a list of customers objects who do have their rate above the specified rate
	 * @author benste
	 */
	public ArrayList<Customer> getCustomersAboveRate(Integer maxRate) {
		ArrayList<Customer> results = new ArrayList<Customer>();
		for (Customer compareCustomer : this.customers) {
			if (compareCustomer != null) // customer exists
			{
				if (compareCustomer.getRate() > maxRate)// customer exceeds rate
				{
					results.add(compareCustomer);// add customer to list
				}// end if
			}// end if precondition customer exists
		}// end of iterating through all customers
		return results;
	} // End getCustomersAboveRate()

	/**
	 * Calculate all customers balances
	 * 
	 * @author benste
	 */
	@Override
	public void calculateAllBalances() {
		for (Customer compareCustomer : this.customers) { // iterate through all
															// customers
			if (compareCustomer != null) // customer exists
			{
				compareCustomer.setBalance(); // calculate Balance for all
												// customers
			}// end if precondition customer exists
		}// end of iterating through all customers
	} // end calculate Balance

	/**
	 * get the total company revenue currently outstanding balances only
	 * 
	 * @author benste
	 * @return Total Revenue in Cents
	 */
	@Override
	public BigDecimal getCompanyRevenue() {
		// TODO Include money that has been paid by customer
		BigDecimal paid = new BigDecimal(0, new MathContext(3,
				RoundingMode.HALF_UP)); // temporary var only - needs to be
										// class var once
		// payment is implemented
		BigDecimal outstanding = new BigDecimal(0.00, new MathContext(3)); // initialize outstanding
		for (Customer compareCustomer : this.customers) { // iterate through all
			// customers
			if (compareCustomer != null) // customer exists
			{
				outstanding = outstanding.add(compareCustomer.getBalance());				
				// add the outstanding balance to current balance
			}// end if precondition customer exists
		}// end of iterating through all customers
		return (paid.add(outstanding));
	}// end getCompanyRevenue();
} // end class
