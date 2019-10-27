package activityreport;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 'Apache POI, a project run by the Apache Software Foundation provides pure Java libraries for reading and
 * writing files in Microsoft Office formats, such as Word, PowerPoint and Excel.'
 * @author LoganDuck
 * @see https://poi.apache.org
 * @see https://en.wikipedia.org/wiki/Apache_POI
 */
public class Excel {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFFont font;
	private Row row;
	private Cell cell;
	private File tempFile;
	private int visitCount;
	private String reportingMonth, reportingYear;
	private Scanner scan;
	
	/**
	 * Constructor for <code>Excel</code>.
	 * @param selectedFileSource
	 * @param date
	 * @param visitCount
	 */
	public Excel(File tempFile, String date, int visitCount) {
		setTempFile(tempFile);
		setVisitCount(visitCount);
		convertDate(date);
		
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(getReportingMonth() + " " + getReportingYear());
		
		font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		addHeader();
		
		font.setUnderline((byte) 1);
		addColumnTitles();
		
		addData();
		
		try {
			File file = new File(System.getProperty("user.home") + "/Desktop/" + getReportingMonth() + getReportingYear() + ".xlsx");
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
			Desktop.getDesktop().open(file);
			
			File extract = new File(System.getProperty("user.home") + "/Desktop/temp_extracted.txt");
			extract.delete();
		} catch (IOException e) {
			System.out.println("File not found: " + e.getMessage());
		}	
	}
	
	/**
	 * Adds the header data to the created report. All reports will follow the same concept.
	 * The report submitted to SilverSneakers for reimbursement must include facilities' name
	 * address, location id, and reporting month and year. 
	 */
	public void addHeader() {
		addRowForHeader(0, "WELLNESS CENTER", 0);
		addRowForHeader(1, "309 MARIARDEN ROAD", 1);
		addRowForHeader(2, "DADEVILLE, AL 36853", 2);
		addRowForHeader(3, "LOCATION: 26100", 3);
		addRowForHeader(4, "REPORTING MONTH: " + getReportingMonth() + " " + getReportingYear(), 4);
	}
	
	/**
	 * Data added for each row in the header.
	 * @param cellRow - the row number the data will be added to.
	 * @param text - the <code>text</code> to be added to the <code>cellRow</code>.
	 * @param index - the index for which to merge and center.
	 */
	public void addRowForHeader(int cellRow, String text, int index) {
		row = sheet.createRow(cellRow);
		cell = row.createCell(0);
		CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
		CellUtil.setFont(cell, font);
		cell.setCellValue(text);
		sheet.addMergedRegion(new CellRangeAddress(index, index, 0, 5));
	}
	
	/**
	 * Adds the column titles to the created report. All reports will follow the same concept.
	 */
	public void addColumnTitles() {
		row = sheet.createRow(5);
		columnTitle(0, "Member ID#");
		columnTitle(1, "16 Digit ID#");
		columnTitle(2, "First Name");
		columnTitle(3, "Last Name");
		columnTitle(4, "Date of Visit");
		columnTitle(5, "Time of Visit");
	}
	
	/**
	 * Data added for each column for the report.
	 * @param cellColumn
	 * @param text
	 */
	public void columnTitle(int cellColumn, String text) {
		cell = row.createCell(cellColumn);
		CellUtil.setFont(cell, font);
		cell.setCellValue(text);
	}
	
	/**
	 * The <code>file</code> contains the extracted data in the report that was saved
	 * from the membership system. The extracted data is then written to the new report
	 * and saved to the users Desktop.
	 */
	public void addData() {
		try {
			scan = new Scanner(getTempFile());
			String[] data = scan.nextLine().split(",");
			
			/* 'i' starts at 6 because header data is added above this row. 6 is added to
			 * 'getVisitCount()' to ensure all members and their visits are written to the
			 * report. */
			for (int i = 6; i < getVisitCount() + 6; i++) {
				row = sheet.createRow(i);
				addMemberToRow(0, data[0]);
				addMemberToRow(1, data[1]);
				addMemberToRow(2, data[2]);
				addMemberToRow(3, data[3]);
				addMemberToRow(4, data[4]);
				addMemberToRow(5, data[5]);
				
				if (i == getVisitCount() + 5) {
					break;
				}
				data = scan.nextLine().split(",");
			}	
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		}
		
		for (int i = 0; i < 6; i++) {
			sheet.autoSizeColumn(i);
		}
		
		/* adds errors to ignore in the spreadsheet */
		sheet.addIgnoredErrors(new CellRangeAddress(6, visitCount + 5, 0, 5), IgnoredErrorType.NUMBER_STORED_AS_TEXT, IgnoredErrorType.TWO_DIGIT_TEXT_YEAR);
	}
	
