package jtt.vikachaze.queries;

public interface CommentQueries {
	public static final String TABLE = "comments";

    public static final String INSERT_QUERY = "INSERT INTO " + TABLE + " (sent_time, user_id, post_id, text) VALUES (CURRENT_TIMESTAMP, ?, ?, ?)";
    
    public static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET sent_time = ?, user_id = ?, post_id = ?, text = ? WHERE id = ?";

    public static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    public static final String GET_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE sent_time = ?, AND user_id = ? AND post_id = ? AND text = ?";
    
    public static final String GET_SINCE_INDEX_QUERY = "SELECT * FROM " + TABLE + " WHERE id > ? AND post_id = ?";
    
    public static final String GET_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE id = ?";
    
    public static final String GET_BY_USER_QUERY = "SELECT * FROM " + TABLE + " WHERE user_id = ?";
    
    public static final String GET_BY_POST_QUERY = "SELECT * FROM " + TABLE + " WHERE post_id = ?";
    
    public static final String GET_BY_TEXT_QUERY = "SELECT * FROM " + TABLE + " WHERE text = ?";
    
    public static final String GET_ALL_QUERY = "SELECT * FROM " + TABLE;
}
