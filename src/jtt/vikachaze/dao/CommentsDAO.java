package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Comments;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;

public interface CommentsDAO extends GenericDAO<Comments> {
List<Comments> getBySentTime(Timestamp time) throws SQLException;
List<Comments> getByUser(User user) throws SQLException;
List<Comments> getByPostID(Post postID) throws SQLException;
List<Comments> getByText(String text) throws SQLException;
}