	/**
	 * Adds data from the <code>Member</code> to each row in the spreadsheet.
	 * @param cellColumn - the column the data is written to.
	 * @param text - the text added to the cell.
	 */
	public void addMemberToRow(int cellColumn, String text) {
		Cell cell = row.createCell(cellColumn);
		cell.setCellValue(text);
	}
	
	/**
	 * The written report is saved to the users Desktop as (month)(year).xlsx.
	 * <br>i.e., a report created in December 2018 would be saved as 'DECEMBER2018.xlsx'.
	 * <br><br><code>convertDate</code> is used to change the numerical date to alphabetical
	 * (i.e., '12' would be converted to 'DECEMBER').
	 * @param date - the previously formatted date as mm/dd/yy.
	 */
	public void convertDate(String date) {
		String month = date.substring(0, 2);
		setReportingYear("20" + date.substring(date.length() - 2));
		switch (month) {
		case "01": setReportingMonth("JANUARY"); break;
		case "02": setReportingMonth("FEBRUARY"); break;
		case "03": setReportingMonth("MARCH"); break;
		case "04": setReportingMonth("APRIL"); break;
		case "05": setReportingMonth("MAY"); break;
		case "06": setReportingMonth("JUNE"); break;
		case "07": setReportingMonth("JULY"); break;
		case "08": setReportingMonth("AUGUST"); break;
		case "09": setReportingMonth("SEPTEMBER"); break;
		case "10": setReportingMonth("OCTOBER"); break;
		case "11": setReportingMonth("NOVEMBER"); break;
		case "12": setReportingMonth("DECEMBER"); break;
		default: setReportingMonth("ERROR"); break;
		}
	}
	
	/**
	 * The <code>file</code> is a temporary txt file that extracted data in the report saved
	 * from the membership system. Once the data is read from the <code>file</code>,
	 * written to the report, and the report is opened from the application, the
	 * <code>file</code> is deleted from the users Desktop.
	 *  
	 * @return the temporary file that contains data extracted in the report that was
	 * saved from the membership system.
	 */
	public File getTempFile() {
		return tempFile;
	}
	
	/**
	 * Sets the temporary file that was created from the extracted data in the report
	 * that was saved from the membership system.
	 * @param tempFile
	 */
	public void setTempFile(File tempFile) {
		this.tempFile = tempFile;
	}
	
	/**
	 * <code>visitCount</code> represents the total number of visits for SilverSneakers
	 * members during the reporting month. The value is initially determined after all the
	 * data is extracted from <code>ExtractVisitData</code>.
	 * @return - the total number of visits for the reporting month.
	 */
	public int getVisitCount() {
		return visitCount;
	}
	
	/**
	 * Sets the visit count to use in the <code>Excel</code> class. The value is initially
	 * determined after all the data is extracted from <code>ExtractVisitData</code>. 
	 * @param visitCount
	 */
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	
	/**
	 * @return the <code>reportingMonth</code> for the SilverSneakers report.
	 */
	public String getReportingMonth() {
		return reportingMonth;
	}
	
	/**
	 * Sets the <code>reportingMonth</code> for the SilverSneakers report.
	 * @param reportingMonth
	 */
	public void setReportingMonth(String reportingMonth) {
		this.reportingMonth = reportingMonth;
	}
	
	/**
	 * @return the <code>reportingYear</code> for the SilverSneakers report. 
	 */
	public String getReportingYear() {
		return reportingYear;
	}
	
	/**
	 * Sets the <code>reportingYear</code> for the SilverSneakers report.
	 * @param reportingYear
	 */
	public void setReportingYear(String reportingYear) {
		this.reportingYear = reportingYear;
	}
}