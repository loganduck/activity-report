package activityreport;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import datasource.ExtractVisitData;

/**
 * <code>SelectedFile</code> is used when a file is selected from the 
 * <code>JFileChooser</code> in <code>ActivityReportMenu</code>
 * @author LoganDuck (logan.duck@yahoo.com)
 */
public class SelectedFile {
	private JFrame frame;
	private JLabel errorIcon;
	private JLabel errorLabel;
	private JButton button;
	private Scanner scan;
	
	/**
	 * Opens up a window with an 'Incompatible file' error.
	 */
	public void throwError() {
		frame = new JFrame("Error");
		frame.setLayout(null);
		frame.setSize(388, 200);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		
		errorIcon = new JLabel(new ImageIcon(Error.class.getResource("/images/error.png")));
		errorIcon.setBounds(155, 10, errorIcon.getPreferredSize().width, errorIcon.getPreferredSize().height);
		frame.add(errorIcon);
		
		errorLabel = new JLabel("Error: Incompatible file. Try again.");
		errorLabel.setFont(new Font("", Font.BOLD, 13));
		errorLabel.setForeground(Color.WHITE);
		errorLabel.setBounds(75, 85, errorLabel.getPreferredSize().width, errorLabel.getPreferredSize().height);
		frame.add(errorLabel);
		
		button = new JButton("OK");
		button.setFont(new Font("", Font.PLAIN, 16));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		button.setBounds(156, 110, button.getPreferredSize().width, button.getPreferredSize().height);
		frame.add(button);
		
		frame.setVisible(true);
	}
	
	/**
	 * <code>isValidFileFormat</code> is called from <code>ActivityReportMenu</code>'s <code>createReportButton</code>
	 * to validate file format. 
	 * 
	 * @param file
	 * @return
	 */
	public boolean isValidFileFormat(String file) {
		try {
			scan = new Scanner(new File(file));
			if (!(scan.hasNext())) {
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		/* Steps through the first six lines of the file to validate format. The first six lines are concrete and
		 * will be the same for all files. Someone could copy the first six lines and put them at the top of any txt file
		 * and it work, but there wouldn't be any benefit to them. They would just waste their time because the code will
		 * break.
		 */
		for (int i = 0; i < 6; i++) {
			String[] line = ExtractVisitData.removeEmptyIndices(scan.nextLine().split(" "));
			
			switch(i) {
			case 0:
				if (line.length != 10 || !(Arrays.toString(line).substring(1, 54).equals("Wellness, Center, LLC, Visit, Activity, Report, Date:"))) {
					return false;
				}
				break;
				
			case 1:
				if (line.length != 5 || !(Arrays.toString(line).substring(1, 38).equals("Member, Types:, SILVERSNEA/, Page:, 1"))) {
					return false;
				}
				break;
				
			case 2:
				if (line.length != 18 || !(Arrays.toString(line).substring(48, 92).equals("Active, and, InActive, Members, Start, Time:"))) {
					return true;
				}
				break;
				
			case 3:
				if (line.length != 0) {
					return false;
				}
				break;
				
			case 4:
				if (line.length != 7 || !(Arrays.toString(line).substring(1, 45).equals("Name, Date, Time, Phone, Messages, Mbr, Type"))) {
					return false;
				}
				break;
				
			case 5:				
				if (line.length != 1 || !(Arrays.toString(line).substring(1, 11).equals("=========="))) {
					return false;
				}
				break;
			}	
		}
		return true;
	}

}