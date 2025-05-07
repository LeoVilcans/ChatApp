package jtt.vikachaze.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import jtt.vikachaze.dto.Message;

public class MessageFactory {
	public static JPanel createMessagePanel(Message message) throws SQLException, IOException {
		JPanel messagePanel = new JPanel();
		messagePanel.setSize(656, 153);
		messagePanel.setPreferredSize(new Dimension(656, 153));
		messagePanel.setLayout(null);
		
		JLabel pfpLabel = new JLabel("");
		pfpLabel.setBounds(12, 10, 87, 87);
		pfpLabel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
		
		if (message.getUser().getPfp() != null) {
			pfpLabel.setIcon(new StretchIcon(message.getUser().getPfpAsImage(), false));
		} else {
			pfpLabel.setIcon(new StretchIcon("emptyPfp.jpg", false));
		}
		
		JPanel messageContentPanel = new JPanel();
		messageContentPanel.setBounds(108, 10, 536, 133);
		messageContentPanel.setLayout(null);
		messageContentPanel.setBorder(BorderFactory.createDashedBorder(new Color(150,150,150), 5, 5));
		messageContentPanel.setBackground(UIManager.getColor("Button.background"));
		
		JLabel usernameLabel = new JLabel(message.getUser().getUsername());
		usernameLabel.setBounds(12, 10, 485, 17);
		
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		textPane.setText(message.getText());
		textPane.setBackground(new Color(238, 238, 238));
		textPane.setBounds(22, 37, 475, 68);
		
		String rawTimeString = message.getSent_time().toString();
		String[] seperatedTimes = rawTimeString.split(" ");
		String dateString = seperatedTimes[0].replace("-", "/");
		String timeString = seperatedTimes[1].split(":")[0] + ":" + seperatedTimes[1].split(":")[1];
		
		JLabel timeLabel = new JLabel(timeString + "  -  " + dateString);
		timeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		timeLabel.setBounds(12, 106, 512, 17);
		
		textPane.setSize(textPane.getWidth(), textPane.getPreferredSize().height);
		
		JLabel imageLabel = new JLabel();
		imageLabel.setBounds(12, 37+textPane.getHeight()+10, 0, 0);
		if (message.getAttachmentAsImage() != null) {
			BufferedImage bimg = ImageIO.read(message.getAttachment().getBinaryStream());
			
			StretchIcon icon = new StretchIcon(bimg, true);
			imageLabel.setIcon(icon);
			
			Double height = 236.0;
			Double width = ((height/bimg.getHeight())*bimg.getWidth())+10;
			//int width = messageContentPanel.getSize().width;
			imageLabel.setBounds(12, 37+textPane.getHeight()+10, width.intValue(), height.intValue());
		}
		
		
		timeLabel.setBounds(12, 37+textPane.getHeight()+10 + imageLabel.getHeight(), timeLabel.getWidth(), 17);
		messageContentPanel.setSize(messageContentPanel.getSize().width, timeLabel.getBounds().y + timeLabel.getBounds().height + 10);
		messagePanel.setSize(634, messageContentPanel.getSize().height + 20);
		
		messagePanel.add(pfpLabel);
		messagePanel.add(messageContentPanel);
		messageContentPanel.add(usernameLabel);
		messageContentPanel.add(textPane);
		messageContentPanel.add(imageLabel);
		messageContentPanel.add(timeLabel);
		
		//Random r = new Random();
		//messagePanel.setBackground(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
		
		return messagePanel;
	}
	
	
}
