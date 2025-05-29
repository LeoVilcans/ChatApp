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
import jtt.vikachaze.dao.impl.PostLikeDAOImpl;
import jtt.vikachaze.dto.Comment;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.PostLikes;
import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.util.CommentFactory;
import jtt.vikachaze.util.PostFactory;
import jtt.vikachaze.util.Settings;
import jtt.vikachaze.util.StretchIcon;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class PostGUI extends JFrame{
	private JPanel profilePanel, postPanel, PostcommentsPanel,commentPanel;
	private JLabel usernameLabel, postTitleLabel, postPicture, likeCounterLabel;
	private JButton pfpButton;
	private JScrollPane postScrollPane, commentScrollPane;
	private JButton commentButton;
	private Post post;
	private JTextArea textArea;
	private JTextField commentTextField;
	private JCheckBox likeCheck;
	private PostLikes postLikes;
	
	private CommentDAOImpl commentDAO;
	private PostLikeDAOImpl postLikeDAO;
	
	private int currentMessageHeight = 0;
	private List<Comment> comments;
	private List<PostLikes> postLike;
	
	public PostGUI(Post post) {
		commentDAO = new CommentDAOImpl();
		postLikeDAO = new PostLikeDAOImpl();
		Theme currentTheme = Settings.getTheme();
		
		this.post = post;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 580, 620);
		setTitle(post.getTitle());
		profilePanel = new JPanel(null);
		profilePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		profilePanel.setLayout(null);
		setContentPane(profilePanel);
		profilePanel.setBackground(currentTheme.getBackgroundColor());
		
		pfpButton = new JButton("");
		pfpButton.setBounds(6, 4, 80, 80);
		pfpButton.setBorder(BorderFactory.createLineBorder(currentTheme.getPrimaryColor()));
		profilePanel.add(pfpButton);
		
		usernameLabel = new JLabel("<Username>");
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		usernameLabel.setForeground(currentTheme.getTextColor());
		usernameLabel.setBounds(91, 5, 258, 26);
		profilePanel.add(usernameLabel);
		
		postTitleLabel = new JLabel("<Title>");
		postTitleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		postTitleLabel.setForeground(currentTheme.getTextColor());
		postTitleLabel.setBounds(94, 38, 255, 33);
		profilePanel.add(postTitleLabel);
		
		postPanel = new JPanel(null);
		postPanel.setBounds(0, 92, 368, 489);
		postPanel.setBackground(currentTheme.getBackgroundColor());
		profilePanel.add(postPanel);
	
		postPicture = new JLabel("");
		postPicture.setBounds(12, 12, 342, 265);
		postPicture.setBorder(BorderFactory.createLineBorder(currentTheme.getPrimaryColor()));
		postPanel.add(postPicture);
	
		postScrollPane = new JScrollPane();
		postScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		postScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		postScrollPane.setBounds(12, 289, 344, 188);
		postScrollPane.setBackground(currentTheme.getBackgroundColor());
		postPanel.add(postScrollPane);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Dialog", Font.PLAIN, 16));
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		postScrollPane.setViewportView(textArea);
	
		PostcommentsPanel = new JPanel(null);
		PostcommentsPanel.setBounds(367, 0, 197, 581);
		PostcommentsPanel.setBackground(currentTheme.getBackgroundColor());
		profilePanel.add(PostcommentsPanel);
		
		commentScrollPane = new JScrollPane();
		commentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		commentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		commentScrollPane.setBounds(3, 0, 194, 542);
		PostcommentsPanel.add(commentScrollPane);
		
		commentPanel = new JPanel();
		commentPanel.setBackground(currentTheme.getBackgroundColor());
		commentScrollPane.setViewportView(commentPanel);
		
		commentTextField = new JTextField();
		commentTextField.setBounds(3, 548, 155, 28);
		PostcommentsPanel.add(commentTextField);
		commentTextField.setColumns(10);
		
		commentButton = new JButton("");
		commentButton.setBounds(155, 548, 38, 27);
		commentButton.setBackground(currentTheme.getButtonColor());
		commentButton.setForeground(currentTheme.getTextColor());
		PostcommentsPanel.add(commentButton);
		
		likeCheck = new JCheckBox("");
		likeCheck.setBounds(299, 38, 50, 46);
		profilePanel.add(likeCheck);
		
		likeCounterLabel = new JLabel("");
		likeCounterLabel.setBounds(286, 70, 46, 14);
		profilePanel.add(likeCounterLabel);
		try {
			List<PostLikes> tempPostLikes = postLikeDAO.getByPostID(post);
			
			int tempLikeCount = tempPostLikes.size();
			String likeCount = Integer.toString(tempLikeCount);
			likeCounterLabel.setText(likeCount);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		likeCheck.setIcon(new StretchIcon("vikachazeLogo.png", false));

		likeCheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					likePost();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		commentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				commentPost();
			}
		});
		
		loadPost();
		addCommentTracker();
	}
	
	public void loadPost() {
		try {
			if (post.getUser().getPfp() == null) {
				pfpButton.setIcon(new StretchIcon("emptyPfp.jpg", false));
			} else {
				pfpButton.setIcon(new StretchIcon(post.getUser().getPfpAsImage(), false));
			}
			
			usernameLabel.setText(post.getUser().getUsername());
			postTitleLabel.setText(post.getTitle());
			
			if (post.getAttachment() == null) {
				postPicture.setIcon(new StretchIcon("emptyRoomIcon.jpg", false));
			} else {
				postPicture.setIcon(new StretchIcon(post.getAttachmentAsImage(), false));
			}
			
			textArea.setText(post.getText());
			
			PostLikes postLike = postLikeDAO.getOnPostByUser(post, Main.getLoggedUser());
			if (postLike != null) {
				likeCheck.setSelected(true);
				refrestLikeCheck();
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void commentPost() {
		String text = commentTextField.getText();
		
		try {
			Comment comment = new Comment(Timestamp.valueOf(LocalDateTime.now()), Main.getLoggedUser(), post, text);
			
			int id = commentDAO.insert(comment);
			comment.setId(id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addCommentTracker() {
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {   
		    	int lastIndex = 0;
		    	if (!comments.isEmpty()) {
		    		lastIndex = comments.get(comments.size()-1).getId();
		    	}

		        try {
					List<Comment> newComments = commentDAO.getSinceIndex(lastIndex);
					
					for (Comment comment : newComments) {
						comments.add(comment);
						addPost(comment);
					}
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
		    }
		}, 0, 1, TimeUnit.SECONDS);
	}
	
	private void addPost(Comment comment) throws SQLException, IOException {
		JPanel newPostPanel = CommentFactory.createCommentPanel(comment);
		newPostPanel.setBounds(0, currentMessageHeight, newPostPanel.getWidth(), newPostPanel.getHeight());
		
		currentMessageHeight+=newPostPanel.getHeight();
		
		commentPanel.add(newPostPanel);
		commentPanel.setSize(new Dimension(commentPanel.getPreferredSize().width, currentMessageHeight));
		commentPanel.setPreferredSize(new Dimension(commentPanel.getPreferredSize().width, currentMessageHeight));
	}
	
	private void refrestLikeCheck() {
		if(likeCheck.isSelected()) {
			likeCheck.setBackground(new Color(255,0,0));
		}else {
			likeCheck.setBackground(new Color(0,0,255));
		}
	}
	
	private void likePost() throws SQLException {
		
		PostLikes postLike = new PostLikes(Main.getLoggedUser(), post);			
		
		if(likeCheck.isSelected()) {
			int id = postLikeDAO.insert(postLike);
			postLike.setId(id);
			likeCounterLabel.setText(Integer.toString(Integer.parseInt(likeCounterLabel.getText())+1));
		}else {
			postLikeDAO.delete(postLike);
			likeCounterLabel.setText(Integer.toString(Integer.parseInt(likeCounterLabel.getText())-1));
		}
		
		refrestLikeCheck();
	}
}
