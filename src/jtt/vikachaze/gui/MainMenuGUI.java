package jtt.vikachaze.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import jtt.vikachaze.dao.impl.RoomDAOImpl;
import jtt.vikachaze.dto.Room;

import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class MainMenuGUI extends JFrame{
	private JPanel contentPane;
	private JTextField searchTextField;
	private JScrollPane roomScrollPane;
	private JList<String> roomList;
	private RoomDAOImpl roomDAO = new RoomDAOImpl();
	
	private DefaultListModel<String> room = new DefaultListModel<String>();
	
	public static void main(String[] args) {
		MainMenuGUI form = new MainMenuGUI();
		form.setVisible(true);
	}
	
	public MainMenuGUI() {
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
		
		JPanel roomPanel_1 = new JPanel();
		roomPanel_1.setLayout(null);
		roomPanel_1.setBounds(461, 0, 182, 454);
		contentPane.add(roomPanel_1);
		
		JLabel pfpLabel = new JLabel("<<pfp>>");
		pfpLabel.setBackground(new Color(128, 128, 128));
		pfpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pfpLabel.setBounds(5, 12, 55, 44);
		roomPanel_1.add(pfpLabel);
		
		JLabel profileUsernameLabel = new JLabel("<<Username>>");
		profileUsernameLabel.setBounds(70, 26, 100, 16);
		roomPanel_1.add(profileUsernameLabel);
		
		JButton profileEditButton = new JButton("edit");
		profileEditButton.setBounds(6, 144, 55, 24);
		roomPanel_1.add(profileEditButton);
		
		JButton btnLogOut = new JButton("log out");
		btnLogOut.setBounds(70, 144, 100, 24);
		roomPanel_1.add(btnLogOut);
		
		JLabel profileStatusLabel = new JLabel("<<status>>");
		profileStatusLabel.setBounds(5, 68, 165, 64);
		roomPanel_1.add(profileStatusLabel);
		
		addRooms();
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
