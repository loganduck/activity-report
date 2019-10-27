package datasource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import activityreport.Excel;

/**
 * The <code>ExtractVisitData</code> class is used to extract data from the file saved from the membership
 * system into a temporary file called 'temp_extracted.txt'. Note: the file could have also been a csv.
 * @author LoganDuck
 *
 */
public class ExtractVisitData {
//	private Scanner scan;
	private String selectedFile;
	private int numberOfVisits;
	private String dateOfReport;
	private List<String[]> queue;
	private Member member;
	private PrintWriter writer;
	
	/* data is temporarily stored in temp_extracted.txt and is deleted once the spreadsheet is created
	 * and is opened. */
	private File file = new File(System.getProperty("user.home") + "/Desktop/temp_extracted.txt");
	
	/**
	 * Constructor for <code>ExtractVisitData</code>. 
	 * @param selectedFile - the <code>selectedFile</code> is selected from <code>ActivityReportMenu</code>'s <code>JFileChooser</code>.
	 */
	public ExtractVisitData(String selectedFile) {
		this.selectedFile = selectedFile;
		
		try (Scanner scan = new Scanner(new File(getSelectedFile()))) {
			writer = new PrintWriter(file);
			setDateOfReport(new MemberVisitDate().getDateFromFile(scan.nextLine()));
			
			/* skips over header data from the report. */
			int index = 0;
			while (index++ < 6) {
				scan.nextLine();
			}
			
			/*
			 * Each line of data in the file is scanned over and is added into an array that removed empty/null indices and delimits by (" ").
			 * The indices of the array will contain visit information that is added to a queue. Once a line is reach that has an array length
			 * of 0, a member is created and the member and their visits are written to the temporary file. Then, the queue is cleared and starts
			 * over with the next member and their visits. This cycle continues until the "Total Visits" line is reached, which tells us all visits
			 * have been accounted for.
			 */
			boolean reachedEndOfVisits = false;
			String[] data;
			while (scan.hasNext()) {
				queue = new ArrayList<String[]>();
				do {
					data = removeEmptyIndices(scan.nextLine().split(" "));
					if (data.length == 0) {
						String[] line = queue.remove(queue.size() - 1);
						member = new Member();
						member.setMemberId(line[2]);
						member.setHealthwaysId(line[line.length - 1]);
						numberOfVisits--;
						break;
					} else if (data.length > 0 && data[0].equals("Total") && data[1].equals("Visits")) {
						reachedEndOfVisits = true;
						break;
					}
					queue.add(data);
					numberOfVisits++;
				} while(data.length > 0);
				if (reachedEndOfVisits) {
					break;
				}
				writeDataToFile(member);
				
			}
			scan.close();
			writer.close();
			
			new Excel(getFile(), getDateOfReport(), getNumberOfVisits());
		} catch (FileNotFoundException e) {
			System.out.println("Error: unable to find file. " + e.getMessage());
		}
	}

	/**
	 * <code>writeDataToFile</code> is called from the constructor for each member so their visits can
	 * be written to the temp file.
	 * @param member
	 */
	private void writeDataToFile(Member member) {
		for (String[] visit : queue) {
			member.setFirstName(visit[1].toString());
			member.setLastName(visit[0].toString().substring(0, visit[0].toString().length() - 1));
			member.setDateOfVisit(visit[2] + " " + visit[3]);
			member.setTimeOfVisit(visit[4].toString());

			writer.println(
					member.getMemberId() + ", " + member.getHealthwaysId() + ", "
					+ member.getFirstName() + ", " + member.getLastName() + ", "
					+ member.getDateOfVisit() + ", " + member.getTimeOfVisit()
				);
			}
		/* new queue for each member */
		queue = new ArrayList<String[]>();
	}

	/**
	 * @return the <code>selectedFile</code> is the file selected from the <code>JFileChooser</code> in <code>ActivityReportMenu</code>.
	 */
	public String getSelectedFile() {
		return selectedFile;
	}
	
	/**
	 * @return the reports date.
	 */
	public String getDateOfReport() {
		return dateOfReport;
	}

	/**
	 * @return the <code>numberOfVisits</code> which is determined when the scanner reaches the line "Total Visits".
	 */
	public int getNumberOfVisits() {
		return numberOfVisits;
	}

	/**
	 * Sets the <code>dateOfReport</code> for the spreadsheet that will be created.
	 * @param dateOfReport
	 */
	public void setDateOfReport(String dateOfReport) {
		this.dateOfReport = dateOfReport;
	}

	/**
	 * @return the 'temp_extracted.txt' file. This file will be deleted by the <code>Excel</code> class after the spreadsheet is created and has opened.
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * <code>removeEmptyIndices</code> will take an array and remove indices that are empty or contain null values.
	 * <br><br>For example: <br>If we have,<br>
	 * <span style="margin-left:2em"><code>{"This", "", "", "is", "an", "  ", "example"}</code></span>
	 * <br><br> This method will return,<br>
	 * <span style="margin-left:2em"><code>{"This", "is", "an", "example"}</code></span>
	 * 
	 * @param arr
	 * @return
	 */
	public static String[] removeEmptyIndices(String[] arr) {
		List<String> list = new ArrayList<String>(Arrays.asList(arr));
		list.removeAll(Arrays.asList("", null));
		return Arrays.copyOf(list.toArray(), list.size(), String[].class);	
	}
}