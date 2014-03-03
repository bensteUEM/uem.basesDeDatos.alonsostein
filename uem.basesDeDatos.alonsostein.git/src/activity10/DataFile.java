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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataFile {
	private Customer[] groupOfCustomers;
	private int numberOfLines = 0;
	private String fileName = "";

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

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
		File path = new File(this.fileName+".csv");
		String fileName = path.getPath();

		System.out.println(path); //TODO debug
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
	
	public void exportCustomerExcel2013(ArrayList<Customer> customers){
		System.out.println("Experimental Excel Export Function of DataFile"); //TODO debug
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
			FileOutputStream out = new FileOutputStream(new File(this.fileName+".xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Customer[] importCustomer(){

		// 1. OPEN file
		File path = new File(this.fileName+".csv");
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
			// array string
			// to store all
			// customers
			// separated
			// @LUIS Syntax TYPE[] NAME = OBJECT
			this.groupOfCustomers = new Customer[numberOfLines];
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

	/**
	 * Import the minimum Balance in Cents from an Excel 2013 sheet
	 * @return
	 */
	public BigDecimal importMinimumBalanceExcel2013(){
		FileInputStream file;
		try {
			file = new FileInputStream(new File(this.fileName+".xlsx"));
			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			//Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			 
			//Get iterator to all the rows in current sheet
			Row row1 = sheet.getRow(0);
			Cell cellA1 = row1.getCell(0); 
			Double cellValue = cellA1.getNumericCellValue();
			System.out.println("Number: "+cellValue); //TODO DEBUG
			BigDecimal b = new BigDecimal(cellValue, new MathContext(2,RoundingMode.HALF_UP));
			System.out.println("Number as BigDecimal with 2 decimals: "+b);//TODO DEBUG
			
			return b;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}  		
	}
	
	public DataFile(Customer[] customers) {
		this.groupOfCustomers = customers;
		this.fileName = "data_customers";
	}

	/**
	 * Another constructor to avoid duplicated passing of the Customer to export
	 * and dynamically use the class
	 */
	public DataFile() {
		this.groupOfCustomers = null;
		this.fileName = "data_customers";
	}

	public Integer getNumberOfLines() {
		return numberOfLines;
	}

	/**
	 * Wrapper to use existing import with new DataTypes
	 * @param newCustomers
	 */
	public void exportCustomer(ArrayList<Customer> newCustomers) {
		// TODO Auto-generated method stub
		Customer[] custArray = new Customer[newCustomers.size()];
		custArray = newCustomers.toArray(new Customer[0]);
		this.exportCustomer(custArray);
	}
	

}
