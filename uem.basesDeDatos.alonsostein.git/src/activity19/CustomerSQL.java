package activity19;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.FileHandler;
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

	private final static Logger LOG = Logger.getLogger(SQLiteStorage.class
			.getName());
	@SuppressWarnings("unused")
	private FileHandler fh;
	private int owner;

	/**
	 * Constructor for a CustomerSQL Object
	 * 
	 * @param newName
	 * @param newCellPhoneNumber
	 * @param newId
	 * @param Owner
	 * @param db
	 */
	public CustomerSQL(String newName, String newCellPhoneNumber,
			Integer newId, Integer Owner, SQLiteStorage db) {
		super(newName, newCellPhoneNumber, newId);
		this.setDb(db);
		if(db==null){
			LOG.warning("CustomerSQL Init with invalid db");
		}		
	}

	/**
	 * Constructor for the creation of a CustomerSQL object based on an SQL
	 * result set and some minimum fields
	 * 
	 * @param rs
	 * @param id
	 * @param name
	 * @param cellPhoneNumber
	 * @param sqlLog
	 * @author benste
	 */
	public CustomerSQL(ResultSet rs, Integer id, String name,
			String cellPhoneNumber, Logger sqlLog, SQLiteStorage db) {
		super(name, cellPhoneNumber, id);
		LOG.entering("CustomerSQL", "Constructor with Resultset - logs in SQL because of RS usage");
		try {
			sqlLog.fine("Creating a Customer with ResultSet Constructor");
			this.setOwner(rs.getInt("Owner"));
			sqlLog.finest("finished Owner");
			this.setRate(rs.getInt("Rate"));
			sqlLog.finest("finished Rate");
			this.setLandlinePhoneNumber(rs.getString("LandlinePhoneNumber"));
			sqlLog.finest("finished LandlinePhoneNumber");
			this.setAirtimeMinutes(rs.getInt("AirTimeMinutes"));
			sqlLog.finest("finished AirTimeMinutes");
		} catch (Exception e) {
			sqlLog.warning("An error occured in the CustomerSQL constructor: "
					+ e);
		}
		// linking with DB
		this.setDb(db);
		if(db==null){
			LOG.warning("CustomerSQL Init with invalid db");
		}		
		LOG.finest("finished DB");
		
		LOG.exiting("CustomerSQL", "Constructor with Resultset");
	}

	/**
	 * Simple Constructor from Text in DataFile
	 * REQUIRES setting the DB object later from PBES
	 * @param customerText
	 */
	public CustomerSQL(String customerText) {
		super(customerText);
		this.setDb(null);
		LOG.finest("CustomerSQL Init with invalid db - should only be used when importing from a file and adding information for DB from PBES manully");
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
		LOG.entering("CustomerSQL", "exportSQLText");
		String[] parts = new String[Customer.IMPLEMENTEDARGS];
		parts[0] = Integer.toString(this.getId());
		parts[1] = Integer.toString(this.getOwner());
		parts[2] = Integer.toString(this.getRate());
		parts[3] = "\'" + this.getName() + "\'";
		parts[4] = "\'" + this.getCellPhoneNumber() + "\'";
		parts[5] = "\'" + this.getLandlinePhoneNumer() + "\'";
		parts[6] = Integer.toString(this.getAirtimeMinutes());
		// parts[7] = this.getBalance().toString();
		// parts[8] = this.getBalance().toString();
		// parts[9] = this.getBalance().toString();

		// Parse the Array into a String
		String result = new String("VALUES(");
		for (String part : parts) {
			result += (part + ",");
		} // end for all items
			// remove last ,
		result = result.substring(0, result.length() - 1);
		result += ")";
		LOG.exiting("CustomerSQL", "exportSQLText");
		return result;
	} // end exportText()

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<CustomerCall> getCalls() {
		String query = "SELECT * FROM CustomerCalls WHERE OriginID="
				+ this.getId() + ";";
		ArrayList<CustomerCall> calls = (ArrayList<CustomerCall>) db
				.ownSQLCommand(query, "ArrayList<CustomerCall>");
		return calls;
	}

	@Override
	public void addCall(CustomerCall newCall) {
		LOG.entering("CustomerSQL", "addCall");
		// get next empty ID using the existing calls in the SQL
		String query = "SELECT MAX(ID) FROM CustomerCalls;";
		LOG.finest("Defined Query Text: " + query);	
		Integer freeID = (Integer) db.ownSQLCommand(query, "Integer");
		LOG.fine("Found Max ID with: " + freeID);
		freeID++;

		// ID,BillID,OriginID,
		String values1 = Integer.toString(freeID) + ",NULL,"
				+ Integer.toString(this.getId()) + ",";
		LOG.finest("Created 1. part of Values String: "+values1);
		// Destination,startTime<char(50)>,Duration
		String values2 = newCall.getDestination() + ",\'"
				+ newCall.getStartTimeString() + "\'," + newCall.getDuration();
		LOG.finest("Created 2. part of Values String: "+values2);
		String query2 = "INSERT INTO CustomerCalls VALUES(" + values1 + values2
				+ ");";
		LOG.finest("Merged Strings to a query"+query2);

		// finally run the real insert query
		db.ownSQLCommand(query2, null);
	} // end addCall

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

	/**
	 * @return the db
	 */
	public SQLiteStorage getDb() {
		return db;
	}

	/**
	 * @param db the db to set
	 */
	public void setDb(SQLiteStorage db) {
		this.db = db;
	}

}
