package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.PostLikes;
import jtt.vikachaze.dto.User;

public interface PostLikesDAO extends GenericDAO<PostLikes> {
	/**
	 * 
	 * Gets a database entry with equal Post id
	 * 
	 * @param Post type object with id of database object
	 * @return value <code>PostLikes</code> type ArrayList object with given Post
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	List<PostLikes> getByPostID(Post post) throws SQLException;
	
	/**
	 * 
	 * Gets a database entry with equal User id
	 * 
	 * @param User type object with id of database object
	 * @return value <code>PostLikes</code> type ArrayList object with given User
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	List<PostLikes> getByUserID(User user) throws SQLException;
	
	/**
	 * 
	 * Gets a database entry with equal Post and User
	 * 
	 * @param Post type object
	 * @param User type object
	 * @return value <code>PostLikes</code> type object with given user and post
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	PostLikes getOnPostByUser(Post post, User user) throws SQLException;
}
