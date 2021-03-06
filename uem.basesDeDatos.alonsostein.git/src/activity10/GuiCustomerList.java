package activity10;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * Simple class to display a list of customers
 * 
 * @author benste
 * 
 */
public class GuiCustomerList extends JFrame implements ActionListener {
	private static final long serialVersionUID = 8158363265686707093L;
	private JPanel contentPane;

	/**
	 * Constructor based on an existing list and some additional explanatory
	 * text
	 * 
	 * @param arrayList
	 *            Customer Objects to be displayed
	 * @param information
	 *            Text to be displayed
	 */
	@SuppressWarnings("unchecked")
	public GuiCustomerList(ArrayList<CustomerAbstract> arrayList, String information) {
		setBounds(100, 100, 450, 300); // set some random sizes
		contentPane = new JPanel(); // create the panel
		contentPane.setLayout(new BorderLayout(0, 0));// Set a layout
		setContentPane(contentPane); // set content pane

		JLabel lblinformation = new JLabel(information); 
		// create information label on top north orientation
		// with predefined text
		contentPane.add(lblinformation, BorderLayout.NORTH); 
		// add the information

		@SuppressWarnings("rawtypes")
		// this will auto convert to the correct class
		JList<Customer> list = new JList(arrayList.toArray());
		// insert the predefined array into the list
		JScrollPane scrollPane = new JScrollPane(list);
		// add the Jlist to a scrollable container
		contentPane.add(scrollPane, BorderLayout.CENTER);
		// add scrollpane to Frame
		contentPane.setVisible(true); // make it visble
	} //End GuiCustomerList()

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// there are no actions in this Class yet
		// TODO might include future feature to click on customer and see its
		// values

	} // End of actionperformed
}
