package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.User;

public interface UserDAO extends GenericDAO<User>{
	final String TABLE = "users";
	
	List<User> getByUsername(String username) throws SQLException;
}
