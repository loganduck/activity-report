package datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Date {
	private String month;
	private String day;
	private String year;
	private String date;

	public String getDateFromFile(String line) {
		String[] arr = line.split(" ");
		List<String> values = new ArrayList<String>(Arrays.asList(arr));
		values.removeAll(Arrays.asList(""));
		arr = Arrays.copyOf(values.toArray(), values.size(), String[].class);

		extractMonth(arr[arr.length - 3]);
		extractDay(arr[arr.length - 2]);
		extractYear(arr[arr.length - 1]);

		setDate(getMonth() + "/" + getDay() + "/" + getYear());

		return date;
	}

	public void extractMonth(String value) {
		switch (value) {
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

	public void extractDay(String value) {
		if (value.length() == 3) {
			setDay(value.substring(0, 2));
		} else {
			setDay(value.substring(0, 1));
		}
	}

	public void extractYear(String value) {
		setYear(value.substring(2));
	}

	public String getMonth() {
		return month;
	}

	public String getDay() {
		return day;
	}

	public String getYear() {
		return year;
	}

	public String getDate() {
		return date;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setDate(String date) {
		this.date = date;
	}
}