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
	private JButton classicButton, darkButton, lightButton, backButton;
	private JLabel themeLabel;
	
	public SettingsGUI() {		
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 286, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		themeLabel = new JLabel("Theme:");
		themeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		themeLabel.setBounds(10, 11, 98, 14);
		contentPane.add(themeLabel);
		
		
		classicButton = new JButton("Classic");
		classicButton.setBounds(10, 33, 264, 26);
		contentPane.add(classicButton);
		classicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.setTheme(ThemeChoice.CLASSIC);
				updateTheme();
			}
		});
		
		darkButton = new JButton("Dark");
		darkButton.setBounds(10, 70, 264, 26);
		contentPane.add(darkButton);
		darkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.setTheme(ThemeChoice.DARK);
				updateTheme();
			}
		});
		
		lightButton = new JButton("Light");
		lightButton.setBounds(10, 107, 264, 26);
		contentPane.add(lightButton);
		lightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.setTheme(ThemeChoice.LIGHT);
				updateTheme();
			}
		});
		
		backButton = new JButton("Save and Go Back");
		backButton.setBounds(73, 154, 138, 26);
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
		backButton.setBackground(currentTheme.getButtonColor());
		
		// getTextColor()
		classicButton.setForeground(currentTheme.getTextColor());
		darkButton.setForeground(currentTheme.getTextColor());
		lightButton.setForeground(currentTheme.getTextColor());
		backButton.setForeground(currentTheme.getTextColor());
		
		themeLabel.setForeground(currentTheme.getTextColor());
	}
}
