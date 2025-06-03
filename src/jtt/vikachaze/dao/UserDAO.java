package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.User;

public interface UserDAO extends GenericDAO<User>{
	final String TABLE = "users";
	
	/**
	 * 
	 * Gets a database entry with equal username
	 * 
	 * @param String username of database object
	 * @return value <code>User</code> type object with given username
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	User getByUsername(String username) throws SQLException;
}
