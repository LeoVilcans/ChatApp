package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Comment;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;

public interface CommentDAO extends GenericDAO<Comment> {
List<Comment> getSinceIndex(int lastIndex) throws SQLException; 
List<Comment> getByUser(User user) throws SQLException;
List<Comment> getByPost(Post post) throws SQLException;
List<Comment> getByText(String text) throws SQLException;
}
