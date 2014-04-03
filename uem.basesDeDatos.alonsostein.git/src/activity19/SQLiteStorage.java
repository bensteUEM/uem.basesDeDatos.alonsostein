package activity19;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import activity10.Customer;
import activity10.CustomerAbstract;
import activity13.CustomerCall;

/**
 * SQLiteStorage Class for use with PBES Project check
 * http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 * 
 * @author benste
 * 
 */
public class SQLiteStorage {
	private String name;
	private Connection c = null;
	private Statement stmt = null;
	private final static Logger LOG = Logger.getLogger(SQLiteStorage.class
			.getName());
	private final static String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	private FileHandler fh;

	/**
	 * SQLiteStorage requires the name for the Database
	 */
	private SQLiteStorage() {
	}

	/**
	 * SQLiteStorage Constructor
	 * 
	 * @param databasename
	 */
	public SQLiteStorage(String databasename) {
		LOG.entering("SQLiteStorage", "Constructor");
		// This is to configure the logger
		LOG.setLevel(Level.ALL);
		// remaining settings
		this.name = databasename;
		this.openDB(this.name);
	}

	/**
	 * Method to properly finish opened SQL Connections
	 * 
	 * @author benste
	 */
	public void finalize() {
		LOG.entering("SQLiteStorage", "finalize");
		try {
			c.close();
			LOG.fine("SQL Connection closed");
		} catch (SQLException e) {
			LOG.warning("Following error occured when trying to close the SQL Connection: "
					+ e);
		}
	}

	/**
	 * This method will initialize an EMPTY database with all the structures
	 * needed as specified by the Diagram
	 * 
	 * @author benste
	 */
	public void init() {
		LOG.entering("SQLiteStorage", "init");
		if (openDB(this.name)) {

			LOG.fine("DELETE ALL Existing Tables");
			String sql0 = "DROP TABLE IF EXISTS Owner;";
			ownSQLCommand(sql0, null);
			sql0 = "DROP TABLE IF EXISTS Customers;";
			ownSQLCommand(sql0, null);
			sql0 = "DROP TABLE IF EXISTS CustomerBill;";
			ownSQLCommand(sql0, null);
			sql0 = "DROP TABLE IF EXISTS CustomerCall;";
			ownSQLCommand(sql0, null);

			LOG.fine("DELETE ALL Existing Tables finished");

			String sql1 = "CREATE TABLE Owner "
					+ "(CIF 					INT 	PRIMARY KEY    	NOT NULL,"
					+ " MinimumConsumption 		REAL, "
					+ " CompanyName         	CHAR(50)     			NOT NULL);";
			ownSQLCommand(sql1, null);
			LOG.finest("Defined table Owner");

			String sql2 = "CREATE TABLE Customers "
					+ "(ID 						INT		PRIMARY KEY		NOT NULL,"
					+ " Owner       			INT					 	NOT NULL, "
					+ " Rate        			INT	    				NOT NULL, "
					+ " Name       				char(50)				NOT NULL, "
					+ " CellPhoneNumber       	char(50), "
					+ " LandlinePhoneNumber     char(50), "
					+ " AirtimeMinutes     		INT, "
					// Reference to Owner Table enforced
					+ " FOREIGN KEY(Owner) REFERENCES Owner(CIF));";
			ownSQLCommand(sql2, null);
			LOG.finest("Defined table Customers");

			String sql3 = "CREATE TABLE CustomerBill "
					+ "(ID 						INT 	PRIMARY KEY     NOT NULL,"
					+ " CustomerID     			INT    					NOT NULL,"
					+ " FileName       			char(50),"
					+ " DateCreated    			char(50),"
					+ " BalanceDue      		REAL,"
					// Reference to Owner Table enforced
					+ " FOREIGN KEY(CustomerID) REFERENCES Customers(ID));";
			ownSQLCommand(sql3, null);
			LOG.finest("Defined table CustomerBill");

			String sql4 = "CREATE TABLE CustomerCall "
					+ "(ID 						INT 	PRIMARY KEY     NOT NULL,"
					+ "BillID 					INT		," + "OriginID					INT 					NOT NULL,"
					+ " Destination				char(50),"
					+ " startTime				char(50)    			NOT NULL,"
					+ " Duration 				INT     				NOT NULL,"
					// Reference to Customer and Bill Table enforced
					+ " FOREIGN KEY(BillID) REFERENCES CustomerBill(ID), "
					+ " FOREIGN KEY(OriginID) REFERENCES Customers(ID));";
			ownSQLCommand(sql4, null);
			LOG.finest("Defined table CustomerCall");

			LOG.fine("All tables structures initialized");
		} // end if
		else {
			LOG.warning("could not open the following database to be initiliazed: "
					+ this.name);
		}
		LOG.exiting("SQLiteStorage", "init");
	}

