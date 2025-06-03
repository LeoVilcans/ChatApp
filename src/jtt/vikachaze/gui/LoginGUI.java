package jtt.vikachaze.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.dao.UserDAO;
import jtt.vikachaze.dao.impl.UserDAOImpl;
import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.util.Settings;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;

public class LoginGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel titleLabel, usernameLabel, passwordLabel;
	private JPasswordField passwordField;
	private JTextField usernameTextField;
	private JButton loginButton, btnRegister;

	private UserDAO userDAO;
	
	public LoginGUI() {
		userDAO = new UserDAOImpl();
		 
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 283, 296);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 146, 247, 33);
		contentPane.add(passwordField);
		
		titleLabel = new JLabel("LOGIN");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(10, 11, 247, 45);
		contentPane.add(titleLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(10, 87, 247, 33);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(10, 72, 80, 14);
		contentPane.add(usernameLabel);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 131, 80, 14);
		contentPane.add(passwordLabel);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(10, 190, 247, 45);
		contentPane.add(loginButton);
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		
		btnRegister  = new JButton("No account? Register here.");
		btnRegister.setBounds(10, 234, 247, 23);
		contentPane.add(btnRegister);
		
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterGUI registerGUI = new RegisterGUI();
				registerGUI.setVisible(true);
				LoginGUI.this.dispose();
			}
		});
		
		updateTheme();
	}
	
	public void login() {
		char[] rawPassword = passwordField.getPassword();
		
		String password = "";
		for (char symbol : rawPassword) {
			password += symbol;
		}
		
		String username = usernameTextField.getText();
		
		try {
			User user = userDAO.getByUsername(username);
			
			if (user == null) {
				JOptionPane.showMessageDialog(LoginGUI.this, "Nav pareizs Lietotājvārds vai Parole! Mēģiniet velreiz", getTitle(), JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (user.getPassword().equals(password)) {
				Main.Login(user);
				LoginGUI.this.dispose();
			} else {
				JOptionPane.showMessageDialog(LoginGUI.this, "Nav pareizs Lietotājvārds vai Parole! Mēģiniet velreiz", getTitle(), JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		} catch (SQLException | IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void updateTheme() {
		Theme currentTheme = Settings.getTheme();
		
		// getBackgroundColor()
		contentPane.setBackground(currentTheme.getBackgroundColor());
		titleLabel.setBackground(currentTheme.getBackgroundColor());
		usernameLabel.setBackground(currentTheme.getBackgroundColor());
		passwordLabel.setBackground(currentTheme.getBackgroundColor());
		
		// getButtonColor()
		loginButton.setBackground(currentTheme.getButtonColor());
		btnRegister.setBackground(currentTheme.getButtonColor());
		
		// getTextColor()
		titleLabel.setForeground(currentTheme.getTextColor());
		usernameLabel.setForeground(currentTheme.getTextColor());
		passwordLabel.setForeground(currentTheme.getTextColor());
		
		loginButton.setForeground(currentTheme.getTextColor());
		btnRegister.setForeground(currentTheme.getTextColor());
	}
}