package activity10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class DataFile {
	private Customer[] groupOfCustomers;
	private Customer oneCustomerToWrite;
	private int numberOfLines = 0;

	/*
	 * public static void main(String[] args) { DataFile w = new DataFile();
	 * w.write();
	 * 
	 * JFrame frame = new JFrame("R/W Files");
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); JLabel label = new
	 * JLabel(w.readFile()); frame.getContentPane().add(label); frame.pack();
	 * frame.setVisible(true);
	 * 
	 * }
	 */
	/**
	 * By default export this objects customers
	 */
	public void exportCustomer() {
		exportCustomer(this.groupOfCustomers);
	}

	/**
	 * Use this class to export any list of customers
	 * 
	 * @param customers
	 */
	public void exportCustomer(Customer[] customers) { // Export customer/s to
														// file
		 //SORTING Addon - ref comparator to empty customer object
		Arrays.sort(customers,new Customer("","",-1));
		
		this.groupOfCustomers = customers;
		//optional Addon - sort customers by ID
		
		
		Integer maxCustomers = customers.length;

		// 1. OPEN
		File path = new File("." + File.separator + File.separator + "src"
				+ File.separator + "activity10" + File.separator
				+ "data_customers.txt");
		String fileName = path.getPath();
		try {
			// 2. WRITE
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,
					true)); // Creates a buffered character-output
							// stream of a
							// class for writing character files

			for (int i = 0; i < maxCustomers; i++) { // Covers the array of
														// customers to export
														// them
				if (customers[i] != null) { // our array can have empty spaces
											// which should not be exported
					writer.write(customers[i].exportText()); // writes a
																// string
																// with
																// the
																// number
					writer.newLine(); // adds a line separator for every
										// customer
				}
			}

			// 3. CLOSE
			writer.flush(); // make sure the buffer writes everything
			System.out.println("Following file has been written" + fileName); // TODO
																				// debug
			writer.close(); // close the file
		} catch (IOException e) {
			e.printStackTrace(); // throws a standard error when there are
									// errors with file handling
		}
	}

	public Customer[] importCustomer() {
		// 1. OPEN file
		File path = new File("." + File.separator + File.separator + "src"
				+ File.separator + "activity10" + File.separator
				+ "data_customers.txt");
		String fileName = path.getPath();

		StringBuffer fileContent = new StringBuffer();

		try {
			// 2. READ file
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;
			numberOfLines = 0; // reset number of lines
			while ((line = reader.readLine()) != null) {
				// groupOfCustomers[numberOfLines].importText(line); // reads
				// the
				// actual
				// customer
				numberOfLines++; // and increments the number of customers the
									// file has till it gets to the end of the
				// file
				fileContent.append(line).append("-"); // add a symbol to split
														// customers from that
														// symbol
			}
			String[] customerData = new String[numberOfLines]; // create a new
																// array string
																// to store all
																// customers
																// separated
			// @LUIS Syntax TYPE[] NAME = OBJECT
			// TODO reset file object and loop directly instead of splitting and
			// Modifying original content with a -
			customerData = fileContent.toString().split("-"); // splits the
																// StringBuffer
																// every time it
																// finds an "-"
																// to store
																// every
																// customer in a
																// position of
																// the array
			this.groupOfCustomers = new Customer[numberOfLines];
			// @LUIS - code not needed for import ...
			// System.out.println(oneCustomerToWrite);
			for (int i = 0; i <= (groupOfCustomers.length - 1); i++) { // @LUIS
																		// index
																		// of
																		// arrays
																		// are
																		// starting
																		// with
																		// 0 and
																		// ending
																		// with
																		// length-1
				/*
				 * LUIS OLD CODE groupOfCustomers[i] = new Customer("0", "0",
				 * 0); // initialize // all // elements // to avoid //
				 * nullpointerexception
				 * 
				 * groupOfCustomers[i].importText(customerData[i]); // import
				 * the // text with // the // required // format // for every //
				 * customer
				 */
				// NEW CODE with easy constructor
				try {
					groupOfCustomers[i] = new Customer(customerData[i]);
				} catch (Exception e) {
					e.printStackTrace();
					System.out
							.println("WARNING, customer was not successfully imported, there might be corrupted data");
				}
			}

			// 3. CLOSE file
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return groupOfCustomers;

	}

	public DataFile(Customer[] customers) {
		this.groupOfCustomers = customers;
	}

	/**
	 * Another constructor to avoid duplicated passing of the Customer to export
	 * and dynamically use the class
	 */
	public DataFile() {
		this.groupOfCustomers = null;
	}

	public Integer getNumberOfLines() {
		return numberOfLines;
	}

}
