package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.sql.Timestamp;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Comments;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;

public interface CommentsDAO extends GenericDAO<Comments> {
Comments getBySentTime(Timestamp time) throws SQLException;
Comments getByUser(User user) throws SQLException;
Comments getByPostID(Post postID) throws SQLException;
Comments getByText(String text) throws SQLException;
}
