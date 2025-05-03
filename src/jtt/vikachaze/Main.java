package jtt.vikachaze;

import java.sql.SQLException;

import jtt.vikachaze.dao.MessageDAO;
import jtt.vikachaze.dao.impl.MessageDAOImpl;
import jtt.vikachaze.dto.Message;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.User;

public class Main {
	public static void main(String[] args) throws SQLException {
		System.out.println("Hello World!");
		
		/*
		MessageDAO messageDAO = new MessageDAOImpl();
		
		for (Message m : messageDAO.getAllData()) {
			System.out.println("[" + m.getSent_time().toString() + "]" + m.getUser().getUsername() + ": " + m.getText());
		}
		*/
	}
}
