package jtt.vikachaze.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.dao.PostDAO;
import jtt.vikachaze.dao.impl.PostDAOImpl;
import jtt.vikachaze.dao.impl.RoomDAOImpl;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.util.PostFactory;
import jtt.vikachaze.util.Settings;
import jtt.vikachaze.util.StretchIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JTextArea;

public class MainMenuGUI extends JFrame{
	private JPanel contentPane, scrollPostPanel;
	private JTextField searchTextField;
	private JScrollPane roomScrollPane;
	private JLabel profileUsernameLabel, pfpLabel;
	private JTextArea profileStatusArea;
	
	private JList<String> roomList;
	private RoomDAOImpl roomDAO = new RoomDAOImpl();
	private PostDAO postDAO = new PostDAOImpl();
	
	private DefaultListModel<String> room = new DefaultListModel<String>();
	private List<Post> posts = new ArrayList<Post>();
	
	private int currentPostHeight = 0;
	
	public MainMenuGUI() {
		Theme currentTheme = Settings.getTheme();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 734, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(currentTheme.getBackgroundColor());

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(null);
		roomPanel.setBounds(0, 0, 163, 454);
		roomPanel.setBackground(currentTheme.getBackgroundColor());
		contentPane.add(roomPanel);
		
		roomScrollPane = new JScrollPane();
		roomScrollPane.setBounds(3, 34, 157, 420);
		roomPanel.add(roomScrollPane);
		roomScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		roomScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		roomScrollPane.setBackground(currentTheme.getPrimaryColor());
		
		roomList = new JList<String>(room);
		roomList.setBackground(currentTheme.getBackgroundColor());
		roomList.setForeground(currentTheme.getTextColor());
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
		roomListLabel.setForeground(currentTheme.getTextColor());
		roomPanel.add(roomListLabel);
		
		JPanel postPanel = new JPanel();
		postPanel.setBackground(currentTheme.getBackgroundColor());
		postPanel.setLayout(null);
		postPanel.setBounds(163, 0, 374, 454);
		contentPane.add(postPanel);
		
		JScrollPane postScrollPane = new JScrollPane();
		postScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		postScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		postScrollPane.setBounds(5, 34, 357, 420);
		postScrollPane.setBackground(currentTheme.getBackgroundColor());
		postScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		postPanel.add(postScrollPane);
		
		scrollPostPanel = new JPanel(null);
		scrollPostPanel.setBackground(currentTheme.getBackgroundColor());
		postScrollPane.setViewportView(scrollPostPanel);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(5, 8, 308, 20);
		postPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		JButton searchButton = new JButton();
		searchButton.setBackground(currentTheme.getButtonColor());
		searchButton.setForeground(currentTheme.getTextColor());
		searchButton.setBounds(316, 8, 46, 19);
		postPanel.add(searchButton);
		
		
		JPanel userPanel = new JPanel();
		userPanel.setBackground(currentTheme.getBackgroundColor());
		userPanel.setLayout(null);
		userPanel.setBounds(536, 0, 182, 454);
		contentPane.add(userPanel);
		
		pfpLabel = new JLabel("");
		pfpLabel.setBackground(new Color(128, 128, 128));
		pfpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pfpLabel.setBounds(12, 12, 159, 159);
		pfpLabel.setBorder(BorderFactory.createLineBorder(currentTheme.getPrimaryColor()));
		userPanel.add(pfpLabel);
		
		profileUsernameLabel = new JLabel("<<Username>>");
		profileUsernameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		profileUsernameLabel.setBounds(12, 177, 159, 21);
		profileUsernameLabel.setBackground(currentTheme.getBackgroundColor());
		profileUsernameLabel.setForeground(currentTheme.getTextColor());
		userPanel.add(profileUsernameLabel);
		
		JButton profileEditButton = new JButton("edit");
		profileEditButton.setBackground(currentTheme.getButtonColor());
		profileEditButton.setForeground(currentTheme.getTextColor());
		profileEditButton.setBounds(7, 419, 55, 24);
		userPanel.add(profileEditButton);
		profileEditButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditProfileGUI profileGUI = new EditProfileGUI();
				profileGUI.setVisible(true);
				MainMenuGUI.this.dispose();
			}
		});
		
		JButton btnLogOut = new JButton("log out");
		btnLogOut.setBackground(currentTheme.getButtonColor());
		btnLogOut.setForeground(currentTheme.getTextColor());
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
					e1.printStackTrace();
				}
			}
		});
		 
		profileStatusArea = new JTextArea();
		profileStatusArea.setEditable(false);
		profileStatusArea.setFont(new Font("Dialog", Font.ITALIC, 12));
		profileStatusArea.setLineWrap(true);
		profileStatusArea.setForeground(currentTheme.getPrimaryColor());
		profileStatusArea.setBackground(currentTheme.getBackgroundColor());
		profileStatusArea.setBounds(12, 201, 158, 101);
		userPanel.add(profileStatusArea);
		
		JButton AddPostButton = new JButton("add post");
		AddPostButton.setBackground(currentTheme.getButtonColor());
		AddPostButton.setForeground(currentTheme.getTextColor());
		AddPostButton.setBounds(7, 384, 164, 24);
		userPanel.add(AddPostButton);
		
		JButton AddRoomButton = new JButton("Add room");
		AddRoomButton.setBackground(currentTheme.getButtonColor());
		AddRoomButton.setForeground(currentTheme.getTextColor());
		AddRoomButton.setBounds(7, 349, 164, 23);
		userPanel.add(AddRoomButton);
		
		JButton SettingsButton = new JButton("");
		SettingsButton.setBounds(7, 314, 55, 23);
		SettingsButton.setBackground(currentTheme.getButtonColor());
		SettingsButton.setForeground(currentTheme.getTextColor());
		userPanel.add(SettingsButton);
		SettingsButton.setIcon(new StretchIcon("settingsIcon.png",true));
		SettingsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SettingsGUI form = new SettingsGUI();
				form.setVisible(true);
				MainMenuGUI.this.dispose();
			}
		});
		
		AddRoomButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddRoomGUI form = new AddRoomGUI();
				form.setVisible(true);
			}
		});
		
		AddPostButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddPostGUI form  = new AddPostGUI();
				form.setVisible(true);
			}
		});
		
		updateProfile();
		addPostTracker();
		addRooms();
	}
	
	public void updateProfile() {
		User user = Main.getLoggedUser();
		
		try {
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
		} catch (SQLException | IOException e) {
			e.printStackTrace();
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
	
	private void addPostTracker() {
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {   
		    	int lastIndex = 0;
		    	if (!posts.isEmpty()) {
		    		lastIndex = posts.get(posts.size()-1).getId();
		    	}

		        try {
					List<Post> newPosts = postDAO.getSinceIndex(lastIndex);
					
					for (Post post : newPosts) {
						posts.add(post);
						addPost(post);
					}
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
		    }
		}, 0, 1, TimeUnit.SECONDS);
	}
	
	private void addPost(Post post) throws SQLException, IOException {
		JPanel newPostPanel = PostFactory.createPostPanel(post);
		newPostPanel.setBounds(0, currentPostHeight, newPostPanel.getWidth(), newPostPanel.getHeight());
		
		currentPostHeight+=newPostPanel.getHeight();
		
		scrollPostPanel.add(newPostPanel);
		scrollPostPanel.setSize(new Dimension(scrollPostPanel.getPreferredSize().width, currentPostHeight));
		scrollPostPanel.setPreferredSize(new Dimension(scrollPostPanel.getPreferredSize().width, currentPostHeight));

	}
}
