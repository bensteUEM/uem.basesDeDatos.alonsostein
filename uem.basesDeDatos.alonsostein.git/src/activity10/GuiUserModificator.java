package activity10;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import activity10.Pbes;
import activity19.SQLiteStorage;

public class GuiUserModificator extends JFrame implements ActionListener {
	private final static Logger LOG = Logger.getLogger(SQLiteStorage.class
			.getName());
	private static final long serialVersionUID = -609295754837654259L;
	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldId;
	private JTextField textFieldCell;
	private JTextField textFieldRate;
	private JTextField textFieldLand;
	private JTextField textFieldAir;
	private JTextField textFieldBalance;
	private DefaultListModel<String> callList;
	private JButton btnChangeId;
	private JButton btnSave;
	private JButton btnImport;
	private JButton btnExport;
	private CustomerAbstract customer;
	private Pbes parent;
	// private CustomerAbstract dataCall; // remove in future if no issues
	// private CustomerCall call; // remove in future if no issues
	private JLabel lblName;
	private JLabel lblLand;
	private JLabel lblCell;
	private JLabel lblBalance;
	private JLabel lblId;
	private JLabel lblAirtime;
	private JLabel lblRate;
	private JLabel lblBilling;
	private JScrollPane listScroller;

	/**
	 * Create the frame.
	 */
	public GuiUserModificator(Pbes sourceParent,
			CustomerAbstract currentCustomer) {
		this.parent = sourceParent;
		this.parent.setVisible(false);
		createElements(currentCustomer);
		fillInformation(currentCustomer);
		arrangeInLayouts();
		// Custom closing window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				onExit();
			}
		});
	}

	public void onExit() { // when the GUI for the call is closed
		this.setVisible(false); // set this gui to not visible
		parent.setVisible(true); // and set the main GUI visible
	} // end of onExit

	public void createElements(CustomerAbstract currentCustomer) {
		this.setVisible(true);
		contentPane = new JPanel(); // creation of content Pane
		lblName = new JLabel("Name");
		textFieldName = new JTextField(currentCustomer.getName());
		lblId = new JLabel("ID number");
		textFieldId = new JTextField(Integer.toString(currentCustomer.getId()));
		lblCell = new JLabel("Cellphone number");
		textFieldCell = new JTextField(currentCustomer.getCellPhoneNumber());
		lblLand = new JLabel("Landline phone number");
		textFieldLand = new JTextField(currentCustomer.getLandlinePhoneNumer());
		lblAirtime = new JLabel("Airtime");
		textFieldAir = new JTextField(Integer.toString(currentCustomer
				.getAirtimeMinutes()));
		lblRate = new JLabel("Rate");
		textFieldRate = new JTextField(Integer.toString(currentCustomer
				.getRate()));
		lblBalance = new JLabel("Balance");
		textFieldBalance = new JTextField(currentCustomer.getBalance()
				.toString());
		lblBilling = new JLabel("Billing information: ");
		callList = new DefaultListModel<String>();
		JList<String> jliCalls = new JList<String>(callList);
		listScroller = new JScrollPane(jliCalls);
		btnChangeId = new JButton("Change ID");
		btnSave = new JButton("Save");
		btnImport = new JButton("Import customer from file");
		btnExport = new JButton("Export customer to file");
		this.customer = currentCustomer;
	}

	public void fillInformation(CustomerAbstract currentCustomer) {
		setBounds(100, 100, 1200, 500); // set the bounds of the mainframe
		this.setTitle("CUSTOMER INFORMATION");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // set Borders
		setContentPane(contentPane);

		textFieldName.setColumns(10);
		textFieldId.setEditable(false); // disable editing for this field
										// because it's the identifier
		textFieldId.setColumns(10);
		textFieldCell.setColumns(10);

		textFieldLand.setColumns(10);
		textFieldAir.setColumns(10);
		textFieldRate.setColumns(10);

		textFieldBalance.setEditable(false);
		textFieldBalance.setColumns(10);
		
		//JList for listing calls
		/*
		for (int i = 0; i < ((Customer) currentCustomer).getBillText(
				currentCustomer.getId()).size(); i++) {
			callList.addElement(((Customer) currentCustomer)
					.getBillText(currentCustomer.getId()).get(i));
		}
		*/
		
		// JList for listing calls	
		 		LOG.fine("preparing to fill existing calls");		
		 		if (currentCustomer.getCalls() != null){
		 			// http://docs.oracle.com/javase/8/docs/api/javax/swing/DefaultListModel.html#addElement-E-
		 			for (int i = 0; i < ((Customer) currentCustomer).getBillText(currentCustomer.getId()).size(); i++) {
		 				callList.addElement(((Customer) currentCustomer).getBillText(currentCustomer.getId()).get(i));
		 			}
		 			LOG.finest("added following call to List");
		 		}
		 		LOG.fine("populated list with all calls");
		
		btnChangeId.addActionListener(this);
		btnChangeId.setEnabled(true);
		btnSave.addActionListener(this);
		btnImport.addActionListener(this);
		btnExport.addActionListener(this);
		this.customer = currentCustomer;
	}

	public void arrangeInLayouts() {
		setLayout(new BorderLayout());
		// main grid layout panel
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new GridLayout(3, 1));
		contentPane.add(pnlMain, BorderLayout.CENTER);

		// subpanel top2
		JPanel pnlTop2 = new JPanel();
		pnlTop2.setLayout(new GridLayout(7, 2));
		pnlMain.add(pnlTop2);
		pnlTop2.add(lblId);
		pnlTop2.add(textFieldId);
		pnlTop2.add(lblRate);
		pnlTop2.add(textFieldRate);
		pnlTop2.add(lblName);
		pnlTop2.add(textFieldName);
		pnlTop2.add(lblCell);
		pnlTop2.add(textFieldCell);
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
		pnlBot1.add(listScroller);
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

	public void setUserModificator(CustomerAbstract customer2) {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				onExit();
			}
		});
		createElements(customer2);
		fillInformation(customer2);
		arrangeInLayouts();
		this.setVisible(true);
	}

	/**
	 * This function does link the actions with the matching functions
	 */
	public void actionPerformed(ActionEvent e) {
		// if the source is the button "next"
		String sourceName = e.getActionCommand();
		if (sourceName.contains("Save")) {
			this.onSave(e);
		} else if (e.getActionCommand() == "Change ID") {
			this.onChangeId(e);
		} else if (sourceName.contains("Import")) {
			try {
				this.onImportCustomer(e);
			} catch (Exception e1) {
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
		this.customer = (Customer) f.importCustomer()[0];
		// @LUIS code not needed // this.customer =
		// customerToImport[this.customer.getId()];
		if (this.parent.addCustomer(this.customer)) {
			this.setUserModificator(this.customer); // set the modificator to
													// the
													// customer required
		} else {
			System.out
					.println("The imported user can not be saved into the current database");
		}
	}

	public void onExportCustomer(ActionEvent ae) {
		this.onSave(ae);
		CustomerAbstract[] customerToExport = new Customer[1]; // as in this
																// case we
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
	public CustomerAbstract getCustomer() {
		return this.customer;
	}
}
