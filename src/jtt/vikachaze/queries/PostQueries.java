package jtt.vikachaze.queries;

public interface PostQueries {
	public static final String TABLE = "posts";
	
    public static final String INSERT_QUERY = "INSERT INTO " + TABLE + " (sent_time, user_id, title, text) VALUES (?, ?, ?, ?)";
    
    public static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET sent_time = ?, user_id = ?, title = ?, text = ? WHERE id = ?";

    public static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    public static final String GET_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE sent_time = ? AND user_id = ? AND title = ? AND text = ?";
    public static final String GET_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE id = ?";
    public static final String GET_BY_SENTTIME_QUERY = "SELECT * FROM " + TABLE + " WHERE sent_time = ?";
    public static final String GET_BY_USER_QUERY = "SELECT * FROM " + TABLE + " WHERE user_id = ?";
    public static final String GET_BY_TITLE_QUERY = "SELECT * FROM " + TABLE + " WHERE title = ?";
    public static final String GET_BY_TEXT_QUERY = "SELECT * FROM " + TABLE + " WHERE text = ?";
    public static final String GET_ALL_QUERY = "SELECT * FROM " + TABLE;
}