package jtt.vikachaze.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.PostDAO;
import jtt.vikachaze.dao.PostLikesDAO;
import jtt.vikachaze.dao.UserDAO;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.PostLikes;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.queries.PostLikesQueries;

public class PostLikeDAOImpl implements PostLikesDAO, PostLikesQueries {
	private UserDAO userDAO;
	private PostDAO postDAO;
	
	
	public PostLikeDAOImpl(){	
		userDAO = new UserDAOImpl();
		postDAO = new PostDAOImpl();
	}

	@Override
	public int insert(PostLikes value) throws SQLException {
		Connection connection = Database.getConnection();
    PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
    
    
    statement.setInt(1,value.getUserID().getId());
    statement.setInt(2,value.getPostID().getId());
    
    int result = statement.executeUpdate();

    Database.closePreparedStatement(statement);
    Database.closeConnection(connection);
    return result;
	}

	@Override
	public int update(PostLikes value) throws SQLException {
		return 0;
	}

	@Override
	public int delete(PostLikes value) throws SQLException {
		Connection connection = Database.getConnection();
	    PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
	    
	    statement.setInt(1, value.getUserID().getId());
	    statement.setInt(2, value.getPostID().getId());

	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    Database.closeConnection(connection);
	    return result;
	}

	@Override
	public int getID(PostLikes value) throws SQLException {
		Connection connection = Database.getConnection();
	    PreparedStatement statement = connection.prepareStatement(GET_ID_QUERY);

	    
	    statement.setInt(1,value.getUserID().getId());
	    statement.setInt(2,value.getPostID().getId());

	    ResultSet result = statement.executeQuery();

	    int id = 0;
	    if (result.next()) {
	        id = result.getInt("id");
	    }

	    Database.closeResultSet(result);
	    Database.closePreparedStatement(statement);
	    Database.closeConnection(connection);
	    return id;
	}

	@Override
	public PostLikes getByID(int id) throws SQLException {
		Connection connection = Database.getConnection();
	    PreparedStatement statement = connection.prepareStatement(GET_ID_QUERY); 

	    statement.setInt(1, id);

	    ResultSet result = statement.executeQuery();

		
		int user_id = result.getInt("user_id");
		User user = userDAO.getByID(user_id);
		int post_id = result.getInt("post_id");
		Post post = postDAO.getByID(post_id);
		
		PostLikes postLikes = new PostLikes(user, post);
		postLikes.setId(id);

	    Database.closeResultSet(result);
	    Database.closePreparedStatement(statement);
	    Database.closeConnection(connection);
	    return postLikes;
	}

	@Override
	public List<PostLikes> getAllData() throws SQLException {
Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		ResultSet result = statement.executeQuery();
		
		List<PostLikes> postLikes = new ArrayList<PostLikes>();
		
		HashMap<Integer, User> postUsers = new HashMap<Integer, User>();
		HashMap<Integer, Post> postPost = new HashMap<Integer, Post>();
		
		while (result.next()) {
			int id = result.getInt("id");
			int user_id = result.getInt("user_id");
			int post_id = result.getInt("post_id");
			
			User user;
			if (postUsers.containsKey(user_id)) {
				user = postUsers.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				postUsers.put(user.getId(), user);
			}
			
			Post post;
			if (postPost.containsKey(post_id)) {
				post = postPost.get(post_id);
			} else {
				post = postDAO.getByID(post_id);
				postPost.put(user.getId(), post);
			}
			
			PostLikes postLike = new PostLikes(user,post);
			postLike.setId(id);
			postLikes.add(postLike);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return postLikes;
	}

	@Override
	public List<PostLikes> getByPostID(Post postID) throws SQLException {
Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_USER_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, postID.getId());
		
		ResultSet result = statement.executeQuery();
		
		List<PostLikes> postLikes = new ArrayList<PostLikes>();
		
		HashMap<Integer, User> postUsers = new HashMap<Integer, User>();
		
		while (result.next()) {
			int id = result.getInt("id");
			int user_id = result.getInt("user_id");
			
			User user;
			if (postUsers.containsKey(user_id)) {
				user = postUsers.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				postUsers.put(user.getId(), user);
			}
			
			
			PostLikes postLike = new PostLikes(user, postID);
			postLike.setId(id);
			
			postLikes.add(postLike);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return postLikes;
	}

	@Override
	public List<PostLikes> getByUserID(User userID) throws SQLException {
		Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_USER_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, userID.getId());
		
		ResultSet result = statement.executeQuery();
		
		List<PostLikes> postLikes = new ArrayList<PostLikes>();
		

		HashMap<Integer, Post> postPost = new HashMap<Integer, Post>();
		
		while (result.next()) {
			int id = result.getInt("id");
			int post_id = result.getInt("post_id");
			
			Post post;
			if (postPost.containsKey(post_id)) {
				post = postPost.get(post_id);
			} else {
				post = postDAO.getByID(post_id);
				postPost.put(post.getId(), post);
			}
			
			
			PostLikes postLike = new PostLikes(userID, post);
			postLike.setId(id);
			
			postLikes.add(postLike);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return postLikes;
	}

	@Override
	public PostLikes getOnPostByUser(Post post, User user) throws SQLException {
		Connection connection = Database.getConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_POST_AND_USER, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, user.getId());
		statement.setInt(2, post.getId());
		
		ResultSet result = statement.executeQuery();
		
		PostLikes postLike = null;
		if (result.next()) {
			postLike = new PostLikes(user, post);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		Database.closeConnection(connection);
		
		return postLike;
	}

}
