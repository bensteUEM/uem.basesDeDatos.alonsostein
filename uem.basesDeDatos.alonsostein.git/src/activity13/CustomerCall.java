package activity13;

import java.util.Calendar;

import activity10.Customer;

public class CustomerCall {

	private Customer origin;
	private String destination;
	private Integer duration; // in seconds
	private Calendar startTime;

	private CustomerCall() {
	}

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
	public CustomerCall(Customer newOrigin, String newDestination, Calendar startTime) {
		// Save params
		this.origin = newOrigin;
		this.destination = newDestination;

		// Initialize other values
		this.duration = 0;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Calculate the Call Time in Seconds based on the End Time of the Call
	 */
	public Integer calculateDuration(Calendar endTime) {
		this.duration = (int) ((this.startTime.getTimeInMillis() -endTime.getTimeInMillis()) / 100);
		return duration;
	}
	
	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * This method does calculate the total costs in Cent of the Call
	 * 
	 * @return costs in Cents
	 * @author benste
	 */
	public Integer getTotal() {
		return duration * origin.getRate();
	}
}
