package jtt.vikachaze.dao;

import java.sql.SQLException;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.PostLikes;

public interface PostLikesDAO extends GenericDAO<PostLikes> {
	PostLikes getByPostID(Post postID) throws SQLException;
	PostLikes getByText(String text) throws SQLException;
}
