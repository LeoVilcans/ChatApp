package jtt.vikachaze.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jtt.vikachaze.Main;
import jtt.vikachaze.dao.impl.CommentDAOImpl;
import jtt.vikachaze.dao.impl.UserDAOImpl;
import jtt.vikachaze.dto.Comment;
import jtt.vikachaze.dto.Message;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.util.CommentFactory;
import jtt.vikachaze.util.MessageFactory;
import jtt.vikachaze.util.StretchIcon;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PostGUI extends JFrame{
	private JPanel profilePanel, postPanel, PostcommentsPanel,commentPanel;
	private JLabel usernameLabel, postTitleLabel, postPicture;
	private JButton pfpButton;
	private JScrollPane postScrollPane, commentScrollPane;
	private JButton commentButton;
	private Post post;
	private JTextArea textArea;
	private JTextField commentTextField;
	
	private CommentDAOImpl commentDAO;
	
	private int currentMessageHeight = 0;
	private List<Comment> comments;
	
	private void AddComment(Comment comment) throws SQLException, IOException {
		JPanel newMessagePanel = CommentFactory.createCommentPanel(comment);
		newMessagePanel.setBounds(0, currentMessageHeight, newMessagePanel.getWidth(), newMessagePanel.getHeight());
		
		currentMessageHeight+=newMessagePanel.getHeight();
		
		commentPanel.add(newMessagePanel);
		commentPanel.setSize(new Dimension(commentPanel.getPreferredSize().width, currentMessageHeight));
		commentPanel.setPreferredSize(new Dimension(commentPanel.getPreferredSize().width, currentMessageHeight));

	}
	
	private void AddAllComments() {
		commentPanel.removeAll();
		currentMessageHeight = 0;
		try {
			comments = commentDAO.getByPost(post);
			
			for (Comment comment : comments) {
				AddComment(comment);
			}
//			ScrollToBottom();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PostGUI(Post post) {
		commentDAO = new CommentDAOImpl();
		
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
		
		postTitleLabel = new JLabel("<Title>");
		postTitleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		postTitleLabel.setBounds(94, 38, 255, 33);
		profilePanel.add(postTitleLabel);
		
		postPanel = new JPanel(null);
		postPanel.setBounds(0, 92, 368, 489);
		profilePanel.add(postPanel);
	
		postPicture = new JLabel("");
		postPicture.setBounds(12, 12, 342, 265);
		postPanel.add(postPicture);
	
		postScrollPane = new JScrollPane();
		postScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		postScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		postScrollPane.setBounds(12, 289, 344, 188);
		postPanel.add(postScrollPane);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Dialog", Font.PLAIN, 16));
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		postScrollPane.setViewportView(textArea);
	
		PostcommentsPanel = new JPanel(null);
		PostcommentsPanel.setBounds(367, 0, 197, 581);
		profilePanel.add(PostcommentsPanel);
		
		commentScrollPane = new JScrollPane();
		commentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		commentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		commentScrollPane.setBounds(3, 0, 194, 542);
		PostcommentsPanel.add(commentScrollPane);
		
		commentPanel = new JPanel();
		commentScrollPane.setViewportView(commentPanel);
		
		commentTextField = new JTextField();
		commentTextField.setBounds(3, 548, 155, 28);
		PostcommentsPanel.add(commentTextField);
		commentTextField.setColumns(10);
		
		commentButton = new JButton("niggers");
		commentButton.setBounds(155, 548, 38, 27);
		PostcommentsPanel.add(commentButton);
		commentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				commentPost();
			}
		});
		
		updatePost();
		AddAllComments();
		
		// Pievieno čata atjaunošanas, jeb refresh funkcionalitāti. Tiek izpildīts ar 1hz frekvenci.
				ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
				ses.scheduleAtFixedRate(new Runnable() {
				    @Override
				    public void run() {   
				    	if (!Main.isLoggedIn()) {
				    		PostGUI.this.dispose();
				    	}
				    	
				    	int lastIndex = 0;
				    	if (!comments.isEmpty()) {
				    		lastIndex = comments.get(comments.size()-1).getId();
				    	}

				        try {
							List<Comment> newComments = commentDAO.getSinceIndex(lastIndex);
							
							for (Comment comment : newComments) {
								comments.add(comment);
								AddComment(comment);
							}
							
							if (!newComments.isEmpty()) {
//								ScrollToBottom();
							}
							
						} catch (SQLException | IOException e) {
							e.printStackTrace();
						}
				    }
				}, 0, 1, TimeUnit.SECONDS);
	}
	
	public void updatePost() {
		try {
			if (post.getUser().getPfp() == null) {
				pfpButton.setIcon(new StretchIcon("emptyPfp.jpg", false));
			} else {
				pfpButton.setIcon(new StretchIcon(post.getUser().getPfpAsImage(), false));
			}
			
			usernameLabel.setText(post.getUser().getUsername());
			postTitleLabel.setText(post.getTitle());
			postPicture.setIcon(new StretchIcon(post.getAttachmentAsImage()));
			textArea.setText(post.getText());
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void commentPost() {
		String text = commentTextField.getText();
		
		try {
			Comment comment = new Comment(Timestamp.valueOf(LocalDateTime.now()), Main.getLoggedUser(), post, text);
			
			commentDAO.insert(comment);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
