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
import activity19.SQLiteStorage;

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

	protected static final long serialVersionUID = 7954557041637449001L;
	protected JPanel contentPane;
	protected JTextField txtSearch;
	protected JTextField txtMoney;
	protected ArrayList<Customer> customers;

	protected BigDecimal b = new BigDecimal(0); // store the minimum balance
	final Color UEMCOLOR = new Color(143, 27, 39);

	// define menu items
	private JMenuItem menImportCustomer;
	private JMenuItem menExportCustomer;
	private JMenuItem menExportCustomerExcel;

	private JMenuItem menMinimumBalance;
	private JMenuItem menCalcBalances;
	private JMenuItem menCalcRev;
	private JMenuItem menAllCustomer;
	private JMenuItem menInitDb;

	// create arrayList of buttons
	private ArrayList<JButton> buttonElements;

	/**
	 * Launch the application
	 * 
	 * @author Maren
	 */
	public static void main(String[] args) { // Main Method that launches the
												// application
		Pbes mainFrame = new Pbes(); // Create new Gui program
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
	public Pbes() { // Constructor
		setGuiStyles(); // use function for predefined GUI mods
		createElements(); // use function to create elements of the GUI
		fillInformation(); // fill the information of the elements of the gui
		arrangeInLayouts(); // arrange the elements of the gui in Layouts

		customers = new ArrayList<Customer>();
		// initialize empty customer array with predefined count

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

	}

	/**
	 * Create elements of the GUi in a modular way
	 * 
	 * @author Luis
	 */
	public void createElements() { // Create the elements of the GUI

		contentPane = new JPanel(); // creation of content Pane
		txtSearch = new JTextField(); // Creation of Textfield for Input
		txtMoney = new JTextField();// Creation of Textfield for Money
		// User Search Button by ID
		buttonElements = new ArrayList<JButton>();

		// create menu items
		menAllCustomer = new JMenuItem("Show all Customers");
		menCalcBalances = new JMenuItem("Calculate Balances");
		menCalcRev = new JMenuItem("Calculate Revenue");
		menImportCustomer = new JMenuItem("Import customers from CSV");
		menExportCustomer = new JMenuItem("Export customers to CSV");
		menExportCustomerExcel = new JMenuItem("Export customers to Excel2013");
		menMinimumBalance = new JMenuItem("Import minimum balance from Excel");
		menInitDb = new JMenuItem("Initiate Database");

		// add buttons to the arrayList of buttons
		buttonElements.add(new JButton("Search User by ID"));
		buttonElements.add(new JButton("Call"));
		buttonElements.add(new JButton("Monthly bill"));
		buttonElements.add(new JButton("Add a new Customer"));
		buttonElements.add(new JButton("Show all Customers"));
		buttonElements.add(new JButton(
				"Compute Balances & Show Company Revenue"));
		buttonElements.add(new JButton("Show Customers Above Rate"));
		buttonElements.add(new JButton("Calculate Balance"));
		buttonElements.add(new JButton("Calculate Revenue"));
		buttonElements.add(new JButton("Import customers"));
		buttonElements.add(new JButton("Export customers to CSV"));
		buttonElements.add(new JButton("Delete User by ID"));

	}

	/**
	 * Fill information of the elements of the GUi in a modular way
	 * 
	 * @author Luis
	 */
	public void fillInformation() { // Fill information of the elements of the
									// GUI
		setBounds(100, 100, 1000, 500); // set the bounds of the mainframe
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // set Borders
		setContentPane(contentPane); // set the content pane

		for (int i = 0; i < buttonElements.size(); i++) { // add action
															// listeners for all
															// buttons iterating
															// through the
															// arrayList of
															// buttons
			buttonElements.get(i).addActionListener(this);
		}
		txtSearch.setToolTipText("enter the User ID here"); // set tooltip
		txtSearch.setText("0"); // set Text to value of 0
		txtSearch.setColumns(10); // Clums for Textfield
		txtMoney.setToolTipText("enter amount of Money in Cents"); // set //
																	// tooltip
		txtMoney.setColumns(10); // set width
		// add actionListeners for the menu items
		menImportCustomer.addActionListener(this);
		menExportCustomer.addActionListener(this);
		menExportCustomerExcel.addActionListener(this);
		menMinimumBalance.addActionListener(this);
		menAllCustomer.addActionListener(this);
		menCalcBalances.addActionListener(this);
		menCalcRev.addActionListener(this);
		menInitDb.addActionListener(this);

	}

	/**
	 * Arrange the elements of the GUI in a modular way
	 * 
	 * @author Luis
	 */
	public void arrangeInLayouts() { // arrange the elements of the GUI in
										// layouts
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
		pnlTopRight.setLayout(new GridLayout(1, 4));
		pnlMain.add(pnlTopRight);

		pnlTopRight.add(buttonElements.get(0)); // search by id
		pnlTopRight.add(buttonElements.get(11)); // delete
		pnlTopRight.add(buttonElements.get(3)); // addcustomer
		pnlTopRight.add(buttonElements.get(1)); // call
		// pnlTopRight.add(buttonElements.get(2)); // monthlyBill

		// subpanel bot-left
		JPanel pnlBotLeft = new JPanel();
		pnlBotLeft.setLayout(new GridLayout(1, 1));
		pnlBotLeft.add(txtMoney);
		pnlMain.add(pnlBotLeft);

		// subpanel bot-right
		JPanel pnlBotRight = new JPanel();
		pnlBotRight.setLayout(new GridLayout(1, 2));
		pnlMain.add(pnlBotRight);
		pnlBotRight.add(buttonElements.get(6)); // show customers above rate

		// Create the menu bar.
		JMenuBar menuBar = new JMenuBar();
		// menuBar.setBackground(Color.DARK_GRAY);

		JMenu menuData = new JMenu("Data"); // Create the Menu Data
		menuData.add(menImportCustomer);
		menuData.add(menExportCustomer);
		menuData.add(menExportCustomerExcel);
		menuData.add(menMinimumBalance);
		menuData.add(menInitDb);
		menuBar.add(menuData); // Add menu to Main Menu

		JMenu menuRevenue = new JMenu("Revenue");
		menuRevenue.add(menCalcBalances);
		menuRevenue.add(menCalcRev);
		menuBar.add(menuRevenue); // Add menu to Main Menu

		JMenu menuAbout = new JMenu("About");
		menuAbout.add(menAllCustomer);
		menuBar.add(menuAbout); // Add menu to Main Menu

		setJMenuBar(menuBar); // DEBUG just simply show the menu
	}

	/**
	 * Overwrite Styles of Items for GUI Template
	 * 
	 * This uses a procedure described here:
	 * http://java-demos.blogspot.com.es/2013
	 * /01/set-selection-background-foreground-for-jmenuitem.html
	 */
	public void setGuiStyles() { // set the style of the GUI
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
		} else if (sourceName.contains("Import minimum balance from Excel")) {
			this.onLoadMinimumBalance(ae);
		} else if (sourceName.contains("Initiate Database")) {
			this.onInitiateDatabase(ae);
		} else if (sourceName.contains("Delete")) {
			this.onDeleteByUserId(ae);
		} else if (sourceName.contains("Call")) {
			this.onCall(ae);
		} else if (sourceName.contains("Monthly bill")) {
			this.onCalculateMonthlyBill(ae);
		}// END if for all buttons as source
	}// End actionPerformed

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
			editor = new GuiUserModificator(this, this.getCustomer(searchId));
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
		System.out.println("Company reveneu big decimal in on revenue is = "
				+ this.getCompanyRevenue());
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
		for (CustomerAbstract oneCustomer : export.importCustomer()) {
			if (this.addCustomer(oneCustomer)) {
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
		export.exportCustomer(this.getCustomers());
		// export the current set of customers
	} // end onExportText

	/**
	 * Export all customers using the default file location of the DataFile
	 */
	public void onExportExcel(ActionEvent ae) {
		DataFile export = new DataFile(); // create a new file without a
											// customer object
		export.exportCustomerExcel2013(this.getCustomers());
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

			DataFile bill = new DataFile("");

			// SQL part
			// TODO - currently outside of specification but future improvement
			// of multiple Bills

			bill.setFileName("Bill-SQLID-"
					+ this.getCustomer(searchId).getName());

			JOptionPane.showMessageDialog(this, bill
					.exportCustomerBill((Customer) this.getCustomer(searchId)));

			JOptionPane.showMessageDialog(this,
					"User Bill has been saved on the Filesystem");
		} else {// user not found
			JOptionPane.showMessageDialog(this, "User with ID: " + searchId
					+ " does not exist yet");
			// message when customer does not exist
		} // end checking existance of user
	} // end onCalculateMonthlyBill

	public void onLoadMinimumBalance(ActionEvent ae) {
		DataFile d = new DataFile("MinimumBalance");
		this.b = d.importMinimumBalanceExcel2013();
		JOptionPane.showMessageDialog(this, "Minimum balance loaded");
	}

	public void onInitiateDatabase(ActionEvent ae) {
		String database = "PBESDatabase";
		SQLiteStorage dbStorage = new SQLiteStorage(database);
		dbStorage.init();
		JOptionPane.showMessageDialog(this, "Database initiated");
	}

	/**
	 * Method which adds a Customer into the local data storage and also sets
	 * its minimum Balance
	 * 
	 * @param customer
	 * @return
	 */
	public boolean addCustomer(CustomerAbstract customer) {
		Integer newId;
		customer.setMinBalance(b); // set minimum balance for customer once it
									// is read from excel
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
		for (Customer compareCustomer : this.getCustomers()) { // iterate
																// through all
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

	@Override
	public ArrayList<CustomerAbstract> getCustomers(Integer searchId) {
		return getCustomersAboveRate(Integer.MIN_VALUE);
	}

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
		for (Customer compareCustomer : this.getCustomers()) { // iterate
																// through all
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
		for (Customer compareCustomer : this.getCustomers()) {
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
		for (Customer compareCustomer : this.getCustomers()) { // iterate
																// through all
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
	public ArrayList<CustomerAbstract> getCustomersAboveRate(Integer maxRate) {
		ArrayList<CustomerAbstract> results = new ArrayList<CustomerAbstract>();
		for (Customer compareCustomer : this.getCustomers()) {
			if (compareCustomer != null) // customer exists
			{
				if (compareCustomer.getRate() > maxRate)// customer exceeds rate
				{
					results.add((CustomerAbstract) compareCustomer);// add
																	// customer
																	// to list
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
		for (Customer compareCustomer : this.getCustomers()) { // iterate
																// through all
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
		BigDecimal outstanding = new BigDecimal(0.00, new MathContext(3)); // initialize
																			// outstanding
		for (Customer compareCustomer : this.getCustomers()) { // iterate
																// through all
			// customers
			if (compareCustomer != null) // customer exists
			{
				outstanding = outstanding.add(compareCustomer.getBalance());
				// add the outstanding balance to current balance
			}// end if precondition customer exists
		}// end of iterating through all customers
		return (paid.add(outstanding));
	}// end getCompanyRevenue();

	public ArrayList<Customer> getCustomers() {
		return this.customers;
	}
} // end class

