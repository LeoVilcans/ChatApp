package jtt.vikachaze.dao.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.conf.ConnectionUrl.Type;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.RoomDAO;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.queries.RoomQueries;

public class RoomDAOImpl implements RoomDAO, RoomQueries{
	private Connection batchConnection;
	
	@Override
	public int insert(Room value) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
	    
	    List<Room> rooms = getAllData();
	    
	    for (Room room : rooms) {
	    	if (room.getTitle().equals(value.getTitle())) {
	    		return 0;
	    	}
	    }
	     
	    statement.setString(1, value.getTitle());
	    
	    if (value.getIcon() != null) {
	    statement.setBlob(2, value.getIcon());
	    }else {
	    	statement.setNull(2, Types.BLOB);
	    }
	    

	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int update(Room value) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
	    
	    statement.setString(1, value.getTitle());
	    statement.setBlob(2, value.getIcon());
	    statement.setInt(3, value.getId());


	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int delete(Room value) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

	    statement.setInt(1, value.getId());

	    int result = statement.executeUpdate();
	    
	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int getID(Room value) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(GET_ID_QUERY);

	    statement.setString(1, value.getTitle());
	    
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
	public Room getByID(int id) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, id);
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		String title = result.getString("title");
		
		Blob icon = result.getBlob("icon");
		if (result.wasNull()) {
			icon = null;
		}
		
		Room room = new Room(title);
		room.setId(id);
		room.setIcon(icon);
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return room;
	}

	@Override
	public List<Room> getAllData() throws SQLException {
Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	
		ResultSet result = statement.executeQuery();
		
		List<Room> rooms = new ArrayList<Room>();
		while (result.next()) {
			String title = result.getString("title");
			
			
			Blob icon = result.getBlob("icon");
			if (result.wasNull()) {
				icon = null;
			}
			
			Room room = new Room(title);
			room.setIcon(icon);

			
			rooms.add(room);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return rooms;
	}

	@Override
	public List<Room> getByTitle(String title) throws SQLException {
Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_TITLE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setString(1, title);
		
		ResultSet result = statement.executeQuery();
		
		List<Room> rooms = new ArrayList<Room>();
		while (result.next()) {
			
			
			
			Blob icon = result.getBlob("icon");
			if (result.wasNull()) {
				icon = null;
			}
			
			Room room = new Room(title);
			room.setIcon(icon);

			
			rooms.add(room);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return rooms;
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
