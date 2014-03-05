package activity10;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator; //needed for sorting 

import activity13.CustomerCall;

public class Customer extends CustomerAbstract implements Comparator<Customer> {

	// Setup NUMBRES and LETTERS constants for validations
	public final static String NUMBERS = "+ 0123456789";
	public final static String LETTERS = "abcdefghijklmnopqrstuvwxyz";
	public final static Integer IMPLEMENTEDARGS = 7; // number of arguments
														// implemented for a
														// customer
	private BigDecimal minBalance = new BigDecimal(0, new MathContext(3,
			RoundingMode.HALF_UP)); //in full ï¿½ cutting of Cents
	private ArrayList<CustomerCall> calls = new ArrayList<CustomerCall>(0);

	/**
	 * Constructor for a new Custoemr object
	 * 
	 * @param newName
	 * @param newCellPhoneNumber
	 * @param newId
	 * @author benste
	 */
	public Customer(String newName, String newCellPhoneNumber, Integer newId) {
		// Call SuperConstructor
		super(newName, newCellPhoneNumber, newId);
		// SAVE all data assume it's already validated
		this.id = newId;
		this.name = newName;
		this.cellPhoneNumber = newCellPhoneNumber;
		this.landlinePhoneNumber = "";
		this.airtimeMinutes = 0;
		this.balance = new BigDecimal(0, new MathContext(3,
				RoundingMode.HALF_UP));
		this.minBalance = new BigDecimal(0, new MathContext(3,
				RoundingMode.HALF_UP));
		this.rate = 0;
	} // end Customer()

	/**
	 * Constructor for a new Customer object based on an import from Text
	 * 
	 * @param a
	 *            String representing fully qualified Customer for Text import
	 * @author benste
	 * @throws Exception
	 *             - error when Customer can not be imported
	 */
	public Customer(String fullyQualifiedExportedCustomerString)
			throws Exception {
		// run parent constructor
		super(fullyQualifiedExportedCustomerString);
		// import from text
		boolean success = this.importText(fullyQualifiedExportedCustomerString);
		if (!(success)) {
			System.out
					.println("Import returned false - check string import function");
		} // END success check
	} // END Customer()

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

	/*
	 * BEGIN OF SECTION WITH COMPARATORS
	 */
	@Override
	/**
	 * compare by selected method
	 */
	public int compare(Customer arg0, Customer arg1) {
		// Choose here the default operation for comparing
		if ((arg0 != null) && (arg1 != null)) { // no object exists
			return this.compareId(arg0, arg1);
		} else if ((arg0 != null)) {// existing object 1 is smaller
			return -1;
		} else if ((arg1 != null)) {// existing object 2 is smaller
			return 1;
		} else // no object exist - they're even
		{
			return 0;
		}// End compare()
	}

	/**
	 * compare by Balance
	 */
	public int compareBalance(Customer arg0, Customer arg1) {
		return arg0.getBalance().compareTo(arg1.getBalance());
	} // End compareBalance()

	/**
	 * compare by Name
	 */
	public int compareName(Customer arg0, Customer arg1) {
		return arg0.getName().compareTo(arg1.getName()); //
	} // End compareName()

	/**
	 * compare by Rate
	 */
	public int compareRate(Customer arg0, Customer arg1) {
		return arg0.getRate().compareTo(arg1.getRate());
	} // End compareRate()

	/**
	 * compare by ID
	 */
	public int compareId(Customer arg0, Customer arg1) {
		return arg0.getId().compareTo(arg1.getId());
	} // End compareId

	/*
	 * END OF SECTION WITH COMPARATORS
	 */

