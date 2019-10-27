package activityreport;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {
	static final int WIDTH = 388;
	static final int HEIGHT = 400;
	
	static JFrame f;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				f = new JFrame("Wellness Center - Activity Report");
				f.setLayout(null);
				f.setSize(WIDTH, HEIGHT);
				f.setResizable(false);
				f.setLocationRelativeTo(null);
				f.getContentPane().setBackground(Color.DARK_GRAY);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setBackground(Color.DARK_GRAY);
				
				f.add(new ActivityReportMenu());
				
				f.setVisible(true);
			}
		});
	}
	
	public static void dispose() {
		f.dispose();
	}
}