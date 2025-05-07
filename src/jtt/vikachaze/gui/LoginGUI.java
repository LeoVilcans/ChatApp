package jtt.vikachaze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.dao.UserDAO;
import jtt.vikachaze.dao.impl.UserDAOImpl;
import jtt.vikachaze.dto.User;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField usernameTextField;

	private UserDAO userDAO;
	
	public LoginGUI() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 283, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 146, 247, 33);
		contentPane.add(passwordField);
		
		JLabel titleLabel = new JLabel("LOGIN");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(10, 11, 247, 45);
		contentPane.add(titleLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(10, 87, 247, 33);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(10, 72, 80, 14);
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 131, 80, 14);
		contentPane.add(passwordLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(10, 190, 247, 45);
		contentPane.add(loginButton);
		
		userDAO = new UserDAOImpl();
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				char[] rawPassword = passwordField.getPassword();
				
				String password = "";
				for (char symbol : rawPassword) {
					password += symbol;
				}
				
				String username = usernameTextField.getText();
				
				try {
					User user = userDAO.getByUsername(username);
					
					if (user.getPassword().equals(password)) {
						Main.Login(user);
						LoginGUI.this.dispose();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	
		
	}
}
