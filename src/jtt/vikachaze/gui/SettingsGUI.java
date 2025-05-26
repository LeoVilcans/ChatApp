package jtt.vikachaze.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.util.Settings;
import jtt.vikachaze.util.Settings.ThemeChoice;

import javax.swing.JLabel;
import javax.swing.JButton;

public class SettingsGUI extends JFrame {

	private JPanel contentPane;
	private JButton ClassicButton, DarkButton, LightButton, LatvianButton, RussianButton, EnglishButton, BackButton;
	private JLabel lblNewLabel, lblLanguage;
	
	public SettingsGUI() {
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 286, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Theme");
		lblNewLabel.setBounds(36, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		ClassicButton = new JButton("Classic");
		ClassicButton.setBounds(10, 33, 98, 26);
		contentPane.add(ClassicButton);
		ClassicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.setTheme(ThemeChoice.CLASSIC);
				UpdateTheme();
			}
		});
		
		DarkButton = new JButton("Dark");
		DarkButton.setBounds(10, 70, 98, 26);
		contentPane.add(DarkButton);
		DarkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.setTheme(ThemeChoice.DARK);
				UpdateTheme();
			}
		});
		
		LightButton = new JButton("Light");
		LightButton.setBounds(10, 107, 98, 26);
		contentPane.add(LightButton);
		LightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.setTheme(ThemeChoice.LIGHT);
				UpdateTheme();
			}
		});
		
		lblLanguage = new JLabel("Language");
		lblLanguage.setBounds(182, 10, 56, 14);
		contentPane.add(lblLanguage);
		
		EnglishButton = new JButton("English");
		EnglishButton.setBounds(163, 33, 98, 26);
		contentPane.add(EnglishButton);
		
		LatvianButton = new JButton("Latvian");
		LatvianButton.setBounds(163, 70, 98, 26);
		contentPane.add(LatvianButton);
		
		RussianButton = new JButton("Russian");
		RussianButton.setBounds(163, 107, 98, 26);
		contentPane.add(RussianButton);
		
		BackButton = new JButton("Go Back");
		BackButton.setBounds(87, 153, 98, 26);
		contentPane.add(BackButton);
		BackButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenuGUI form = new MainMenuGUI();
				form.setVisible(true);
				SettingsGUI.this.dispose();
			}
		});
	}
	
	public void UpdateTheme() {
		Theme currentTheme = Settings.getTheme();
		
		contentPane.setBackground(currentTheme.getBackgroundColor());
		
		ClassicButton.setBackground(currentTheme.getButtonColor());
		DarkButton.setBackground(currentTheme.getButtonColor());
		LightButton.setBackground(currentTheme.getButtonColor());
		LatvianButton.setBackground(currentTheme.getButtonColor());
		RussianButton.setBackground(currentTheme.getButtonColor());
		EnglishButton.setBackground(currentTheme.getButtonColor());
		BackButton.setBackground(currentTheme.getButtonColor());
		
		ClassicButton.setForeground(currentTheme.getTextColor());
		DarkButton.setForeground(currentTheme.getTextColor());
		LightButton.setForeground(currentTheme.getTextColor());
		LatvianButton.setForeground(currentTheme.getTextColor());
		RussianButton.setForeground(currentTheme.getTextColor());
		EnglishButton.setForeground(currentTheme.getTextColor());
		BackButton.setForeground(currentTheme.getTextColor());
		
		lblNewLabel.setForeground(currentTheme.getTextColor());
		lblLanguage.setForeground(currentTheme.getTextColor());
	}
}
