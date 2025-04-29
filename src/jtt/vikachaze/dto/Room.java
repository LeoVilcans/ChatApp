package jtt.vikachaze.dto;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;

public class Room {
private int id;
private String title;
private Blob icon;

	public Room(String title) {
	this.title = title;
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
	
	public Blob getIcon() {
		return icon;
	}
	
	public void setIcon(Blob icon) {
		this.icon = icon;
	}
	
	public Image getIconAsImage() throws SQLException, IOException {
		InputStream in = icon.getBinaryStream();  
		BufferedImage image = ImageIO.read(in);
		return image;
	}
}
