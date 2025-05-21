package jtt.vikachaze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class SettingsGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsGUI frame = new SettingsGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SettingsGUI() {
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 286, 195);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Theme");
		lblNewLabel.setBounds(36, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		JButton ClassicButton = new JButton("Classic");
		ClassicButton.setBounds(10, 33, 98, 26);
		contentPane.add(ClassicButton);
		
		JButton DarkButton = new JButton("Dark");
		DarkButton.setBounds(10, 70, 98, 26);
		contentPane.add(DarkButton);
		
		JButton LightButton = new JButton("Light");
		LightButton.setBounds(10, 107, 98, 26);
		contentPane.add(LightButton);
		
		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setBounds(182, 10, 56, 14);
		contentPane.add(lblLanguage);
		
		JButton EnglishButton = new JButton("English");
		EnglishButton.setBounds(163, 33, 98, 26);
		contentPane.add(EnglishButton);
		
		JButton LatvianButton = new JButton("Latvian");
		LatvianButton.setBounds(163, 70, 98, 26);
		contentPane.add(LatvianButton);
		
		JButton RussianButton = new JButton("Russian");
		RussianButton.setBounds(163, 107, 98, 26);
		contentPane.add(RussianButton);
	}
}
