package jtt.vikachaze.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dto.Room;
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

import java.awt.event.ActionListener;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class AddRoomGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel titleLabel;
	private JTextField roomNameTextField;
	private JButton addIconButton, addRoomButton;
	
	private RoomDAO roomDAO;
	private File currentFile = null;

	public AddRoomGUI() {
		roomDAO = new RoomDAOImpl();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 280, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		roomNameTextField = new JTextField();
		roomNameTextField.setBounds(58, 38, 151, 20);
		contentPane.add(roomNameTextField);
		roomNameTextField.setColumns(10);
		
		titleLabel = new JLabel("Room name");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(10, 12, 244, 16);
		contentPane.add(titleLabel);
		 
		addIconButton = new JButton("Add icon");
		addIconButton.setBounds(58, 72, 151, 26);
		contentPane.add(addIconButton);
		addIconButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==addIconButton) {
					JFileChooser fileChooser = new JFileChooser();
					int response = fileChooser.showOpenDialog(null);
					
					if(response == JFileChooser.APPROVE_OPTION) {
						currentFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
						JOptionPane.showMessageDialog(AddRoomGUI.this, "Bilde tika pievienota.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		addRoomButton = new JButton("Add room");
		addRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRoom();
			}
		});
		
		addRoomButton.setBounds(58, 110, 151, 26);
		contentPane.add(addRoomButton);
		
		updateTheme();
	}

	public void addRoom() {
		String roomName = roomNameTextField.getText();
		Room room = new Room(roomName);
		
		try {
			if (currentFile != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				String format = currentFile.toPath().getFileName().toString().split("\\.")[1];
				ImageIO.write(Scalr.resize(ImageIO.read(currentFile), 236), format, baos);
				Blob b1 = Database.getConnection().createBlob();
				b1.setBytes(1,  baos.toByteArray());
				room.setIcon(b1);
			}
			int id  = roomDAO.insert(room);
			room.setId(id);
			
			AddRoomGUI.this.dispose();
		} catch (IllegalArgumentException | ImagingOpException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateTheme() {
		Theme currentTheme = Settings.getTheme();
		
		// getBackgroundColor()
		contentPane.setBackground(currentTheme.getBackgroundColor());
		
		titleLabel.setBackground(currentTheme.getBackgroundColor());
		
		// getButtonColor()
		addIconButton.setBackground(currentTheme.getButtonColor());
		addRoomButton.setBackground(currentTheme.getButtonColor());
		
		// getTextColor()
		titleLabel.setForeground(currentTheme.getTextColor());
		
		addIconButton.setForeground(currentTheme.getTextColor());
		addRoomButton.setForeground(currentTheme.getTextColor());
	}
}