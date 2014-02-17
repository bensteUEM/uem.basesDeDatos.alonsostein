package activity10;

import java.util.ArrayList;

import javax.swing.JFrame;

public abstract class PbesAbstract  extends JFrame{
	private static final long serialVersionUID = -7819707263724623611L;
	@SuppressWarnings("unused") //implemented later
	private CustomerAbstract[] customers;
	
	public abstract boolean addCustomer(CustomerAbstract customer);
	public abstract boolean saveCustomer(CustomerAbstract currentCustomer);
	public abstract CustomerAbstract getCustomer(Integer searchId);
	// Optional Future Optimisation
	//public abstract CustomerAbstract getCustomer(String searchCellPhoneNumber);
	//public abstract CustomerAbstract getCustomer(String searchName, String landLineNumber);
	
	public abstract Integer getAllCustomerBalance();
	public abstract ArrayList<Customer> getCustomersAboveRate(Integer maxRate); //List of CustomerAbstract
	public abstract void calculateAllBalances();
	
	public abstract Integer getCompanyRevenue();
	
	@SuppressWarnings("unused") //use to block default constructor
	private PbesAbstract() {
	}
	public PbesAbstract(Integer numberOfCustomers) {
	}
}
