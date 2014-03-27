package activity19;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class SQLiteStorage {
	private String name;
	private Connection c = null;
	private Statement stmt = null;
	private final static Logger LOG = Logger.getLogger(SQLiteStorage.class
			.getName());

	public SQLiteStorage() {
		LOG.entering("SQLiteStorage", "Constructor");
		// TODO Auto-generated constructor stub
		this.name = "benste_test";
		this.openDB(this.name);
	}

	/**
	 * Method to properly finish opened SQL Connections
	 * @author benste
	 */
	public void finalize(){
		LOG.entering("SQLiteStorage", "finalize");
		try {
			c.close();
			LOG.fine("SQL Connection closed");
		} catch (SQLException e) {
			LOG.warning("Following error occured when trying to close the SQL Connection: "+e);
		}
	}
	/**
	 * This method will initialize an EMPTY database with all the structures
	 * needed as specified by the Diagram
	 * @author benste
	 */
	public void init() {
		LOG.entering("SQLiteStorage", "init");
		if (openDB(this.name)) {
			String sql1 = "CREATE TABLE OWNER "
					+ "(CIF 					INT 	PRIMARY KEY    	NOT NULL,"
					+ " MinimumConsumption 		REAL, "
					+ " CompanyName         	CHAR(50)     			NOT NULL ";
			ownSQLCommand(sql1);
			LOG.finest("Defined table Owner");

			String sql2 = "CREATE TABLE CUSTOMER "
					+ "(ID 						INT		PRIMARY KEY		NOT NULL,"
					+ " Owner       			INT		FOREIGN KEY 	NOT NULL, "
					+ " Rate        			REAL    				NOT NULL, "
					+ " Name       				char(50)				NOT NULL, "
					+ " CellPhoneNumber       	char(50), "
					+ " LandlinePhoneNumber     char(50), "
					+ " AirtimeMinutes     		INT, "
					// Reference to Owner Table enforced
					+ " FOREIGN KEY(Owner) REFERENCES OWNER(CIF)";
			ownSQLCommand(sql2);
			LOG.finest("Defined table CUSTOMER");

			String sql3 = "CREATE TABLE CUSTOMERBILL "
					+ "(ID 						INT 	PRIMARY KEY     NOT NULL,"
					+ " CustomerId     			INT    					NOT NULL,"
					+ " FileName       			char(50),"
					+ " DateCreated    			char(50),"
					+ " BalanceDue      		REAL,"
					// Reference to Owner Table enforced
					+ " FOREIGN KEY(CustomerId) REFERENCES CUSTOMER(ID)";
			ownSQLCommand(sql3);
			LOG.finest("Defined table CUSTOMERBILL");

			String sql4 = "CREATE TABLE CUSTOMERCALL "
					+ "(ID 						INT 	PRIMARY KEY     NOT NULL,"
					+ "Bill 					INT		PRIMARY KEY,"
					+ "Origin 					INT 	PRIMARY KEY     NOT NULL,"
					+ " Destination				char(50),"
					+ " startTime				char(50)    			NOT NULL,"
					+ " Duration 				 INT     				NOT NULL,"
					// Reference to Customer and Bill Table enforced
					+ " FOREIGN KEY(Bill) REFERENCES CUSTOMERBILL(ID)"
					+ " FOREIGN KEY(Origin) REFERENCES CUSTOMER(ID)";
			ownSQLCommand(sql4);
			LOG.finest("Defined table CUSTOMERCALL");

			LOG.fine("All tables structures initialized");
		} // end if
		else {
			LOG.warning("could not open the following database to be initiliazed: "+this.name);
		}
		LOG.exiting("SQLiteStorage", "init");
	}
		

	/**
	 * Open the Database file and save the connection field
	 * @return
	 */
	public boolean openDB(String fileName) {
		LOG.entering("SQLiteStorage", "openDB");
		try {
			Class.forName("org.sqlite.JDBC");
			this.c = DriverManager.getConnection("jdbc:sqlite:"+fileName+".db");
			LOG.fine("Opened: "+this.c.toString());
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
	 * Wrapper to execute SQL Commands without the need to catch exceptions locally;
	 * @param sql
	 * @return
	 */
	public int ownSQLCommand(String sql) {
		LOG.entering("SQLiteStorage", "ownSQLCommand");
		LOG.fine("Attempting to execute following SQL Statement: "+sql);
		int result = 0;
		try {
			this.stmt = c.createStatement();
			LOG.finest("Statement created: "+this.stmt.toString());
			result = stmt.executeUpdate(sql);
			LOG.fine("SQL Statement returned following result: "+result);
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