	/**
	 * Open the Database file and save the connection field
	 * 
	 * @return
	 */
	public boolean openDB(String fileName) {
		LOG.entering("SQLiteStorage", "openDB");
		try {
			Class.forName("org.sqlite.JDBC");
			this.c = DriverManager.getConnection("jdbc:sqlite:" + fileName
					+ ".db");
			LOG.fine("Opened: " + this.c.toString());
			LOG.exiting("SQLiteStorage", "openDB");
			return true;
		} catch (ClassNotFoundException e) {
			LOG.warning(e.toString());
		} catch (SQLException e) {
			LOG.warning(e.toString());
		}
		LOG.exiting("SQLiteStorage", "openDB");
		return false;
	}

	// // TODO i stopped here
	/**
	 * Wrapper to execute SQL Commands without the need to catch exceptions
	 * locally;
	 * 
	 * @param sql
	 * @param args
	 *            - arguments e.g. expected return class
	 * @return value depending on args
	 */
	public Object ownSQLCommand(String sql, String args) {
		LOG.entering("SQLiteStorage", "ownSQLCommand");
		LOG.fine("Attempting to execute following SQL Statement: " + sql);
		Object result = null;
		try {
			this.stmt = c.createStatement();
			LOG.finest("Statement created: " + this.stmt.toString());
			if (args == null) {
				LOG.fine("Executing an SQL querry which is not expected to return a result");
				result = stmt.executeUpdate(sql);
			} else if (args.equals("CustomerSQL")) {
				LOG.fine("Executing an SQL querry which is expected to return ONE CustomerSQL Object");
				ResultSet rs = stmt.executeQuery(sql);
				LOG.fine("Statement Excecuted");
				CustomerSQL customer = new CustomerSQL(rs, rs.getInt("ID"),
						rs.getString("Name"), rs.getString("CellPhoneNumber"),
						LOG);
				LOG.fine("CustomerSQL item created with: " + rs.getInt("ID")
						+ "//" + rs.getString("Name") + "//"
						+ rs.getString("CellPhoneNumber"));
				result = customer;
			} else if (args.equals("ArrayList<Customer>")) {
				LOG.fine("Executing an SQL querry which is expected to return an ArrayList of CustomerSQL Objects");
				ResultSet rs = stmt.executeQuery(sql);
				ArrayList<CustomerSQL> customers = new ArrayList<CustomerSQL>();
				while (rs.next()) {
					CustomerSQL customer = new CustomerSQL(rs, rs.getInt("ID"),
							rs.getString("Name"),
							rs.getString("CellPhoneNumber"), LOG);
					customers.add(customer);
				}
				result = customers;
			} else if (args.equals("Integer-ID")) {	
				LOG.fine("Executing an SQL which should return a simple integer");
				ResultSet rs = stmt.executeQuery(sql);
				result = rs.getInt("ID");
				
			} else if (args.equals("BigDecimal")) {
				LOG.fine("Executing an SQL querry which is expected to return a Double - Balance for one or more than one customer");
				ResultSet rs = stmt.executeQuery(sql);
				BigDecimal money = new BigDecimal(0, new MathContext(3,
						RoundingMode.HALF_UP));
				;
				while (rs.next()) {
					BigDecimal value = new BigDecimal(
							rs.getInt("AirtimeMinutes") * rs.getInt("Rate")
									/ 60.0, new MathContext(3,
									RoundingMode.HALF_UP));
					money = money.add(value);
				}
				result = money;

			} else if (args.equals("ArrayList<CustomerCall>")) {
				LOG.fine("Executing an SQL querry which is expected to return a ArrayList of CustomerCall");
				ResultSet rs = stmt.executeQuery(sql);
				ArrayList<CustomerCall> calls = new ArrayList<CustomerCall>();
				LOG.finest("iterate through all results");
				while (rs.next()) {
					/*
					 * Existing fields are: ID BillID OriginID Destination
					 * startTime<char(50)> Duration
					 */

					// get the customer
					LOG.finest("Attempting to get the Customer of the Current Call");
					String query2 = "SELECT * FROM Customers WHERE ID="
							+ rs.getString("OriginID") + ";";
					ArrayList<CustomerAbstract> customers = (ArrayList<CustomerAbstract>) this
							.ownSQLCommand(query2, "ArrayList<Customer>");
					CustomerAbstract newOrigin = customers.get(0);

					// Calendar Part
					LOG.finest("creating Calendar and importing start Date");
					DateFormat formatter;
					Date date;
					String strDate = rs.getString("startTime");
					formatter = new SimpleDateFormat(DATEFORMAT);
					date = (Date) formatter.parse(strDate);
					Calendar startTime = Calendar.getInstance();
					startTime.setTime(date);

					// Final object
					LOG.finest("creating CustomerCall");
					CustomerCall call = new CustomerCall(newOrigin,
							rs.getString("Destination"), startTime);
					call.setDuration(rs.getInt("duration"));
					LOG.finest("adding this call:" + call);
					calls.add(call);
				} // end while
				LOG.fine("Finished creating List of Calls: " + calls);
				result = calls;
			} // end else if

			LOG.fine("SQL Statement returned following result: " + result);
			stmt.close();
			LOG.finest("Statement closed");
			LOG.exiting("SQLiteStorage", "ownSQLCommand");
			return result;
		} catch (Exception e) {
			LOG.warning(e.toString());
		}
		LOG.exiting("SQLiteStorage", "ownSQLCommand");
		return null;
	}
}
