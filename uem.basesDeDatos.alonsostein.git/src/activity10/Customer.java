package activity10;

import java.util.Comparator; //needed for sorting 

import javax.swing.JTextField;

public class Customer extends CustomerAbstract implements Comparator<Customer> {

	// Setup NUMBRES and LETTERS constants for validations
	public final static String NUMBERS = "+ 0123456789";
	public final static String LETTERS = "abcdefghijklmnopqrstuvwxyz";
	public final static Integer IMPLEMENTEDARGS = 7; // number of arguments
														// implemented for a
														// customer

	/**
	 * Constructor for a new Custoemr object
	 * 
	 * @param newName
	 * @param newCellPhoneNumber
	 * @param newId
	 * @author benste
	 */
	public Customer(String newName, String newCellPhoneNumber, Integer newId) {
		super(newName, newCellPhoneNumber, newId);
		// SAVE all data assume it's already validated
		this.id = newId;
		this.name = newName;
		this.cellPhoneNumber = newCellPhoneNumber;
		this.landlinePhoneNumber = "";
		this.airtimeMinutes = 0; // Amount of minutes consumed
		this.balance = 0; // Balance of Account in Euros
		this.rate = 0; // per minute in Cents
	}

	/**
	 * Constructor for a new Custoemr object based on an import from Text
	 * 
	 * @param a
	 *            String representing a fully qualified Customer for Text import
	 * @author benste
	 * @throws Exception
	 *             - error when Customer can not be imported
	 */
	public Customer(String fullyQualifiedExportedCustomerString)
			throws Exception {
		super(fullyQualifiedExportedCustomerString); // run parent constructor
		boolean success = this.importText(fullyQualifiedExportedCustomerString); // import
																					// from
																					// text
		if (!(success)) {
			System.out
					.println("Import returned false - check string import function");
		}
	}

	/**
	 * Additional payment functionality modifies the customers balance by the
	 * ammount paid
	 * 
	 * @author benste
	 * @param cashAmount
	 *            - Value in Cents which gets paid
	 */
	@Override
	public Integer payBalance(Integer cashAmount) {
		return cashAmount;
		/*
		 * This is not part of the evaluated project but a future extension
		 * Integer currentBalance = this.getBalance(); this.balance =
		 * currentBalance + cashAmount; if (currentBalance + cashAmount > 0) {
		 * this.balance = 0; return currentBalance + cashAmount; } else { //
		 * there is still something outstanding return 0; }
		 */
	}

	// Comparators for sorting by different attributes
	@Override
	/**
	 * compare by Balance
	 */
	public int compare(Customer arg0, Customer arg1) {
		return arg0.getBalance().compareTo(arg1.getBalance());
	}

	/**
	 * compare by Name
	 */
	public int compareName(Customer arg0, Customer arg1) {
		return arg0.getName().compareTo(arg1.getName()); //
	}

	/**
	 * compare by Rate
	 */
	public int compareRate(Customer arg0, Customer arg1) {
		return arg0.getRate().compareTo(arg1.getRate());
	}

	// set and get Methods for attributes

	@Override
	/**
	 * Set a new Landline Phone number
	 * @author benste
	 * @param new phone number as String
	 * @return success of the operation
	 */
	public boolean setLandlinePhoneNumber(String newPhoneNumber) {
		// iterate through all signs of the String
		for (int i = 0, n = newPhoneNumber.length(); i < n; i++) {
			// save the one sign
			String c = String.valueOf(newPhoneNumber.charAt(i));
			if ((i == 0) && (NUMBERS.contains(c) || c.contains("+"))) {
				// pass if first sign is a number or +
			} else if (NUMBERS.contains(c)) {
				// pass for all other letters if they are numbers only
			} else {
				return false;
				// if any character is wrong the landline can not be set
			}
		} // end for checking all signs
		if (!(newPhoneNumber.length() <= 9)) { // check that number is not too
												// long
			System.out.println("Landline number is too long");// DEBUG warning
			return false;
		}
		this.landlinePhoneNumber = newPhoneNumber;
		return true;
	}

	/**
	 * Set a new Cell Phone number same as setLandlinePhonenumber - please check
	 * for structural comments there
	 * 
	 * @author benste
	 * @param new phone number as String
	 * @return success of the operation
	 */
	public boolean setCellPhoneNumber(String newPhoneNumber) {
		for (int i = 0, n = newPhoneNumber.length(); i < n; i++) {
			String c = String.valueOf(newPhoneNumber.charAt(i));
			if ((i == 0) && (NUMBERS.contains(c) || c.contains("+"))) {
				// pass if first sign is a number or +
			} else if (NUMBERS.contains(c)) {
				// pass for all other letters if they are numbers only
			} else {
				return false; // if any character is wrong the landline can not
								// be set
			}
		} // end for checking all signs
			// check that number is not too long
		if (!(newPhoneNumber.length() <= 9)) {
			System.out.println("CellphoneNumber is too long");// DEBUG warning
			return false;
		}
		this.cellPhoneNumber = newPhoneNumber;
		return true;
	}

