package jtt.vikachaze.gui;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.util.StretchIcon;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;

public class PostGUI extends JFrame{
	private JPanel profilePanel, postPanel, PostcommentsPanel, textPanel,commentPanel;
	private JLabel usernameLabel;
	private JButton pfpButton;
	private JScrollPane postScrollPane, commentScrollPane;
	
	private Post post;
	
	public PostGUI(Post post) {
		this.post = post;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 580, 620);
		setTitle(post.getTitle());
		profilePanel = new JPanel(null);
		profilePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		profilePanel.setLayout(null);
		setContentPane(profilePanel);
		
		pfpButton = new JButton("");
		pfpButton.setBounds(6, 4, 80, 80);
		pfpButton.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
		profilePanel.add(pfpButton);
		
		usernameLabel = new JLabel("<Username>");
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		usernameLabel.setForeground(new Color(0, 0, 0));
		usernameLabel.setBounds(91, 5, 258, 26);
		profilePanel.add(usernameLabel);
		
		JLabel postTitleLabel = new JLabel("<Title>");
		postTitleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		postTitleLabel.setBounds(94, 38, 255, 33);
		profilePanel.add(postTitleLabel);
		
		postPanel = new JPanel(null);
		postPanel.setBounds(0, 92, 368, 489);
		profilePanel.add(postPanel);
	
		JLabel postPicture = new JLabel("");
		postPicture.setBounds(12, 12, 342, 265);
		postPanel.add(postPicture);
	
		postScrollPane = new JScrollPane();
		postScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		postScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		postScrollPane.setBounds(12, 289, 344, 188);
		postPanel.add(postScrollPane);
		
		textPanel = new JPanel();
		postScrollPane.setViewportView(textPanel);
	
		PostcommentsPanel = new JPanel(null);
		PostcommentsPanel.setBounds(367, 0, 197, 581);
		profilePanel.add(PostcommentsPanel);
		
		commentScrollPane = new JScrollPane();
		commentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		commentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		commentScrollPane.setBounds(3, 0, 194, 581);
		PostcommentsPanel.add(commentScrollPane);
		
		commentPanel = new JPanel();
		commentScrollPane.setViewportView(commentPanel);
		
		updateProfile();
	}
	
	public void updateProfile() {
		try {
			if (post.getUser().getPfp() == null) {
				pfpButton.setIcon(new StretchIcon("emptyPfp.jpg", false));
			} else {
				pfpButton.setIcon(new StretchIcon(post.getUser().getPfpAsImage(), false));
			}
			
			usernameLabel.setText(post.getUser().getUsername());
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
