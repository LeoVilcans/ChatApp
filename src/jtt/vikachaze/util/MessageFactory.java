package jtt.vikachaze.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

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
		timeLabel.setBounds(12, 37+textPane.getHeight()+10, timeLabel.getWidth(), 17);
		messageContentPanel.setSize(messageContentPanel.getSize().width, timeLabel.getBounds().y + timeLabel.getBounds().height + 10);
		messagePanel.setSize(634, messageContentPanel.getSize().height + 20);
		
		messagePanel.add(pfpLabel);
		messagePanel.add(messageContentPanel);
		messageContentPanel.add(usernameLabel);
		messageContentPanel.add(textPane);
		messageContentPanel.add(timeLabel);
		
		//Random r = new Random();
		//messagePanel.setBackground(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
		
		return messagePanel;
	}
	
	
}
