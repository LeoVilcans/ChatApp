package jtt.vikachaze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.util.PostFactory;
import jtt.vikachaze.util.StretchIcon;

import jtt.vikachaze.dao.PostDAO;
import jtt.vikachaze.dao.impl.PostDAOImpl;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class UserProfileGUI extends JFrame {

	private JPanel contentPane;
	private JLabel usernameLabel;
	private JButton pfpButton;
	private JTextArea statusLabel;
	private JPanel scrollPostPanel;
	
	private int currentPostHeight = 0;
	private List<Post> posts = new ArrayList<Post>();
	private PostDAO postDAO = new PostDAOImpl();
	
	private User user;
	
	public UserProfileGUI(User user) {
		this.user = user;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 489);
		setTitle(user.getUsername());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		usernameLabel = new JLabel("Username: " + user.getUsername());
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		usernameLabel.setForeground(new Color(0, 0, 0));
		usernameLabel.setBounds(148, 11, 276, 26);
		contentPane.add(usernameLabel);
		
		setContentPane(contentPane);
		
		pfpButton = new JButton("");
		pfpButton.setBounds(10, 7, 128, 128);
		pfpButton.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
		contentPane.add(pfpButton);
		
		statusLabel = new JTextArea();
		statusLabel.setWrapStyleWord(true);
		statusLabel.setLineWrap(true);
		statusLabel.setEditable(false);
		statusLabel.setForeground(new Color(107, 107, 107));
		statusLabel.setBackground(new Color(238, 238, 238));
		statusLabel.setText("\"anonims puiss\"");
		statusLabel.setBounds(148, 40, 276, 95);
		contentPane.add(statusLabel);
		
		JScrollPane scrollPostPane = new JScrollPane();
		scrollPostPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPostPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPostPane.setBounds(10, 168, 412, 270);
		scrollPostPane.getVerticalScrollBar().setUnitIncrement(15);
		contentPane.add(scrollPostPane);
		
		scrollPostPanel = new JPanel(null);
		scrollPostPane.setViewportView(scrollPostPanel);
		
		JLabel postsLabel = new JLabel("Posts:");
		postsLabel.setForeground(new Color(0, 0, 0));
		postsLabel.setBounds(10, 147, 414, 16);
		contentPane.add(postsLabel);
		
		updateProfile();
		addPostTracker();
	}
	
	public void updateProfile() {
		try {
			if (user.getPfp() == null) {
				pfpButton.setIcon(new StretchIcon("emptyPfp.jpg", false));
			} else {
				pfpButton.setIcon(new StretchIcon(user.getPfpAsImage(), false));
			}
			
			usernameLabel.setText(user.getUsername());
			
			if (user.getStatus() != null) {
				statusLabel.setText('"' + user.getStatus() + '"');
			} else {
				statusLabel.setText("");
			}
		} catch (SQLException | IOException e) {
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
					List<Post> newPosts = postDAO.getSinceIndexForUser(user, lastIndex);
					
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
