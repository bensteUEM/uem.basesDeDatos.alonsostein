package activity10;

import java.util.ArrayList;

import activity13.CustomerCall;
import activity19.CustomerSQL;
import activity19.PbesSQL;
import activity19.SQLiteStorage;

public class TestClass {
	public static SQLiteStorage db;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Customer a = new Customer("", "", 0);
		 * System.out.println("empty customer: " + a);
		 * 
		 * String testtext = "<100>,Yi-Min Yang,+49234205,+3200000,200,2,1,";
		 * 
		 * if (a.importText(testtext)) { System.out.println("imported customer "
		 * + a); System.out.println("imported customer Landline is: " +
		 * a.getLandlinePhoneNumer());
		 * System.out.println("imported customer Airtime is:  " +
		 * a.getAirtimeMinutes());
		 * System.out.println("imported customer Rate is: " + a.getRate());
		 * 
		 * String exported = a.exportText();
		 * System.out.println("Exported Customer equals testcustomer " +
		 * testtext.contains(exported)); } else {
		 * System.out.println("the test finished with problematic import"); }
		 */
		System.out.println("Test - Create DB: " + testDB());
		System.out.println("Test - Create Customer SQL: "
				+ testSetCustomerSQL());
		System.out.println("Test - Read Customer SQL: " + testGetCustomerSQL());
		
		System.out.println("Test - Write Dummy Call to SQL: "+testSetCall());
		System.out.println("Test -  Calls of Customer: "
				+ testGetCalls(testGetCustomerSQL()));

	}

	public static boolean testDB() {
		TestClass.db = new SQLiteStorage("testing");
		TestClass.db.init();
		return true;
	}

	/*
	 * Customers
	 */
	public static boolean testSetCustomerSQL() {
		CustomerSQL sqlcustomer = new CustomerSQL("Name", "+00000", 1, 1, db);
		String sql = " INSERT INTO Customers " + sqlcustomer.exportSQLText()
				+ ";";
		db.ownSQLCommand(sql, null);
		return true;
	}

	public static CustomerAbstract testGetCustomerSQL() {

		String query = "SELECT * FROM Customers WHERE ID=1;";
		@SuppressWarnings("unchecked")
		ArrayList<CustomerAbstract> list = (ArrayList<CustomerAbstract>) db
				.ownSQLCommand(query, "ArrayList<CustomerAbstract>");
		return list.get(0);
	}

	/*
	 * Calls
	 */
	
	public static boolean testSetCall(){
		// ID,BillID,OriginID,
		String values1 = "1,NULL,1,";
		// Destination,startTime<char(50)>,Duration
		//yyyy-MM-dd HH:mm:ss
		String values2 = "\'1234\',\'1888-10-05 11:22:33\',44";
		String query2 = "INSERT INTO CustomerCalls VALUES(" + values1 + values2
				+ ");";
		System.out.println("\tDummy Call to be created with: "+query2);
		TestClass.db.ownSQLCommand(query2,null);	
		return true;
	}
	
	
	public static boolean testGetCalls(CustomerAbstract customer) {
		ArrayList<CustomerCall> c = customer.getCalls();
		if (c.size() > 0) { // has a size
			System.out.println("\tFound Call: " + c.get(0)); // has a first
																// element ?
		} else {
			System.out.println("\tCustomer has no Calls");
		}
		return true;
	}

	/*
	 * OLD
	 */

	public static boolean testExcelExport() {
		System.out.println("Excel Min Balance import test");
		DataFile d = new DataFile("MinimumBalance");
		d.importMinimumBalanceExcel2013();
		return true;
	}

}
