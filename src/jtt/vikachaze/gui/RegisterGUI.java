package jtt.vikachaze.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.UserDAO;
import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.util.Scalr;
import jtt.vikachaze.util.Settings;
import jtt.vikachaze.dao.impl.UserDAOImpl;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class RegisterGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private UserDAO userDAO = new UserDAOImpl();
	private File currentAttachment = null;

	public RegisterGUI() {
		Theme currentTheme = Settings.getTheme();
		 
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 283, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(currentTheme.getBackgroundColor());
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRegister = new JLabel("REGISTER");
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblRegister.setBounds(10, 11, 247, 45);
		lblRegister.setBackground(currentTheme.getBackgroundColor());
		lblRegister.setForeground(currentTheme.getTextColor());
		contentPane.add(lblRegister);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(10, 72, 80, 14);
		usernameLabel.setBackground(currentTheme.getBackgroundColor());
		usernameLabel.setForeground(currentTheme.getTextColor());
		contentPane.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(10, 87, 247, 33);
		contentPane.add(usernameField);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 131, 80, 14);
		passwordLabel.setBackground(currentTheme.getBackgroundColor());
		passwordLabel.setForeground(currentTheme.getTextColor());
		contentPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 146, 247, 33);
		contentPane.add(passwordField);
		
		JButton registerButton = new JButton("Register");
		registerButton.setBounds(10, 282, 247, 45);
		registerButton.setBackground(currentTheme.getButtonColor());
		registerButton.setForeground(currentTheme.getTextColor());
		contentPane.add(registerButton);
		
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					register();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JLabel lblProfilePicture = new JLabel("Profile picture:");
		lblProfilePicture.setBounds(10, 192, 96, 14);
		contentPane.add(lblProfilePicture);
		
		JButton AddProfilePictureButton = new JButton("Add profile picture");
		AddProfilePictureButton.setBounds(10, 219, 247, 45);
		AddProfilePictureButton.setBackground(currentTheme.getButtonColor());
		AddProfilePictureButton.setForeground(currentTheme.getTextColor());
		contentPane.add(AddProfilePictureButton);
		
		AddProfilePictureButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==AddProfilePictureButton) {
					JFileChooser fileChooser = new JFileChooser();
					int response = fileChooser.showOpenDialog(null);
					
					if(response == JFileChooser.APPROVE_OPTION) {
						currentAttachment = new File(fileChooser.getSelectedFile().getAbsolutePath());
						JOptionPane.showMessageDialog(RegisterGUI.this, "Bilde tika pievienota.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			
		});
	}
	public void register() throws SQLException {
		String username = usernameField.getText();
		char[] password = passwordField.getPassword();
		String str = String.valueOf(password);
		
		User user = new User(username,str);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			List<User> users = userDAO.getAllData();
			
			for (User u : users) {
				if (u.getUsername().equals(username)) {
					JOptionPane.showMessageDialog(RegisterGUI.this, "Tāds Lietotājs jau eksistē! Mēģinat citu Lietotājvārdu", getTitle(), JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			if (str.isEmpty()) {
				JOptionPane.showMessageDialog(RegisterGUI.this, "Nav paroles, Mēģinat velreiz", getTitle(), JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (currentAttachment != null) {
				String format = currentAttachment.toPath().getFileName().toString().split("\\.")[1];
				ImageIO.write(Scalr.resize(ImageIO.read(currentAttachment), 236), format, baos);
				Blob b1 = Database.getConnection().createBlob();
				b1.setBytes(1,  baos.toByteArray());
				user.setPfp(b1);
			}
			
			int id  = userDAO.insert(user);
			user.setId(id);
			
			Main.Login(user);
			
			RegisterGUI.this.dispose();
		} catch (IllegalArgumentException | ImagingOpException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}
