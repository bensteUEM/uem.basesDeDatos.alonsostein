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

	public GuiCallPlacement(Pbes sourceParent, Customer currentCustomer) {
		this.parent = sourceParent;
		this.parent.setVisible(false);
		this.setVisible(true);
		setBounds(100, 100, 450, 335);
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 2, 0, 0));

		this.setTitle("CALL PLACEMENT");
		JLabel lblCustomerInformation = new JLabel("Welcome "+currentCustomer.getName());
		lblCustomerInformation.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCustomerInformation);

		JLabel lblInfo = new JLabel("");
		contentPane.add(lblInfo);

		JLabel lblName = new JLabel("Your cellphone number");
		contentPane.add(lblName);

		textFieldCellPhoneNumber = new JTextField(
				currentCustomer.getCellPhoneNumber());
		textFieldCellPhoneNumber.setEditable(false);
		contentPane.add(textFieldCellPhoneNumber);
		textFieldCellPhoneNumber.setColumns(10);

		JLabel lblId = new JLabel("Enter the number you want to call");
		contentPane.add(lblId);

		textFieldNumberToCall = new JTextField(20);
		this.numberToCall = textFieldNumberToCall.getText();
		contentPane.add(textFieldNumberToCall);

		btnCall = new JButton("Call");
		contentPane.add(btnCall);
		btnCall.addActionListener(this);

		btnHangUp = new JButton("Hang up");
		contentPane.add(btnHangUp);
		btnHangUp.addActionListener(this);
		btnHangUp.setVisible(false);

		textAreaInfo = new JTextArea(15, 20);
		textAreaInfo.setEditable(false);
		contentPane.add(textAreaInfo);
		textAreaInfo.setVisible(false);

		customer = currentCustomer;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				onExit();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		String sourceName = e.getActionCommand();
		// System.out.println("The following button was pressed: " +
		// sourceName); // DEBUG
		if (sourceName.contains("Call")) {
			this.onCall(e);
		} else if (sourceName.contains("Hang up")) {
			this.onHangUp(e);
		}
	}

	public void onExit() {
		this.setVisible(false);
		parent.setVisible(true);
	}

	public void onCall(ActionEvent e) {
		this.textAreaInfo.setText("");
		this.textAreaInfo.setVisible(false);
		this.call = new CustomerCall(this.customer,
				textFieldNumberToCall.getText(), Calendar.getInstance());
		this.btnCall.setVisible(false);
		this.btnHangUp.setVisible(true);
		this.textFieldNumberToCall.setEnabled(false);
	}

	public void onHangUp(ActionEvent e) {
		this.call.calculateDuration(Calendar.getInstance());

		textAreaInfo.append("Destination: " + this.call.getDestination() + "\n"
				+ "Duration: " + this.call.getDuration() + " seconds" + "\n"
				+ "Cost: " + call.getTotal());
		customer.addCall(call);
		this.btnCall.setVisible(true);
		this.btnHangUp.setVisible(false);
		this.textAreaInfo.setVisible(true);
		this.textFieldNumberToCall.setText("");
		this.textFieldNumberToCall.setEnabled(true);
	}
}
