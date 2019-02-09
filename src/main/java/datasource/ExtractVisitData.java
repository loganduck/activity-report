package datasource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import activityreport.Excel;

public class ExtractVisitData {
	private Scanner scan;
	private String source;
	
	private Visits visits;
	private int visitCount;
	private int counter = 0;
	
	private Date date = new Date();
	private String dateOfReport;

	private ArrayList<String[]> queue;
	private Member member;
	private PrintWriter writer;
	private File file = new File(System.getProperty("user.home") + "/Desktop/temp_extracted.txt");

	public ExtractVisitData(String source) {
		this.source = source;
		visits = new Visits(getSource());
		setVisitCount(visits.getVisitCount());

		try {
			scan = new Scanner(new File(getSource()));
		} catch (FileNotFoundException e) {

		}

		setDateOfReport(date.getDateFromFile(scan.nextLine())); // 08/31/18
		
		scan.nextLine();
		scan.nextLine();
		scan.nextLine();
		scan.nextLine();
		scan.nextLine();

		try {
			writer = new PrintWriter(file);

		} catch (FileNotFoundException e) {

		}
		String[] data;
		while (counter < visitCount) {
			queue = new ArrayList<String[]>();
			do {
				data = removeSpacesFromLine();
				if (data.length == 0) {
					String[] line = queue.remove(queue.size() - 1);
					member = new Member();
					member.setMemberId(line[2]);
					member.setHealthwaysId(line[line.length - 1]);
					counter--;
					break;
				}
				queue.add(data);
				counter++;
			} while (data.length > 0);
			writeDataToFile(member);
		}
		scan.close();
		writer.close();
		
		new Excel(getFile(), getDateOfReport(), getVisitCount());
		
	}

	public void writeDataToFile(Member member) {
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

	public String getSource() {
		return source;
	}

	public String getDateOfReport() {
		return dateOfReport;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setDateOfReport(String dateOfReport) {
		this.dateOfReport = dateOfReport;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	
	public File getFile() {
		return file;
	}

	public String[] removeSpacesFromLine() {
		List<String> listed_data = new ArrayList<String>(Arrays.asList(scan.nextLine().split(" ")));
		listed_data.removeAll(Arrays.asList("", null));
		return Arrays.copyOf(listed_data.toArray(), listed_data.size(), String[].class);	
	}
}