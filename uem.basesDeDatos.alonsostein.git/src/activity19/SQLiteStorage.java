package activity19;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * SQLiteStorage Class for use with PBES Project
 * check http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 * @author benste
 *
 */
public class SQLiteStorage {
	private String name;
	private Connection c = null;
	private Statement stmt = null;
	private final static Logger LOG = Logger.getLogger(SQLiteStorage.class
			.getName());
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
		// This block configure the logger with handler and formatter
		try {
			fh = new FileHandler("SQLiteStorage.log");
			LOG.addHandler(fh);
			LOG.setLevel(Level.FINE);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

		this.name = "benste_test";
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
			String sql0 = "DROP TABLE IF EXISTS OWNER;";
			ownSQLCommand(sql0);
			sql0 = "DROP TABLE IF EXISTS CUSTOMER;";
			ownSQLCommand(sql0);
			sql0 = "DROP TABLE IF EXISTS CUSTOMERBILL;";
			ownSQLCommand(sql0);
			sql0 = "DROP TABLE IF EXISTS CUSTOMERCALL;";
			ownSQLCommand(sql0);

			LOG.fine("DELETE ALL Existing Tables finished");
			
			String sql1 = "CREATE TABLE OWNER "
					+ "(CIF 					INT 	PRIMARY KEY    	NOT NULL,"
					+ " MinimumConsumption 		REAL, "
					+ " CompanyName         	CHAR(50)     			NOT NULL);";
			ownSQLCommand(sql1);
			LOG.finest("Defined table Owner");

			String sql2 = "CREATE TABLE CUSTOMER "
					+ "(ID 						INT		PRIMARY KEY		NOT NULL,"
					+ " Owner       			INT					 	NOT NULL, "
					+ " Rate        			INT	    				NOT NULL, "
					+ " Name       				char(50)				NOT NULL, "
					+ " CellPhoneNumber       	char(50), "
					+ " LandlinePhoneNumber     char(50), "
					+ " AirtimeMinutes     		INT, "
					// Reference to Owner Table enforced
					+ " FOREIGN KEY(Owner) REFERENCES OWNER(CIF));";
			ownSQLCommand(sql2);
			LOG.finest("Defined table CUSTOMER");

			String sql3 = "CREATE TABLE CUSTOMERBILL "
					+ "(ID 						INT 	PRIMARY KEY     NOT NULL,"
					+ " CustomerId     			INT    					NOT NULL,"
					+ " FileName       			char(50),"
					+ " DateCreated    			char(50),"
					+ " BalanceDue      		REAL,"
					// Reference to Owner Table enforced
					+ " FOREIGN KEY(CustomerId) REFERENCES CUSTOMER(ID));";
			ownSQLCommand(sql3);
			LOG.finest("Defined table CUSTOMERBILL");

			String sql4 = "CREATE TABLE CUSTOMERCALL "
					+ "(ID 						INT 	PRIMARY KEY     NOT NULL,"
					+ "Bill 					INT		," + "Origin 					INT 					NOT NULL,"
					+ " Destination				char(50),"
					+ " startTime				char(50)    			NOT NULL,"
					+ " Duration 				INT     				NOT NULL,"
					// Reference to Customer and Bill Table enforced
					+ " FOREIGN KEY(Bill) REFERENCES CUSTOMERBILL(ID), "
					+ " FOREIGN KEY(Origin) REFERENCES CUSTOMER(ID));";
			ownSQLCommand(sql4);
			LOG.finest("Defined table CUSTOMERCALL");

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
	 * @return
	 */
	public int ownSQLCommand(String sql) {
		LOG.entering("SQLiteStorage", "ownSQLCommand");
		LOG.fine("Attempting to execute following SQL Statement: " + sql);
		int result = 0;
		try {
			this.stmt = c.createStatement();
			LOG.finest("Statement created: " + this.stmt.toString());
			result = stmt.executeUpdate(sql);
			LOG.fine("SQL Statement returned following result: " + result);
			stmt.close();
			LOG.finest("Statement closed");
			LOG.exiting("SQLiteStorage", "ownSQLCommand");
			return result;
		} catch (Exception e) {
			LOG.warning(e.toString());
		}
		LOG.exiting("SQLiteStorage", "ownSQLCommand");
		return -1;
	}
}
