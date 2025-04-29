package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.sql.Timestamp;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;

public interface PostDAO extends GenericDAO<Post>{
	final String TABLE = "posts";
	
	Post getPostByTitle(String title) throws SQLException;
	Post getPostByUser(User user) throws SQLException;
	Post getPostByText(String text) throws SQLException;
	Post getPostByTime(Timestamp time) throws SQLException;
}
