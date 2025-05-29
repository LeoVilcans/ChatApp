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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField usernameTextField;

	private UserDAO userDAO;
	
	public LoginGUI() {
		userDAO = new UserDAOImpl();
		
		Theme currentTheme = Settings.getTheme();
		 
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 283, 296);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(currentTheme.getBackgroundColor());
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 146, 247, 33);
		contentPane.add(passwordField);
		
		JLabel titleLabel = new JLabel("LOGIN");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(10, 11, 247, 45);
		titleLabel.setBackground(currentTheme.getBackgroundColor());
		titleLabel.setForeground(currentTheme.getTextColor());
		contentPane.add(titleLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(10, 87, 247, 33);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(10, 72, 80, 14);
		usernameLabel.setBackground(currentTheme.getBackgroundColor());
		usernameLabel.setForeground(currentTheme.getTextColor());
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 131, 80, 14);
		passwordLabel.setBackground(currentTheme.getBackgroundColor());
		passwordLabel.setForeground(currentTheme.getTextColor());
		contentPane.add(passwordLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(10, 190, 247, 45);
		loginButton.setBackground(currentTheme.getButtonColor());
		loginButton.setForeground(currentTheme.getTextColor());
		contentPane.add(loginButton);
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		
		JButton btnRegister = new JButton("No account? Register here.");
		btnRegister.setBounds(10, 234, 247, 23);
		btnRegister.setBackground(currentTheme.getButtonColor());
		btnRegister.setForeground(currentTheme.getTextColor());
		contentPane.add(btnRegister);
		
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterGUI registerGUI = new RegisterGUI();
				registerGUI.setVisible(true);
				LoginGUI.this.dispose();
			}
		});
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
}