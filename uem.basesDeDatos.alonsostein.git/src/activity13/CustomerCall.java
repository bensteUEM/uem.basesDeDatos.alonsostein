package activity13;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import activity10.Customer;

public class CustomerCall {
	// declaration of the class variables needed
	private Customer origin;
	private String destination;
	private Integer duration; // in seconds
	private Calendar startTime;

	private CustomerCall() {
	} // End Customer Call forbidden constructor

	/**
	 * Constructor for a new CustomerCall Object, will be initialized with cost
	 * and and duration 0 but does require two parameters
	 * 
	 * @param origin
	 *            Customer object of the Caller who will pay for it
	 * @param destination
	 *            String identiying the destination of the Call
	 * @author benste
	 */
	public CustomerCall(Customer newOrigin, String newDestination,
			Calendar startTime) { // constructor for Customer call
		// Save params
		this.origin = newOrigin; // set the customer who makes the call
		this.destination = newDestination; // set the destination of the call
		this.startTime = startTime; // set the start time

		// Initialize other values
		this.duration = 0; // initialize the duration to 0
	}// End Customer Call default constructor

	/**
	 * @return the destination
	 */
	public String getDestination() { // getter fot destination
		return destination;
	} // end getDestination

	/**
	 * Calculate the Call Time in Seconds based on the End Time of the Call
	 */
	public Integer calculateDuration(Calendar endTime) { // method th calculate
															// the call duration
															// passing the
															// endTime
		this.duration = (int) ((endTime.getTimeInMillis() - this.startTime
				.getTimeInMillis()) / 1000); // difference between the end time
												// and the start time would give
												// the call time in millisecods.
												// Divide it by 1000 to get it
												// in seconds
		return duration; // return the duration of the call
	} // end calculateDuration

	/**
	 * @return the duration
	 */
	public Integer getDuration() { // getter for the duration
		return duration;
	} // end getDuration

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(Integer duration) { // setter for duration
		this.duration = duration;
	} // end setDuration

	/**
	 * This method does calculate the total costs in Cent of the Call
	 * 
	 * @return costs in Cents
	 * @author benste
	 */
	public BigDecimal getTotal() { // method to calculate the total cost of the
									// call
		BigDecimal total = new BigDecimal(
				(duration * origin.getRate()) / 100.00, new MathContext(3)); // calculates
																				// the
																				// total
																				// with
																				// 3
																				// significative
																				// digits
																				// as
																				// the
																				// (duration*the
																				// rate
																				// of
																				// the
																				// customer)/100
		// System.out.println(total); //TODO DEBUG
		return total;
	} // end getTotal

	/**
	 * @return startTime the time the call started
	 */
	public Date getStartTime() { // getter for the start time of the call
		return this.startTime.getTime();
	} // end of getStartTime

	public String toString() {
		return "Destination: " + this.getDestination() + "  Duration: "
				+ this.getDuration() + " Cost: " + this.getTotal()
				+ " Start time: " + this.getStartTime();
	}
} // end of class CustomerCall
