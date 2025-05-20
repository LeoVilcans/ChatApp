package jtt.vikachaze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.dto.User;

public class UserProfileGUI extends JFrame {

	private JPanel contentPane;

	public UserProfileGUI(User user) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
		usernameLabel.setBounds(10, 10, 200, 30);
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password: " + user.getPassword());
		passwordLabel.setBounds(40, 10, 200, 30);
		contentPane.add(passwordLabel);
		
		setContentPane(contentPane);
	}

}
