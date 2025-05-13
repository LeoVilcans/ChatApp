package jtt.vikachaze.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.dao.impl.RoomDAOImpl;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.util.StretchIcon;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JTextArea;

public class MainMenuGUI extends JFrame{
	private JPanel contentPane;
	private JTextField searchTextField;
	private JScrollPane roomScrollPane;
	private JLabel profileUsernameLabel, pfpLabel;
	private JTextArea profileStatusArea;
	
	private JList<String> roomList;
	private RoomDAOImpl roomDAO = new RoomDAOImpl();
	
	private DefaultListModel<String> room = new DefaultListModel<String>();
	
	public MainMenuGUI() throws SQLException, IOException {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 659, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(null);
		roomPanel.setBounds(0, 0, 163, 454);
		contentPane.add(roomPanel);
		
		roomScrollPane = new JScrollPane();
		roomScrollPane.setBounds(3, 34, 157, 420);
		roomPanel.add(roomScrollPane);
		roomScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		roomScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		roomList = new JList<String>(room);
		roomScrollPane.setViewportView(roomList);
		roomList.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					String currentRoom = roomList.getSelectedValue();
					
					Room tempRoom = new Room(currentRoom);
					try {
						int roomID = roomDAO.getID(tempRoom);
						Room room = roomDAO.getByID(roomID);

						RoomGUI roomGUI = new RoomGUI(room);
						roomGUI.setVisible(true);
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
		        }
			}
		});
		
		JLabel roomListLabel = new JLabel("Rooms");
		roomListLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		roomListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roomListLabel.setBounds(10, 11, 143, 17);
		roomPanel.add(roomListLabel);
		
		JPanel postPanel = new JPanel();
		postPanel.setLayout(null);
		postPanel.setBounds(163, 0, 299, 454);
		contentPane.add(postPanel);
		
		JScrollPane postScrollPane = new JScrollPane();
		postScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		postScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		postScrollPane.setBounds(5, 34, 288, 420);
		postPanel.add(postScrollPane);
		
		JPanel messagePanel_1 = new JPanel((LayoutManager) null);
		postScrollPane.setViewportView(messagePanel_1);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(5, 8, 262, 20);
		postPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		JButton searchButton = new JButton();
		searchButton.setBounds(264, 8, 29, 19);
		postPanel.add(searchButton);
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(null);
		userPanel.setBounds(461, 0, 182, 454);
		contentPane.add(userPanel);
		
		pfpLabel = new JLabel("");
		pfpLabel.setBackground(new Color(128, 128, 128));
		pfpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pfpLabel.setBounds(12, 12, 159, 159);
		pfpLabel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
		userPanel.add(pfpLabel);
		
		profileUsernameLabel = new JLabel("<<Username>>");
		profileUsernameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		profileUsernameLabel.setBounds(12, 177, 159, 21);
		userPanel.add(profileUsernameLabel);
		
		JButton profileEditButton = new JButton("edit");
		profileEditButton.setBounds(7, 419, 55, 24);
		userPanel.add(profileEditButton);
		
		JButton btnLogOut = new JButton("log out");
		btnLogOut.setBounds(71, 419, 100, 24);
		userPanel.add(btnLogOut);
		
		btnLogOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.logout();
				MainMenuGUI.this.dispose();
				try {
					Main.main(null);
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		profileStatusArea = new JTextArea();
		profileStatusArea.setEditable(false);
		profileStatusArea.setFont(new Font("Dialog", Font.ITALIC, 12));
		profileStatusArea.setLineWrap(true);
		profileStatusArea.setForeground(new Color(107, 107, 107));
		profileStatusArea.setBackground(new Color(238, 238, 238));
		profileStatusArea.setBounds(12, 201, 158, 119);
		userPanel.add(profileStatusArea);
		
		JButton AddPostButton = new JButton("add post");
		AddPostButton.setBounds(7, 384, 164, 24);
		userPanel.add(AddPostButton);
		AddPostButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddPostGUI form  = new AddPostGUI();
						form.setVisible(true);
			}
		});
		
		updateProfile();
		addRooms();
	}
	
	public void updateProfile() throws SQLException, IOException {
		User user = Main.getLoggedUser();
		
		if (user.getPfp() == null) {
			pfpLabel.setIcon(new StretchIcon("emptyPfp.jpg", false));
		} else {
			pfpLabel.setIcon(new StretchIcon(user.getPfpAsImage(), false));
		}
		
		profileUsernameLabel.setText(user.getUsername());
		
		if (user.getStatus() != null) {
			profileStatusArea.setText('"' + user.getStatus() + '"');
		} else {
			profileStatusArea.setText("");
		}
	}
	
	public void addRooms() {
		try {
			List<Room> rooms = roomDAO.getAllData();
			
			for (Room r : rooms) {
				room.addElement(r.getTitle());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
