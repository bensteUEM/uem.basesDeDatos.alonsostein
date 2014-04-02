/**
 * 
 */
package activity19;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

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
		String query = "INSERT INTO Company VALUES ("+this.getCompanyID()+",0.00,\'"+this.getCompanyName()+"\')";
		db.ownSQLCommand(query,null);
	}

	@Override
	public boolean addCustomer(CustomerAbstract customer) {
		CustomerSQL sqlcustomer = (CustomerSQL) customer; 
		String sql = " INSERT INTO Customer VALUES " + sqlcustomer.exportSQLText();
		db.ownSQLCommand(sql,null);
		return true;
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
	
	/**
	 * Method to return all customers
	 * @return ArrayList of all Customers in the Database
	 */
	@Override
	public ArrayList<Customer> getCustomers() {
		String query = "SELECT * FROM Customers";
		@SuppressWarnings("unchecked") // is checked with method param
		ArrayList<Customer> customers = (ArrayList<Customer>) db.ownSQLCommand(query,"ArrayList<Customer>");
		return customers;
	}
	/**
	 * Method to return all customers
	 * @return ArrayList of all Customers in the Database
	 */
	@Override
	public ArrayList<Customer> getCustomersAboveRate(Integer rate) {
		String query = "SELECT * FROM Customers WHERE Rate>"+rate+";";
		@SuppressWarnings("unchecked") // is checked with method param
		ArrayList<Customer> customers = (ArrayList<Customer>) db.ownSQLCommand(query,"ArrayList<Customer>");
		return customers;
	}
	
	/**
	 * Method to return the cumulative balance of all customers
	 * @return ArrayList of all Customers in the Database
	 */
	@Override
	public BigDecimal getAllCustomerBalance() {
		String query = "SELECT ID,Name FROM Customers;";
		@SuppressWarnings("unchecked") // is checked with method param
		BigDecimal money = (BigDecimal) db.ownSQLCommand(query,"BigDecimal");
		return money;
	}
		
	public Integer getCompanyID() {
		return COMPANYID;
	}
	public String getCompanyName() {
		return COMPANYNAME;
	}

}
