package jtt.vikachaze.queries;

public interface PostLikesQueries {
	public static final String TABLE = "post_likes";

    public static final String INSERT_QUERY = "INSERT INTO " + TABLE + " (user_id, post_id) VALUES (?, ?)";

    public static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE user_id = ? AND post_id = ?";
    
    public static final String GET_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE user_id = ? AND post_id = ?";
    
    public static final String GET_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE id = ?";
    
    public static final String GET_BY_USER_QUERY = "SELECT * FROM " + TABLE + " WHERE user_id = ?";
    
    public static final String GET_BY_POST_QUERY = "SELECT * FROM " + TABLE + " WHERE post_id = ?";
    
    public static final String GET_ALL_QUERY = "SELECT * FROM " + TABLE;
}
