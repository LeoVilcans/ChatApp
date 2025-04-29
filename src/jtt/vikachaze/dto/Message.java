package jtt.vikachaze.dto;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.imageio.ImageIO;

public class Message {
	private int id;
	private Room room;
	private String text;
	private Timestamp sent_time;
	private User user;
	private Blob attachment;
	
	public Message(Room room, String text, Timestamp sent_time, User user) {
		this.room = room;
		this.text = text;
		this.sent_time = sent_time;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getSent_time() {
		return sent_time;
	}

	public void setSent_time(Timestamp sent_time) {
		this.sent_time = sent_time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Blob getAttachment() {
		return attachment;
	}

	public void setAttachment(Blob attachment) {
		this.attachment = attachment;
	}
 
	public Image getAttachmentAsImage() throws SQLException, IOException {
		InputStream in = attachment.getBinaryStream();  
		BufferedImage image = ImageIO.read(in);
		return image;
	}
}
