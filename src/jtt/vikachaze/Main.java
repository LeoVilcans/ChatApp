package jtt.vikachaze;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;

import jtt.vikachaze.dao.MessageDAO;
import jtt.vikachaze.dao.impl.MessageDAOImpl;
import jtt.vikachaze.dto.Message;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.gui.LoginGUI;
import jtt.vikachaze.gui.MainMenuGUI;
import jtt.vikachaze.gui.RoomGUI;

public class Main {
	private static User currentUser = null;
	
	public static void main(String[] args) throws SQLException, IOException {
		
		JFrame loginGUI = new LoginGUI();
		loginGUI.setVisible(true);
	}
	
	public static void Login(User user) throws SQLException, IOException {
		currentUser = user;
		
		JFrame mainMenu;
		mainMenu = new MainMenuGUI();
		mainMenu.setVisible(true);
	}
	
	public static boolean isLoggedIn() {
		return !(currentUser == null);
	}
	
	public static User getLoggedUser() {
		return currentUser;
	}
}