	/*
	 * Set and Get Methods for Attributes
	 */

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
			} // end of checking the integrity of the number characters
		} // end for checking all signs
		if (!(newPhoneNumber.length() <= 9)) { // check that number is not too
												// long
			System.out.println("Landline number is too long");// DEBUG warning
			return false;
		} // end phone number length check
		this.landlinePhoneNumber = newPhoneNumber; // save the number
		return true; // acknowledge success
	} // end setLandline...()

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
			// get each character
			String c = String.valueOf(newPhoneNumber.charAt(i));
			if ((i == 0) && (NUMBERS.contains(c) || c.contains("+"))) {
				// pass if first sign is a number or +
			} else if (NUMBERS.contains(c)) {
				// pass for all other letters if they are numbers only
			} else {
				return false; // if any character is wrong
			} // end cases for character checking
		} // end FOR all characters
			// check that number is not too long
		if (!(newPhoneNumber.length() <= 9)) {
			System.out.println("CellphoneNumber is too long");// DEBUG warning
			return false;
		} // end IF length check
		this.cellPhoneNumber = newPhoneNumber; // save the number
		return true; // acknowledge success
	} // end setCellPhone...

	/**
	 * Adds the specified ammount of mintues to the existing airtime
	 * 
	 * @author benste
	 * @param airtimeMinutes
	 *            total minutes to be added into account
	 */
	public boolean addAirtimeMinutes(Integer airtimeMinutes) {// TODO simply set
																// by default
		if (this.airtimeMinutes != null) {// needed for importing first
											// customers
			this.airtimeMinutes += airtimeMinutes;
		} else {
			this.airtimeMinutes = airtimeMinutes;
		} // end null check
		return true;
	} // end setAirtime...

	/**
	 * set a new Rate for the customer cellphone contract in Cents per Minute
	 * 
	 * @author benste
	 * @param new rate in cents
	 * @return Success of operation
	 */
	@Override
	public boolean setRate(Integer rate) {
		this.rate = rate;
		return true;
	} // end setRate

	/**
	 * set a new Name for the customer
	 * 
	 * @author benste
	 * @param new name
	 * @return success of operation
	 */
	public boolean setName(String newName) {
		this.name = newName;
		return true;
	} // end setName

	/**
	 * overwrite the ID of this item - use with great care and consideration
	 * ONLY
	 * 
	 * @author benste
	 * @param new ID (please verfiy it's validity before!)
	 * @return success of operation
	 */
	public boolean setId(Integer newId) {
		// check that ID is a valid positve number with up to 8 characters
		if ((newId < 100000000) && (newId >= 0)) {
			this.id = newId;
			return true;
		} // end if - max ID
		return false;
	} // end setId()

	/**
	 * Get Balance in full EURO, NEW Version - taking into account the minimum
	 * balance per customer
	 */
	public BigDecimal getBalance() {
		BigDecimal minBalance = this.getMinBalance(); // get customer min Balance
		if (this.balance.compareTo(minBalance)== 1) {
			return this.balance;
		} //end if
		return minBalance;
	} //end getBalance
	
	/**
	 * calculate Balance in € based on given formula
	 * and reset the Minutes used as they have been billed to balance
	 */
	public void setBalance() {
		/* DEBUGGING Part for Balance Calculation //TODO
		System.out.println("Rate= "+this.getRate());
		System.out.println("Airtime= "+this.getAirtimeMinutes());
		System.out.println("Rate*Airtime= "+this.getRate()*this.getAirtimeMinutes());
		System.out.println("Rate*Airtime/100= "+(this.getRate())*this.getAirtimeMinutes()/100.0);
		*/
		this.balance = 
				new BigDecimal((this.getRate() * this.getAirtimeMinutes()) /100.00,new MathContext(4,
						RoundingMode.HALF_UP)); //costs are negative rates
		System.out.println("saved balance="+this.balance);
		this.setAirtimeMinutes(0);
	} //end setBalance
	

	/**
	 * @return the minBalance
	 */
	public BigDecimal getMinBalance() {
		return minBalance;
	}

	/**
	 * @param minBalance
	 *            the minBalance to set
	 */
	public void setMinBalance(BigDecimal minBalance) {
		this.minBalance = minBalance;
	}

	/**
	 * @return the calls
	 */
	public ArrayList<CustomerCall> getCalls() {
		return calls;
	}

	/**
	 * @param the call to be appended to the List of Calls
	 */
	public void addCall(CustomerCall newCall) {
		this.calls.add(newCall);
	}
	
	/**
	 * Read the Customers Call Log to calculate the minutes which were used
	 */
	public void addAirtimeMinutesFromCalls(){
		for (CustomerCall call : this.calls){
			this.airtimeMinutes += call.getDuration();
		}
	}

	/**
	 * Method to make sure that the Customer is shown in a user readable format
	 * in case it needs to get displayed
	 * 
	 * @author benste
	 * @return a String for easy display of the Object
	 */
	public String toString() {
		return exportText();
	} // End toString()

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
		boolean success = true; // init success
		// System.out.println("Import line for this customer is: " +
		// savedCustomer); //DEBUG only
		String[] parts = savedCustomer.split(","); // split by delimiter
		if (parts.length != Customer.IMPLEMENTEDARGS) {
			System.out.println("WARNING - wrong number of items: "
					+ parts.length);
			return false;
		} // end if numberofArgs Check
			// check that first item is the ID with a <ID> formatting
		else if (!(parts[0].startsWith("<") && parts[0].endsWith(">"))) {
			System.out.println("WARNING - import has wrong formatting");
			return false;
		} else // if checks succeeds
		{
			/*
			 * This following few lines do save the recognized parts into the
			 * respective fields
			 */
			String idText = parts[0];
			idText = idText.substring(1, idText.length() - 1);
			success = success && this.setId(Integer.parseInt(idText));
			success = success && this.setName(parts[1]);
			if (!(this.setCellPhoneNumber(parts[2]))) {
				success = false;
			} // end if
			if (!(this.setLandlinePhoneNumber(parts[3]))) {
				success = false;
			} // end if
			success = success
					&& this.setAirtimeMinutes(Integer.parseInt(parts[4]));
			success = success && this.setRate(Integer.parseInt(parts[5]));
			this.balance = new BigDecimal(parts[6], new MathContext(4,
					RoundingMode.HALF_UP));
		} // end if validated parts
		return success;
	} // end importText()

	/**
	 * Function which export an existing customer object with a String formated
	 * the following way:
	 * 
	 * @return {@literal <}
	 *         FieldId>,FieldName,FieldCell,FieldLand,FieldAir,FieldRate,
	 *         FieldBalance;
	 * @author benste
	 */
	public String exportText() {
		/*
		 * The following section does create an array with the args of the class
		 */
		String[] parts = new String[Customer.IMPLEMENTEDARGS];
		parts[0] = "<" + Integer.toString(this.getId()) + ">";
		parts[1] = this.getName();
		parts[2] = this.getCellPhoneNumber();
		parts[3] = this.getLandlinePhoneNumer();
		parts[4] = Integer.toString(this.getAirtimeMinutes());
		parts[5] = Integer.toString(this.getRate());
		parts[6] = this.getBalance().toString();
		// Parse the Array into a String
		String result = "";
		for (String part : parts) {
			result += part + ",";
		} // end for all items
		return result;
	} // end exportText()
} // end class
