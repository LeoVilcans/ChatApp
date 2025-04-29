package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.sql.Timestamp;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Message;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.User;

public interface MessageDAO extends GenericDAO<Message>{
	final String TABLE = "messages";
	
	Message getByRoom(Room room) throws SQLException;
	Message getByUser(User user) throws SQLException;
	Message getByTime(Timestamp time) throws SQLException; 
	Message getByText(String text) throws SQLException;
}
