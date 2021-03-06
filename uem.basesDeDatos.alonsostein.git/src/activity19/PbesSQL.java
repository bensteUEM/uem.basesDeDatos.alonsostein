/**
 * 
 */
package activity19;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
	private static final long serialVersionUID = -2637791780598587562L;
	SQLiteStorage db;
	final String COMPANYNAME = "My First Company";
	final Integer COMPANYID = 1000;
	private final static Logger LOG = Logger.getLogger(SQLiteStorage.class
			.getName());
	private FileHandler fh;

	public static void main(String[] args) {
		PbesSQL gui = new PbesSQL();
		// Database
		gui.db = new SQLiteStorage("activity19");
		PbesSQL.LOG.setLevel(Level.ALL);
		
		//check DB consistency
		String query = "SELECT CIF FROM Owner WHERE CIF="+gui.getCompanyCIF()+";";
		Integer checkCIF = (Integer) gui.db.ownSQLCommand(query,"Integer");
		if ((gui.getCompanyCIF() - checkCIF) == 0){
			gui.setTitle("PBES - SQLite Version � CIF("+ checkCIF+ ")");
			LOG.info("Started PBES SQL registered for Company with CIF: "+checkCIF);
		}
		else {
			LOG.warning("Empty database or unknown license - make sure you Own the Database (if unsure run DB.init())");
			gui.setTitle("PBES - SQLite Version (DEMO ONLY - no CIF or Database)");
		}
		gui.setVisible(true);
	}

	/**
	 * Constructor for the PbesSQL Class
	 * 
	 * @author benste
	 */
	public PbesSQL() {
		// invoke Super Constructor
		super();
		// Create logfile on filesystem
		try {
			fh = new FileHandler("Logs"+File.separator+"execution.log");
			LOG.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onAddCustomer(ActionEvent ae) {
		CustomerSQL newCustomer = new CustomerSQL("", "",
				Integer.parseInt(this.txtSearch.getText()),
				this.getCompanyCIF(), this.db);
		// function to add new customer
		if (!(this.addCustomer(newCustomer))) {
			JOptionPane
					.showMessageDialog(
							this,
							"<html>Something went wrong when trying to save a new user <br> you might have tried to exceed your user maximum</html>");
		} else {
			new GuiUserModificator(this, newCustomer);
			// this will run the Modificator hiding this window until action was
			// successful
		}
	}
	@Override
	public void onInitiateDatabase(ActionEvent ae) {
		db.init();
		String query = "INSERT INTO Owner VALUES (" + getCompanyCIF()
				+ ",0.00,\'" + getCompanyName() + "\')";
		db.ownSQLCommand(query, null);
	}
	
	
	@Override
	public boolean addCustomer(CustomerAbstract customer) {
		LOG.entering("PBES SQL", "addCustomer");
		CustomerSQL sqlcustomer = (CustomerSQL) customer;
		LOG.finest("Casted customer");
		if (sqlcustomer.getDb() == null) {
			LOG.info("Customer did not have a DB Connection");
			sqlcustomer.setDb(db);
			LOG.finest("Added following DB to the Customer" + db);
		}
		LOG.finest("Customer should have a DB now");
		LOG.finest("Customer minimum Balance updated with Global Value");

		Integer newId;
		String query = "SELECT Count(ID) FROM Customers WHERE ID="
				+ sqlcustomer.getId() + ";";
		// as long as the current customer has an id that already exists
		while ((Integer) db.ownSQLCommand(query, "Integer") > 0) {
			LOG.info("The ID is already in use, trying to find a new one");
			try {
				newId = Integer
						.parseInt(JOptionPane
								.showInputDialog(this,
										"This ID already exists or has a problematic format please choose a new one"));
				query = "SELECT Count(ID) FROM Customers WHERE ID=" + newId
						+ ";";
			} catch (Exception e) { // reset ID to original choice
				newId = sqlcustomer.getId(); // in case ID was not valid format
			}
			((CustomerSQL) sqlcustomer).setId(newId); // set a new ID
		}
		LOG.fine("ID Accepted, preparing for SQL");
		String sql = " INSERT INTO Customers " + sqlcustomer.exportSQLText()
				+ ";";
		db.ownSQLCommand(sql, null);
		LOG.exiting("PbesSQL", "addCustomer");
		return true;
	};

	@Override
	/**
	 * Lazy Version to update the SQL DB with the current user data
	 */
	public boolean saveCustomer(CustomerAbstract currentCustomer) {
		// 1. Delete old User
		String sql = " DELETE FROM Customers WHERE ID="
				+ currentCustomer.getId() + ";";
		db.ownSQLCommand(sql, null);
		// 2. Add new user
		// Better would be an update statement but it requires a different
		// formatting of the values
		CustomerSQL customer = (CustomerSQL) currentCustomer;
		sql = " INSERT INTO Customers " + customer.exportSQLText() + ";";
		db.ownSQLCommand(sql, null);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerAbstract getCustomer(Integer searchId) {
		LOG.entering("PbesSQL", "getCustomer");
		String query = "SELECT * FROM Customers WHERE ID=" + searchId + ";";
		LOG.finest("Defined following SQL query: " + query);
		/*
		 * WORKAROUND required because of missing SQL Function
		 * 
		 * CustomerSQL customer = (CustomerSQL) 
		 * db.ownSQLCommand(query,"CustomerSQL");
		 * 
		 * original code: LOG.exiting("PbesSQL", "getCustomer");
		 */
		LOG.info("WORKAROUND applied because of missing function in SQLite API");	
		Object temp = db.ownSQLCommand(query, "ArrayList<CustomerAbstract>");
		LOG.finest("WORKAROUND : got LIST of customers element: " + temp);
		CustomerSQL customer = null; // init variable
		if (temp != null) {
			LOG.finest("END WORKAROUND : >0");
			if (((ArrayList<CustomerAbstract>) temp).size()>0){
			customer = (CustomerSQL) ((ArrayList<CustomerSQL>)temp).get(0);
			}
		} else {
			LOG.finest("END WORKAROUND : <0 return null");
		}
		/*
		 * END WORKAROUND
		 */

		if (customer == null) {
			LOG.fine("No customer found with this ID");
			return null;
		} else {
			LOG.fine("Customer found with this ID: " + customer);
			return customer;
		}// end if null
	}// end getCustomer method
	
	/**
	 * Method to return all customers
	 * 
	 * @return ArrayList of all Customers in the Database
	 */
	@Override
	public ArrayList<CustomerAbstract> getCustomersAboveRate(Integer rate) {
		LOG.entering("PbesSQL", "getCustomersAboveRate");
		String query = "SELECT * FROM Customers WHERE Rate>" + rate + ";";
		LOG.finest("defined SQL statement");
		@SuppressWarnings("unchecked")
		// is checked with method param
		ArrayList<CustomerAbstract> customers = (ArrayList<CustomerAbstract>) db
				.ownSQLCommand(query, "ArrayList<CustomerAbstract>");
		LOG.fine("excecuted SQL Statement with result: " + customers);
		LOG.exiting("PbesSQL", "getCustomersAboveRate");
		return customers;
	}
	
	public ArrayList<CustomerAbstract> getCustomers() {
		LOG.entering("PbesSQL","getCustomers");
		return this.getCustomersAboveRate(Integer.MIN_VALUE);
	}

	@Override
	public boolean deleteCustomer(Integer searchId) {
		LOG.entering("PbesSQL", "deleteCustomer");
		
		CustomerAbstract customer = this.getCustomer(searchId);
		customer.deleteCalls();
		customer.deleteBills();
		
		String query = "DELETE FROM Customers WHERE ID="
				+ searchId + ";";	
		LOG.finer("Defined SQL query: "+query);
		db.ownSQLCommand(query, null);
		LOG.exiting("PbesSQL", "deleteCustomer");
		return true;
	}

	/**
	 * Method to return the cumulative balance of all customers
	 * 
	 * @return ArrayList of all Customers in the Database
	 */
	@Override
	public BigDecimal getAllCustomerBalance() {
		String query = "SELECT ID,Name FROM Customers;";
		// is checked with method param
		BigDecimal money = (BigDecimal) db.ownSQLCommand(query, "BigDecimal");
		return money;
	}

	/**
	 * GETTER company ID
	 * @return
	 */
	public Integer getCompanyCIF() {
		return COMPANYID;
	}

	/**
	 * GETTER company Name
	 * @return
	 */
	public String getCompanyName() {
		return COMPANYNAME;
	}
	
	/**
	 * Getter Minimum Balance
	 * @return currentMinBalance
	 */
	@Override
	public BigDecimal getMinimumBalance() {
		LOG.entering("PbesSQL","getMinimumBalance");
		String query = "SELECT MinimumConsumption FROM Owner WHERE CIF="+this.getCompanyCIF()+";";
		LOG.finest("Defined Query: "+query);
		BigDecimal consumption = (BigDecimal) db.ownSQLCommand(query,"BigDecimal-One");
		LOG.finest("will return: "+consumption);
		LOG.exiting("PbesSQL","getMinimumBalance");
		return consumption;
	}

	/**
	 * Setter Minimum Balance
	 * @param newMinBalance
	 */
	@Override
	public void setMinimumBalance(BigDecimal newBalance) {
		LOG.entering("PbesSQL","setMinimumBalance");
		String query = "UPDATE Owner SET MinimumConsumption="+newBalance.toString()+" WHERE CIF="+this.getCompanyCIF()+";";
		LOG.finest("Defined Query: "+query);
		db.ownSQLCommand(query,null);
		LOG.exiting("PbesSQL","setMinimumBalance");
	}

}
