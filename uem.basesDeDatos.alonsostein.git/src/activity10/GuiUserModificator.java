package activity10;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import activity10.Pbes;

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
	private Customer customer;
	private Pbes parent;

	/**
	 * Create the frame.
	 */
	public GuiUserModificator(Pbes sourceParent, Customer currentCustomer) {
		this.parent = sourceParent;
		this.parent.setVisible(false);
		this.setVisible(true);
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
		textFieldBalance = new JTextField(Integer.toString(currentCustomer
				.getBalance()));
		textFieldBalance.setEditable(false);
		textFieldBalance.setColumns(10);
		contentPane.add(textFieldBalance);

		btnChangeId = new JButton("Change ID");
		contentPane.add(btnChangeId);
		btnChangeId.addActionListener(this);
		// disable button until 100% works - currently issue after 2nd change -
		// most likely insert / delete
		btnChangeId.setEnabled(true);

		btnSave = new JButton("Save");
		contentPane.add(btnSave);

		this.customer = currentCustomer;

		btnSave.addActionListener(this);
		this.setVisible(true);
	}

	/**
	 * This function does link the actions with the matching functions
	 */
	public void actionPerformed(ActionEvent e) {
		// if the source is the button "next"
		if (e.getSource() == btnSave) {
			this.onSave(e);
		} else if (e.getActionCommand() == "Change ID") {
			this.onChangeId(e);
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
			}
		} else {
			error();
		}
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
