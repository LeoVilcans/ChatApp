package jtt.vikachaze.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.CommentDAO;
import jtt.vikachaze.dao.PostDAO;
import jtt.vikachaze.dao.UserDAO;
import jtt.vikachaze.dto.Comment;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.queries.CommentQueries;

public class CommentDAOImpl implements CommentDAO, CommentQueries{
	private PostDAO postDAO = new PostDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private Connection batchConnection;
	
	@Override
	public int insert(Comment comment) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
	    
	    //sent_time nevajag, jo tas automatiski tiek pievienots ar INSERT_QUERY kā pašreizējais laiks uz servera.
	    
	    statement.setInt(1, comment.getUser().getId());
	    statement.setInt(2, comment.getPost().getId());
	     
	    statement.setString(3, comment.getText());
	    
	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int update(Comment comment) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
	    
	    statement.setTimestamp(1, comment.getSent_time());
	    statement.setInt(2, comment.getUser().getId());
	    statement.setInt(3, comment.getPost().getId());
	     
	    statement.setString(4, comment.getText());
	    
	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int delete(Comment comment) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
	    
	    statement.setInt(1, comment.getId());

	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int getID(Comment comment) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(GET_ID_QUERY);

	    statement.setTimestamp(1, comment.getSent_time());
	    statement.setInt(2, comment.getUser().getId());
	    statement.setInt(3, comment.getPost().getId());
	    statement.setString(4, comment.getText());

	    ResultSet result = statement.executeQuery();

	    int id = 0;
	    if (result.next()) {
	        id = result.getInt("id");
	    }

