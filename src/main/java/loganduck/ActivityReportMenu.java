package loganduck;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import datasource.ExtractVisitData;

public class ActivityReportMenu implements MouseListener {
	private JFrame frame;
	private JLabel logo;
	private JLabel menuTitle;
	private JButton chooseFileButton, getHelpButton, createReportButton, aboutButton;
	private JLabel filePathLabel;
	private String file;
	
	public ActivityReportMenu() {
		frame = new JFrame("Wellness Center - Activity Report");
		frame.setLayout(null);
		frame.setSize(388, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		logo = new JLabel(new ImageIcon(ActivityReportMenu.class.getResource("/images/logo.png")));
		logo.setBounds(152, 20, 84, 84);
		frame.add(logo);
		
		menuTitle = new JLabel("Activity Report Menu");
		menuTitle.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 30));
		menuTitle.setForeground(Color.WHITE);
		menuTitle.setBounds(43, 120, 303, 36);
		frame.add(menuTitle);
		
		chooseFileButton = new JButton("<html><body><font size='6'>Choose File</font></body></html>");
		chooseFileButton.setName("chooseFileButton");
		chooseFileButton.setBackground(new Color(0, 150, 255));
		chooseFileButton.setOpaque(true);
		chooseFileButton.setBorderPainted(false);
		chooseFileButton.setForeground(Color.WHITE);
		chooseFileButton.addMouseListener(this);
		chooseFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
				fileChooser.setDialogTitle("Choose File");
				fileChooser.setFileFilter(new FileNameExtensionFilter(".txt, txt", "txt", "text"));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				boolean fileSelected = false;
				if (fileChooser.showOpenDialog(chooseFileButton) == JFileChooser.APPROVE_OPTION) {
					String dir = fileChooser.getCurrentDirectory().toString();
					File selectedFile = fileChooser.getSelectedFile();
					String source = "";
					if (dir.substring(dir.length() - 1, dir.length()).equals(".")) {
						dir = dir.substring(0, dir.length() - 1);
						source = dir + selectedFile.getName();
					} else {
						source = dir + "/" + selectedFile.getName();
					}
					setFile(source);
					fileSelected = true;
					
					if (fileSelected) {
						filePathLabel.setText(source);
					}
				}
			}
		});
		chooseFileButton.setBounds(75, 175, 240, 40);
		frame.add(chooseFileButton);
		
		getHelpButton = new JButton("<html><body><font size='6'>Get Help</font></body></html>");
		getHelpButton.setName("getHelpButton");		
		getHelpButton.setBackground(new Color(0, 150, 255));
		getHelpButton.setOpaque(true);
		getHelpButton.setBorderPainted(false);
		getHelpButton.setForeground(Color.WHITE);
		getHelpButton.addMouseListener(this);
		getHelpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				frame.setLayout(null);
				frame.setSize(300, 300);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.getContentPane().setBackground(Color.DARK_GRAY);
				
				JLabel label = new JLabel("<html><body>"
						+ "Saving Visit Activity Report:<br>"
						+ "1) 'Reports Menu' > 'Activity/Visit History'.<br>"
						+ "2) 'Start Date' as first day of month.<br>"
						+ "3) 'End Date' as last day of month.<br>"
						+ "4) 'Start Time' as '00:00'.<br>"
						+ "5) 'Stop Time' as '24:00'.<br>"
						+ "6) 'Member Types' as 'SILVERSNEA'.<br>"
						+ "7) 'Print/View/Export' > 'Save to File'.<br>"
						+ "8) Save file as txt on the Desktop.<br>"
						+ "<br><br>"
						+ "Creating the spreadsheet report:<br>"
						+ "1) Click 'Choose File'.<br>"
						+ "2) Navigate to file and select it.<br>"
						+ "3) Click 'Create Report'.<br>"
						+ "4) File > Save as"
						+ "</html>");
				
				label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
				label.setForeground(Color.WHITE);
				label.setBounds(5, 5, 266, 240);
				frame.add(label);
				frame.setVisible(true);
			}
		});
		getHelpButton.setBounds(75, 230, 240, 40);
		frame.add(getHelpButton);
		
		createReportButton = new JButton("<html><body><font size='6'>Create Report</font></body></html>");
		createReportButton.setName("createReportButton");
		createReportButton.setBackground(new Color(0, 150, 255));
		createReportButton.setOpaque(true);
		createReportButton.setBorderPainted(false);
		createReportButton.setForeground(Color.WHITE);
		createReportButton.addMouseListener(this);
		createReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Error error = new Error();
				if (getFile() == null || getFile().length() < 1 || !(error.checkFileFormat(getFile()))) {
					error.throwError();
				} else {
					frame.dispose();
					new ExtractVisitData(getFile());					
				}
			}
		});
		createReportButton.setBounds(75, 285, 240, 40);
		frame.add(createReportButton);
		
		aboutButton = new JButton(new ImageIcon(ActivityReportMenu.class.getResource("/images/about.png")));
		aboutButton.setName("aboutButton");
		aboutButton.setBackground(Color.DARK_GRAY);
		aboutButton.setOpaque(true);
		aboutButton.setBorderPainted(false);
		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFrame frame = new JFrame("About");
				frame.setLayout(null);
				frame.setSize(388, 280);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.getContentPane().setBackground(Color.DARK_GRAY);
				
				JLabel disclaimer = new JLabel("<html><body>"
						+ "Activity Report automates Excel spreadsheet reports of Silver Sneakers visit"
						+ "activity at the Wellness Center of Lake Martin. This product is not associated"
						+ "or affiliated with Silver Sneakers or Tivity Health."
						+ "</body></html>");
				disclaimer.setFont(new Font("Verdana", Font.BOLD, 15));
				disclaimer.setForeground(Color.WHITE);
				disclaimer.setBounds(5, 5, 372, 120);
				frame.add(disclaimer);
				
				JLabel label = new JLabel("Product by Logan Duck");
				label.setFont(new Font("Verdana", Font.BOLD, 13));
				label.setForeground(Color.WHITE);
				label.setBounds(5, 150, 167, 17);
				frame.add(label);
				
				JButton button = new JButton("OK");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				});
				button.setBounds(156, 180, 76, 30);
				frame.add(button);
				
				JLabel version = new JLabel("v: 4.0.0");
				version.setFont(new Font("Verdana", Font.ITALIC, 12));
				version.setForeground(Color.WHITE);
				version.setBounds(5, 240, 48, 16);
				frame.add(version);
				
				frame.setVisible(true);	
			}
		});
		aboutButton.setBounds(353, 345, 41, 37);
		frame.add(aboutButton);
		
		filePathLabel = new JLabel("");
		filePathLabel.setForeground(Color.WHITE);
		filePathLabel.setFont(new Font("Verdana", Font.PLAIN, 9));
		filePathLabel.setBounds(3, 360, 430, 20);
		frame.add(filePathLabel);
		
		frame.setVisible(true);
	}
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public void mousePressed(MouseEvent e) {
		String[] arr = e.getSource().toString().split(",");
		String button = arr[0].substring(arr[0].indexOf("[") + 1);
		switch (button) {
			case "chooseFileButton":
				chooseFileButton.setBackground(new Color(145, 145, 145));
				break;
			case "getHelpButton":
				getHelpButton.setBackground(new Color(145, 145, 145));
				break;
			case "createReportButton":
				createReportButton.setBackground(new Color(145, 145, 145));
				break;
			default:
				System.out.println("No case set for button.");
		}
	}

	public void mouseReleased(MouseEvent e) {
		String[] arr = e.getSource().toString().split(",");
		String button = arr[0].substring(arr[0].indexOf("[") + 1);
		switch (button) {
			case "chooseFileButton":
				chooseFileButton.setBackground(new Color(0, 150, 255));
				break;
			case "getHelpButton":
				getHelpButton.setBackground(new Color(0, 150, 255));
				break;
			case "createReportButton":
				createReportButton.setBackground(new Color(0, 150, 255));
				break;
			default:
				System.out.println("No case set for button.");
		}
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ActivityReportMenu();
			}
		});
	}
}