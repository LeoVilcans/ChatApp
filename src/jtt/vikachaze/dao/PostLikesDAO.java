package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.PostLikes;
import jtt.vikachaze.dto.User;

public interface PostLikesDAO extends GenericDAO<PostLikes> {
	List<PostLikes> getByPostID(Post post) throws SQLException;
	List<PostLikes> getByUserID(User user) throws SQLException;
	PostLikes getOnPostByUser(Post post, User user) throws SQLException;
}
