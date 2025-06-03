package jtt.vikachaze.gui;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.impl.UserDAOImpl;
import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.util.Scalr;
import jtt.vikachaze.util.Settings;
import jtt.vikachaze.util.StretchIcon;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;

public class EditProfileGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel pfpLabel, EditLabel;
	private JTextField usernameTextField, statusTextField;
	private JButton editPasswordButton, applyButton, cancelButton;
	private File file;
	
	private UserDAOImpl userDAO;
	
	public EditProfileGUI() {
		userDAO = new UserDAOImpl();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 240, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pfpLabel = new JLabel();
		pfpLabel.setForeground(new Color(0, 0, 0));
		pfpLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		pfpLabel.setBackground(new Color(255, 255, 255));
		pfpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pfpLabel.setBounds(31, 35, 159, 159);
		pfpLabel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
		contentPane.add(pfpLabel);
		pfpLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				pfpLabel.setText("");
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				pfpLabel.setText("Edit");
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource()==pfpLabel) {
					JFileChooser fileChooser = new JFileChooser();
					int response = fileChooser.showOpenDialog(null);
				
					if(response == JFileChooser.APPROVE_OPTION) {
						file = new File(fileChooser.getSelectedFile().getAbsolutePath());
						JOptionPane.showMessageDialog(EditProfileGUI.this, "Bilde tika rediģēta", getTitle(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		usernameTextField = new JTextField();
		usernameTextField.setToolTipText("username");
		usernameTextField.setBounds(31, 206, 159, 20);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		statusTextField = new JTextField();
		statusTextField.setToolTipText("status");
		statusTextField.setBounds(31, 277, 159, 114);
		contentPane.add(statusTextField);
		statusTextField.setColumns(10);
		
		EditLabel = new JLabel("Edit Profile");
		EditLabel.setHorizontalAlignment(SwingConstants.CENTER);
		EditLabel.setBounds(12, 11, 200, 20);
		contentPane.add(EditLabel);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(115, 403, 75, 26);
		contentPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenuGUI mainMenuGUI = new MainMenuGUI();
				mainMenuGUI.setVisible(true);
				EditProfileGUI.this.dispose();
			}
		});
		
		applyButton = new JButton("Apply");
		applyButton.setBounds(31, 403, 72, 26);
		contentPane.add(applyButton);
		applyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (usernameTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(EditProfileGUI.this, "Nav Username!", getTitle(), JOptionPane.ERROR_MESSAGE);
				} else {
					updateProfile();
				}
			}
		});
		
		editPasswordButton = new JButton("Edit Password");
		editPasswordButton.setBounds(31, 239, 159, 26);
		contentPane.add(editPasswordButton);
		editPasswordButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditUserPasswordGUI passwordGUI = new EditUserPasswordGUI();
				passwordGUI.setVisible(true);
			}
		});
		
		updateTheme();
		loadProfileText();
	}
	
	public void loadProfileText() {
		User user = Main.getLoggedUser();
		
		try {
			if (user.getPfp() == null) {
				pfpLabel.setIcon(new StretchIcon("emptyPfp.jpg", false));
			} else {
				pfpLabel.setIcon(new StretchIcon(user.getPfpAsImage(), false));
			}
			
			usernameTextField.setText(user.getUsername());
			statusTextField.setText(user.getStatus());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateProfile() {
	    String username = usernameTextField.getText();
	    String status = statusTextField.getText();
	    
	    String oldUsername = Main.getLoggedUser().getUsername();
	    String oldPassword = Main.getLoggedUser().getPassword();
	    
	    User oldUser = new User(oldUsername, oldPassword);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    
	    try {
	        int oldUserID = userDAO.getID(oldUser);
	        oldUser = userDAO.getByID(oldUserID);

	        User newUser = Main.getLoggedUser();
	        
	        if (file != null) {
	        	String format = file.toPath().getFileName().toString().split("\\.")[1];
	            ImageIO.write(Scalr.resize(ImageIO.read(file), 236), format, baos);
	            Blob b1 = Database.getConnection().createBlob();
	            b1.setBytes(1, baos.toByteArray());
	            newUser.setPfp(b1);
	        }
	        
	        newUser.setUsername(username);
	        
	        if (status.isEmpty()) {
	        	status = null;
	        }
	        newUser.setStatus(status);
	        newUser.setId(oldUser.getId());
	        userDAO.update(newUser); 
	        
	        Main.logout();
	        Main.Login(newUser);
	        
	        EditProfileGUI.this.dispose();
	        
	    } catch (IllegalArgumentException | ImagingOpException | IOException | SQLException e) {
	        e.printStackTrace();
	    }
	    
	    updateTheme();
	}
	
	public void updateTheme() {
		Theme currentTheme = Settings.getTheme();
		
		// getBackgroundColor()
		contentPane.setBackground(currentTheme.getBackgroundColor());
		
		EditLabel.setBackground(currentTheme.getBackgroundColor());
		
		// getButtonColor()
		cancelButton.setBackground(currentTheme.getButtonColor());
		applyButton.setBackground(currentTheme.getButtonColor());
		editPasswordButton.setBackground(currentTheme.getButtonColor());
		
		// getTextColor()
		EditLabel.setForeground(currentTheme.getTextColor());
		
		cancelButton.setForeground(currentTheme.getTextColor());
		applyButton.setForeground(currentTheme.getTextColor());
		editPasswordButton.setForeground(currentTheme.getTextColor());
	}
}