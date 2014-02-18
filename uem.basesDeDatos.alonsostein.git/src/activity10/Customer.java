package activity10;
//This is a test commit for conflicting changes
import java.util.Comparator; //needed for sorting 

public class Customer extends CustomerAbstract implements Comparator<Customer> {

	// Setup NUMBRES and LETTERS constants for validations
	public final static String NUMBERS = "+ 0123456789";
	public final static String LETTERS = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * Constructor for a new Custoemr object
	 * 
	 * @param newName
	 * @param newCellPhoneNumber
	 * @param newId
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
		this.airtimeMinutes += airtimeMinutes;
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
}
