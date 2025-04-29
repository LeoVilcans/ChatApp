package jtt.vikachaze.dto;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.imageio.ImageIO;

public class Post {
	private int id;
	private String title;
	private Timestamp sent_time;
	private Blob attachment;
	private User user;
	
	public Post(String title, Timestamp sent_time, User user) {
		this.title = title;
		this.sent_time = sent_time;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getSent_time() {
		return sent_time;
	}

	public void setSent_time(Timestamp sent_time) {
		this.sent_time = sent_time;
	}

	public Blob getAttachment() {
		return attachment;
	}

	public void setAttachment(Blob attachment) {
		this.attachment = attachment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Image getAttachmentAsImage() throws SQLException, IOException {
		InputStream in = attachment.getBinaryStream();  
		BufferedImage image = ImageIO.read(in);
		return image;
	}
}
