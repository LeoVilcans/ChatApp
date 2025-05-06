package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Message;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.User;

public interface MessageDAO extends GenericDAO<Message>{
	final String TABLE = "messages";
	
	List<Message> getSinceIndex(Room room, int index) throws SQLException;
	List<Message> getByRoom(Room room) throws SQLException;
	List<Message> getByUser(User user) throws SQLException;
	List<Message> getByText(String text) throws SQLException;
}
