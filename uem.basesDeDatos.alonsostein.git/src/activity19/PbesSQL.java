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
		// Also create the company
		String sql = " INSERT INTO Customer VALUES " + customer.exportSQLText(this.getCompany());
		// TODO run SQL
	}

	@Override
	public boolean addCustomer(CustomerSQL customer) {
		String sql = " INSERT INTO Customer VALUES " + customer.exportSQLText(this.getCompany());
		// TODO run SQL
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
