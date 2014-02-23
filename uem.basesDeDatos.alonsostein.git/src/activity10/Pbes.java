package activity10;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Pbes extends PbesAbstract implements ActionListener {

	private static final long serialVersionUID = 7954557041637449001L;
	private JPanel contentPane;
	JTextField txtSearch;
	private JTextField txtMoney;
	private Integer customerCount;
	private Customer[] customers;
	private Customer[] customerToImport;
	final Color UEMCOLOR = new Color(143, 27, 39);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { // Main Method that launches the
												// application
		Integer maxCustomers = 0;
		try {
			GuiFilter a = new GuiFilter(0);

			while (maxCustomers == 0) {
				maxCustomers = a.getNumber();
				Thread.sleep(200); // waits a time in miliseconds

			}
			Pbes mainFrame = new Pbes(maxCustomers);
			mainFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public Pbes(Integer numberOfCustomers) { // Constrcutor
		super(numberOfCustomers);

		customers = new Customer[numberOfCustomers];// initialize empty customer
													// array
		this.customerCount = 0; // initialize customer count

		// Frame and GUI Setup
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // constant that it
														// exits
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel(); // creation of content Pane
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // set Borders
		setContentPane(contentPane);

		txtSearch = new JTextField(); // Creation fo Tetfield for Input
		txtSearch.setToolTipText("enter the User ID here");
		txtSearch.setText("0"); // set Text to value of 0
		txtSearch.setColumns(10); // Clums for Textfield

		txtMoney = new JTextField();
		txtMoney.setToolTipText("enter amount of Money in Cents");
		txtMoney.setColumns(10);

		// User Search Button by ID
		JButton btnSearch = new JButton("Search User by ID"); // creation of new
																// Button and
																// its
																// appearance
		btnSearch.setMnemonic('s');
		btnSearch.addActionListener(this);
		btnSearch.setBackground(Color.DARK_GRAY);
		btnSearch.setForeground(Color.WHITE);

		// Pay Button by ID
		JButton btnPay = new JButton("Pay by User ID");
		btnPay.setSize(40, 40);
		btnPay.setEnabled(false); // Disable additional feature for evaluation
		btnPay.addActionListener(this);
		btnPay.setBackground(Color.DARK_GRAY);
		btnPay.setForeground(Color.WHITE);

		// Button to add a new customer
		JButton btnAddCustomer = new JButton("Add a new Customer");
		btnAddCustomer.setEnabled(true);
		btnAddCustomer.addActionListener(this);
		btnAddCustomer.setBackground(UEMCOLOR);
		btnAddCustomer.setForeground(Color.WHITE);

		// Show all customer Button
		JButton btnAllCustomer = new JButton("Show all Customers");
		btnAllCustomer.setEnabled(true);
		btnAllCustomer.addActionListener(this);
		btnAllCustomer.setBackground(UEMCOLOR);
		btnAllCustomer.setForeground(Color.WHITE);

		// Button that shows customer revenue
		JButton btnCompanyRev = new JButton(
				"Compute Balances & Show Company Revenue");
		btnCompanyRev.addActionListener(this);
		btnCompanyRev.setBackground(UEMCOLOR);
		btnCompanyRev.setForeground(Color.WHITE);

		// Button CustomerRev
		JButton btnCustomersAboveRate = new JButton("Show Customers Above Rate");
		btnCustomersAboveRate.addActionListener(this);

		// Button CustomerRev
		JButton btnCalcBalance = new JButton("Calculate Balance");
		btnCalcBalance.addActionListener(this);
		btnCalcBalance.setBackground(UEMCOLOR);
		btnCalcBalance.setForeground(Color.WHITE);

		// Button CustomerRev
		JButton btnCalcRev = new JButton("Calculate Revenue");
		btnCalcRev.addActionListener(this);
		btnCalcRev.setBackground(UEMCOLOR);
		btnCalcRev.setForeground(Color.WHITE);

		// Import customer from text
		JButton btnCustFromText = new JButton("Import customers");
		btnCustFromText.addActionListener(this);
		btnCustFromText.setBackground(UEMCOLOR);
		btnCustFromText.setForeground(Color.WHITE);

		// Export customer to text
		JButton btnCustToText = new JButton("Export customers");
		btnCustToText.addActionListener(this);
		btnCustToText.setBackground(UEMCOLOR);
		btnCustToText.setForeground(Color.WHITE);

		// Positioning of the Buttons
		setLayout(new GridLayout(3, 1));

		// Search Panel
		JPanel pnlSearch = new JPanel();
		pnlSearch.setLayout(new BorderLayout());
		add(pnlSearch);
		pnlSearch.add(btnSearch, BorderLayout.WEST);
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
	}

	@Override
	public void actionPerformed(ActionEvent ae) { // ActionList
		String sourceName = ae.getActionCommand();
		System.out.println("PBES - performing Action for: " + sourceName); // DEBUG
																			// -
																			// show
																			// corect
																			// button
																			// name

		// Ifs even if it's just caps
		// TODO please SPLIT functions like in GuiUserModificator
		if (sourceName.contains("Search User by ID")) { // Conditions for the
														// button search by user
														// ID
			this.onSearchId(ae);
		} else if (sourceName.contains("Add a new Customer")) { // condition to
																// react on the
																// add new
																// customer
																// button
			// try to create an empty new customer and add it to our list of
			// customers
			this.onAddCustomer(ae);
		}

		// Conditions for Pay by User ID Button
		else if (sourceName.contains("Pay by User ID")) {
			this.onPayByUserId(ae);
		}
		// Conditions for Show all Customer Button
		else if (sourceName.contains("Show all Customers")) {
			this.onShowCustomer(ae);
		}
		// Conditions for show customers rate Button
		else if (sourceName.contains("Show Customers Above Rate")) {
			this.onCustomerAboveRate(ae);
		}
		// Conditions for Revenue Button and balances
		else if (sourceName.contains("Company Revenue")) { // function that
															// shows the revenue
															// of each customer
			this.onCompanyRevenue(ae);
		}
		// Conditions for Calculate Balance
		else if (sourceName.contains("Calculate Balance")) { // function that
																// shows the
			// revenue of each customer
			this.calculateAllBalances();
		}
		// Conditions for Revenue Button
		else if (sourceName.contains("Calculate Revenue")) { // function that
																// shows the
																// company
																// revenue
			this.onRevenue(ae);
		}
		// Conditions for Revenue Button
		else if (sourceName.contains("Get customer")) { // function that
														// shows the
														// company
														// revenue
			this.onImportText(ae);
		}
		// Conditions for Revenue Button
		else if (sourceName.contains("Save customer")) { // function that
															// shows the
															// company
															// revenue
			this.onExportText(ae);
		} else if (sourceName.contains("Export customers")) { // function that
																// imports
																// customers
																// from text
																// file

			this.onExportGroupOfCustomers(ae);
		}

		else if (sourceName.contains("Import customers")) {

			try {
				this.onImportGroupOfCustomers(ae);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Add a recently created to customer object to the next available position
	 * in the array in case the ID does not exist yet
	 * 
	 * @author benste
	 * @param new Customer Object
	 * @return success of the operation
	 */

	public void onSearchId(ActionEvent ae) {

		Integer searchId = Integer.parseInt(this.txtSearch.getText());
		@SuppressWarnings("unused")
		// interaction links back from other class
		GuiUserModificator editor = null;
		if (this.getCustomer(searchId) != null) {
			editor = new GuiUserModificator(this,
					(Customer) this.getCustomer(searchId));
		} else // user not found
		{
			JOptionPane.showMessageDialog(this, "User with ID: " + searchId
					+ " does not exist yet"); // message when customer does
												// not exist
		}
	}

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

	public void onCompanyRevenue(ActionEvent ae) {
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

	// TODO set conditions to import text
	public void onImportText(ActionEvent ae) {
		Integer revenue = this.getCompanyRevenue();
		JOptionPane.showMessageDialog(this, " Your Revenue is:" + revenue
				+ " € not taking into accounts Cents");
	}

	// TODO set conditions to export text
	public void onExportText(ActionEvent ae) {
		Integer revenue = this.getCompanyRevenue();
		JOptionPane.showMessageDialog(this, " Your Revenue is:" + revenue
				+ " € not taking into accounts Cents");
	}

	public void onImportGroupOfCustomers(ActionEvent ae) throws Exception {
		
	}

	public void onExportGroupOfCustomers(ActionEvent ae) {
		ArrayList<Customer> results = this
				.getCustomersAboveRate(Integer.MIN_VALUE);

		Customer[] customerToExport = results.toArray(new Customer[0]);
		DataFile f = new DataFile(customerToExport);
		f.exportCustomer(customerToExport);
		this.setVisible(false);
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
