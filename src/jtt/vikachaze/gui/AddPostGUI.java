package jtt.vikachaze.gui;

import java.awt.EventQueue;
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
import jtt.vikachaze.util.Scalr;
import jtt.vikachaze.dao.*;
import jtt.vikachaze.dao.impl.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class AddPostGUI extends JFrame {

	private JPanel contentPane;
	private JTextField TitleField;
	private JTextField TextField;
	private PostDAO postDAO = new PostDAOImpl();
	private File currentAttachment = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPostGUI frame = new AddPostGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddPostGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 280, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Title");
		lblNewLabel.setBounds(121, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		TitleField = new JTextField();
		TitleField.setBounds(10, 36, 250, 20);
		contentPane.add(TitleField);
		TitleField.setColumns(10);
		
		JLabel lblText = new JLabel("Text:");
		lblText.setBounds(121, 81, 46, 14);
		contentPane.add(lblText);
		
		TextField = new JTextField();
		TextField.setColumns(10);
		TextField.setBounds(10, 107, 250, 160);
		contentPane.add(TextField);
		
		JButton PostButton = new JButton("Post");
		PostButton.setBounds(149, 281, 98, 26);
		contentPane.add(PostButton);
		PostButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addPost();
				
			}
		});
		
		JButton AddAttachmentButton = new JButton("Add attachment");
		AddAttachmentButton.setBounds(10, 281, 130, 26);
		contentPane.add(AddAttachmentButton);
		AddAttachmentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==AddAttachmentButton) {
					JFileChooser fileChooser = new JFileChooser();
					int response = fileChooser.showOpenDialog(null);
					
					if(response == JFileChooser.APPROVE_OPTION) {
						currentAttachment = new File(fileChooser.getSelectedFile().getAbsolutePath());
						JOptionPane.showMessageDialog(AddPostGUI.this, "Bilde tika pievienota.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
						
						}
				}
			}
		});
		
		
	}
	public void addPost() {
		String title = TitleField.getText();
		String posttext = TextField.getText();
		
		Post post = new Post(Timestamp.valueOf(LocalDateTime.now()), Main.getLoggedUser(),title,posttext);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			String format = currentAttachment.toPath().getFileName().toString().split("\\.")[1];
			ImageIO.write(Scalr.resize(ImageIO.read(currentAttachment), 236), format, baos);
			
			Blob b1 = Database.getConnection().createBlob();
			b1.setBytes(1,  baos.toByteArray());
			post.setAttachment(b1);
			
			int id  = postDAO.insert(post);
			post.setId(id);
			
			
			AddPostGUI.this.dispose();
		} catch (IllegalArgumentException | ImagingOpException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}
