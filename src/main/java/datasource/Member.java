package datasource;

/**
 * This class represents a <code>Member</code> model. A <code>Member</code>
 * will have a <code>firstName</code>, <code>lastName</code>, <code>memberId</code>,
 * <code>healthwaysId</code>, <code>dateOfVisit</code>, and <code>timeOfVisit</code> 
 * @author LoganDuck
 */
public class Member {
	private String firstName;
	private String lastName;
	private String memberId;
	private String healthwaysId;
	private String dateOfVisit;
	private String timeOfVisit;
	
	/**
	 * @return <code>firstName</code> of the <code>Member</code>.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the <code>firstName</code> of the <code>Member</code>.
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return <code>lastName</code> of the <code>Member</code>.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the <code>lastName</code> of the <code>Member</code>.
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return <code>memberId</code> of the <code>Member</code>. The
	 * <code>memberId</code> is the identification number of the
	 * <code>Member</code> in the membership system.
	 */
	public String getMemberId() {
		return memberId;
	}
	
	/**
	 * Sets the <code>memberId</code> of the <code>Member</code>.
	 * @param memberId
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	/**
	 * @return <code>healthwaysId</code> of the <code>Member</code>. The
	 * <code>healthwaysId</code> is the identification number of the
	 * <code>Member</code>, which is provided by SilverSneakers.
	 */
	public String getHealthwaysId() {
		return healthwaysId;
	}
	
	/**
	 * Sets the <code>healthwaysId</code> of the <code>Member</code>.
	 * @param healthwaysId
	 */
	public void setHealthwaysId(String healthwaysId) {
		this.healthwaysId = healthwaysId;
	}

	/**
	 * @return <code>dateOfVisit</code> of the <code>Member</code>.
	 */
	public String getDateOfVisit() {
		return dateOfVisit;
	}
	
	/**
	 * Sets the <code>dateOfVisit</code> of the <code>Member</code>.
	 * @param dateOfVisit
	 */
	public void setDateOfVisit(String dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}
	
	/**
	 * @return <code>timeOfVisit</code> of the <code>Member</code>.
	 */
	public String getTimeOfVisit() {
		return timeOfVisit;
	}
	
	/**
	 * Sets the <code>timeOfVist</code> of the <code>Member</code>.
	 * @param timeOfVisit
	 */
	public void setTimeOfVisit(String timeOfVisit) {
		this.timeOfVisit = timeOfVisit;
	}
}