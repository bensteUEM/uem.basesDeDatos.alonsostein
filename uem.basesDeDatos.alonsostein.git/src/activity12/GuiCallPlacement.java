package activity12;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import activity10.Customer;
import activity10.CustomerCall;
import activity10.Pbes;

public class GuiCallPlacement extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JTextField textFieldCellPhoneNumber;
	private JTextField textFieldNumberToCall;
	private JButton btnCall;
	private JButton btnHangUp;
	private Customer customer;
	private String numberToCall;
	private Pbes parent;
	private Calendar startCall;
	private Integer callTimeStart;
	private Calendar endCall;
	private Integer callTimeEnd;
	private Integer callDurationMillis;
	private Integer callDurationSeconds;
	private JTextArea textAreaInfo;
	private CustomerCall call;
	private String fileName = "";

	public GuiCallPlacement(Pbes sourceParent, Customer currentCustomer) {
		this.parent = sourceParent;
		this.parent.setVisible(false);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 2, 0, 0));

		JLabel lblCustomerInformation = new JLabel("CALL PLACEMENT");
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

		textAreaInfo = new JTextArea(15, 20);
		textAreaInfo.setEditable(false);
		contentPane.add(textAreaInfo);
		
		customer = currentCustomer;
	}

	public void actionPerformed(ActionEvent e) {
		// if the source is the button "next"
		String sourceName = e.getActionCommand();
		System.out.println("The following button was pressed: " + sourceName); // TODO
																				// DEBUG
		if (sourceName.contains("Call")) {
			this.onCall(e);
		} else if (sourceName.contains("Hang up")) {
			try {
				this.onHangUp(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void onCall(ActionEvent e) {
		this.startCall = Calendar.getInstance();
		this.callTimeStart = (int) startCall.getTimeInMillis();

	}

	public void onHangUp(ActionEvent e) throws IOException {
		this.endCall = Calendar.getInstance();
		this.callTimeEnd = (int) endCall.getTimeInMillis();
		this.callDurationMillis = this.callTimeEnd - this.callTimeStart;
		this.callDurationSeconds = callDurationMillis / 1000;
		System.out.println(callDurationSeconds);
		call = new CustomerCall(this.customer,
				textFieldNumberToCall.getText());
		call.setDuration(callDurationSeconds);
		textAreaInfo.append("Destination: " + textFieldNumberToCall.getText()
				+ "\n" + "Duration: " + callDurationSeconds + " seconds" + "\n"
				+ "Cost: " + call.getTotal());
		exportMonthlyBill();

	}
	public String getDestination(){
		return textFieldNumberToCall.getText();
	}
	public Integer getDuration(){
		return callDurationSeconds;
	}
	public Integer getCost(){
		return call.getTotal();
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void exportMonthlyBill() throws IOException{
		//File path = new File(this.fileName + ".txt");
		//String fileName = path.getPath();
		String fileName = "I:\\test.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,
				true));
		writer.write(customer.getName());
		writer.newLine();
		writer.write(customer.getCellPhoneNumber());
		writer.newLine();
		writer.write(textFieldNumberToCall.getText());
		writer.newLine();
		writer.write(callDurationSeconds);
		writer.newLine();
		writer.write(call.getTotal());
		
		writer.flush(); // make sure the buffer writes everything
		System.out.println("Following file has been written" + fileName); // TODO
																			// debug
		writer.close(); // close the file
	}
}
