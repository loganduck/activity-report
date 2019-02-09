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
	
	/* entity widths */
	private final int FRAME_WIDTH = 388;
	private final int LOGO_WIDTH = 84;
	private final int MENU_TITLE_WIDTH = 303;
	private final int BUTTON_WIDTH = 240;
	private final int ABOUT_BTN_WIDTH = 41;
	private final int FILE_PATH_WIDTH = 430;
	private final int GET_HELP_FRAME_WIDTH = 300;
	private final int GET_HELP_WIDTH = 266;
	private final int ABOUT_FRAME_WIDTH = 388;
	private final int ABOUT_DISCLAIMER_WIDTH = 372;
	private final int ABOUT_PRODUCTBY_WIDTH = 167;
	private final int ABOUT_OK_WIDTH = 76;
	private final int ABOUT_VERS_WIDTH = 48;
	
	/* entity heights */
	private final int FRAME_HEIGHT = 400;
	private final int LOGO_HEIGHT = 84;
	private final int MENU_TITLE_HEIGHT = 36;
	private final int BUTTON_HEIGHT = 40;
	private final int ABOUT_BTN_HEIGHT = 37;
	private final int FILE_PATH_HEIGHT = 20;
	private final int GET_HELP_FRAME_HEIGHT = 300;
	private final int GET_HELP_HEIGHT = 240;
	private final int ABOUT_FRAME_HEIGHT = 280;	
	private final int ABOUT_DISCLAIMER_HEIGHT = 120;
	private final int ABOUT_PRODUCTBY_HEIGHT = 17;
	private final int ABOUT_OK_HEIGHT = 30;
	private final int ABOUT_VERS_HEIGHT = 16;
	
	/* entity x-coordinates / centered */
	private final int LOGO_CENTERED = (FRAME_WIDTH / 2) - (LOGO_WIDTH / 2);
	private final int MENU_TITLE_CENTERED = (FRAME_WIDTH / 2) - (MENU_TITLE_WIDTH / 2);
	private final int BUTTONS_CENTERED = (FRAME_WIDTH / 2) - (BUTTON_WIDTH / 2);
	private final int ABOUT_BTN_X = 353;
	private final int FILE_PATH_X = 3;
	private final int GET_HELP_X = 5;
	private final int ABOUT_DISCLAIMER_X = 5;
	private final int ABOUT_PRODUCTBY_X = 5;
	private final int ABOUT_OK_X = 156;
	private final int ABOUT_VERS_X = 5;
	
	/* entity y-coordinates */
	private final int LOGO_Y = 20;
	private final int MENU_TITLE_Y = 102;
	private final int CHOOSE_FILE_BTN_Y = 175;
	private final int GET_HELP_BTN_Y = 230;
	private final int CREATE_REPORT_BTN_Y = 285;
	private final int ABOUT_BTN_Y = 345;
	private final int FILE_PATH_Y = 360;
	private final int GET_HELP_Y = 5;
	private final int ABOUT_DISCLAIMER_Y = 5;
	private final int ABOUT_PRODUCTBY_Y = 150;
	private final int ABOUT_OK_Y = 180;
	private final int ABOUT_VERS_Y = 240;
	
	/* button / label text */
	private final String CHOOSE_FILE_BTN_TEXT = "<html><body><font size='6'>Choose File</font></body></html>";
	private final String GET_HELP_BTN_TEXT = "<html><body><font size='6'>Get Help</font></body></html>";
	private final String CREATE_REPORT_BTN_TEXT = "<html><body><font size='6'>Create Report</font></body></html>";
	private final String ABOUT_BTN_NAME = "aboutButton";
	private final String ABOUT_PRODUCTBY_TEXT = "Product by Logan Duck";
	private final String ABOUT_VERS_TEXT = "v: 4.0.0";
	private final String MENU_TITLE_TEXT = "Activity Report Menu";
	private final String GET_HELP_TEXT = "<html><body>"
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
			+ "</html>";
	private final String ABOUT_DISCLAIMER_TEXT = "<html><body>"
			+ "The Activity Report application generates spreadsheet reports of SilverSneakers"
			+ " members monthly visit activity at the Wellness Center of Lake Martin. This product is"
			+ "not associated or affiliated with SilverSneakers or Tivity Health.</body></html>";
	
	/* button names */
	private final String CHOOSE_FILE_BTN_NAME = "chooseFileButton";
	private final String GET_HELP_BTN_NAME = "getHelpButton";
	private final String CREATE_REPORT_BTN_NAME = "createReportButton";
	
	/* button colors */
	private final Color BUTTON_BACKGROUND = new Color(0, 150, 255);
	private final Color BUTTON_COLOR_BLUE = new Color(0, 150, 255);
	private final Color BUTTON_COLOR_GREY = new Color(145, 145, 145);
	
	/* entity fonts */
	private final Font MENU_TITLE_FONT = new Font(Font.SANS_SERIF, Font.ITALIC, 30);
	private final Font FILE_PATH_FONT = new Font("Verdana", Font.PLAIN, 9);
	private final Font GET_HELP_DESCRIPTION_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	private final Font ABOUT_DISCLAIMER_FONT = new Font("Verdana", Font.BOLD, 15);
	private final Font ABOUT_PRODUCTBY_FONT = new Font("Verdana", Font.BOLD, 13);
	private final Font ABOUT_VERS_FONT = new Font("Verdana", Font.ITALIC, 12);
	
	/* frame titles */
	private final String FRAME_TITLE = "Wellness Center - Activity Report";
	private final String CHOOSER_DIALOG_TITLE = "Choose File";
	private final String GET_HELP_FRAME_TITLE = "Get Help";
	private final String ABOUT_TITLE = "About";
	
	/* application icons */
	private final ImageIcon LOGO_ICON = new ImageIcon(ActivityReportMenu.class.getResource("/images/logo.png"));
	private final ImageIcon ABOUT_ICON = new ImageIcon(ActivityReportMenu.class.getResource("/images/about.png"));
	
	/* file chooser */
	private final String CHOOSER_DIR_PATH = System.getProperty("user.home") + "/Desktop";
	private final FileNameExtensionFilter CHOOSER_EXT_FILTER = new FileNameExtensionFilter("TEXT FILES", "txt", "text"); // new FileNameExtensionFilter(".txt, txt", "txt", "text")
	
	public ActivityReportMenu() {
		frame = new JFrame(FRAME_TITLE);
		frame.setLayout(null);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		logo = new JLabel(LOGO_ICON);
		logo.setBounds(LOGO_CENTERED, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);
		frame.add(logo);
		
		menuTitle = new JLabel(MENU_TITLE_TEXT);
		menuTitle.setFont(MENU_TITLE_FONT);
		menuTitle.setForeground(Color.WHITE);
		menuTitle.setBounds(MENU_TITLE_CENTERED, MENU_TITLE_Y, MENU_TITLE_WIDTH, MENU_TITLE_HEIGHT);
		frame.add(menuTitle);
		
		chooseFileButton = new JButton(CHOOSE_FILE_BTN_TEXT);
		chooseFileButton.setName(CHOOSE_FILE_BTN_NAME);
		chooseFileButton.setBackground(BUTTON_BACKGROUND);
		chooseFileButton.setOpaque(true);
		chooseFileButton.setBorderPainted(false);
		chooseFileButton.setForeground(Color.WHITE);
		chooseFileButton.addMouseListener(this);
		chooseFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(CHOOSER_DIR_PATH);
				fileChooser.setDialogTitle(CHOOSER_DIALOG_TITLE);
				fileChooser.setFileFilter(CHOOSER_EXT_FILTER); 
			
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
		chooseFileButton.setBounds(BUTTONS_CENTERED, CHOOSE_FILE_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		frame.add(chooseFileButton);
		
		getHelpButton = new JButton(GET_HELP_BTN_TEXT);
		getHelpButton.setName(GET_HELP_BTN_NAME);		
		getHelpButton.setBackground(BUTTON_BACKGROUND);
		getHelpButton.setOpaque(true);
		getHelpButton.setBorderPainted(false);
		getHelpButton.setForeground(Color.WHITE);
		getHelpButton.addMouseListener(this);
		getHelpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame(GET_HELP_FRAME_TITLE);
				frame.setLayout(null);
				frame.setSize(GET_HELP_FRAME_WIDTH, GET_HELP_FRAME_HEIGHT);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.getContentPane().setBackground(Color.DARK_GRAY);
				
				JLabel label = new JLabel(GET_HELP_TEXT);
				label.setFont(GET_HELP_DESCRIPTION_FONT);
				label.setForeground(Color.WHITE);
				label.setBounds(GET_HELP_X, GET_HELP_Y, GET_HELP_WIDTH, GET_HELP_HEIGHT);
				frame.add(label);
				
				frame.setVisible(true);
			}
		});
		getHelpButton.setBounds(BUTTONS_CENTERED, GET_HELP_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		frame.add(getHelpButton);
		
		createReportButton = new JButton(CREATE_REPORT_BTN_TEXT);
		createReportButton.setName(CREATE_REPORT_BTN_NAME);
		createReportButton.setBackground(BUTTON_BACKGROUND);
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
		createReportButton.setBounds(BUTTONS_CENTERED, CREATE_REPORT_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		frame.add(createReportButton);
		
		aboutButton = new JButton(ABOUT_ICON); 
		aboutButton.setName(ABOUT_BTN_NAME);
		aboutButton.setBackground(Color.DARK_GRAY);
		aboutButton.setOpaque(true);
		aboutButton.setBorderPainted(false);
		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFrame frame = new JFrame(ABOUT_TITLE);
				frame.setLayout(null);
				frame.setSize(ABOUT_FRAME_WIDTH, ABOUT_FRAME_HEIGHT);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.getContentPane().setBackground(Color.DARK_GRAY);
				
				JLabel disclaimer = new JLabel(ABOUT_DISCLAIMER_TEXT);
				disclaimer.setFont(ABOUT_DISCLAIMER_FONT);
				disclaimer.setForeground(Color.WHITE);
				disclaimer.setBounds(ABOUT_DISCLAIMER_X, ABOUT_DISCLAIMER_Y, ABOUT_DISCLAIMER_WIDTH, ABOUT_DISCLAIMER_HEIGHT);
				frame.add(disclaimer);
				
				JLabel label = new JLabel(ABOUT_PRODUCTBY_TEXT);
				label.setFont(ABOUT_PRODUCTBY_FONT);
				label.setForeground(Color.WHITE);
				label.setBounds(ABOUT_PRODUCTBY_X, ABOUT_PRODUCTBY_Y, ABOUT_PRODUCTBY_WIDTH, ABOUT_PRODUCTBY_HEIGHT);
				frame.add(label);
				
				JButton button = new JButton("OK");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				});
				button.setBounds(ABOUT_OK_X, ABOUT_OK_Y, ABOUT_OK_WIDTH, ABOUT_OK_HEIGHT);
				frame.add(button);
				
				JLabel version = new JLabel(ABOUT_VERS_TEXT);
				version.setFont(ABOUT_VERS_FONT);
				version.setForeground(Color.WHITE);
				version.setBounds(ABOUT_VERS_X, ABOUT_VERS_Y, ABOUT_VERS_WIDTH, ABOUT_VERS_HEIGHT);
				frame.add(version);
				
				frame.setVisible(true);	
			}
		});
		aboutButton.setBounds(ABOUT_BTN_X, ABOUT_BTN_Y, ABOUT_BTN_WIDTH, ABOUT_BTN_HEIGHT);
		frame.add(aboutButton);
		
		filePathLabel = new JLabel("");
		filePathLabel.setForeground(Color.WHITE);
		filePathLabel.setFont(FILE_PATH_FONT);
		filePathLabel.setBounds(FILE_PATH_X, FILE_PATH_Y, FILE_PATH_WIDTH, FILE_PATH_HEIGHT);
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
				chooseFileButton.setBackground(BUTTON_COLOR_GREY);
				break;
			case "getHelpButton":
				getHelpButton.setBackground(BUTTON_COLOR_GREY);
				break;
			case "createReportButton":
				createReportButton.setBackground(BUTTON_COLOR_GREY);
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
				chooseFileButton.setBackground(BUTTON_COLOR_BLUE);
				break;
			case "getHelpButton":
				getHelpButton.setBackground(BUTTON_COLOR_BLUE);
				break;
			case "createReportButton":
				createReportButton.setBackground(BUTTON_COLOR_BLUE);
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