package activity10;

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Customer a = new Customer("", "", 0);
		System.out.println("empty customer: " + a);
		
		String testtext =  "<100>,Yi-Min Yang,+49234205,+3200000,200,2,1,";

		if (a.importText(testtext)) {
			System.out.println("imported customer " + a);
			System.out.println("imported customer Landline is: "
					+ a.getLandlinePhoneNumer());
			System.out.println("imported customer Airtime is:  "
					+ a.getAirtimeMinutes());
			System.out.println("imported customer Rate is: " + a.getRate());

			String exported = a.exportText();
			System.out.println("Exported Customer equals testcustomer "
					+ testtext.contains(exported));
		}
		else {
			System.out.println("the test finished with problematic import");
		}
	}

}
