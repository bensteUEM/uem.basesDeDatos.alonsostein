package activity10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
	public void exportCustomer(Customer[] customers) { // Export customer/s to
														// file
		this.groupOfCustomers = customers;
		Integer maxCustomers = customers.length;

		// 1. OPEN
		// String fileName = "customerData" + File.separator + "data.txt";
		// String fileName = "I:\\workspace\\data.txt";
		File path = new File("." + File.separator + File.separator + "src"
				+ File.separator +  "activity10" + File.separator + "data.txt");
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
				writer.write(customers[i].exportText()); // writes a
															// string
															// with
															// the
															// number
				writer.newLine(); // adds a line separator for every customer
			}

			// 3. CLOSE
			writer.close(); // close the file
		} catch (IOException e) {
			e.printStackTrace(); // throws a standard error
		}
	}

	// TODO find out how to do the read file
	public Customer[] importCustomer() throws Exception {
		// 1. OPEN file
		File path = new File("." + File.separator + File.separator + "src"
				+ File.separator +  "activity10" + File.separator + "data.txt");
		String fileName = path.getPath();
		// TODO set array length to number of rows in file

		StringBuffer fileContent = new StringBuffer();

		try {
			// 2. READ file
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;
			String customerData[] = new String[50];

			while ((line = reader.readLine()) != null) {
				
				numberOfLines++; // and increments the number of customers the
									// file has till it gets to the end of the
				customerData[numberOfLines-1] = line;
			}
			
			this.groupOfCustomers = new Customer[numberOfLines];
			for (int i = 0; i < numberOfLines; i++) {
				
				groupOfCustomers[i] = new Customer(customerData[i]);
			}

			// TODO add customer into local array

			// TODO increment the number of customers acordingly
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

	public Integer getNumberOfLines() {
		return numberOfLines;
	}

}
