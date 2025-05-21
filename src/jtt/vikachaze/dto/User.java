package jtt.vikachaze.dto;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;

public class User {
	private int id;
	private String username;
	private String password;
	private String status;
	private Blob pfp;
	
	public User(String username) {
		this.username = username;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Blob getPfp() {
		return pfp;
	}

	public void setPfp(Blob pfp) {
		this.pfp = pfp;
	}
	
	public Image getPfpAsImage() throws SQLException, IOException {
		InputStream in = pfp.getBinaryStream();  
		BufferedImage image = ImageIO.read(in);
		return image;
	}
}
