package loganduck;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Error {
	private JFrame frame;
	private JLabel errorIcon;
	private JLabel errorLabel;
	private JButton button;
	private Scanner scan;
	
	public void throwError() {
		frame = new JFrame("Error");
		frame.setLayout(null);
		frame.setSize(388, 200);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		
		errorIcon = new JLabel(new ImageIcon(Error.class.getResource("/images/error.png")));
		errorIcon.setBounds(155, 10, 78, 66);
		frame.add(errorIcon);
		
		errorLabel = new JLabel("Error: Incompatible file. Try again.");
		errorLabel.setFont(new Font("Default", Font.BOLD, 13));
		errorLabel.setForeground(Color.WHITE);
		errorLabel.setBounds(75, 85, 238, 16);
		frame.add(errorLabel);
		
		button = new JButton("OK");
		button.setFont(new Font("Default", Font.PLAIN, 16));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		button.setBounds(156, 110, 76, 30);
		frame.add(button);
		
		frame.setVisible(true);
	}
	
	public boolean checkFileFormat(String sourceFolder) {
		try {
			scan = new Scanner(new File(sourceFolder));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (!(scan.hasNext())) {
			return false;
		}
		
		for (int i = 0; i < 6; i++) {
			String[] line = removeSpacesFromLine();
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
	
	public String[] removeSpacesFromLine() {
		List<String> listed_data = new ArrayList<String>(Arrays.asList(scan.nextLine().split(" ")));
		listed_data.removeAll(Arrays.asList("", null));
		return Arrays.copyOf(listed_data.toArray(), listed_data.size(), String[].class);	
	}

}