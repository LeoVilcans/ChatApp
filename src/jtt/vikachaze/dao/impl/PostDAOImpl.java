package jtt.vikachaze.dao.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jtt.vikachaze.connection.Database;
import jtt.vikachaze.dao.PostDAO;
import jtt.vikachaze.dao.UserDAO;
import jtt.vikachaze.dto.Post;
import jtt.vikachaze.dto.User;
import jtt.vikachaze.queries.PostQueries;

public class PostDAOImpl implements PostDAO, PostQueries{
	private UserDAO userDAO;
	private Connection batchConnection;
	
	public PostDAOImpl() {
		userDAO = new UserDAOImpl();
	}

	@Override
	public List<Post> getAllData() throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		ResultSet result = statement.executeQuery();
		
		List<Post> posts = new ArrayList<Post>();
		
		HashMap<Integer, User> postUsers = new HashMap<Integer, User>();
		
		userDAO.startBatchMode();
		while (result.next()) {
			int id = result.getInt("id");
			String text = result.getString("text");
			String title = result.getString("title");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			
			Blob attachment = result.getBlob("attachment");
			if (result.wasNull()) {
				attachment = null;
			}
			
			User user;
			if (postUsers.containsKey(user_id)) {
				user = postUsers.get(user_id);
			} else {
				user = userDAO.getByID(user_id);
				postUsers.put(user.getId(), user);
			}
			
			Post post = new Post(sent_time, user, title, text);
			post.setId(id);
			post.setAttachment(attachment);
			
			posts.add(post);
		}
		userDAO.stopBatchMode();
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return posts;
	}

	@Override
	public int insert(Post value) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
	    
	    // user_id, title, text
	    statement.setInt(1,value.getUser().getId());
	    statement.setString(2, value.getTitle());
	    statement.setString(3, value.getText());
	    //sent_time nevajag, jo tas automatiski tiek pievienots ar INSERT_QUERY kā pašreizējais laiks uz servera.
	    
	    if (value.getAttachment() != null) {
	    	statement.setBlob(4, value.getAttachment());
	    } else {
	    	statement.setNull(4, Types.BLOB);
	    }
	    
	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int update(Post value) throws SQLException {
		return 0;
	}

	@Override
	public int delete(Post value) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
	    
	    statement.setInt(1, value.getId());

	    int result = statement.executeUpdate();

	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return result;
	}

	@Override
	public int getID(Post value) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(GET_ID_QUERY);

	    //sent_time = ? AND user_id = ? AND title = ? AND text = ?
	    statement.setTimestamp(1, value.getSent_time());
	    statement.setInt(2, value.getUser().getId());
	    statement.setString(3, value.getTitle());
	    statement.setString(4, value.getText());

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
	public List<Post> getPostByTitle(String title) throws SQLException {
		List<Post> allPosts = getAllData();
		
		List<Post> searchedPosts = new ArrayList<Post>();
		for (Post post : allPosts) {
			if (post.getTitle().toLowerCase().contains(title.toLowerCase())) {
				searchedPosts.add(post);
			}
		}
		
		return searchedPosts;
	}

	@Override
	public List<Post> getPostByUser(User user) throws SQLException {
		Connection connection = findConnection();
		
		PreparedStatement statement = connection.prepareStatement(GET_BY_USER_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.setInt(1, user.getId());
		
		ResultSet result = statement.executeQuery();
		
		List<Post> posts = new ArrayList<Post>();
		
		while (result.next()) {
			int id = result.getInt("id");
			String text = result.getString("text");
			String title = result.getString("title");
			Timestamp sent_time = result.getTimestamp("sent_time");
			
			Blob attachment = result.getBlob("attachment");
			if (result.wasNull()) {
				attachment = null;
			}
			
			Post post = new Post(sent_time, user, title, text);
			post.setId(id);
			post.setAttachment(attachment);
			
			posts.add(post);
		}
		
		Database.closeResultSet(result);
		Database.closePreparedStatement(statement);
		closeConnection(connection);
		
		return posts;
	}

	@Override
	public List<Post> getPostByText(String text) throws SQLException {
		List<Post> allPosts = getAllData();
		
		List<Post> searchedPosts = new ArrayList<Post>();
		for (Post post : allPosts) {
			if (post.getText().toLowerCase().contains(text.toLowerCase())) {
				searchedPosts.add(post);
			}
		}
		
		return searchedPosts;
	}

	@Override
	public Post getByID(int id) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(GET_ID_QUERY); 

	    statement.setInt(1, id);

	    ResultSet result = statement.executeQuery();

	    //int id = result.getInt("id");
		String text = result.getString("text");
		String title = result.getString("title");
		Timestamp sent_time = result.getTimestamp("sent_time");
		int user_id = result.getInt("user_id");
		User user = userDAO.getByID(user_id);
		
		Blob attachment = result.getBlob("attachment");
		if (result.wasNull()) {
			attachment = null;
		}
		
		Post post = new Post(sent_time, user, title, text);
		post.setId(id);
		post.setAttachment(attachment);

	    Database.closeResultSet(result);
	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return post;
	}

	@Override
	public List<Post> getSinceIndex(int lastIndex) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(GET_SINCE_INDEX_QUERY); 

	    statement.setInt(1, lastIndex);

	    ResultSet result = statement.executeQuery();
	    
	    userDAO.startBatchMode();
	    List<Post> posts = new ArrayList<Post>();
	    while (result.next()) {
		    int id = result.getInt("id");
			String text = result.getString("text");
			String title = result.getString("title");
			Timestamp sent_time = result.getTimestamp("sent_time");
			int user_id = result.getInt("user_id");
			User user = userDAO.getByID(user_id);
			 
			Blob attachment = result.getBlob("attachment");
			if (result.wasNull()) {
				attachment = null;
			}
			
			Post post = new Post(sent_time, user, title, text);
			post.setId(id);
			post.setAttachment(attachment);
			
			posts.add(post);
	    }
	    userDAO.stopBatchMode();
	    
	    Database.closeResultSet(result);
	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return posts;
	}	
	
	@Override
	public List<Post> getSinceIndexForUser(User user, int lastIndex) throws SQLException {
		Connection connection = findConnection();
	    PreparedStatement statement = connection.prepareStatement(GET_SINCE_INDEX_FOR_USER_QUERY); 

	    statement.setInt(1, user.getId());
	    statement.setInt(2, lastIndex);

	    ResultSet result = statement.executeQuery();
	    
	    List<Post> posts = new ArrayList<Post>();
	    while (result.next()) {
		    int id = result.getInt("id");
			String text = result.getString("text");
			String title = result.getString("title");
			Timestamp sent_time = result.getTimestamp("sent_time");
			 
			Blob attachment = result.getBlob("attachment");
			if (result.wasNull()) {
				attachment = null;
			}
			
			Post post = new Post(sent_time, user, title, text);
			post.setId(id);
			post.setAttachment(attachment);
			
			posts.add(post);
	    }
	    
	    Database.closeResultSet(result);
	    Database.closePreparedStatement(statement);
	    closeConnection(connection);
	    return posts;
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
