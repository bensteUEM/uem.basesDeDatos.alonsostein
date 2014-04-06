package activity19;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	public final static Logger LOG = Logger.getLogger(SQLiteStorage.class
			.getName());
	private final static String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	@SuppressWarnings("unused")
	private FileHandler fh;

	/**
	 * SQLiteStorage requires the name for the Database
	 */
	@SuppressWarnings("unused")
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
			sql0 = "DROP TABLE IF EXISTS CustomerBills;";
			ownSQLCommand(sql0, null);
			sql0 = "DROP TABLE IF EXISTS CustomerCalls;";
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

			String sql3 = "CREATE TABLE CustomerBills "
					+ "(ID 						INT 	PRIMARY KEY     NOT NULL,"
					+ " CustomerID     			INT    					NOT NULL,"
					+ " FileName       			char(50),"
					+ " DateCreated    			char(50),"
					+ " BalanceDue      		REAL,"
					// Reference to Owner Table enforced
					+ " FOREIGN KEY(CustomerID) REFERENCES Customers(ID));";
			ownSQLCommand(sql3, null);
			LOG.finest("Defined table CustomerBills");

			String sql4 = "CREATE TABLE CustomerCalls "
					+ "(ID 						INT 	PRIMARY KEY     NOT NULL,"
					+ "BillID 					INT		," + "OriginID					INT 					NOT NULL,"
					+ " Destination				char(50),"
					+ " startTime				char(50)    			NOT NULL,"
					+ " Duration 				INT     				NOT NULL,"
					// Reference to Customer and Bill Table enforced
					+ " FOREIGN KEY(BillID) REFERENCES CustomerBills(ID), "
					+ " FOREIGN KEY(OriginID) REFERENCES Customers(ID));";
			ownSQLCommand(sql4, null);
			LOG.finest("Defined table CustomerCalls");

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

	/**
	 * Wrapper to execute SQL Commands without the need to catch exceptions
	 * locally;
	 * 
	 * @param sql
	 * @param args
	 *            - arguments e.g. expected return class
	 * @return value depending on args
	 */
	@SuppressWarnings("unchecked")
	public Object ownSQLCommand(String sql, String args) {
		Statement stmt = null;
		LOG.entering("SQLiteStorage", "ownSQLCommand");
		LOG.fine("Attempting to execute following SQL Statement: " + sql);
		Object result = null;
		try {
			stmt = c.createStatement();
			LOG.finest("Statement created: " + stmt.toString());
			if (args == null) {
				LOG.fine("Executing an SQL querry which is not expected to return a result: "
						+ args);
				result = stmt.executeUpdate(sql);
			} else if (args.equals("CustomerSQL")) {
				/*
				 * LOG.fine(
				 * "Executing an SQL querry which is expected to return ONE CustomerSQL Object"
				 * ); ResultSet rs = stmt.executeQuery(sql);
				 * LOG.fine("Statement Excecuted");
				 * 
				 * The following Code can not be used due to an missing SQLite
				 * function in the API
				 * 
				 * if (rs.isLast()) {
				 * LOG.fine("SQL Database has no results for this query");
				 * result = null; } else { CustomerSQL customer = new
				 * CustomerSQL(rs, rs.getInt("ID"), rs.getString("Name"),
				 * rs.getString("CellPhoneNumber"), LOG, this);
				 * LOG.fine("CustomerSQL item created with: " + rs.getInt("ID")
				 * + "//" + rs.getString("Name") + "//" +
				 * rs.getString("CellPhoneNumber")); result = customer; }
				 */
				LOG.warning("USE Workaround in method for missing SQL Function instead");
				/*
				 * BEGIN of workaround to be used in functions
				 */
				/*
				 * ArrayList<CustomerSQL> temp = (ArrayList<CustomerSQL>)
				 * ownSQLCommand( sql, "ArrayList<CustomerAbstract>");
				 * LOG.finest("WORKAROUND : got element: " + temp); if
				 * (temp.size() > 0) {
				 * LOG.finest("END WORKAROUND : >0 return first element");
				 * return temp.get(0); } else {
				 * LOG.finest("END WORKAROUND : <0 return null"); return null; }
				 */
				/*
				 * END of workaround
				 */

			} else if (args.equals("ArrayList<CustomerAbstract>")) {
				LOG.fine("Executing an SQL querry which is expected to return an ArrayList of CustomerSQL Objects");
				ResultSet rs = stmt.executeQuery(sql);
				ArrayList<CustomerAbstract> customers = new ArrayList<CustomerAbstract>();
				/*
				 * Missing Java API implementation in Wrapper ! if (rs.isLast())
				 * { LOG.fine("SQL Database has no results for this query");
				 * result = null; } else {
				 */
				LOG.finest("Will now iterated through all results of the query");

				while (rs.next()) {
					LOG.finest("A resultset exists will continue with customer");
					CustomerSQL customer = new CustomerSQL(rs, rs.getInt("ID"),
							rs.getString("Name"),
							rs.getString("CellPhoneNumber"), LOG, this);
					customers.add(customer);
					LOG.finest("Added following customer to ArrayList: "
							+ customer);
				}// end while
				LOG.finest("Finished loop of all results with customer array: "
						+ customers);
				result = customers;
			} else if (args.equals("Integer")) {
				LOG.fine("Executing an SQL which should return a simple integer");
				ResultSet rs = stmt.executeQuery(sql);
				Integer subresult = 0;
				while (rs.next()) {
					subresult += (Integer) rs.getInt(1);
				}
				result = subresult;
				LOG.finest("sum of result 1 is: " + result);

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
					LOG.finer("Attempting to get the Customer of the Current Call");
					String query2 = "SELECT * FROM Customers WHERE ID="
							+ rs.getString("OriginID") + ";";
					LOG.finest("subSQL query: " + query2
							+ "should return: ArrayList<CustomerAbstract>");
					ArrayList<CustomerAbstract> customers = (ArrayList<CustomerAbstract>) this
							.ownSQLCommand(query2,
									"ArrayList<CustomerAbstract>");
					LOG.finest("DEBUG subSQL returned: " + customers);
					CustomerAbstract newOrigin = customers.get(0);
					LOG.fine("The current user associated as origin is: "
							+ newOrigin);

					// Calendar Part
					LOG.finer("creating Calendar and importing start Date");
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
		} catch (SQLException | ParseException e) {
			LOG.warning(e.toString());
		}
		LOG.exiting("SQLiteStorage", "ownSQLCommand");
		return null;
	}

	/**
	 * Function which associates ALL Calls of the selected user with a new
	 * Bill
	 * 
	 * @return BillID
	 */
	public int newBill(Integer userID, String fileName, String date) {
		LOG.entering("SQLiteStorage","newBill");
		//get new ID for bill
		String query = "SELECT MAX(ID) FROM CustomerBills";
		Integer freeID = (Integer) this.ownSQLCommand(query,"Integer") + 1;
		LOG.fine("next Bill will have this id: "+freeID);
		
		// create new bill
		// ID,CustomerID, FileName, DateCreated, BalanceDue  
		query = "INSERT INTO CustomerBills VALUES(1,"+userID.toString()+",\'"+fileName+"\',\'"+date+"\',NULL);";
		this.ownSQLCommand(query,null);
		LOG.fine("Added Bill to the SQLite Database");
		
		// update Calls and link them to the Bill
		query = "UPDATE CustomerCalls BillID="+freeID+" WHERE [CustomerID="+userID+"];";
		// AND BillID=Null];"; // FUTURE ADDITION 
		this.ownSQLCommand(query,null);
		LOG.fine("Updated Calls to reference correct BillID: "+freeID);
	
		//set Airtime Minutes based on Calls
		query = "SELECT Duration FROM CustomerCalls WHERE BillID="+freeID+" AND OriginID="+userID+";";
		Integer totalminutes = (Integer) ownSQLCommand(query,"Integer");
		LOG.fine("got Total Minutes for this bill and customer: "+totalminutes);
		query = "UPDATE Customers Duration="+totalminutes.toString()+" WHERE ID="+userID+";";
		ownSQLCommand(query,null);
		LOG.fine("set minutes to the customer: "+userID);
		
		//calculate total - WARNING 
		
		return freeID;
	}
	
	/**
	 * Calculate the Total of a Bill
	 * @param billID
	 * @return total of the Bill
	 */
	public BigDecimal getBillTotal(Integer billID){
		String query = "SELECT CustomerID FROM CustomerBills WHERE ID="+billID+";";
		Integer userID = (Integer) ownSQLCommand(query,"Integer");
		LOG.fine("got user ID for this Bill: "+userID);
		
		query = "SELECT AirtimeMinutes, Rate FROM Customers WHERE ID="+userID+";";
		BigDecimal total = (BigDecimal) this.ownSQLCommand(query,"BigDecimal");
		LOG.fine("Calculated the Customer Total: "+total);
	
		return total;
	}
}
