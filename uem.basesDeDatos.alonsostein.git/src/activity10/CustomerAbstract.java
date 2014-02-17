package activity10;

public abstract class CustomerAbstract {
	protected String name;
	protected Integer id;
	protected String cellPhoneNumber;
	protected String landlinePhoneNumber;
	protected Integer airtimeMinutes; // Amount of minutes consumed
	protected Integer balance; // Balance of Account in Euros
	protected Integer rate; // per minute

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
	public Integer getBalance(){
		return this.balance;
	}

	/**
	 * calculate Balance in full € based on given formula
	 * and reset the Minutes used as they have been billed to balance
	 */
	public void setBalance() {
		// TODO this is using integer division and cutting of Cents !
		this.balance = (-this.getRate()) * this.getAirtimeMinutes() /100; //costs are negative rates
		this.setAirtimeMinutes(0);
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
}
