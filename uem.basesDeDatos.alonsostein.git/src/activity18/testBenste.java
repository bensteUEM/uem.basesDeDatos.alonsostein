package activity18;

import java.sql.*;

public class testBenste {
	static Connection c = null;
	static Statement stmt = null;

	public static void main(String args[]) {
		if (openDB()) {
			//System.out.println(deleteTable("COMPANY")); // DELETE  EXISTING TABLES // DEBUG ONLY
			String sql = "CREATE TABLE COMPANY "
					+ "(ID INT PRIMARY KEY     NOT NULL,"
					+ " NAME           TEXT    NOT NULL, "
					+ " AGE            INT     NOT NULL, "
					+ " ADDRESS        CHAR(50), "
					+ " SALARY         REAL)";
			ownSQLCommand(sql);
			//System.out.println("Table created successfully");
			
			System.out.println(ownSQLCommand("INSERT INTO COMPANY VALUES (3,\"Testname\",99,\"Calle Muster 4\",2000.00)"));
		}
	}

	public static boolean openDB() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:benste_test.db");
			System.out.println("Opened database successfully");
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static int ownSQLCommand(String sql){
		System.out.println("trying the following statement \n SQL > "+sql);
		int result = 0;
		try {
			stmt = c.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
			c.close();
			return result;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": "
					+ e.getMessage());
			System.exit(0);
		}
		return -1;
	}
	
	public static boolean deleteTable(String tableName){
		return (ownSQLCommand("DROP TABLE IF EXISTS "+ tableName + ";")>=0);
	}
}