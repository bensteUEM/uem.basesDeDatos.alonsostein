package activity10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class DataFile {
	private Customer[] customersToWrite;

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
	public void exportText() {
		Integer maxCustomers = 0;
		try {
			GuiFilter a = new GuiFilter(0);

			while (maxCustomers == 0) {
				maxCustomers = a.getNumber();
				Thread.sleep(200); // waits a time in miliseconds
				customersToWrite = new Customer[maxCustomers];
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 1. OPEN
		String fileName = "customerData" + File.separator + "data.txt"; // sets
																		// the
																		// file
																		// name
																		// we
		// want to use and its
		// location
		try {
			// 2. WRITE
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,
					true)); // Creates a buffered character-output stream of a
							// class for writing character files
			for (int i = 0; i <= maxCustomers; i++) {
				writer.write(customersToWrite[i].exportText()); // writes a
																// string with
																// the number
				writer.newLine(); // adds a line separator for every customer

			}
			// 3. CLOSE
			writer.close(); // close the file
		} catch (IOException e) {
			e.printStackTrace(); // throws a standard error
		}
	}

	// TODO find out how to do the read file
	public Customer[] importText() {
		// 1. OPEN file
		String fileName = "customerData" + File.separator + "data.txt";
		int numberOfLines = 0;
		Customer[] newCustomers = new Customer[numberOfLines];
		// TODO set array length to number of rows in file

		try {
			// 2. READ file
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;

			while ((line = reader.readLine()) != null) {
				newCustomers[numberOfLines].importText(line); // reads the
																// actual
																// customer
				numberOfLines++; // and increments the number of customers the
									// file has till it gets to the end of the
									// file
			}

			// TODO add customer into local array

			// TODO increment the number of customers acordingly
			// 3. CLOSE file
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return newCustomers;

	}

	public DataFile(Customer[] customers) {

	}

}
