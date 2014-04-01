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
	
	

	public CustomerSQL(String newName, String newCellPhoneNumber, Integer newId) {
		super(newName, newCellPhoneNumber, newId);
	}

	/**
	 * Creates a new Customer based on the response of an SQL query with one user
	 * @param fullyQualifiedSQLReply
	 * @throws Exception
	 */
	public CustomerSQL(String fullyQualifiedSQLReplyString)
			throws Exception {
		//1. create the new customer based on super method
		super(fullyQualifiedSQLReplyString);
		// TODO Auto-generated constructor stub
	}
		/**
	 * Function which export an existing customer object as a VALUES part for an SQL statement
	 * 
	 * @return VALUES(ID,Owner,Rate,Name,CellPhoneNumber,LandlinePhoneNumber,AirtimeMinutes) 
	 *         
	 * @author benste
	 */
	public String exportSQLText(String owner) {
		/*
		 * The following section does create an array with the args of the class
		 */
		String[] parts = new String[Customer.IMPLEMENTEDARGS+1]; // parent
		parts[0] = Integer.toString(this.getId())+",";
		parts[1] = "\'"+owner+"\'"+",";
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
	}
	@Override
	public void addCall(CustomerCall newCall) {
	}

}
