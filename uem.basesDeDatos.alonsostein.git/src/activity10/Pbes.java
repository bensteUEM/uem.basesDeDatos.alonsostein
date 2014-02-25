package activity10;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
	private Customer[] customers;
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
		setGuiStyles(); //use function for predefined GUI mods
		customers = new Customer[numberOfCustomers];
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

		setBounds(100, 100, 450, 500); // set the bounds of the mainframe
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
		// Added new Menu Item Style //TODO
		JMenuItem menCalcRev = new JMenuItem("Calculate Revenue");
		menCalcRev.addActionListener(this);

		// Import customer from text - same implementation as Search
		JButton btnCustFromText = new JButton("Import customers");
		btnCustFromText.addActionListener(this);
		// Added new Menu Item Style
		JMenuItem menImportCustomer = new JMenuItem("Import customers");
		menImportCustomer.addActionListener(this);

		// Export customer to text - same implementation as Search
		JButton btnCustToText = new JButton("Export customers");
		btnCustToText.addActionListener(this);
		// Added new Menu Item Style
		JMenuItem menExportCustomer = new JMenuItem("Export customers");
		menExportCustomer.addActionListener(this);

		// User Delete button by ID - same implementation as Search
		JButton btnDelete = new JButton("Delete User by ID");
		btnDelete.setMnemonic('d');
		btnDelete.addActionListener(this);

		// Positioning of the Buttons
		setLayout(new GridLayout(3, 1));

		// Search Panel
		JPanel pnlSearch = new JPanel();
		pnlSearch.setLayout(new BorderLayout());
		add(pnlSearch);
		pnlSearch.add(btnSearch, BorderLayout.WEST);
		pnlSearch.add(btnDelete, BorderLayout.EAST);
		pnlSearch.add(txtSearch, BorderLayout.CENTER);

		// Pay Panel
		JPanel pnlPay = new JPanel();
		pnlPay.setLayout(new BorderLayout());
		add(pnlPay);
		pnlPay.add(btnPay, BorderLayout.WEST);
		pnlPay.add(txtMoney, BorderLayout.CENTER);
		pnlPay.add(btnCustomersAboveRate, BorderLayout.EAST);

		// Function Panel
		JPanel pnlFunctions = new JPanel();
		pnlFunctions.setLayout(new FlowLayout());
		add(pnlFunctions);
		pnlFunctions.add(btnAddCustomer);
		pnlFunctions.add(btnAllCustomer);
		pnlFunctions.add(btnCompanyRev);
		pnlFunctions.add(btnCalcBalance);
		pnlFunctions.add(btnCalcRev);
		pnlFunctions.add(btnCustFromText);
		pnlFunctions.add(btnCustToText);

		// Create the menu bar.
		JMenuBar menuBar = new JMenuBar();
		// menuBar.setBackground(Color.DARK_GRAY);

		JMenu menuData = new JMenu("Data"); // Create the Menu Data
		menuData.add(menImportCustomer);
		menuData.add(menExportCustomer);
		menuBar.add(menuData); // Add menu to Main Menu

		JMenu menuRevenue = new JMenu("Revenue");
		menuRevenue.add(menCalcBalances);
		menuRevenue.add(menCalcRev);
		menuBar.add(menuRevenue); // Add menu to Main Menu

		JMenu menuAbout = new JMenu("About");
		menuAbout.add(menAllCustomer);
		menuBar.add(menuAbout); // Add menu to Main Menu

		pnlFunctions.add(menuBar); // DEBUG just simply show the menu
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
		// DEBUG only
		// System.out.println("PBES - performing Action for: " + sourceName);

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
		} else if (sourceName.contains("Import customers")) {
			// Conditions for Revenue Button
			// function that shows the company revenue
			this.onImportText(ae);
		} else if (sourceName.contains("Export customers")) {
			// Conditions for Revenue Button
			// function that shows the company revenue
			this.onExportText(ae);
		} else if (sourceName.contains("Delete")) {
			this.onDeleteByUserId(ae);
		} // END if for all buttons as source
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
			rate = Integer.parseInt(txtMoney.getText()); // get the input
															// text
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"No Valid minimum rate specified , using 0 instead");
			rate = 0;
		}

		GuiCustomerList list = new GuiCustomerList(
				this.getCustomersAboveRate(rate),
				"List of all customers above rate: " + rate); // shows list
																// of all
																// customers
																// and rates
		list.setVisible(true);

	}

	public void onShowAndCalculateCompanyRevenue(ActionEvent ae) {
		// revenue of each customer
		this.calculateAllBalances();
		Integer revenue = this.getCompanyRevenue();
		JOptionPane.showMessageDialog(this, " Your Revenue is:" + revenue
				+ " € not taking into accounts Cents");
	}

	public void onRevenue(ActionEvent ae) {
		Integer revenue = this.getCompanyRevenue();
		JOptionPane.showMessageDialog(this, " Your Revenue is:" + revenue
				+ " € not taking into accounts Cents");
	}

	/**
	 * Import all customers using the default file location of the DataFile
	 */
	public void onImportText(ActionEvent ae) {
		DataFile export = new DataFile(); // create a new file without a
											// customer object
		Customer[] importCustomers = export.importCustomer();
		if (importCustomers.length <= 50) {
			int counter = 0;
			this.customers = new Customer[50];
			for (Customer oneCustomer : importCustomers) {
				this.customers[counter] = oneCustomer;
				counter++;
			}
		} else {
			System.out.println("Warning trying to import too many customers");
		}
	}

	/**
	 * Export all customers using the default file location of the DataFile
	 */
	public void onExportText(ActionEvent ae) {
		DataFile export = new DataFile(); // create a new file without a
											// customer object
		export.exportCustomer(this.customers); // export the current set of
												// customers
	}

	public void onDeleteByUserId(ActionEvent ae) {
		Integer searchId = Integer.parseInt(this.txtSearch.getText());
		this.deleteCustomer(searchId);
	}

	@Override
	public boolean addCustomer(CustomerAbstract customer) {
		Integer position = 0;
		Integer newId;
		while (null != this.getCustomer(customer.getId())) { // as long as the
																// current
																// customer has
																// an id that
																// already
																// exists

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
		for (Customer compareCustomer : this.customers) { // iterate through all
			// customers
			if ((compareCustomer == null) && (position < this.customers.length)) // unused
																					// position
																					// within
																					// array
																					// size
																					// found
			{
				this.customers[position] = (Customer) customer; // save
																// customer
																// to empty
																// spot
				this.customerCount++;
				return true;
			} else {
				position++; // check next spot
			}
		} // end for
		return false; // no more space for more customers

	}

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
				this.customers[position] = (Customer) currentCustomer; // overwrite
																		// customer
																		// at
																		// this
																		// index
				return true; // stop iterating for more
			} else { // go to next customer if this one didn't match
				position++;
			}
		} // end for
		return false; // no more space for more customers
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
		for (Customer compareCustomer : this.customers) { // iterate through all
			// customers
			if (compareCustomer != null) {
				if (compareCustomer.getId().equals(searchId)) // customer is the
																// one
				{
					return compareCustomer;

				} // delete customer
			}// end null checking
		} // end for
		return null;
	}

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
		Integer position = 0;
		for (Customer compareCustomer : this.customers) { // iterate through all
															// customers
			if (compareCustomer.getId().equals(searchId)) // customer is the one
															// we
			{ // search
				System.out.println("Found customer" + compareCustomer.getId()
						+ " and will delete " + searchId + " at position "
						+ position);
				System.out.println("Deleting this user: " + compareCustomer);
				this.customers[position] = null; // delete customer
				this.customerCount--;
				return true; // stop procedure
			} else {
				position++; // increase to get next index
			}
		}// end of iterating through all customers
		return false; // ID was not found not successfull deleted
	}

	/**
	 * This function will return the cumulative total of all customer balances
	 * 
	 * @author benste
	 * @return Total Balance of all customers
	 */
	@Override
	public Integer getAllCustomerBalance() {
		Integer result = 0; // initialize result variable
		for (Customer compareCustomer : this.customers) { // iterate through all
															// customers
			if (compareCustomer != null) // customer exists
			{
				result += compareCustomer.getBalance();
			}// end if precondition customer exists
		}// end of iterating through all customers
		return result;
	}

	@Override
	/**
	 * This function does itterate through the classes array of customers and return a list of customers objects who do have their rate above the specified rate
	 * @author benste
	 */
	public ArrayList<Customer> getCustomersAboveRate(Integer maxRate) {
		ArrayList<Customer> results = new ArrayList<Customer>();
		results.clear();
		for (Customer compareCustomer : this.customers) {
			if (compareCustomer != null) // customer exists
			{
				if (compareCustomer.getRate() > maxRate)// customer exceeds rate
				{
					results.add(compareCustomer);// add customer to list
				}
			}// end if precondition customer exists
		}// end of iterating through all customers
		return results;
	}

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
				System.out.println(compareCustomer + " has a new balance of "
						+ compareCustomer.getBalance());// TODO Debug
			}// end if precondition customer exists
		}// end of iterating through all customers
	}

	/**
	 * get the total company revenue currently outstanding balances only
	 * 
	 * @author benste
	 * @return Total Revenue in Cents
	 */
	@Override
	public Integer getCompanyRevenue() {
		// TODO Include money that has been paid by customer
		Integer paid = 0; // temporary var only - needs to be class var once
							// payment is implemented
		Integer outstanding = 0; // initialize outstanding
		for (Customer compareCustomer : this.customers) { // iterate through all
			// customers
			if (compareCustomer != null) // customer exists
			{
				outstanding += compareCustomer.getBalance();
				// add the outstanding balance to current balance
			}// end if precondition customer exists
		}// end of iterating through all customers
		return (paid - outstanding);
	}
}
