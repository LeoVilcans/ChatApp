package jtt.vikachaze.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.UserDAO;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.queries.UserQueries;


public class UserDAOImpl implements UserDAO, UserQueries{

	@Override
	public int insert(User value) throws SQLException {
		Connection connection = Database.getConnection();
	    PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
	    
	    List<User> users = getAllData();
	    
	    for (User user : users) {
	    	if (user.getUsername().equals(value.getUsername())) {
	    		return 0;
	    	}
	    }
	    
	    statement.setString(1, value.getUsername());
	    statement.setString(2, value.getPassword());

	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    Database.closeConnection(connection);
	    return result;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getID(User value) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getByID(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User getByUsername(String username) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}	
}
