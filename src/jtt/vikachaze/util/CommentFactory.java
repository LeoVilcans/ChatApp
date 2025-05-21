package jtt.vikachaze.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import jtt.vikachaze.dto.Comment;

public class CommentFactory {
	public static JPanel createCommentPanel(Comment comment) throws SQLException, IOException {
		JPanel commentPanel = new JPanel();
		commentPanel.setSize(656, 153);
		commentPanel.setPreferredSize(new Dimension(656, 153));
		commentPanel.setLayout(null);
		
		JButton pfpButton = new JButton("");
		pfpButton.setBounds(12, 10, 87, 87);
		pfpButton.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
		
		if (comment.getUser().getPfp() != null) {
			pfpButton.setIcon(new StretchIcon(comment.getUser().getPfpAsImage(), false));
		} else {
			pfpButton.setIcon(new StretchIcon("emptyPfp.jpg", false));
		}
		
		JPanel commentContentPanel = new JPanel();
		commentContentPanel.setBounds(108, 10, 536, 133);
		commentContentPanel.setLayout(null);
		commentContentPanel.setBorder(BorderFactory.createDashedBorder(new Color(150,150,150), 5, 5));
		commentContentPanel.setBackground(UIManager.getColor("Button.background"));
		
		JLabel usernameLabel = new JLabel(comment.getUser().getUsername());
		usernameLabel.setBounds(12, 10, 485, 17);
		
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		textPane.setText(comment.getText());
		textPane.setBackground(new Color(238, 238, 238));
		textPane.setBounds(22, 37, 475, 68);
		
		String rawTimeString = comment.getSent_time().toString();
		String[] seperatedTimes = rawTimeString.split(" ");
		String dateString = seperatedTimes[0].replace("-", "/");
		String timeString = seperatedTimes[1].split(":")[0] + ":" + seperatedTimes[1].split(":")[1];
		
		JLabel timeLabel = new JLabel(timeString + "  -  " + dateString);
		timeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		timeLabel.setBounds(12, 106, 512, 17);
		
		textPane.setSize(textPane.getWidth(), textPane.getPreferredSize().height);
		
//		JLabel imageLabel = new JLabel();
//		imageLabel.setBounds(12, 37+textPane.getHeight()+10, 0, 0);
//		if (comment.getAttachmentAsImage() != null) {
//			BufferedImage bimg = ImageIO.read(comment.getAttachment().getBinaryStream());
//			
//			StretchIcon icon = new StretchIcon(bimg, true);
//			imageLabel.setIcon(icon);
//			
//			Double height = 236.0;
//			Double width = ((height/bimg.getHeight())*bimg.getWidth())+10;
//			//int width = messageContentPanel.getSize().width;
//			imageLabel.setBounds(12, 37+textPane.getHeight()+10, width.intValue(), height.intValue());
//		}
		
		
//		timeLabel.setBounds(12, 37+textPane.getHeight()+10 + imageLabel.getHeight(), timeLabel.getWidth(), 17);
		commentContentPanel.setSize(commentContentPanel.getSize().width, timeLabel.getBounds().y + timeLabel.getBounds().height + 10);
		commentPanel.setSize(634, commentContentPanel.getSize().height + 20);
		
		commentPanel.add(pfpButton);
		commentPanel.add(commentContentPanel);
		commentContentPanel.add(usernameLabel);
		commentContentPanel.add(textPane);
//		messageContentPanel.add(imageLabel);
		commentContentPanel.add(timeLabel);
		
		return commentPanel;
	}
}
