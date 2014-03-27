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
		// TODO Auto-generated constructor stub
	}

	public CustomerSQL(String fullyQualifiedExportedCustomerString)
			throws Exception {
		super(fullyQualifiedExportedCustomerString);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public ArrayList<CustomerCall> getCalls() {
	}
	@Override
	public void addCall(CustomerCall newCall) {
	}

}
