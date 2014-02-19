package activity10;

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Customer a = new Customer("", "", 0);
		System.out.println("empty customer "+ a);
		String testtext= "<100>;Yi-Min Yang;+49234235;+320000000;200;2;1;"; 
		
		a.importText(testtext);
		System.out.println("imported customer "+ a);
		
		String exported = a.exportText();
		System.out.println("Exported Customer equals testcustomer " + testtext.contains(exported));
		
	}

}
