package jtt.vikachaze.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.util.Settings;
import jtt.vikachaze.util.Settings.ThemeChoice;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class SettingsGUI extends JFrame {

	private JPanel contentPane;
	private JButton classicButton, darkButton, lightButton, latvianButton, russianButton, englishButton, backButton;
	private JLabel themeLabel, languageLabel;
	
	public SettingsGUI() {		
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 286, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		themeLabel = new JLabel("Theme");
		themeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		themeLabel.setBounds(10, 11, 98, 14);
		contentPane.add(themeLabel);
		
		
		classicButton = new JButton("Classic");
		classicButton.setBounds(10, 33, 98, 26);
		contentPane.add(classicButton);
		classicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.setTheme(ThemeChoice.CLASSIC);
				updateTheme();
			}
		});
		
		darkButton = new JButton("Dark");
		darkButton.setBounds(10, 70, 98, 26);
		contentPane.add(darkButton);
		darkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.setTheme(ThemeChoice.DARK);
				updateTheme();
			}
		});
		
		lightButton = new JButton("Light");
		lightButton.setBounds(10, 107, 98, 26);
		contentPane.add(lightButton);
		lightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.setTheme(ThemeChoice.LIGHT);
				updateTheme();
			}
		});
		
		languageLabel = new JLabel("Language");
		languageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		languageLabel.setBounds(163, 10, 98, 14);
		contentPane.add(languageLabel);
		
		englishButton = new JButton("English");
		englishButton.setBounds(163, 33, 98, 26);
		contentPane.add(englishButton);
		
		latvianButton = new JButton("Latvian");
		latvianButton.setBounds(163, 70, 98, 26);
		contentPane.add(latvianButton);
		
		russianButton = new JButton("Russian");
		russianButton.setBounds(163, 107, 98, 26);
		contentPane.add(russianButton);
		
		backButton = new JButton("Go Back");
		backButton.setBounds(87, 153, 98, 26);
		contentPane.add(backButton);
		backButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				//MainMenuGUI form = new MainMenuGUI();
				//form.setVisible(true);
				try {
					User user = Main.getLoggedUser();
					Main.logout();
					Main.Login(user);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
				
				SettingsGUI.this.dispose();
			}
		});
		
		updateTheme();
	}
	
	public void updateTheme() {
		Theme currentTheme = Settings.getTheme();
		
		// getBackgroundColor()
		contentPane.setBackground(currentTheme.getBackgroundColor());
		
		// getbuttonColor()
		classicButton.setBackground(currentTheme.getButtonColor());
		darkButton.setBackground(currentTheme.getButtonColor());
		lightButton.setBackground(currentTheme.getButtonColor());
		latvianButton.setBackground(currentTheme.getButtonColor());
		russianButton.setBackground(currentTheme.getButtonColor());
		englishButton.setBackground(currentTheme.getButtonColor());
		backButton.setBackground(currentTheme.getButtonColor());
		
		// getTextColor()
		classicButton.setForeground(currentTheme.getTextColor());
		darkButton.setForeground(currentTheme.getTextColor());
		lightButton.setForeground(currentTheme.getTextColor());
		latvianButton.setForeground(currentTheme.getTextColor());
		russianButton.setForeground(currentTheme.getTextColor());
		englishButton.setForeground(currentTheme.getTextColor());
		backButton.setForeground(currentTheme.getTextColor());
		
		themeLabel.setForeground(currentTheme.getTextColor());
		languageLabel.setForeground(currentTheme.getTextColor());
	}
}
