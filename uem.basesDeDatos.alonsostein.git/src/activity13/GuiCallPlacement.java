package activity13;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import activity10.Customer;
import activity10.Pbes;

public class GuiCallPlacement extends JFrame implements ActionListener {
	// declarate the class variables needed
	private static final long serialVersionUID = -6100529901132049016L;
	private JPanel contentPane;
	private JTextField textFieldCellPhoneNumber;
	private JTextField textFieldNumberToCall;
	private JButton btnCall;
	private JButton btnHangUp;
	private Customer customer;
	private String numberToCall;
	private Pbes parent;
	CustomerCall call;
	private JTextArea textAreaInfo;
	private JLabel lblCustomerInformation;
	private JLabel lblInfo;
	private JLabel lblName;
	private JLabel lblId;

	public GuiCallPlacement(Pbes sourceParent, Customer currentCustomer) { // method
																			// to
																			// create
																			// the
																			// GUI
																			// for
																			// the
																			// call
																			// placement
		this.parent = sourceParent;
		this.parent.setVisible(false);
		createElements(currentCustomer);
		fillInformation();
		arrangeInLayouts();

		// method to set a custom close operation
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				onExit();
			}
		});
	} // end of GuiCallPlacement

	public void actionPerformed(ActionEvent e) {
		String sourceName = e.getActionCommand();
		// System.out.println("The following button was pressed: " +
		// sourceName); // DEBUG
		if (sourceName.contains("Call")) {
			this.onCall(e);
		} else if (sourceName.contains("Hang up")) {
			this.onHangUp(e);
		}
	} // end of actionPerformed

	public void createElements(Customer currentCustomer) {
		this.setVisible(true);
		setBounds(100, 100, 450, 335);
		contentPane = new JPanel();
		lblCustomerInformation = new JLabel("Welcome "
				+ currentCustomer.getName());
		lblInfo = new JLabel("");

		lblName = new JLabel("Your cellphone number");
		textFieldCellPhoneNumber = new JTextField(
				currentCustomer.getCellPhoneNumber());
		lblId = new JLabel("Enter the number you want to call");
		textFieldNumberToCall = new JTextField(20);
		btnCall = new JButton("Call");
		btnHangUp = new JButton("Hang up");
		textAreaInfo = new JTextArea(15, 20);
		customer = currentCustomer;
	}

	public void fillInformation() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // set to add a
																// custom close
																// operation
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(5, 2, 0, 0));
		this.setTitle("CALL PLACEMENT");
		lblCustomerInformation.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCellPhoneNumber.setEditable(false);
		textFieldCellPhoneNumber.setColumns(10);
		this.numberToCall = textFieldNumberToCall.getText();
		btnCall.addActionListener(this);
		btnHangUp.addActionListener(this);
		btnHangUp.setVisible(false);
		textAreaInfo.setEditable(false);
		textAreaInfo.setVisible(false);
	}

	public void arrangeInLayouts() {
		setContentPane(contentPane);
		contentPane.add(lblCustomerInformation);
		contentPane.add(lblInfo);
		contentPane.add(lblName);
		contentPane.add(textFieldCellPhoneNumber);
		contentPane.add(lblId);
		contentPane.add(textFieldNumberToCall);
		contentPane.add(btnCall);
		contentPane.add(btnHangUp);
		contentPane.add(textAreaInfo);
	}

	public void onExit() { // when the GUI for the call is closed
		this.setVisible(false); // set this gui to not visible
		parent.setVisible(true); // and set the main GUI visible
	} // end of onExit

	public void onCall(ActionEvent e) { // when button call is clicked
		this.textAreaInfo.setText(""); // clear the text area
		this.textAreaInfo.setVisible(false); // set the text area to not visible
		if (this.textFieldNumberToCall.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter a destination");
		} else {
			this.call = new CustomerCall(this.customer,
					textFieldNumberToCall.getText(), Calendar.getInstance()); // create
																				// a
																				// new
																				// CustomerCall
			this.btnCall.setVisible(false); // set the button call to not
											// visible
			this.btnHangUp.setVisible(true); // and set the hang up button to
												// visible
			this.textFieldNumberToCall.setEnabled(false); // dont allow to edit
															// the
															// number to call
															// during
															// the call
		}
	}

	public void onHangUp(ActionEvent e) { // when button hang up is clicked
		this.call.calculateDuration(Calendar.getInstance()); // call the method
																// to calculate
																// the call
																// duration

		textAreaInfo.append("Destination: " + this.call.getDestination() + "\n"
				+ "Duration: " + this.call.getDuration() + " seconds" + "\n"
				+ "Cost: " + call.getTotal()); // set the information to be
												// showed in the text area
		customer.addCall(call); // and add a new call to the current customer
		this.btnCall.setVisible(true); // allow to click on call again
		this.btnHangUp.setVisible(false); // dont allow to click on hang up
											// because the call is already over
		this.textAreaInfo.setVisible(true); // show the information in the text
											// area
		this.textFieldNumberToCall.setText(""); // reset the number to call
		this.textFieldNumberToCall.setEnabled(true); // and allow to edit it
	}
}