	    Database.closeResultSet(result);
	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return id;
	}

	@Override
	public Comment getByID(int id) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, id);
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		Timestamp sent_time = result.getTimestamp("sent_time");
		String text = result.getString("text");
		
		int user_id = result.getInt("user_id");
		User user = userDAO.getByID(user_id);
		
		int post_id = result.getInt("post_id");
		Post post = postDAO.getByID(post_id);
		
		Comment comment = new Comment(sent_time, user, post, text);
		comment.setId(id);
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return comment;
	}
	
	@Override
	public List<Comment> getByUser(User user) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_USER_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, user.getId());
		
		ResultSet result = statement.executeQuery();
		
		List<Comment> comments = new ArrayList<Comment>();
		
		HashMap<Integer, Post> commentPost = new HashMap<Integer, Post>();
		
		while (result.next()) {
			int id = result.getInt("id");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int post_id = result.getInt("post_id");
			String text = result.getString("text");
			
			Post post;
			if (commentPost.containsKey(post_id)) {
				post = commentPost.get(post_id);
			} else {
				post = postDAO.getByID(post_id);
				commentPost.put(post.getId(), post);
			}
			
			Comment comment = new Comment(sent_time, user, post, text);
			comment.setId(id);
			
			comments.add(comment);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return comments;
	}

	@Override
	public List<Comment> getByPost(Post post) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_POST_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, post.getId());
		
		ResultSet result = statement.executeQuery();
		
		List<Comment> comments = new ArrayList<Comment>();
		
		HashMap<Integer, User> commentUser = new HashMap<Integer, User>();
		
		userDAO.startBatchMode();
		while (result.next()) {
			int id = result.getInt("id");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			String text = result.getString("text");
			
			User user;
			if (commentUser.containsKey(user_id)) {
				user = commentUser.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				commentUser.put(user.getId(), user);
			}
			
			Comment comment = new Comment(sent_time, user, post, text);
			comment.setId(id);
			
			comments.add(comment);
		}
		userDAO.stopBatchMode();
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return comments;
	}

	@Override
	public List<Comment> getByText(String text) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_TEXT_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setString(1, text);
		
		ResultSet result = statement.executeQuery();
		
		List<Comment> comments = new ArrayList<Comment>();
		
		HashMap<Integer, User> commentUser = new HashMap<Integer, User>();
		HashMap<Integer, Post> commentPost = new HashMap<Integer, Post>();
		
		userDAO.startBatchMode();
		postDAO.startBatchMode();
		while (result.next()) {
			int id = result.getInt("id");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			int post_id = result.getInt("post_id");
			
			User user;
			if (commentUser.containsKey(user_id)) {
				user = commentUser.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				commentUser.put(user.getId(), user);
			}
			
			Post post;
			if (commentPost.containsKey(post_id)) {
				post = commentPost.get(post_id);
			} else {
				post = postDAO.getByID(post_id);
				commentPost.put(post.getId(), post);
			}
			
			Comment comment = new Comment(sent_time, user, post, text);
			comment.setId(id);
			
			comments.add(comment);
		}
		userDAO.stopBatchMode();
		postDAO.stopBatchMode();
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return comments;
	}
	
	@Override
	public List<Comment> getAllData() throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		ResultSet result = statement.executeQuery();
		
		List<Comment> comments = new ArrayList<Comment>();
		
		HashMap<Integer, User> commentUser = new HashMap<Integer, User>();
		HashMap<Integer, Post> commentPost = new HashMap<Integer, Post>();
		
		userDAO.startBatchMode();
		postDAO.startBatchMode();
		while (result.next()) {
			int id = result.getInt("id");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			int post_id = result.getInt("post_id");
			String text = result.getString("text");
			
			User user;
			if (commentUser.containsKey(user_id)) {
				user = commentUser.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				commentUser.put(user.getId(), user);
			}
			
			Post post;
			if (commentPost.containsKey(post_id)) {
				post = commentPost.get(post_id);
			} else {
				post = postDAO.getByID(post_id);
				commentPost.put(post.getId(), post);
			}
			
			Comment comment = new Comment(sent_time, user, post, text);
			comment.setId(id);
			
			comments.add(comment);
		}
		userDAO.stopBatchMode();
		postDAO.stopBatchMode();
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return comments;
	}

	@Override
	public List<Comment> getSinceIndex(int lastIndex) throws SQLException {
		Connection connection = findConnection();
		PreparedStatement statement = connection.prepareStatement(GET_SINCE_INDEX_QUERY);
		
		statement.setInt(1, lastIndex);
		
		ResultSet result = statement.executeQuery();
		
		List<Comment> comments = new ArrayList<Comment>();

		HashMap<Integer, User> commentUser = new HashMap<Integer, User>();
		HashMap<Integer, Post> commentPost = new HashMap<Integer, Post>();

		userDAO.startBatchMode();
		postDAO.startBatchMode();
		while (result.next()) {
			int id = result.getInt("id");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			int post_id = result.getInt("post_id");
			String text = result.getString("text");
			
			User user;
			if (commentUser.containsKey(user_id)) {
				user = commentUser.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				commentUser.put(user.getId(), user);
			}
			
			Post post;
			if (commentPost.containsKey(post_id)) {
				post = commentPost.get(post_id);
			} else {
				post = postDAO.getByID(post_id);
				commentPost.put(post.getId(), post);
			}
			
			Comment comment = new Comment(sent_time, user, post, text);
			comment.setId(id);
			
			comments.add(comment);
		}
		userDAO.stopBatchMode();
		postDAO.stopBatchMode();
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return comments;
	}
	
	@Override
	public void startBatchMode() throws SQLException {
		batchConnection = Database.getConnection();
	}

	@Override
	public void stopBatchMode() throws SQLException {
		Database.closeConnection(batchConnection);
		batchConnection = null;
	}
	
	private Connection findConnection() throws SQLException {
		if (batchConnection != null) {
			return batchConnection;
		} else {
			return Database.getConnection();
		}
	}
	
	private void closeConnection(Connection con) throws SQLException {
		if (batchConnection == null) { 
			Database.closeConnection(con);
		}
	}
}