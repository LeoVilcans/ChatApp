package jtt.vikachaze.util;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jtt.vikachaze.dto.Comment;
import jtt.vikachaze.dto.Theme;
import jtt.vikachaze.gui.UserProfileGUI;

public class CommentFactory {
	public static JPanel createCommentPanel(Comment comment) throws SQLException, IOException {
		Theme currentTheme = Settings.getTheme();
		
		JPanel commentPanel = new JPanel();
		commentPanel.setSize(350, 120);
		commentPanel.setLayout(null);
		commentPanel.setBorder(BorderFactory.createDashedBorder(currentTheme.getPrimaryColor()));
		commentPanel.setBackground(currentTheme.getBackgroundColor());
		
		JButton pfpLabel = new JButton("");
		pfpLabel.setBounds(10, 10, 75, 75);
		pfpLabel.setBorder(BorderFactory.createLineBorder(currentTheme.getPrimaryColor()));
		commentPanel.add(pfpLabel);
		pfpLabel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserProfileGUI form = new UserProfileGUI(comment.getUser());
				form.setVisible(true);
			}
		});
		
		if(comment.getUser().getPfp() != null) {
			pfpLabel.setIcon(new StretchIcon(comment.getUser().getPfpAsImage(), false));
		} else {
			pfpLabel.setIcon(new StretchIcon("emptyPfp.jpg", false));
		}
		
		JLabel titleLabel = new JLabel(comment.getText());
		titleLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		titleLabel.setForeground(currentTheme.getTextColor());
		titleLabel.setBounds(95, 30, 288-95, 25);
		commentPanel.add(titleLabel);
		
		JLabel usernameLabel = new JLabel(comment.getUser().getUsername());
		usernameLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		usernameLabel.setForeground(currentTheme.getTextColor());
		usernameLabel.setBounds(95, 10, 288-95, 25);
		commentPanel.add(usernameLabel);
		
		return commentPanel;
		
	}
}
