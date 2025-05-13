package jtt.vikachaze.dao.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.UserDAO;
import jtt.vikachaze.dto.Message;
import jtt.vikachaze.dto.Room;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.queries.UserQueries;


public class UserDAOImpl implements UserDAO, UserQueries{

	@Override
	public int insert(User value) throws SQLException {
		Connection connection = Database.getConnection();
	    PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
	    
	    List<User> users = getAllData();
	    
	    for (User user : users) {
	    	String newUserUsername = value.getUsername().toLowerCase();
	    	String checkUsername = user.getUsername().toLowerCase();
	    	if (newUserUsername.equals(checkUsername)) {
	    		return 0;
	    	}
	    }
	    
	    statement.setString(1, value.getUsername());
	    statement.setString(2, value.getPassword());
	    
	    if (value.getPfp() != null) {
	    	statement.setBlob(3, value.getPfp());
	    } else {
	    	statement.setNull(3, Types.BLOB);
	    }
	    
	    int result = statement.executeUpdate();

	    ResultSet rs = statement.getGeneratedKeys();
	    int insertedID = 0;
        if(rs.next())
        {
               insertedID = rs.getInt(1);
        }
	    
	    Database.closePreparedStatement(statement);
	    Database.closeConnection(connection);
	    return insertedID;
	}

	@Override
	public int update(User value) throws SQLException {
		Connection connection = Database.getConnection();
	    PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
	    
	    statement.setString(1, value.getUsername());
	    statement.setString(2, value.getPassword());
	    statement.setInt(3, value.getId());

	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    Database.closeConnection(connection);
	    return result;
	}

	@Override
	public int delete(User value) throws SQLException {
		Connection connection = Database.getConnection();
	    PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
	    
	    statement.setInt(1, value.getId());

	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    Database.closeConnection(connection);
	    return result;
	}

	@Override
	public int getID(User value) throws SQLException {
		Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setString(1, value.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		int id = result.getInt("id");
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return id;
	}

	@Override
	public User getByID(int id) throws SQLException {
		Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, id);
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		String username = result.getString("username");
		String password = result.getString("password");
		
		String status = result.getString("status");
		if (result.wasNull()) {
			status = null;
		}
		
		Blob pfp = result.getBlob("pfp");
		if (result.wasNull()) {
			pfp = null;
		}
		
		User user = new User(username, password);
		user.setId(id);
		user.setPfp(pfp);
		user.setStatus(status);
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return user;
	}
	
	@Override
	public User getByUsername(String username) throws SQLException {
		Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_USERNAME_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setString(1, username);
		
		ResultSet result = statement.executeQuery();
		
		if (!result.isBeforeFirst()) {
			return null;
		}
		
		result.next();
		
		String password = result.getString("password");
		int id = result.getInt("id");
		
		String status = result.getString("status");
		if (result.wasNull()) {
			status = null;
		}
		
		Blob pfp = result.getBlob("pfp");
		if (result.wasNull()) {
			pfp = null;
		}
		
		User user = new User(username, password);
		user.setId(id);
		user.setPfp(pfp);
		user.setStatus(status);
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return user;
	}

	@Override
	public List<User> getAllData() throws SQLException {
		Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet result = statement.executeQuery();
		
		List<User> users = new ArrayList<User>();
		while (result.next()) {
			String username = result.getString("username");
			String password = result.getString("password");
			int id = result.getInt("id");
			
			String status = result.getString("status");
			if (result.wasNull()) {
				status = null;
			}
			
			Blob pfp = result.getBlob("pfp");
			if (result.wasNull()) {
				pfp = null;
			}
			
			User user = new User(username, password);
			user.setId(id);
			user.setPfp(pfp);
			user.setStatus(status);
			users.add(user);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return users;
	}	
}
