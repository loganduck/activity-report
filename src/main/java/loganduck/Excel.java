package loganduck;

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

public class Excel {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private Row row;
	private Cell cell;
	private File source;
	private int visitCount;
	private String reportingMonth;
	private String reportingYear;
	private XSSFFont font;
	private Scanner scan;
	
	public Excel(File source, String date, int visitCount) {
		setSource(source);
		setVisitCount(visitCount);
		extractDate(date);
		
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
	
	public void addHeader() {
		addRowForHeader(0, "WELLNESS CENTER", 0);
		addRowForHeader(1, "309 MARIARDEN ROAD", 1);
		addRowForHeader(2, "DADEVILLE, AL 36853", 2);
		addRowForHeader(3, "LOCATION: 26100", 3);
		addRowForHeader(4, "REPORTING MONTH: " + getReportingMonth() + " " + getReportingYear(), 4);
	}
	
	public void addRowForHeader(int cellRow, String value, int index) {
		row = sheet.createRow(cellRow);
		cell = row.createCell(0);
		CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
		CellUtil.setFont(cell, font);
		cell.setCellValue(value);
		sheet.addMergedRegion(new CellRangeAddress(index, index, 0, 5));
	}
	
	public void addColumnTitles() {
		row = sheet.createRow(5);
		columnTitle(0, "Member ID#");
		columnTitle(1, "16 Digit ID#");
		columnTitle(2, "First Name");
		columnTitle(3, "Last Name");
		columnTitle(4, "Date of Visit");
		columnTitle(5, "Time of Visit");
	}
	
	public void columnTitle(int index, String value) {
		cell = row.createCell(index);
		CellUtil.setFont(cell, font);
		cell.setCellValue(value);
	}
	
	public void addData() {
		try {
			scan = new Scanner(getSource());
			String[] data = scan.nextLine().split(",");
			for (int i = 6; i < visitCount + 6; i++) {
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
		
		sheet.addIgnoredErrors(new CellRangeAddress(6, visitCount + 5, 0, 5), IgnoredErrorType.NUMBER_STORED_AS_TEXT, IgnoredErrorType.TWO_DIGIT_TEXT_YEAR);
	}
	
	public void addMemberToRow(int index, String value) {
		Cell cell = row.createCell(index);
		cell.setCellValue(value);
	}
	
	public void extractDate(String date) {
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
	
	public File getSource() {
		return source;
	}
	
	public void setSource(File source) {
		this.source = source;
	}
	
	public int getVisitCount() {
		return visitCount;
	}
	
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	
	public String getReportingMonth() {
		return reportingMonth;
	}
	
	public void setReportingMonth(String reportingMonth) {
		this.reportingMonth = reportingMonth;
	}
	
	public String getReportingYear() {
		return reportingYear;
	}
	
	public void setReportingYear(String reportingYear) {
		this.reportingYear = reportingYear;
	}
}