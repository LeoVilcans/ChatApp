package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;

public interface PostDAO extends GenericDAO<Post>{
	final String TABLE = "posts";
	
	List<Post> getPostByTitle(String title) throws SQLException;
	List<Post> getPostByUser(User user) throws SQLException;
	List<Post> getPostByText(String text) throws SQLException;
}
