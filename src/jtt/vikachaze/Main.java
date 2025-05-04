package jtt.vikachaze;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;

import jtt.vikachaze.dao.MessageDAO;
import jtt.vikachaze.dao.impl.MessageDAOImpl;
import jtt.vikachaze.dto.Message;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.gui.RoomGUI;

public class Main {
	public static void main(String[] args) throws SQLException, IOException {
		System.out.println("Hello World!");
		
		User u = new User("Leo", "123");
		u.setId(1);
		
		Room r = new Room("Ģenerālis");
		r.setId(1);
		
		JFrame roomGUI = new RoomGUI(u, r);
		roomGUI.setVisible(true);
		
		/*
		MessageDAO messageDAO = new MessageDAOImpl();
		
		for (Message m : messageDAO.getAllData()) {
			System.out.println("[" + m.getSent_time().toString() + "]" + m.getUser().getUsername() + ": " + m.getText());
		}
		*/
	}
}
