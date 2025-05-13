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
import jtt.vikachaze.dto.Post;

public class PostFactory {
	public static JPanel createPostPanel(Post post) throws SQLException, IOException {
		JPanel postPanel = new JPanel();
		postPanel.setSize(288, 95);
		postPanel.setPreferredSize(new Dimension(288, 95));
		postPanel.setLayout(null);
		postPanel.setBorder(BorderFactory.createDashedBorder(new Color(150,150,150)));
		
		//Random r = new Random(); 
		//postPanel.setBackground(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
		
		JLabel pfpLabel = new JLabel("");
		pfpLabel.setBounds(10, 10, 75, 75);
		pfpLabel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
		postPanel.add(pfpLabel);
		
		if (post.getUser().getPfp() != null) {
			pfpLabel.setIcon(new StretchIcon(post.getUser().getPfpAsImage(), false));
		} else {
			pfpLabel.setIcon(new StretchIcon("emptyPfp.jpg", false));
		}
		
		JLabel titleLabel = new JLabel(post.getTitle());
		titleLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		titleLabel.setBounds(95, 10, 288-95, 25);
		postPanel.add(titleLabel);
		
		String rawTimeString = post.getSent_time().toString();
		String[] seperatedTimes = rawTimeString.split(" ");
		String dateString = seperatedTimes[0].replace("-", "/");
		String timeString = seperatedTimes[1].split(":")[0] + ":" + seperatedTimes[1].split(":")[1];
		
		JLabel timeLabel = new JLabel(timeString + "  -  " + dateString);
		timeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		timeLabel.setBounds(95, 65, 288-95, 25);
		postPanel.add(timeLabel);
		
		return postPanel;
	}
	
	
}
