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
	SQLiteStorage db;
	public PbesSQL(Integer numberOfCustomers) {
		super(numberOfCustomers); // TODO remove number of custoemrs in constructor
		db = new SQLiteStorage("activity19");
		// TODO Auto-generated constructor stub
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
		String query = "SELECT * FROM Customers WHERE ID=" + searchId;
		CustomerSQL customer = (CustomerSQL) db.ownSQLCommand(query,"CustomerSQL");
		return customer;
	}

	@Override
	public boolean deleteCustomer(Integer searchId) {
	}
	
	@Override
	public boolean getCustomers(Integer searchId) {
	}

}
