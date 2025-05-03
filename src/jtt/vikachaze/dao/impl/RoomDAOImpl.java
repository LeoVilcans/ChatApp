package jtt.vikachaze.dao.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.RoomDAO;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.queries.RoomQueries;

public class RoomDAOImpl implements RoomDAO, RoomQueries{

	@Override
	public int insert(Room value) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Room value) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Room value) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getID(Room value) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Room getByID(int id) throws SQLException {
		Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, id);
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		String title = result.getString("title");
		
		Blob icon = result.getBlob("icon");
		if (!result.wasNull()) {
			icon = null;
		}
		
		Room room = new Room(title);
		room.setId(id);
		room.setIcon(icon);
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return room;
	}

	@Override
	public List<Room> getAllData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room getByTitle(String title) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
