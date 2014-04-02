package activity19;

import java.util.ArrayList;

import activity10.Customer;
import activity13.CustomerCall;

/**
 * This is the SQL Version of our Customer object, most important is the different implementation of the storage of call
 * @author benste
 *
 */
public class CustomerSQL extends Customer {
	
	private SQLiteStorage db;
	private String owner;
	
	public CustomerSQL(String newName, String newCellPhoneNumber, Integer newId, Integer Owner, SQLiteStorage db) {
		super(newName, newCellPhoneNumber, newId);
		this.db = db;
	}

	/**
	 * Function which export an existing customer object as a VALUES part for an SQL statement
	 * 
	 * @return VALUES(ID,Owner,Rate,Name,CellPhoneNumber,LandlinePhoneNumber,AirtimeMinutes) 
	 *         
	 * @author benste
	 */
	public String exportSQLText() {
		/*
		 * The following section does create an array with the args of the class
		 */
		String[] parts = new String[Customer.IMPLEMENTEDARGS+1]; // parent
		parts[0] = Integer.toString(this.getId())+",";
		parts[1] = "\'"+this.getOwner()+"\'"+",";
		parts[2] = Integer.toString(this.getRate())+",";
		parts[3] = this.getName()+",";
		parts[4] = "\'"+this.getCellPhoneNumber()+"\'"+",";
		parts[5] = "\'"+this.getLandlinePhoneNumer()+"\'"+",";
		parts[6] = Integer.toString(this.getAirtimeMinutes())+",";
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
		String query = "SELECT * FROM Calls WHERE CustomerId=" + this.getId() + ";";
		ArrayList<CustomerCall> customer = (ArrayList<CustomerCall>) db.ownSQLCommand(query,"ArrayList<Calls>");
		return customer;
	}
	@Override
	public void addCall(CustomerCall newCall) {
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

}
