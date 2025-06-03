package jtt.vikachaze.util;

import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jtt.vikachaze.dto.Comment;
import jtt.vikachaze.dto.Theme;

public class CommentFactory {
	public static JPanel createCommentPanel(Comment comment) throws SQLException, IOException {
		Theme currentTheme = Settings.getTheme();
		
		JPanel commentPanel = new JPanel();
		commentPanel.setSize(350, 100);
		commentPanel.setLayout(null);
		commentPanel.setBorder(BorderFactory.createDashedBorder(currentTheme.getPrimaryColor()));
		commentPanel.setBackground(currentTheme.getBackgroundColor());
		
		JButton pfpLabel = new JButton("");
		pfpLabel.setBounds(10, 10, 75, 75);
		pfpLabel.setBorder(BorderFactory.createLineBorder(currentTheme.getPrimaryColor()));
		commentPanel.add(pfpLabel);
		
		if(comment.getUser().getPfp() != null) {
			pfpLabel.setIcon(new StretchIcon(comment.getUser().getPfpAsImage(), false));
		} else {
			pfpLabel.setIcon(new StretchIcon("emptyPfp.jpg", false));
		}
		
		JLabel titleLabel = new JLabel(comment.getText());
		titleLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		titleLabel.setForeground(currentTheme.getTextColor());
		titleLabel.setBounds(95, 10, 288-95, 25);
		commentPanel.add(titleLabel);
		
		return commentPanel;
		
	}
}
