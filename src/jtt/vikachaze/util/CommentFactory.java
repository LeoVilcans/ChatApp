package jtt.vikachaze.util;

import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import jtt.vikachaze.dto.Comment;
import jtt.vikachaze.dto.Theme;

public class CommentFactory {
	private static Theme currentTheme = Settings.getTheme();
	
	public static JPanel createCommentPanel(Comment comment) throws SQLException, IOException {
		JPanel postPanel = new JPanel();
		postPanel.setSize(350, 340);
		postPanel.setLayout(null);
		postPanel.setBorder(BorderFactory.createDashedBorder(currentTheme.getPrimaryColor()));
		postPanel.setBackground(currentTheme.getBackgroundColor());
		
		JButton pfpLabel = new JButton("");
		pfpLabel.setBounds(10, 10, 75, 75);
		pfpLabel.setBorder(BorderFactory.createLineBorder(currentTheme.getPrimaryColor()));
		postPanel.add(pfpLabel);
		
		if (comment.getUser().getPfp() != null) {
			pfpLabel.setIcon(new StretchIcon(comment.getUser().getPfpAsImage(), false));
		} else {
			pfpLabel.setIcon(new StretchIcon("emptyPfp.jpg", false));
		}
		
		JTextArea postLabel = new JTextArea(comment.getText());
		postLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		postLabel.setForeground(currentTheme.getTextColor());
		postLabel.setBounds(95, 40, 230, 100);
		postLabel.setBackground(currentTheme.getBackgroundColor());
		postLabel.setEditable(false);
		postLabel.setLineWrap(true);
		postLabel.setWrapStyleWord(true);
		postPanel.add(postLabel);
		postLabel.setSize(postLabel.getWidth(), postLabel.getPreferredSize().height);
		
		JLabel usernameLabel = new JLabel(comment.getUser().getUsername());
		usernameLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		usernameLabel.setBounds(10, 90, 85, 20);
		usernameLabel.setForeground(currentTheme.getTextColor());
		postPanel.add(usernameLabel);
		
		String rawTimeString = comment.getSent_time().toString();
		String[] seperatedTimes = rawTimeString.split(" ");
		String dateString = seperatedTimes[0].replace("-", "/");
		String timeString = seperatedTimes[1].split(":")[0] + ":" + seperatedTimes[1].split(":")[1];
		
		JLabel timeLabel = new JLabel(timeString + "  -  " + dateString);
		timeLabel.setForeground(currentTheme.getPrimaryColor());
		timeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		timeLabel.setBounds(10, postLabel.getHeight() + postLabel.getY() + 50, 288, 14);
		postPanel.add(timeLabel);
		
		postPanel.setSize(postPanel.getWidth(), timeLabel.getY() +timeLabel.getHeight() + 10);
		
		JButton openPostButton = new JButton();
		openPostButton.setBounds(0, 0, postPanel.getWidth(), postPanel.getHeight());
		openPostButton.setOpaque(false);
		openPostButton.setContentAreaFilled(false);
		openPostButton.setBorderPainted(false);
		
		return postPanel;
	}
}
