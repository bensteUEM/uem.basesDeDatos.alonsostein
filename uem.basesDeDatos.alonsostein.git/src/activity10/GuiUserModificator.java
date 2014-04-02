package activity10;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import activity10.Pbes;
import activity13.CustomerCall;

public class GuiUserModificator extends JFrame implements ActionListener {

	private static final long serialVersionUID = -609295754837654259L;
	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldId;
	private JTextField textFieldCell;
	private JTextField textFieldRate;
	private JTextField textFieldLand;
	private JTextField textFieldAir;
	private JTextField textFieldBalance;
	private JButton btnChangeId;
	private JButton btnSave;
	private JButton btnImport;
	private JButton btnExport;
	private Customer customer;
	private Pbes parent;
	private CustomerAbstract dataCall;

	/**
	 * Create the frame.
	 */
	public GuiUserModificator(Pbes sourceParent, Customer currentCustomer) {
		this.parent = sourceParent;
		this.parent.setVisible(false);
		this.setVisible(true);
		this.setTitle("CUSTOMER INFORMATION");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500); // set the bounds of the mainframe
		contentPane = new JPanel(); // creation of content Pane
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // set Borders
		setContentPane(contentPane); // set the content pane

		JLabel lblCustomerInformation = new JLabel("CUSTOMER INFORMATION");
		lblCustomerInformation.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblName = new JLabel("Name");

		textFieldName = new JTextField(currentCustomer.getName());
		textFieldName.setColumns(10);

		JLabel lblId = new JLabel("ID number");

		textFieldId = new JTextField(Integer.toString(currentCustomer.getId()));
		textFieldId.setEditable(false); // disable editing for this field
										// because it's the identifier
		textFieldId.setColumns(10);

		JLabel lblCell = new JLabel("Cellphone number");

		textFieldCell = new JTextField(currentCustomer.getCellPhoneNumber());
		textFieldCell.setColumns(10);

		JLabel lblLand = new JLabel("Landline phone number");

		textFieldLand = new JTextField(currentCustomer.getLandlinePhoneNumer());
		textFieldLand.setColumns(10);

		JLabel lblAirtime = new JLabel("Airtime");

		textFieldAir = new JTextField(Integer.toString(currentCustomer
				.getAirtimeMinutes()));
		textFieldAir.setColumns(10);

		JLabel lblRate = new JLabel("Rate");

		textFieldRate = new JTextField(Integer.toString(currentCustomer
				.getRate()));
		textFieldRate.setColumns(10);

		JLabel lblBalance = new JLabel("Balance");

		// Balance field
		textFieldBalance = new JTextField(currentCustomer.getBalance()
				.toString());
		textFieldBalance.setEditable(false);
		textFieldBalance.setColumns(10);

		JLabel lblBilling = new JLabel("Billing information: ");

		// JList for listing calls
		JList<CustomerCall> callList = new JList<CustomerCall>(); // data has type Object[]
		JScrollPane listScroller = new JScrollPane(callList);

		btnChangeId = new JButton("Change ID");
		btnChangeId.addActionListener(this);
		// disable button until 100% works - currently issue after 2nd change -
		// most likely insert / delete
		btnChangeId.setEnabled(true);

		btnSave = new JButton("Save");
		btnSave.addActionListener(this);

		btnImport = new JButton("Import customer from file");
		btnImport.addActionListener(this);

		btnExport = new JButton("Export customer to file");
		btnExport.addActionListener(this);

		this.customer = currentCustomer;

		this.setVisible(true);

