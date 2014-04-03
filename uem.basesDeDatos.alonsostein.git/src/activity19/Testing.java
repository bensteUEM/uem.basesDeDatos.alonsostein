package activity19;

public class Testing {

	public Testing() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args){
		SQLiteStorage s = new SQLiteStorage("testing");
		s.init();
		
		s.ownSQLCommand("INSERT INTO Owner VALUES(1000,0.0,\'TestCompany\');", null);
		//s.ownSQLCommand("INSERT INTO Customers VALUES(0,'0',0,'','',0,0);", null); // original
		/*
		+ "(ID 						INT		PRIMARY KEY		NOT NULL,"
		+ " Owner       			INT					 	NOT NULL, "
		+ " Rate        			INT	    				NOT NULL, "
		+ " Name       				char(50)				NOT NULL, "
		+ " CellPhoneNumber       	char(50), "
		+ " LandlinePhoneNumber     char(50), "
		+ " AirtimeMinutes     		INT, "
		*/
		//s.ownSQLCommand("INSERT INTO Customers VALUES(1,1000,0,\'TestName1\',\'TestNumber1\',\'TestNumber1\',0);", null);
		s.ownSQLCommand("INSERT INTO Customers VALUES(0,'0',0,'','',0,0);", null); // original
		//INSERT INTO Customers VALUES(0,'0',0,,'','',0,0);
		System.out.println(s.ownSQLCommand("SELECT * FROM Customers","Customer;"));
		System.out.println(s.ownSQLCommand("SELECT * FROM Customers WHERE ID=0","Customer;"));
		s.finalize();
	}
}
