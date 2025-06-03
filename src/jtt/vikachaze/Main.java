package jtt.vikachaze;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;

import jtt.vikachaze.dto.User;
import jtt.vikachaze.gui.LoginGUI;
import jtt.vikachaze.gui.MainMenuGUI;
import jtt.vikachaze.util.FileManager;
import jtt.vikachaze.util.Settings;
import jtt.vikachaze.dao.impl.UserDAOImpl;
import jtt.vikachaze.dao.UserDAO;

public class Main {
	private static User currentUser = null;
	
	public static void main(String[] args) throws SQLException, IOException {
		boolean result = loginFromFile();
		
		if (!result) {
			JFrame loginGUI = new LoginGUI();
			loginGUI.setVisible(true);
		}
	}
	
	public static void Login(User user) throws SQLException, IOException {
		currentUser = user;
		
		FileManager.writeToFile("login.data", user.getUsername());
		FileManager.writeToFile("login.data", user.getPassword());
		FileManager.writeToFile("login.data", Settings.getThemeChoice().toString());
		
		JFrame mainMenu;
		mainMenu = new MainMenuGUI();
		mainMenu.setVisible(true);
	}
	
	private static boolean loginFromFile() throws SQLException, IOException {
		List<String> data = FileManager.readFromFile("login.data");
		
		if (data != null) {
			FileManager.cleanFile("login.data");
			
			UserDAO userDAO = new UserDAOImpl();
			User newUser = userDAO.getByUsername(data.get(0));
			
			if (newUser.getPassword().equals(data.get(1))) {
				Settings.setTheme(Settings.ThemeChoice.valueOf(data.get(2)));
				
				Login(newUser);
				return true;
			}
		}
		return false;
	}
	
	public static void logout() {
		FileManager.cleanFile("login.data");
		currentUser = null;
	}
	
	public static boolean isLoggedIn() {
		return !(currentUser == null);
	}
	
	public static User getLoggedUser() {
		return currentUser;
	}
}
