/**
 * 
 */
package activity19;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import activity10.Customer;
import activity10.CustomerAbstract;
import activity10.GuiUserModificator;
import activity10.Pbes;

/**
 * @author benste
 * 
 */
public class PbesSQL extends Pbes {

	public PbesSQL(Integer numberOfCustomers) {
		super(numberOfCustomers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addCustomer(Customer customer) {
	};

	@Override
	public boolean saveCustomer(CustomerAbstract currentCustomer) {
	}

	@Override
	public CustomerAbstract getCustomer(Integer searchId) {
	}

	@Override
	public boolean deleteCustomer(Integer searchId) {
	}
	
	@Override
	public boolean getCustomers(Integer searchId) {
	}

}
