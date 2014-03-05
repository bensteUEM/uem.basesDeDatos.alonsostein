package activity13;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import activity10.Customer;

public class CustomerCall {

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
			Calendar startTime) {
		// Save params
		this.origin = newOrigin;
		this.destination = newDestination;
		this.startTime = startTime;

		// Initialize other values
		this.duration = 0;
	}// End Customer Call default constructor

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	} // end getDestination

	/**
	 * Calculate the Call Time in Seconds based on the End Time of the Call
	 */
	public Integer calculateDuration(Calendar endTime) {
		this.duration = (int) ((endTime.getTimeInMillis() - this.startTime
				.getTimeInMillis()) / 1000);
		return duration;
	} // end calculateDuration

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	} // end getDuration

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	} // end setDuration

	/**
	 * This method does calculate the total costs in Cent of the Call
	 * 
	 * @return costs in Cents
	 * @author benste
	 */
	public BigDecimal getTotal() {
		BigDecimal total = new BigDecimal((duration * origin.getRate())/100.00, new MathContext(3,
				RoundingMode.HALF_UP));
		System.out.println(total); //TODO DEBUG
		return total;
	} // end getTotal

	/**
	 * @return startTime
	 *            the time the call started
	 */
	public Date getStartTime() {
		return this.startTime.getTime();
	}
}
