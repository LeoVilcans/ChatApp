package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;

public interface PostDAO extends GenericDAO<Post>{
	final String TABLE = "posts";
	
	/**
	 * Gets a database entry with equal title
	 * 
	 * @param String title of database object
	 * @return <code>Post</code> type ArrayList object with given title
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	List<Post> getPostByTitle(String title) throws SQLException;
	
	/**
	 * Gets a database entry with equal User
	 * 
	 * @param String title of database object
	 * @return <code>Post</code> type ArrayList object with given title
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	List<Post> getPostByUser(User user) throws SQLException;
	
	/**
	 * Gets a database entry with equal text
	 * 
	 * @param String text of database object
	 * @return <code>Room</code> type ArrayList object with given text
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	List<Post> getPostByText(String text) throws SQLException;
	
	/**
	 * Gets a database entry with an ID bigger than the given value
	 * 
	 * @param Integer ID to return database entries with bigger ID 
	 * @return <code>Post</code> type ArrayList object with bigger IDs
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	List<Post> getSinceIndex(int lastIndex) throws SQLException;
	
	/**
	 * Gets a database entry with an ID bigger than the given value, that belong to given User
	 * 
	 * @param Integer ID to return database entries with bigger ID 
	 * @param User type object to return database entries with equal user
	 * @return <code>Post</code> type ArrayList object
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	List<Post> getSinceIndexForUser(User user, int lastIndex) throws SQLException; 
}
