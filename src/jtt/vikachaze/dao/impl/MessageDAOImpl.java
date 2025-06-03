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
	private Connection batchConnection;
	
	public MessageDAOImpl() {
		roomDAO = new RoomDAOImpl();
		userDAO = new UserDAOImpl();
	}
	
	@Override
	public int insert(Message message) throws SQLException {
		Connection connection = findConnection();
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
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int update(Message message) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
	    
	    //room_id, text, sent_time, attachment, user_id
	    statement.setInt(1, message.getRoom().getId());
	    statement.setString(2, message.getText());
	    statement.setTimestamp(3, message.getSent_time());
	    
	    if (message.getAttachment() != null) {
	    	statement.setBlob(3, message.getAttachment());
	    } else {
	    	statement.setNull(3, Types.BLOB);
	    }
	    
	    statement.setInt(4, message.getUser().getId());
	    
	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int delete(Message message) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
	    
	    statement.setInt(1, message.getId());

	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int getID(Message message) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(GET_ID_QUERY);

	    statement.setInt(1, message.getRoom().getId());
	    statement.setString(2, message.getText());
	    statement.setTimestamp(3, message.getSent_time());
	    statement.setInt(4, message.getUser().getId());

	    ResultSet result = statement.executeQuery();

	    int id = 0;
	    if (result.next()) {
	        id = result.getInt("id");
	    }

	    Database.closeResultSet(result);
	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return id;
	}

	@Override
	public List<Message> getSinceIndex(Room room, int index) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_SINCE_INDEX_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, room.getId());
		statement.setInt(2, index);
		
		ResultSet result = statement.executeQuery();
		
		List<Message> messages = new ArrayList<Message>();
		
		HashMap<Integer, User> messageUsers = new HashMap<Integer, User>();
		
		userDAO.startBatchMode();
		while (result.next()) {
			int id = result.getInt("id");
			String text = result.getString("text");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			
			Blob attachment = result.getBlob("attachment");
			if (result.wasNull()) {
				attachment = null;
			}
			
			User user;
			if (messageUsers.containsKey(user_id)) {
				user = messageUsers.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				messageUsers.put(user.getId(), user);
			}
			
			Message message = new Message(room, text, sent_time, user);
			message.setId(id);
			message.setAttachment(attachment);
			
			messages.add(message);
		}
		userDAO.stopBatchMode();
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return messages;
	}

	@Override
	public List<Message> getByRoom(Room room) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_ROOM_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, room.getId());
		
		ResultSet result = statement.executeQuery();
		
		List<Message> messages = new ArrayList<Message>();
		
		HashMap<Integer, User> messageUsers = new HashMap<Integer, User>();
		
		userDAO.startBatchMode();
		while (result.next()) {
			int id = result.getInt("id");
			String text = result.getString("text");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			
			Blob attachment = result.getBlob("attachment");
			if (result.wasNull()) {
				attachment = null;
			}
			
			User user;
			if (messageUsers.containsKey(user_id)) {
				user = messageUsers.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				messageUsers.put(user.getId(), user);
			}
			
			Message message = new Message(room, text, sent_time, user);
			message.setId(id);
			message.setAttachment(attachment);
			
			messages.add(message);
		}
		userDAO.stopBatchMode();
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return messages;
	}

	@Override
	public List<Message> getByUser(User user) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_USER_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, user.getId());
		
		ResultSet result = statement.executeQuery();
		
		List<Message> messages = new ArrayList<Message>();
		
		HashMap<Integer, Room> messagesRoom = new HashMap<Integer, Room>();
		
		roomDAO.startBatchMode();
		while (result.next()) {
			int id = result.getInt("id");
			int room_id = result.getInt("room_id");
			String text = result.getString("text");
			Timestamp sent_time = result.getTimestamp("sent_time");
			
			Blob attachment = result.getBlob("attachment");
			if (!result.wasNull()) {
				attachment = null;
			}
			
			Room room;
			if (messagesRoom.containsKey(room_id)) {
				room = messagesRoom.get(room_id);
			} else {
				room = roomDAO.getByID(room_id);
				messagesRoom.put(room.getId(), room);
			}
			
			Message message = new Message(room, text, sent_time, user);
			message.setId(id);
			message.setAttachment(attachment);
			
			messages.add(message);
		}
		roomDAO.stopBatchMode();
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return messages;
	}

	@Override
	public List<Message> getByText(String text) throws SQLException {
		List<Message> allMessages = getAllData();
		
		List<Message> searchedMesages = new ArrayList<Message>();
		for (Message message : allMessages) {
			if (message.getText().toLowerCase().contains(text.toLowerCase())) {
				searchedMesages.add(message);
			}
		}
		
		return searchedMesages;
	}

	@Override
	public Message getByID(int id) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, id);
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		String text = result.getString("text");
		Timestamp sent_time = result.getTimestamp("sent_time");
		
		int room_id = result.getInt("room_id");
		Room room = roomDAO.getByID(room_id);
		
		int user_id = result.getInt("user_id");
		User user = userDAO.getByID(user_id);
		
		Blob attachment = result.getBlob("attachment");
		if (result.wasNull()) {
			attachment = null;
		}
		
		Message messages = new Message(room, text, sent_time, user);
		messages.setId(id);
		messages.setAttachment(attachment);
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return messages;
	}

	@Override
	public List<Message> getAllData() throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		ResultSet result = statement.executeQuery();
		
		List<Message> messages = new ArrayList<Message>();
		
		HashMap<Integer, User> messageUsers = new HashMap<Integer, User>();
		HashMap<Integer, Room> messageRooms = new HashMap<Integer, Room>();
		
		userDAO.startBatchMode();
		roomDAO.startBatchMode();
		while (result.next()) {
			int id = result.getInt("id");
			int room_id = result.getInt("room_id");
			String text = result.getString("text");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			
			Blob attachment = result.getBlob("attachment");
			if (result.wasNull()) {
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
		userDAO.stopBatchMode();
		roomDAO.stopBatchMode();
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return messages;
	}
	
	@Override
	public void startBatchMode() throws SQLException {
		batchConnection = Database.getConnection();
	}

	@Override
	public void stopBatchMode() throws SQLException {
		Database.closeConnection(batchConnection);
		batchConnection = null;
	}
	
	private Connection findConnection() throws SQLException {
		if (batchConnection != null) {
			return batchConnection;
		} else {
			return Database.getConnection();
		}
	} 
	
	private void closeConnection(Connection con) throws SQLException {
		if (batchConnection == null) {
			Database.closeConnection(con);
		}
	}
}
