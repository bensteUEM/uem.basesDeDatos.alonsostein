package activity10;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * Simple class to display a list of customers
 * @author steinb
 *
 */
public class GuiCustomerList extends JFrame {

	private JPanel contentPane;

	/**
	 * A simple window with a message and a scrollable List of Customers
	 */
	public GuiCustomerList(ArrayList<Customer> listContent, String information) {
		setBounds(100, 100, 450, 300); // set some random sizes
		contentPane = new JPanel(); // create the panel
		contentPane.setLayout(new BorderLayout(0, 0));// Set a layout
		setContentPane(contentPane); // set content pane

		JLabel lblinformation = new JLabel(information); //create information label on top north orientation with predefined text
		contentPane.add(lblinformation, BorderLayout.NORTH); //add the information

		JList<Customer> list = new JList(listContent.toArray());// insert the predefined array into the list

		JScrollPane scrollPane = new JScrollPane(list); //add the Jlist to a scrollable container
		contentPane.add(scrollPane, BorderLayout.CENTER); //add scrollpane to Frame

		contentPane.setVisible(true); //make it visble
	}
}
