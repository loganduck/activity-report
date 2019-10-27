package activityreport;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import datasource.ExtractVisitData;

/**
 * SilverSneakers is a program that encourages older adults to participate in health and fitness by offering
 * free memberships at participating fitness centers and is included with many Medicare plans. In return,
 * fitness centers must submit their participating members visits per month for reimbursement.
 * 
 * ActivityReport is used at the Wellness Center of Lake Martin to extract visit activity data for
 * SilverSneakers’ members to automatically generate required spreadsheet reporting using Apache POI.
 * <br><br>
 * Disclaimer: ActivityReport is not affiliated with SilverSneakers or Tivity Health and is
 * specifically intended for use by the Wellness Center of Lake Martin to automatically generate
 * required reporting.
 * 
 * <br><br>
 * For any issues or feedback, please contact Logan Duck at logan.duck@yahoo.com with subject “ActivityReport”.
 * @author LoganDuck
 * @version 3
 */
public class ActivityReportMenu extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private JButton chooseFileButton, getHelpButton, createReportButton, aboutButton;
	private JLabel logo;
	private JLabel menuTitle;
	
	private JLabel filePathLabel;
	private String file;
	
	private final Color BUTTON_BACKGROUND = new Color(0, 150, 255);
	private final int BUTTON_WIDTH = 240, BUTTON_HEIGHT = 40;
	
	/**
	 * <code>ActivityReportMenu</code>'s constructor.
	 * @see ExtractVisitData
	 */
	public ActivityReportMenu() {
		setLayout(null);
		setSize(App.WIDTH, App.HEIGHT);
		setBackground(Color.DARK_GRAY);
		
		logo = new JLabel(new ImageIcon(ActivityReportMenu.class.getResource("/images/logo.png")));	
		logo.setBounds((App.WIDTH / 2) - (logo.getPreferredSize().height / 2), 20, logo.getPreferredSize().width, logo.getPreferredSize().height);
		add(logo);
		
		menuTitle = new JLabel("Activity Report Menu");
		menuTitle.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 30));
		menuTitle.setForeground(Color.WHITE);
		menuTitle.setBounds((App.WIDTH / 2) - (menuTitle.getPreferredSize().width / 2), 102, menuTitle.getPreferredSize().width, menuTitle.getPreferredSize().height);
		add(menuTitle);
		
		chooseFileButton = new JButton("<html><body><font size='6'>Choose File</font></body></html>");
		chooseFileButton.setName("chooseFileButton");
		chooseFileButton.setBackground(BUTTON_BACKGROUND);
		chooseFileButton.setForeground(Color.WHITE);
		chooseFileButton.setOpaque(true);
		chooseFileButton.setBorderPainted(false);
		chooseFileButton.addMouseListener(this);
		chooseFileButton.addActionListener(new ActionListener() {
			/**
			 * When <code>Choose File</code> is selected from the menu, it opens a <code>JFileChooser</code> to
			 * select the file from the membership system to extract the data from. The file filter is set to txt
			 * files only. Once the file is selected, the value for <code>file</code> is set.
			 */
			public void actionPerformed(ActionEvent e) {
				/*
				 * When 'Choose File' is clicked, it opens a JFileChooser to select the file to extract data from.
				 * The file filter is set to txt files only. Once the file is selected, the value for 'file'
				 * is set.
				 */
				JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
				fileChooser.setDialogTitle("Choose File");
				fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", ".txt"));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				boolean fileSelected = false;
				if (fileChooser.showOpenDialog(chooseFileButton) == JFileChooser.APPROVE_OPTION) {
					setFile(fileChooser.getCurrentDirectory().toString() + "/" + fileChooser.getSelectedFile().getName());
					fileSelected = true;
					/*
					 * ActivityReportMenu will display the dir and file when is selected in the footer.
					 */
					if (fileSelected) {
						filePathLabel.setText(getFile().toString());
					}
				}
			}
		});
		chooseFileButton.setBounds((App.WIDTH / 2) - (BUTTON_WIDTH / 2), 175, BUTTON_WIDTH, BUTTON_HEIGHT);
		add(chooseFileButton);
		
		getHelpButton = new JButton("<html><body><font size='6'>Get Help</font></body></html>");
		getHelpButton.setName("getHelpButton");		
		getHelpButton.setBackground(BUTTON_BACKGROUND);
		getHelpButton.setForeground(Color.WHITE);
		getHelpButton.setOpaque(true);
		getHelpButton.setBorderPainted(false);
		getHelpButton.addMouseListener(this);
		getHelpButton.addActionListener(new ActionListener() {
			/**
			 * Opens a window with instructions on how to save the file from the membership system
			 * and how to get the report created.
			 */
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Get Help");
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
				label.setBounds(5, 5, label.getPreferredSize().width, label.getPreferredSize().height);
				frame.add(label);
				frame.setVisible(true);
			}
		});
		getHelpButton.setBounds((App.WIDTH / 2) - (BUTTON_WIDTH / 2), 230, BUTTON_WIDTH, BUTTON_HEIGHT);
		add(getHelpButton);
		
		createReportButton = new JButton("<html><body><font size='6'>Create Report</font></body></html>");
		createReportButton.setName("createReportButton");
		createReportButton.setBackground(BUTTON_BACKGROUND);
		createReportButton.setForeground(Color.WHITE);
		createReportButton.setOpaque(true);
		createReportButton.setBorderPainted(false);
		createReportButton.addMouseListener(this);
		createReportButton.addActionListener(new ActionListener() {
			/**
			 * Validates the selected <code>file</code> is in the correct format by checking specific header information.
			 * If the <code>file</code> is not correct, a <code>SelectedFile</code> window opens with the error.
			 * If the <code>file</code> is correct, the data is extracted in <code>ExtractVisitData</code>.
			 * 
			 * <br><br>See also: <code>SelectedFile.java</code>, <code>ExtractVisitData.java</code>
			 */
			public void actionPerformed(ActionEvent e) {
				SelectedFile selectedFile = new SelectedFile();
				if (getFile() == null || getFile().length() < 1 || !(selectedFile.isValidFileFormat(getFile()))) {
					selectedFile.throwError();
				} else {
					App.dispose();
					new ExtractVisitData(getFile());					
				}
			}
		});
		createReportButton.setBounds((App.WIDTH / 2) - (BUTTON_WIDTH / 2), 285, BUTTON_WIDTH, BUTTON_HEIGHT);
		add(createReportButton);
		
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
				
				//TODO: update disclaimer info and bounds
				JLabel disclaimer = new JLabel("<html><body>"
						+ "ActivityReport is not affiliated with SilverSneakers or Tivity Health and is"
						+ " specifically intended for use by the Wellness Center of Lake Martin to automatically generate"
						+ " required reporting for SilverSneakers.</body></html>");
				disclaimer.setFont(new Font("Verdana", Font.BOLD, 15));
				disclaimer.setForeground(Color.WHITE);
				disclaimer.setBounds(5, 5, 372, 120);
				frame.add(disclaimer);
				
				JLabel label = new JLabel("Product by Logan Duck");
				label.setFont(new Font("Verdana", Font.BOLD, 13));
				label.setForeground(Color.WHITE);
				label.setBounds(5, 150, label.getPreferredSize().width, label.getPreferredSize().height);
				frame.add(label);
				
				JButton button = new JButton("OK");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				});
				button.setBounds(156, 180, button.getPreferredSize().width, button.getPreferredSize().height);
				frame.add(button);
				
				JLabel version = new JLabel("v: 3");
				version.setFont(new Font("Verdana", Font.ITALIC, 12));
				version.setForeground(Color.WHITE);
				version.setBounds(5, 240, version.getPreferredSize().width, version.getPreferredSize().height);
				frame.add(version);
				
				frame.setVisible(true);	
			}
		});
		aboutButton.setBounds(353, 345, aboutButton.getPreferredSize().width, aboutButton.getPreferredSize().height);
		add(aboutButton);
		
		filePathLabel = new JLabel("");
		filePathLabel.setForeground(Color.WHITE);
		filePathLabel.setFont(new Font("Verdana", Font.PLAIN, 9));
		filePathLabel.setBounds(3, 360, 430, 20);
		add(filePathLabel);
		
	}
	
	/**
	 * @return - The <code>file</code> chosen from JFileChooser
	 */
	public String getFile() {
		return file;
	}
	
	/**
	 * The <code>file</code> selected from the <code>JFileChooser</code> must be in txt format.
	 * Once chosen, the value for <code>file</code> is set. 
	 * @param file - The valid file selected.
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	/**
	 * When a button is pressed, the background color changes to gray. Once released,
	 * it will default.
	 */
	public void mousePressed(MouseEvent e) {
		Color buttonColorGray = new Color(145, 145, 145);
		String[] arr = e.getSource().toString().split(",");
		String button = arr[0].substring(arr[0].indexOf("[") + 1);
		switch (button) {
			case "chooseFileButton":
				chooseFileButton.setBackground(buttonColorGray);
				break;
			case "getHelpButton":
				getHelpButton.setBackground(buttonColorGray);
				break;
			case "createReportButton":
				createReportButton.setBackground(buttonColorGray);
				break;
			default:
				System.out.println("No case set for button.");
		}
	}

	/**
	 * When a button is pressed, the background color changes to gray. Once released,
	 * it will default.
	 */
	public void mouseReleased(MouseEvent e) {
		Color buttonColorBlue = new Color(0, 150, 255);
		String[] arr = e.getSource().toString().split(",");
		String button = arr[0].substring(arr[0].indexOf("[") + 1);
		switch (button) {
			case "chooseFileButton":
				chooseFileButton.setBackground(buttonColorBlue);
				break;
			case "getHelpButton":
				getHelpButton.setBackground(buttonColorBlue);
				break;
			case "createReportButton":
				createReportButton.setBackground(buttonColorBlue);
				break;
			default:
				System.out.println("No case set for button.");
		}
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
}