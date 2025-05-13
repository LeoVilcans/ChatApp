package jtt.vikachaze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.util.Scalr;
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

public class AddRoomGUI extends JFrame {

	private JPanel contentPane;
	private JTextField RoomNameField;
	private File currentAttachment = null;
	private RoomDAO roomDAO = new RoomDAOImpl();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRoomGUI frame = new AddRoomGUI();
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
	public AddRoomGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 280, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RoomNameField = new JTextField();
		RoomNameField.setBounds(58, 38, 136, 20);
		contentPane.add(RoomNameField);
		RoomNameField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Room name");
		lblNewLabel.setBounds(91, 12, 68, 16);
		contentPane.add(lblNewLabel);
		
		JButton IconButton = new JButton("Add icon");
		IconButton.setBounds(58, 72, 136, 26);
		contentPane.add(IconButton);
		IconButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==IconButton) {
					JFileChooser fileChooser = new JFileChooser();
					int response = fileChooser.showOpenDialog(null);
					
					if(response == JFileChooser.APPROVE_OPTION) {
						currentAttachment = new File(fileChooser.getSelectedFile().getAbsolutePath());
						JOptionPane.showMessageDialog(AddRoomGUI.this, "Bilde tika pievienota.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
						
						}
				}
			}
		});
		
		JButton AddRoomButton = new JButton("Add room");
		AddRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRoom();
			}
		});
		
		AddRoomButton.setBounds(58, 110, 136, 26);
		contentPane.add(AddRoomButton);
	}

	public void addRoom() {
		String roomName = RoomNameField.getText();
		Room room = new Room(roomName);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			
			
			Blob b1 = Database.getConnection().createBlob();
			b1.setBytes(1,  baos.toByteArray());
			room.setIcon(b1);
			
			
			int id  = roomDAO.insert(room);
			room.setId(id);
			
			
			
			AddRoomGUI.this.dispose();
		} catch (IllegalArgumentException | ImagingOpException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
