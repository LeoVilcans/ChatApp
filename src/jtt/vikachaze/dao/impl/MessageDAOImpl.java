package jtt.vikachaze.dao.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.MessageDAO;
import jtt.vikachaze.dao.RoomDAO;
import jtt.vikachaze.dao.UserDAO;
import jtt.vikachaze.dto.Message;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.queries.MessageQueries;

public class MessageDAOImpl implements MessageDAO, MessageQueries{
	private RoomDAO roomDAO;
	private UserDAO userDAO;
	
	public MessageDAOImpl() {
		roomDAO = new RoomDAOImpl();
		userDAO = new UserDAOImpl();
	}
	
	@Override
	public int insert(Message message) throws SQLException {
		Connection connection = Database.getConnection();
	    PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
	    
	    //room_id, text, sent_time, attachment, user_id
	    statement.setInt(1, message.getRoom().getId());
	    statement.setString(2, message.getText());
	    //sent_time nevajag, jo tas automatiski tiek pievienots ar INSERT_QUERY kā pašreizējais laiks uz servera.
	    
	    if (message.getAttachment() != null) {
	    	statement.setBlob(3, message.getAttachment());
	    } else {
	    	statement.setNull(3, Types.BLOB);
	    }
	    
	    statement.setInt(4, message.getUser().getId());
	    
	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    Database.closeConnection(connection);
	    return result;
	}

	@Override
	public int update(Message value) throws SQLException {
	    return 0;
	}

	@Override
	public int delete(Message value) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getID(Message value) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Message> getSinceIndex(Room room, int index) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getByRoom(Room room) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getByUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getByTime(Timestamp time) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getByText(String text) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message getByID(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getAllData() throws SQLException {
		Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		ResultSet result = statement.executeQuery();
		
		List<Message> messages = new ArrayList<Message>();
		
		HashMap<Integer, User> messageUsers = new HashMap<Integer, User>();
		HashMap<Integer, Room> messageRooms = new HashMap<Integer, Room>();
		
		while (result.next()) {
			int id = result.getInt("id");
			int room_id = result.getInt("room_id");
			String text = result.getString("text");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			
			Blob attachment = result.getBlob("attachment");
			if (!result.wasNull()) {
				attachment = null;
			}
			
			User user;
			if (messageUsers.containsKey(user_id)) {
				user = messageUsers.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				messageUsers.put(user.getId(), user);
			}
			
			Room room;
			if (messageRooms.containsKey(room_id)) {
				room = messageRooms.get(room_id);
			} else {
				room = roomDAO.getByID(room_id);
				messageRooms.put(room.getId(), room);
			}
			
			Message message = new Message(room, text, sent_time, user);
			message.setId(id);
			message.setAttachment(attachment);
			
			messages.add(message);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return messages;
	}	
}
