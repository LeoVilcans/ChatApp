package jtt.vikachaze.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.dao.impl.UserDAOImpl;
import jtt.vikachaze.dto.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPasswordField;

public class EditUserPasswordGUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField oldPassowrdTextField, newPasswordTextField;
	private JButton confirmButton, cancelButton;
	
	private UserDAOImpl userDAO = new UserDAOImpl();
	
	public EditUserPasswordGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel EditLabel = new JLabel("Edit Password");
		EditLabel.setHorizontalAlignment(SwingConstants.CENTER);
		EditLabel.setBounds(10, 11, 214, 20);
		contentPane.add(EditLabel);
		
		JLabel oldPasswordLabel = new JLabel("Old password:");
		oldPasswordLabel.setBounds(10, 45, 109, 16);
		contentPane.add(oldPasswordLabel);
		
		oldPassowrdTextField = new JPasswordField();
		oldPassowrdTextField.setBounds(10, 72, 214, 20);
		contentPane.add(oldPassowrdTextField);
		oldPassowrdTextField.setColumns(10);
		
		JLabel newPasswordLabel = new JLabel("New password:");
		newPasswordLabel.setBounds(10, 103, 109, 16);
		contentPane.add(newPasswordLabel);
		
		newPasswordTextField = new JPasswordField();
		newPasswordTextField.setColumns(10);
		newPasswordTextField.setBounds(10, 130, 214, 20);
		contentPane.add(newPasswordTextField);
		
		cancelButton = new JButton("cancel");
		cancelButton.setBounds(143, 161, 81, 26);
		contentPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditUserPasswordGUI.this.dispose();
			}
		});
		
		confirmButton = new JButton("confirm");
		confirmButton.setBounds(52, 161, 81, 26);
		contentPane.add(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updatePassword();
			}
		});
	}
	
	public void updatePassword() {
		char[] charOldPassword = oldPassowrdTextField.getPassword();
		char[] charNewPassword = newPasswordTextField.getPassword();
		
		String oldPassword = String.valueOf(charOldPassword);
		String newPassword = String.valueOf(charNewPassword);
		
		String trueOldPassword = Main.getLoggedUser().getPassword();
		String username = Main.getLoggedUser().getUsername();
		
		if (!trueOldPassword.equals(oldPassword)) {
			JOptionPane.showConfirmDialog(EditUserPasswordGUI.this, "Parole nav pareiza!", getTitle(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		User tempOldUser = new User(username, trueOldPassword);
		
		try {
			int oldUserID = userDAO.getID(tempOldUser);
			
			User newUser = Main.getLoggedUser();
			
			newUser.setPassword(newPassword);
			newUser.setId(oldUserID);
			userDAO.update(newUser);
			
			EditUserPasswordGUI.this.dispose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}