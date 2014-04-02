package activity19;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

import activity10.Customer;
import activity13.CustomerCall;

/**
 * This is the SQL Version of our Customer object, most important is the
 * different implementation of the storage of call
 * 
 * @author benste
 * 
 */
public class CustomerSQL extends Customer {

	private SQLiteStorage db;
	private int owner;

	/**
	 * Constructor for a CustomerSQL Object
	 * @param newName
	 * @param newCellPhoneNumber
	 * @param newId
	 * @param Owner
	 * @param db
	 */
	public CustomerSQL(String newName, String newCellPhoneNumber,
			Integer newId, Integer Owner, SQLiteStorage db) {
		super(newName, newCellPhoneNumber, newId);
		this.db = db;
	}

	/**
	 * Constructor for the creation of a CustomerSQL object based on an SQL result set and some minimum fields
	 * @param rs
	 * @param id
	 * @param name
	 * @param cellPhoneNumber
	 * @param sqlLog
	 * @author benste
	 */
	public CustomerSQL(ResultSet rs, Integer id, String name, String cellPhoneNumber, Logger sqlLog) {
			super(name,cellPhoneNumber,id);
			try {
				sqlLog.fine("Creating a Customer with ResultSet Constructor");
			this.setOwner(rs.getInt("Owner"));
			this.setLandlinePhoneNumber(rs.getString("LandlinePhoneNumber"));
			this.setRate(rs.getInt("Rate"));
			this.setAirtimeMinutes(rs.getInt("AirTimeMinutes"));
			this.setOwner(rs.getInt("Owner"));
		} catch (Exception e) {
			sqlLog.warning("An error occured in the CustomerSQL constructor: "+e);
		}
	}

	/**
	 * Function which export an existing customer object as a VALUES part for an
	 * SQL statement
	 * 
	 * @return VALUES(ID,Owner,Rate,Name,CellPhoneNumber,LandlinePhoneNumber,
	 *         AirtimeMinutes)
	 * 
	 * @author benste
	 */
	public String exportSQLText() {
		/*
		 * The following section does create an array with the args of the class
		 */
		String[] parts = new String[Customer.IMPLEMENTEDARGS + 1]; // parent
		parts[0] = Integer.toString(this.getId()) + ",";
		parts[1] = "\'" + this.getOwner() + "\'" + ",";
		parts[2] = Integer.toString(this.getRate()) + ",";
		parts[3] = this.getName() + ",";
		parts[4] = "\'" + this.getCellPhoneNumber() + "\'" + ",";
		parts[5] = "\'" + this.getLandlinePhoneNumer() + "\'" + ",";
		parts[6] = Integer.toString(this.getAirtimeMinutes()) + ",";
		parts[7] = this.getBalance().toString();

		// Parse the Array into a String
		String result = "VALUES(";
		for (String part : parts) {
			result += part + ",";
		} // end for all items
		result += ")";
		return result;
	} // end exportText()

	@Override
	public ArrayList<CustomerCall> getCalls() {
		String query = "SELECT * FROM Calls WHERE CustomerId=" + this.getId()
				+ ";";
		ArrayList<CustomerCall> customer = (ArrayList<CustomerCall>) db
				.ownSQLCommand(query, "ArrayList<Calls>");
		return customer;
	}

	@Override
	public void addCall(CustomerCall newCall) {
	}

	/**
	 * @return the owner
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * @param i
	 *            the owner to set
	 */
	public void setOwner(int i) {
		this.owner = i;
	}

}