		// NEW GUI DISTRIBUTION
		setLayout(new BorderLayout());
		// main grid layout panel
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new GridLayout(3, 1));
		contentPane.add(pnlMain, BorderLayout.CENTER);

		// subpanel top2
		JPanel pnlTop2 = new JPanel();
		pnlTop2.setLayout(new GridLayout(7, 2));
		pnlMain.add(pnlTop2);
		pnlTop2.add(lblName);
		pnlTop2.add(textFieldName);
		pnlTop2.add(lblId);
		pnlTop2.add(textFieldId);
		pnlTop2.add(lblCell);
		pnlTop2.add(textFieldCell);
		pnlTop2.add(lblRate);
		pnlTop2.add(textFieldRate);
		pnlTop2.add(lblLand);
		pnlTop2.add(textFieldLand);
		pnlTop2.add(lblAirtime);
		pnlTop2.add(textFieldAir);
		pnlTop2.add(lblBalance);
		pnlTop2.add(textFieldBalance);

		// subpanel bot1
		JPanel pnlBot1 = new JPanel();
		pnlBot1.setLayout(new GridLayout(1, 2));
		pnlBot1.add(lblBilling);
		pnlBot1.add(callList);
		pnlMain.add(pnlBot1);

		// subpanel bot2
		JPanel pnlBot2 = new JPanel();
		pnlBot2.setLayout(new GridLayout(2, 2));
		pnlMain.add(pnlBot2);
		pnlBot2.add(btnSave);
		pnlBot2.add(btnImport);
		pnlBot2.add(btnExport);
		pnlBot2.add(btnChangeId);

	}

	public void setUserModificator(Customer currentCustomer) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(17, 2, 0, 0));

		JLabel lblCustomerInformation = new JLabel("CUSTOMER INFORMATION");
		lblCustomerInformation.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCustomerInformation);

		JLabel lblInfo = new JLabel("");
		contentPane.add(lblInfo);

		JLabel lblName = new JLabel("Name");
		contentPane.add(lblName);

		textFieldName = new JTextField(currentCustomer.getName());
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);

		JLabel lblId = new JLabel("ID number");
		contentPane.add(lblId);

		textFieldId = new JTextField(Integer.toString(currentCustomer.getId()));
		textFieldId.setEditable(false); // disable editing for this field
										// because it's the identifier
		contentPane.add(textFieldId);
		textFieldId.setColumns(10);

		JLabel lblCell = new JLabel("Cellphone number");
		contentPane.add(lblCell);

		textFieldCell = new JTextField(currentCustomer.getCellPhoneNumber());
		contentPane.add(textFieldCell);
		textFieldCell.setColumns(10);

		JLabel lblLand = new JLabel("Landline phone number");
		contentPane.add(lblLand);

		textFieldLand = new JTextField(currentCustomer.getLandlinePhoneNumer());
		contentPane.add(textFieldLand);
		textFieldLand.setColumns(10);

		JLabel lblAirtime = new JLabel("Airtime");
		contentPane.add(lblAirtime);

		textFieldAir = new JTextField(Integer.toString(currentCustomer
				.getAirtimeMinutes()));
		contentPane.add(textFieldAir);
		textFieldAir.setColumns(10);

		JLabel lblRate = new JLabel("Rate");
		contentPane.add(lblRate);

		textFieldRate = new JTextField(Integer.toString(currentCustomer
				.getRate()));
		contentPane.add(textFieldRate);
		textFieldRate.setColumns(10);

		JLabel lblBalance = new JLabel("Balance");
		contentPane.add(lblBalance);

		// Balance field
		textFieldBalance = new JTextField(currentCustomer.getBalance()
				.toString());
		textFieldBalance.setEditable(false);
		textFieldBalance.setColumns(10);
		contentPane.add(textFieldBalance);

		Container callList;
		// Billing information
		for (CustomerCall call : currentCustomer.getCalls()) {
			callList.addElement(call);
		}
		// Buttons

		btnChangeId = new JButton("Change ID");
		contentPane.add(btnChangeId);
		btnChangeId.addActionListener(this);

		btnChangeId.setEnabled(true);

		btnSave = new JButton("Save");
		contentPane.add(btnSave);

		btnImport = new JButton("Import customer from file");
		contentPane.add(btnImport);
		btnImport.addActionListener(this);

		btnExport = new JButton("Export customer to file");
		contentPane.add(btnExport);
		btnExport.addActionListener(this);

		this.customer = currentCustomer;

		btnSave.addActionListener(this);
		this.setVisible(true);
	}

	/**
	 * This function does link the actions with the matching functions
	 */
	public void actionPerformed(ActionEvent e) {
		// if the source is the button "next"
		String sourceName = e.getActionCommand();
		// System.out.println("The following button was pressed: "+ sourceName);
		// //TODO DEBUG
		if (sourceName.contains("Save")) {
			this.onSave(e);
		} else if (e.getActionCommand() == "Change ID") {
			this.onChangeId(e);
		} else if (sourceName.contains("Import")) {
			try {
				this.onImportCustomer(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (sourceName.contains("Export")) {
			this.onExportCustomer(e);
		}
	}

	/*
	 * 
	 * The following section does have the functions for the buttons
	 */

	/**
	 * This function should be called when the content of the modificator should
	 * be saved. It will also send the user back to the Pbes instance to allow
	 * more modifications.
	 * 
	 * @param ae
	 * @author Luis
	 */
	public void onSave(ActionEvent ae) {

		boolean success = true;
		success = success && this.customer.setName(textFieldName.getText());
		// ID should not be editable
		success = success
				&& this.customer.setCellPhoneNumber(textFieldCell.getText());
		success = success
				&& this.customer
						.setLandlinePhoneNumber(textFieldLand.getText());
		success = success
				&& this.customer.setAirtimeMinutes(Integer
						.parseInt(textFieldAir.getText()));
		success = success
				&& this.customer.setRate(Integer.parseInt(textFieldRate
						.getText()));
		if (success) {
			if (this.parent.saveCustomer(this.customer)) {
				this.parent.setVisible(true);
				this.setVisible(false);
				// System.out.println("onSave of User modificator visibiility changes applied");
			} else { // if there is a problem saving the user
				System.out
						.println("WARNING Something went wrong in PBES when saving the user");
			}
		} else {
			error();
		}
	}

	public void onImportCustomer(ActionEvent ae) {
		DataFile f = new DataFile();
		this.parent.deleteCustomer(this.customer.getId()); // DELETE template
															// user
		this.customer = f.importCustomer()[0];
		// @LUIS code not needed // this.customer =
		// customerToImport[this.customer.getId()];
		if (this.parent.addCustomer(this.customer)) {
			this.setUserModificator(this.customer); // set the modificator to
													// the
													// customer required
		} else {
			System.out
					.println("The imported user can not be saved into the current database");
			// TODO offer alternative ID if this is the problem
		}
	}

	public void onExportCustomer(ActionEvent ae) {
		this.onSave(ae);
		Customer[] customerToExport = new Customer[1]; // as in this case we
														// want to
		// add only one customer, it
		// creates an array of 1 element
		customerToExport[0] = this.customer; // sets the position 0 of the array
												// to the current customer
		DataFile f = new DataFile(customerToExport);
		f.exportCustomer(customerToExport);
		this.parent.setVisible(true);
		this.setVisible(false);
	}

	/**
	 * This function is called for the Action of changing the ID of an existing
	 * customer object It will ask for a new valid ID 3 times and only proceed
	 * if the ID is not used by any other customer yet With a validated ID it
	 * will delete the existing user object and add the modified copy
	 * 
	 * @param ae
	 * @author benste
	 */
	public void onChangeId(ActionEvent ae) {
		Integer newId = -1; // initialize a new ID
		Integer checkingId = -1; // initialize a cheking ID
		Byte counter = 1; // max attempt counter
		while (newId < 0) {
			try {
				checkingId = Integer.parseInt(JOptionPane.showInputDialog(this,
						"Please insert your new ID of your choice"));
			} catch (NumberFormatException e) {// just an invalid attempt
			}
			if ((checkingId > 0)
					&& (null == this.parent.getCustomer(checkingId))) {
				newId = checkingId;
			} else if (counter == 3) {
				break;
			} else {
				System.out.println("ID:" + checkingId
						+ "is either negative or does already exist");

			}// end else
			counter++; // increase max. attempt counter

		}// end while
		if (newId > 0) {
			boolean a = true;
			a = a && this.parent.deleteCustomer(this.customer.getId());
			a = a && this.customer.setId(newId);
			a = a && this.parent.addCustomer(this.customer);
			System.out.println("change ID Success ? :" + a); // TODO debug
			this.setVisible(false);
			this.parent.setVisible(true);
			this.parent.txtSearch.setText(Integer.toString(newId));
		} else {
			JOptionPane
					.showMessageDialog(
							this,
							"You tried 3 invalid new IDs. Please consult the list of existing Users with their ID before trying again");
		}
	}// end function

	/*
	 * 
	 * The following section only includes helper functions
	 */

	// Set a window to show there is an error in the imput information
	public void error() {
		System.out.println("WARNING, saving failed;");// TODO debug
		JFrame frameError = new JFrame("Error");
		JLabel labelError = new JLabel(
				"ERROR: Please check that the information is correct");
		frameError.getContentPane().add(labelError);
		frameError.setLocation(90, 90);
		frameError.pack();
		frameError.setVisible(true);
	}

	// JUST FOR TESTING shows the input information is correct
	public void correct() {
		/*
		 * The following Code is only to visualize different steps of data
		 * processing JFrame frameError = new JFrame("Correct"); JLabel
		 * labelError = new JLabel(
		 * "Correct informtion saved, please wait while we process your changes"
		 * ); frameError.getContentPane().add(labelError);
		 * frameError.setLocation(90, 90); frameError.pack();
		 * frameError.setVisible(true);
		 */
		this.setVisible(false); // hide input window
	}

	public boolean isANumber(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/*
	 * returns the customer object in its last saved state
	 * 
	 * @author benste
	 * 
	 * @return Customer customer object of this GUI
	 */
	public Customer getCustomer() {
		return this.customer;
	}
}
// TODO add import and export one customer using DataFile
