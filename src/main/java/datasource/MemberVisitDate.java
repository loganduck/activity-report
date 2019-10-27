package datasource;

/**
 * <code>MemberVisitDate</code> is used with <code>ExtractVisitData</code> to get the
 * preferred date format, mm/dd/yy.
 * 
 * @author LoganDuck (logan.duck@yahoo.com)
 */
public class MemberVisitDate {
	private String month;
	private String day;
	private String year;
	private String date;

	/**
	 * The date in the report from the membership system will always be in the header
	 * at the top right. Let's say the date is 'Jan 01, 2019' the date will then 
	 * be changed and returned as '01/01/19'.
	 * @param line - The first line from the file. Since each file would have the same header
	 * info, the data from the line is put into an array for constant access time.
	 * @return - <code>date</date>: formatted as mm/dd/yy
	 */
	public String getDateFromFile(String line) {
		String[] arr = ExtractVisitData.removeEmptyIndices(line.split(" "));

		extractMonth(arr[arr.length - 3]);
		extractDay(arr[arr.length - 2]);
		extractYear(arr[arr.length - 1]);

		setDate(getMonth() + "/" + getDay() + "/" + getYear());

		return date;
	}

	/**
	 * Updates <code>month</code> to numerical. i.e., 'Mar' = '03'
	 * @param month
	 */
	public void extractMonth(String month) {
		switch (month) {
			case "Jan": setMonth("01"); return;
			case "Feb": setMonth("02"); return;
			case "Mar": setMonth("03"); return;
			case "Apr": setMonth("04"); return;
			case "May": setMonth("05"); return;
			case "Jun": setMonth("06"); return;
			case "Jul": setMonth("07"); return;
			case "Aug": setMonth("08"); return;
			case "Sep": setMonth("09"); return;
			case "Oct": setMonth("10"); return;
			case "Nov": setMonth("11"); return;
			case "Dec": setMonth("12"); return;
			default: setMonth("00"); return;
		}
	}
	
	/**
	 * Extracts the <code>day</code> from the date.
	 * 
	 * @param day
	 */
	public void extractDay(String day) {
		if (day.length() == 3) {
			setDay(day.substring(0, 2));
		} else {
			setDay(day.substring(0, 1));
		}
	}

	/**
	 * Extracts the <code>year</code> from the date. 
	 * @param year
	 */
	public void extractYear(String year) {
		setYear(year.substring(2));
	}

	/**
	 * @return <code>month</code> from the date.
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @return <code>day</code> from the date.
	 */
	public String getDay() {
		return day;
	}
	
	/**
	 * @return <code>year</code> from the date.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @return formatted date. (mm/dd/yy)
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the <code>month</code> of the report.
	 * @param month
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * Sets the <code>day</code> of the report.
	 * @param day
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * Sets the <code>year</code> of the report.
	 * @param year
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Sets the <code>date</code> of the report.
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
}