	/**
	 * Adds the specified ammount of mintues to the existing airtime
	 * 
	 * @author benste
	 * @param airtimeMinutes
	 *            total minutes to be added into account
	 */
	@Override
	public boolean setAirtimeMinutes(Integer airtimeMinutes) {
		if (this.airtimeMinutes != null) {//needed for importing customers from 0
			this.airtimeMinutes += airtimeMinutes;
		} else {
			this.airtimeMinutes = airtimeMinutes; 
		}
		return true;
	}

	/**
	 * set a new Rate for the customer cellphone contract in Cents per Minute
	 * 
	 * @author benste
	 * @param new rate in cents
	 */
	@Override
	public boolean setRate(Integer rate) {
		this.rate = rate;
		return true;
	}

	/**
	 * set a new Name for the customer
	 * 
	 * @author benste
	 * @param new name
	 */
	public boolean setName(String newName) {
		this.name = newName;
		return true;
	}

	/**
	 * overwrite the ID of this item - use with great care and consideration
	 * ONLY
	 * 
	 * @author benste
	 * @param new ID (please verfiy it's validity before!)
	 */
	public boolean setId(Integer newId) {
		// check that ID is a valid positve number with up to 8 characters
		if ((newId < 100000000) && (newId >= 0)) {
			this.id = newId;
			return true;
		}
		return false;
	}

	/**
	 * Method to make sure that the Customer is shown in a user readable format
	 * in case it needs to get displayed
	 * 
	 * @author benste
	 */
	public String toString() {
		return "ID: " + this.id + " " + this.name + " Tel: "
				+ this.cellPhoneNumber + " Current Rate: " + this.rate
				+ " Current Minutes: " + this.airtimeMinutes;
	}

	/**
	 * Function which allows to overwrite an existing customer object with a
	 * String formated the following way:
	 * 
	 * @param savedCustomer
	 *            {@literal <}
	 *            FieldId>,FieldName,FieldCell,FieldLand,FieldAir,FieldRate,
	 *            FieldBalance,
	 * @return success of the opertaion
	 * @author benste
	 */
	public boolean importText(String savedCustomer) {
		boolean success = true;
		System.out
				.println("Import line for this cusotmer is: " + savedCustomer); // TODO
																				// DEBUG

		String[] parts = savedCustomer.split(","); // split by delimiter

		if (parts.length != Customer.IMPLEMENTEDARGS) {
			System.out.println("WARNING - wrong number of items: "
					+ parts.length); // TODO check whether +1 is needed
			success = false;
		}
		// check that first item is the ID with a <ID> formatting
		else if (!(parts[0].startsWith("<") && parts[0].endsWith(">"))) {
			System.out.println("WARNING - import has wrong formatting");
			success = false;
		} else // if checks succeeds
		{
			String idText = parts[0];
			idText = idText.substring(1, idText.length() - 1);
			System.out.println("ID with removed outer <> is " + idText); // TODO
																			// debugging
																			// only
			success = success && this.setId(Integer.parseInt(idText));
			success = success && this.setName(parts[1]);
			if (!(this.setCellPhoneNumber(parts[2]))) {
				success = false;
			}
			if (!(this.setLandlinePhoneNumber(parts[3]))) {
				success = false;
			}
			success = success
					&& this.setAirtimeMinutes(Integer.parseInt(parts[4]));
			success = success && this.setRate(Integer.parseInt(parts[5]));
			this.balance = Integer.parseInt(parts[6]);
		}
		return success;
	}

	/**
	 * Function which export an existing customer object with a String formated
	 * the following way:
	 * 
	 * @return {@literal <}
	 *         FieldId>,FieldName,FieldCell,FieldLand,FieldAir,FieldRate,
	 *         FieldBalance;
	 * @author benste
	 */
	public String exportText() { // TODO
		// Parse All Items into an Array
		String[] parts = new String[Customer.IMPLEMENTEDARGS];
		parts[0] = "<" + Integer.toString(this.getId()) + ">";
		parts[1] = this.getName();
		parts[2] = this.getCellPhoneNumber();
		parts[3] = this.getLandlinePhoneNumer();
		parts[4] = Integer.toString(this.getAirtimeMinutes());
		parts[5] = Integer.toString(this.getRate());
		parts[6] = Integer.toString(this.getBalance());
		// Parse the Array into a String
		String result = "";
		for (String part : parts) {
			result += part + ",";
		}
		return result;
	}
}
