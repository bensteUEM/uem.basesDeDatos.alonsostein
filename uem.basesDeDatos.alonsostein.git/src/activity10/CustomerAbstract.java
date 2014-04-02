package activity10;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import activity13.CustomerCall;

/**
 * Abstract of the Customer which was the minimal specification to task 8
 * As this is an abstract methods are commented in the respective implementation
 * @author benste
 *
 */
public abstract class CustomerAbstract {
	protected String name;
	protected Integer id;
	protected String cellPhoneNumber;
	protected String landlinePhoneNumber;
	protected Integer airtimeMinutes; // Amount of minutes consumed
	protected BigDecimal balance; // Balance of Account in Euros
	protected Integer rate; // per minute
	
	protected abstract void setMinBalance(BigDecimal value);

	@SuppressWarnings("unused")
	private CustomerAbstract() {
	}

	/**
	 * Customers require a minimum of data
	 * 
	 * @param new_name
	 * @param new_cellPhoneNumber
	 * @param last_id
	 *            - last used ID, use 0 for first
	 */
	public CustomerAbstract(String newName, String newCellPhoneNumber,
			Integer newId) {
	}
	
	public CustomerAbstract(String fullyQualifiedExportedCustomerString) {
	}

	/**
	 * @return the landlinePhoneNumer
	 */
	public String getLandlinePhoneNumer() {
		return landlinePhoneNumber;
	}

	public abstract boolean setLandlinePhoneNumber(String landlinePhoneNumer);

	public Integer getAirtimeMinutes(){
		return this.airtimeMinutes;
	}

	public boolean setAirtimeMinutes(Integer airtimeMinutes){
		this.airtimeMinutes = airtimeMinutes;
		return true;
	}

	/**
	 * Get Balance in full €
	 */
	public BigDecimal getBalance(){
		return this.balance;
	}

	public abstract Integer payBalance(Integer cashAmount);

	/**
	 * Get the rate of the custoemr
	 * @author benste
	 * @return positve value rate in cents
	 */
	public Integer getRate(){
		return this.rate;
	}

	public abstract boolean setRate(Integer rate);

	public String getName() {
		return this.name;
	}

	public Integer getId() {
		return id;
	}

	/**
	 * @return the cellPhoneNumber
	 */
	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public abstract ArrayList<CustomerCall> getCalls();
}
