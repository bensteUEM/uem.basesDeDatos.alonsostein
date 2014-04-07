package activity10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import activity13.CustomerCall;
import activity19.CustomerSQL;

public class DataFile {
	private CustomerAbstract[] groupOfCustomers;
	private int numberOfLines = 0;
	private String fileName = "";

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	} // end getFile

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	} // end set file

	/**
	 * Export the customers Bill to a textfile
	 * 
	 * @return File Location
	 */
	public String exportCustomerBill(CustomerAbstract customer) {
		try {
			// 0. Calculate Customer Balance
			Integer previousMinutes = customer.getAirtimeMinutes();
			customer.addAirtimeMinutesFromCalls();
			customer.setBalance();

			// precondition Date Format
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = Calendar.getInstance().getTime();
			
			// 1. OPEN
			//this.fileName+"_"
			File path = new File("Bills"+File.separator+customer.getName()+"-"+sdf2.format(currentDate)+".txt");
			String fileName = path.getPath();
			// 2. Create Writer
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,
					false));
			// Creates a buffered character-output stream of a class for writing
			// character files
			// 3. Write Heading
			writer.write("Monthly Bill for " + customer.getName());
			writer.newLine();
			writer.write("Phone Number " + customer.getCellPhoneNumber());
			writer.newLine();

			// 4. Write Some General Information
			writer.write("Current Rate: " + customer.getRate());
			writer.newLine();
			writer.write("====");
			writer.newLine();
			writer.write("Total outstanding Balance is: "
					+ customer.getBalance() + " EURO");
			writer.write("====");
			writer.newLine();

			// 5. Write Call Log A) previous minutes
			if (previousMinutes > 0) { //
				writer.write("====");
				writer.newLine();
				writer.write("There were " + previousMinutes
						+ " minutes outstanding from previous bills");
				writer.newLine();
			}// end if previous minutes
				// 5B)
				// Destination, Date, Duration, Cost - HEADER ROW
			writer.write("Destination,");
			writer.write("Date,");
			writer.write("Duration,");
			writer.write("Cost,");
			writer.newLine();
			writer.write("====");
			writer.newLine();
			// get the billing text from Customer class

			//TODO specify Bill ID
			for (String textline : customer.getBillText(null)) {
				writer.write(textline);
				writer.newLine();
			} // end for

			
			writer.write("====");
			writer.newLine();
			// 5b - minimum Consumption Advice
			if (customer.getBalance() == customer.getMinBalance()) {
				writer.write("Please be adviced that you are paying the minimum Balance required for your contract!");
				writer.newLine();
			}

			// 6. Finish File
			writer.write("Last updated:" + sdf.format(currentDate));
			writer.flush();
			writer.close();
			return path.getPath();
		} catch (IOException e) {
			return e.getMessage(); // throws a standard error when there are
									// errors with file handling
			
		} // end try
	}

	public void createBillCalls(CustomerAbstract customer) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			File path = new File(this.fileName + ".txt");
			String fileName = path.getPath();
			// 2. Create Writer
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,
					false));
			// get the billing text from Customer class

			for (int i = 0; i < customer.getBillText(customer.getId()).size(); i++) {
				writer.write(customer.getBillText(customer.getId()).get(i));
				writer.newLine();
			} // end for

			writer.write("====");
			writer.newLine();
			// 5b - minimum Consumption Advice
			if (customer.getBalance() == customer.getMinBalance()) {
				writer.write("Please be adviced that you are paying the minimum Balance required for your contract!");
				writer.newLine();
			}

			// 6. Finish File
			Date currentDate = Calendar.getInstance().getTime();
			writer.write("Last updated:" + sdf.format(currentDate));

			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace(); // throws a standard error when there are
									// errors with file handling
		} // end try
	}

	/**
	 * Use this class to export any list of customers
	 * 
	 * @param customers
	 */
	public void exportCustomer(CustomerAbstract[] customers) { // Export
																// customer/s to
		// file
		// SORTING Addon - ref comparator to empty customer object
		// Arrays.sort(customers, new Customer("", "", -1));

		this.groupOfCustomers = customers;
		// optional Addon - sort customers by ID

		Integer maxCustomers = customers.length;

		// 1. OPEN
		File path = new File(this.fileName + ".csv");
		String fileName = path.getPath();

		try {
			// 2. WRITE
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,
					false)); // Creates a buffered character-output
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
			System.out.println("Following file has been written" + fileName);
			writer.close(); // close the file
		} catch (IOException e) {
			e.printStackTrace(); // throws a standard error when there are
									// errors with file handling
		}// end catch
	}// end exportcustomer

	public void exportCustomerExcel2013(ArrayList<Customer> customers) {
		System.out.println("Experimental Excel Export Function of DataFile"); // TODO
																				// debug
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Full Customer Export");

		int rownum = 0;
		for (Customer currentCustomer : customers) {
			Row row = sheet.createRow(rownum++);
			int cellnum = 0;
			for (Object obj : currentCustomer.exportText().split(",")) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date)
					cell.setCellValue((Date) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}

		try {
			FileOutputStream out = new FileOutputStream(new File(this.fileName
					+ ".xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CustomerAbstract[] importCustomer() {

		// 1. OPEN file
		File path = new File(this.fileName + ".csv");
		String fileName = path.getPath();

		StringBuffer fileContent = new StringBuffer();

		try {
			// 2. READ file
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;
			numberOfLines = 0; // reset number of lines

			while ((line = reader.readLine()) != null) {

				numberOfLines++; // and increments the number of customers the
									// file has till it gets to the end of the
				fileContent.append(line).append("-");

			}
			String[] customerData = new String[numberOfLines]; // create a new
			// array string to store all customers separated

			// TODO reset file object and loop directly instead of splitting and
			// Modifying original content with a -
			customerData = fileContent.toString().split("-");
			// splits the StringBuffer every time it finds an "-" to store every
			// customer in a position of the array
			this.groupOfCustomers = textToCustomer(customerData);
			// 3. CLOSE file
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return groupOfCustomers;
	}

	/**
	 * This function converts multiple Strings which represent Customers into a
	 * Customer object
	 * 
	 * @param customerData
	 * @return
	 */
	public CustomerAbstract[] textToCustomer(String[] customerData) {
		this.groupOfCustomers = new CustomerSQL[numberOfLines];
		for (int i = 0; i <= (groupOfCustomers.length - 1); i++) {
			// NEW CODE with easy constructor
			try {
				groupOfCustomers[i] = new CustomerSQL(customerData[i]);
			} catch (Exception e) {
				e.printStackTrace();
				System.out
						.println("WARNING, customer was not successfully imported, there might be corrupted data");
			}
		}
		return this.groupOfCustomers;
	}

	/**
	 * Import the minimum Balance in Cents from an Excel 2013 sheet
	 * 
	 * @return
	 */
	public BigDecimal importMinimumBalanceExcel2013() {
		FileInputStream file;
		try {
			file = new FileInputStream(new File(this.fileName + ".xlsx"));
			// Get the workbook instance for XLS file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			// Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			Row row1 = sheet.getRow(0); // Get first item from row
			Cell cellA1 = row1.getCell(0); // Get first cell from row
			Double cellValue = cellA1.getNumericCellValue(); // REad value
			BigDecimal b = new BigDecimal(cellValue, new MathContext(3,
					RoundingMode.HALF_UP)); // Convert Value into correct
											// datatype
			file.close();
			return b; // return result
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} // end of try
	}// end of import minimum Balance

	public DataFile(CustomerAbstract[] customerToExport) {
		this.groupOfCustomers = customerToExport;
		this.fileName = "data_customers";
	} // end of constructor

	/**
	 * Another constructor to avoid duplicated passing of the Customer to export
	 * and dynamically use the class
	 */
	public DataFile() {
		this.groupOfCustomers = null;
		this.fileName = "data_customers";
	} // end of constructor

	/**
	 * Constructor for simple filename import only
	 * 
	 * @author benste
	 * @param newFileName
	 */
	public DataFile(String newFileName) {
		this.groupOfCustomers = null;
		this.fileName = newFileName;
	} // end of constructor

	public Integer getNumberOfLines() {
		return numberOfLines;
	} // end of getNumberofLines

	/**
	 * Wrapper to use existing import with new DataTypes
	 * 
	 * @param newCustomers
	 */
	public void exportCustomer(ArrayList<Customer> newCustomers) {
		Customer[] custArray = new Customer[newCustomers.size()];
		custArray = newCustomers.toArray(new Customer[0]);
		this.exportCustomer(custArray);
	}

}
