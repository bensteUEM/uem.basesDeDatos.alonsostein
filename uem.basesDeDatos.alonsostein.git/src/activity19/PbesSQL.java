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
	final String COMPANYNAME="My First Company";
	final Integer COMPANYID = 1000;
	
	public PbesSQL(Integer numberOfCustomers) {
		super(numberOfCustomers); // TODO remove number of customers in constructor
		db = new SQLiteStorage("activity19");
		String query = "INSERT INTO Company VALUES ("+COMPANYID+",0.00,\'"+COMPANYNAME+"\')";
		db.ownSQLCommand(query,null);
	}

	@Override
	public boolean addCustomer(CustomerAbstract customer) {
		String sql = " INSERT INTO Customer VALUES " + customer.exportSQLText(this.getCompany());
		// TODO run SQL
	};

	@Override
	public boolean saveCustomer(CustomerAbstract currentCustomer) {
		return false; //TODO not yet implemented
	}

	@Override
	public CustomerAbstract getCustomer(Integer searchId) {
		String query = "SELECT * FROM Customers WHERE ID=" + searchId;
		CustomerSQL customer = (CustomerSQL) db.ownSQLCommand(query,"CustomerSQL");
		return customer;
	}

	@Override
	public boolean deleteCustomer(Integer searchId) {
		return false; //TODO not yet implemented
	}
	
	@Override
	public boolean getCustomers(Integer searchId) {
		return false; //TODO not yet implemented
	}

}
