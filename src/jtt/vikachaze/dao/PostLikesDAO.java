package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.PostLikes;

public interface PostLikesDAO extends GenericDAO<PostLikes> {
	List<PostLikes> getByPostID(Post postID) throws SQLException;
	List<PostLikes> getByText(String text) throws SQLException;
}
