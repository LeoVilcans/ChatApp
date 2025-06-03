package jtt.vikachaze.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.util.Scalr;
import jtt.vikachaze.util.Settings;
import jtt.vikachaze.dao.*;
import jtt.vikachaze.dao.impl.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

public class AddPostGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel titleLabel, textLabel;
	private JTextField titleTextField, textTextField;
	private JButton postButton, addAttachmentButton;
	
	private PostDAO postDAO;
	private File currentAttachment = null;

	public AddPostGUI() {
		postDAO = new PostDAOImpl();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 280, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		titleLabel = new JLabel("Title");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(10, 11, 249, 14);
		contentPane.add(titleLabel);
		
		titleTextField = new JTextField();
		titleTextField.setBounds(10, 36, 244, 20);
		contentPane.add(titleTextField);
		titleTextField.setColumns(10);
		
		textLabel = new JLabel("Text:");
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setBounds(10, 81, 249, 14);
		contentPane.add(textLabel);
		
		textTextField = new JTextField();
		textTextField.setColumns(10);
		textTextField.setBounds(10, 107, 244, 160);
		contentPane.add(textTextField);
		
		postButton = new JButton("Post");
		postButton.setBounds(150, 281, 104, 26);
		contentPane.add(postButton);
		postButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addPost();
				
			}
		});
		
		addAttachmentButton = new JButton("Add attachment");
		addAttachmentButton.setBounds(10, 281, 130, 26);
		contentPane.add(addAttachmentButton);
		addAttachmentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==addAttachmentButton) {
					JFileChooser fileChooser = new JFileChooser();
					int response = fileChooser.showOpenDialog(null);
					
					if(response == JFileChooser.APPROVE_OPTION) {
						currentAttachment = new File(fileChooser.getSelectedFile().getAbsolutePath());
						JOptionPane.showMessageDialog(AddPostGUI.this, "Bilde tika pievienota.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});	
		
		updateTheme();
	}
	
	public void addPost() {
		String title = titleTextField.getText();
		String posttext = textTextField.getText();
		
		Post post = new Post(Timestamp.valueOf(LocalDateTime.now()), Main.getLoggedUser(),title,posttext);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			if (currentAttachment != null) {
				String format = currentAttachment.toPath().getFileName().toString().split("\\.")[1];
				ImageIO.write(Scalr.resize(ImageIO.read(currentAttachment), 236), format, baos);
				
				Blob b1 = Database.getConnection().createBlob();
				b1.setBytes(1,  baos.toByteArray());
				post.setAttachment(b1);
			}
			
			int id  = postDAO.insert(post);
			post.setId(id);
			
			AddPostGUI.this.dispose();
		} catch (IllegalArgumentException | ImagingOpException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateTheme() {
		Theme currentTheme = Settings.getTheme();
		
		// getBackgroundColor()
		contentPane.setBackground(currentTheme.getBackgroundColor());
		
		titleLabel.setBackground(currentTheme.getBackgroundColor());
		textLabel.setBackground(currentTheme.getBackgroundColor());
		
		// getButtonColor()
		postButton.setBackground(currentTheme.getButtonColor());
		addAttachmentButton.setBackground(currentTheme.getButtonColor());
		
		// getTextColor()
		titleLabel.setForeground(currentTheme.getTextColor());
		textLabel.setForeground(currentTheme.getTextColor());
		
		postButton.setForeground(currentTheme.getTextColor());
		addAttachmentButton.setForeground(currentTheme.getTextColor());
	}
}
