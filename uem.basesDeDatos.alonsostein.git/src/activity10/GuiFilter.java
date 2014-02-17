package activity10;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GuiFilter extends JFrame implements ActionListener, KeyListener {

	private JPanel contentPane;
	private JTextField textFieldFilter;
	final static int MAX_CUSTOMERS = 20;
	private JButton btnSubmitFilter;
	private int number;
	public int mode; // 0 for number of customers; 1 for amount to pay; 2 for
						// rate

	private GuiFilter() {
	}

	/**
	 * Create the frame.
	 * 
	 * @author Luis
	 * @param mode
	 *            0 for number of customers; 1 for amount to pay; 2 for rate
	 */
	public GuiFilter(Integer mode) {
		setTitle("Filter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 143);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(3, 0, 0, 0));
		JLabel lblFilter;
		if (mode == 0) {
			lblFilter = new JLabel(
					"How many customers do you wish to input (From 0 to 20)?");
		} else if (mode == 1) {
			lblFilter = new JLabel("Input the ammount you wish to pay");
		} else { // e.g. for number 2
			lblFilter = new JLabel("Input the rate");
		}
		lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblFilter);
		textFieldFilter = new JTextField();
		panel.add(textFieldFilter);
		textFieldFilter.setColumns(10);
		textFieldFilter.addKeyListener(this);

		btnSubmitFilter = new JButton("Submit");
		panel.add(btnSubmitFilter);
		btnSubmitFilter.addActionListener(this);
		btnSubmitFilter.addKeyListener(this);
		this.pack();
		this.setVisible(true);

	}

	/**
	 * Method for applying the Action - Submitbutton / Enter actions
	 */
	public void applyAction() {
		String fil = textFieldFilter.getText();
		// check if the input is a number
		try {
			Integer.parseInt(fil);
			// stores the number introduced by user
			number = Integer.parseInt(fil);
			// check if the number is inside the limits

			if (number > MAX_CUSTOMERS || number < 0) {
				throw new Exception(fil + " Number out of Range 0..20");
			}
			/*
			 * Propper code to be used instead of this construct while (number >
			 * MAX_CUSTOMERS || number < 0) {
			 * 
			 * number = Integer.parseInt(JOptionPane.showInputDialog(this,
			 * "Please enter a number between 0 and 20", "Inane warning",
			 * JOptionPane.WARNING_MESSAGE)); }
			 */
			System.out.println("DEBUG - number accepted: " + number);
			// ADD waiting message and waiting time
			this.setVisible(false);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Please enter a valid number in between 0 and 20",
					"Inane warning - " + ex.getMessage(),
					JOptionPane.WARNING_MESSAGE);
			this.textFieldFilter.setText("");
		}

	}

	public void actionPerformed(ActionEvent e) {
		// if the event is triggered by the button
		if (e.getSource() == btnSubmitFilter) { // if action is not limited all
												// actions confirm e.g. enter as
												// well
			applyAction();
		}// end if action limit to source

	}

	public int getNumber() {
		return number;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			applyAction();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
