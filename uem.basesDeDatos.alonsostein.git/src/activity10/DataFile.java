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
		String fileName = "I:\\workspace\\data.txt";
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
	public Customer[] importCustomer() {
		// 1. OPEN file
		String fileName = "I:\\workspace\\data.txt";
		// TODO set array length to number of rows in file

		StringBuffer fileContent = new StringBuffer();

		try {
			// 2. READ file
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;

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
			String customerData[] = new String[numberOfLines]; // create a new
																// array string
																// to store all
																// customers
																// separated
			customerData = fileContent.toString().split("-"); // splits the
																// StringBuffer
																// every time it
																// fids an "-"
																// to store
																// every
																// customer in a
																// position of
																// the array
			this.groupOfCustomers = new Customer[numberOfLines];
			System.out.println(oneCustomerToWrite);
			for (int i = 0; i < numberOfLines; i++) {
				groupOfCustomers[i]=new Customer("0","0",0);	// initialize all elements to avoid nullpointerexception
				groupOfCustomers[i].importText(customerData[i]);	// import the text with the required format for every customer
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
