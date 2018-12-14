package datasource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Visits {
	private int visitCount;
	private Scanner scan;

	public Visits(String source) {
		try {
			scan = new Scanner(new File(source));
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		}		
		String line = "";
		while (scan.hasNext()) {
			line = scan.nextLine().trim();
			if (line.length() >= 14 && line.substring(0, 14).equals("Total Visits :")) {
				break;
			}
		}
		setVisitCount(Integer.parseInt(line.substring(15)));
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
}