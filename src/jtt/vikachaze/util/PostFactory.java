package jtt.vikachaze.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.gui.PostGUI;

public class PostFactory {
	public static JPanel createPostPanel(Post post) throws SQLException, IOException {
		Theme currentTheme = Settings.getTheme();
		
		JPanel postPanel = new JPanel();
		postPanel.setSize(350, 340);
		postPanel.setLayout(null);
		postPanel.setBorder(BorderFactory.createDashedBorder(currentTheme.getPrimaryColor()));
		postPanel.setBackground(currentTheme.getBackgroundColor());
		
		JButton pfpLabel = new JButton("");
		pfpLabel.setBounds(10, 10, 75, 75);
		pfpLabel.setBorder(BorderFactory.createLineBorder(currentTheme.getPrimaryColor()));
		postPanel.add(pfpLabel);
		
		if (post.getUser().getPfp() != null) {
			pfpLabel.setIcon(new StretchIcon(post.getUser().getPfpAsImage(), false));
		} else {
			pfpLabel.setIcon(new StretchIcon("emptyPfp.jpg", false));
		}
		
		JLabel titleLabel = new JLabel(post.getTitle());
		titleLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		titleLabel.setForeground(currentTheme.getTextColor());
		titleLabel.setBounds(95, 10, 288-95, 25);
		postPanel.add(titleLabel);
		
		JTextArea postLabel = new JTextArea(post.getText());
		postLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		postLabel.setForeground(currentTheme.getTextColor());
		postLabel.setBounds(95, 40, 230, 100);
		postLabel.setBackground(currentTheme.getBackgroundColor());
		postLabel.setEditable(false);
		postLabel.setLineWrap(true);
		postLabel.setWrapStyleWord(true);
		postPanel.add(postLabel);
		postLabel.setSize(postLabel.getWidth(), postLabel.getPreferredSize().height);
		
		JLabel usernameLabel = new JLabel(post.getUser().getUsername());
		usernameLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		usernameLabel.setBounds(10, 90, 85, 20);
		usernameLabel.setForeground(currentTheme.getTextColor());
		postPanel.add(usernameLabel);
		
		JLabel imageLabel = new JLabel();
		imageLabel.setBackground(currentTheme.getBackgroundColor());
		int imageLabelHeight = postLabel.getHeight() + postLabel.getY() + 10;
		if (post.getAttachment() != null) {
			BufferedImage bimg = ImageIO.read(post.getAttachment().getBinaryStream());
			
			StretchIcon icon = new StretchIcon(bimg, true);
			imageLabel.setIcon(icon);
			imageLabel.setBackground(new Color(12,12,12));
			Double height = 136.0;
			Double width = ((height/bimg.getHeight())*bimg.getWidth())+10;
			//int width = messageContentPanel.getSize().width;
			imageLabel.setBounds(90, imageLabelHeight, width.intValue(), height.intValue());
		}
		postPanel.add(imageLabel);
		
		String rawTimeString = post.getSent_time().toString();
		String[] seperatedTimes = rawTimeString.split(" ");
		String dateString = seperatedTimes[0].replace("-", "/");
		String timeString = seperatedTimes[1].split(":")[0] + ":" + seperatedTimes[1].split(":")[1];
		
		JLabel timeLabel = new JLabel(timeString + "  -  " + dateString);
		timeLabel.setForeground(currentTheme.getPrimaryColor());
		timeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		if (post.getAttachment() != null) {
			int timeLabelHeight = imageLabel.getHeight() + imageLabel.getY() + 5;
			timeLabel.setBounds(10, timeLabelHeight, 288, 14);
		} else {
			timeLabel.setBounds(10, postLabel.getHeight() + postLabel.getY() + 50, 288, 14);
		}
		postPanel.add(timeLabel);
		
		postPanel.setSize(postPanel.getWidth(), timeLabel.getY() +timeLabel.getHeight() + 10);
		
		JButton openPostButton = new JButton();
		openPostButton.setBounds(0, 0, postPanel.getWidth(), postPanel.getHeight());
		openPostButton.setOpaque(false);
		openPostButton.setContentAreaFilled(false);
		openPostButton.setBorderPainted(false);
		
		openPostButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PostGUI postGUI = new PostGUI(post);
				postGUI.setVisible(true);
			}
		});
		postPanel.add(openPostButton);
		
		return postPanel;
	}
	
	
}
