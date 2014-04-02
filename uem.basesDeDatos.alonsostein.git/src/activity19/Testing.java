package activity19;

public class Testing {

	public Testing() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args){
		SQLiteStorage s = new SQLiteStorage("testing");
		s.init();
		//s.ownSQLCommand("INSERT INTO Customers VALUES(0,'0',0,,'','',0,0);", null);
		s.ownSQLCommand("INSERT INTO Customers VALUES(0,'0',0,'','',0,0);", null);
		System.out.println(s.ownSQLCommand("SELECT * FROM Customers WHERE ID=0","Customer;"));
	}
}
