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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JTextArea;

public class MainMenuGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, scrollPostPanel, roomPanel, postPanel, userPanel;
	private JTextField searchTextField;
	private JScrollPane roomScrollPane, postScrollPane;
	private JLabel profileUsernameLabel, pfpLabel, roomListLabel;
	private JTextArea profileStatusArea;
	private JButton searchButton, profileEditButton, btnLogOut, AddPostButton, AddRoomButton, SettingsButton;
	
	private JList<String> roomList;
	private RoomDAOImpl roomDAO = new RoomDAOImpl();
	private PostDAO postDAO = new PostDAOImpl();
	
	private DefaultListModel<String> room = new DefaultListModel<String>();
	private List<Post> posts = new ArrayList<Post>();
	
	private ScheduledExecutorService ses = null;
	
	private int currentPostHeight = 0;
	
	public MainMenuGUI() {
		setIconImage(new ImageIcon("vikachazeLogo.png").getImage());
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 734, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		roomPanel = new JPanel();
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
		
		roomListLabel = new JLabel("Rooms");
		roomListLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		roomListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roomListLabel.setBounds(10, 11, 143, 17);
		roomPanel.add(roomListLabel);
		
		postPanel = new JPanel();
		postPanel.setLayout(null);
		postPanel.setBounds(163, 0, 374, 454);
		contentPane.add(postPanel);
		
		postScrollPane = new JScrollPane();
		postScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		postScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		postScrollPane.setBounds(5, 34, 357, 420);
		postScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		postPanel.add(postScrollPane);
		
		scrollPostPanel = new JPanel(null);
		postScrollPane.setViewportView(scrollPostPanel);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(5, 8, 308, 20);
		postPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		searchButton = new JButton();
		searchButton.setBounds(316, 8, 46, 19);
		postPanel.add(searchButton);
		
		searchButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {	
				ses.close();
				scrollPostPanel.removeAll();
				posts.clear();
				currentPostHeight = 0;
				
				if (searchTextField.getText().isEmpty()) {
					addPostTracker();
					return;
				}
				
				List<Post> allPosts;
				try {
					allPosts = postDAO.getAllData();
					for (Post post : allPosts) {
						if (post.getTitle().toLowerCase().contains(searchTextField.getText())) {
							posts.add(post);
							addPost(post);
						}
					}
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		userPanel = new JPanel();
		userPanel.setLayout(null);
		userPanel.setBounds(536, 0, 182, 454);
		contentPane.add(userPanel);
		
		pfpLabel = new JLabel("");
		pfpLabel.setBackground(new Color(128, 128, 128));
		pfpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pfpLabel.setBounds(12, 12, 159, 159);
		userPanel.add(pfpLabel);
		
		profileUsernameLabel = new JLabel("<<Username>>");
		profileUsernameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		profileUsernameLabel.setBounds(12, 177, 159, 21);
		userPanel.add(profileUsernameLabel);
		
		profileEditButton = new JButton("edit");
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
		
		btnLogOut = new JButton("log out");
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
		profileStatusArea.setBounds(12, 201, 158, 101);
		userPanel.add(profileStatusArea);
		
		AddPostButton = new JButton("add post");
		AddPostButton.setBounds(7, 384, 164, 24);
		userPanel.add(AddPostButton);
		
		AddRoomButton = new JButton("Add room");
		AddRoomButton.setBounds(7, 349, 164, 23);
		userPanel.add(AddRoomButton);
		
		SettingsButton = new JButton("");
		SettingsButton.setBounds(7, 314, 55, 23);
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
		
		updateTheme();
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
		ses = Executors.newSingleThreadScheduledExecutor();
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
	
	public void updateTheme() {
		Theme currentTheme = Settings.getTheme();
		
		// getBackgroundColor()
		contentPane.setBackground(currentTheme.getBackgroundColor());
		
		roomPanel.setBackground(currentTheme.getBackgroundColor());
		postPanel.setBackground(currentTheme.getBackgroundColor());
		userPanel.setBackground(currentTheme.getBackgroundColor());
		
		postScrollPane.setBackground(currentTheme.getBackgroundColor());
		scrollPostPanel.setBackground(currentTheme.getBackgroundColor());

		roomList.setBackground(currentTheme.getBackgroundColor());
		
		profileUsernameLabel.setBackground(currentTheme.getBackgroundColor());
		profileStatusArea.setBackground(currentTheme.getBackgroundColor());
		
		// getButtonColor()
		searchButton.setBackground(currentTheme.getButtonColor());
		profileEditButton.setBackground(currentTheme.getButtonColor());
		btnLogOut.setBackground(currentTheme.getButtonColor());
		AddPostButton.setBackground(currentTheme.getButtonColor());
		AddRoomButton.setBackground(currentTheme.getButtonColor());
		SettingsButton.setBackground(currentTheme.getButtonColor());
		
		// getTextColor()
		roomList.setForeground(currentTheme.getTextColor());
		roomListLabel.setForeground(currentTheme.getTextColor());
		
		profileUsernameLabel.setForeground(currentTheme.getTextColor());
		profileStatusArea.setForeground(currentTheme.getPrimaryColor());
		
		searchButton.setForeground(currentTheme.getTextColor());
		profileEditButton.setForeground(currentTheme.getTextColor());
		btnLogOut.setForeground(currentTheme.getTextColor());
		AddPostButton.setForeground(currentTheme.getTextColor());
		AddRoomButton.setForeground(currentTheme.getTextColor());
		SettingsButton.setForeground(currentTheme.getTextColor());
		
		// getPrimaryColor()
		roomScrollPane.setBackground(currentTheme.getPrimaryColor());
		pfpLabel.setBorder(BorderFactory.createLineBorder(currentTheme.getPrimaryColor()));
		
	}
}
