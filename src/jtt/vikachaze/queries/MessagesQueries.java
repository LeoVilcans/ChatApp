package jtt.vikachaze.queries;

public interface MessagesQueries {
public static final String TABLE = "messages";
	
	public static final String INSERT_QUERY = "INSERT INTO " + TABLE + " (room_id, text, sent_time, attachment, user_id) VALUES (?, ?, ?, ?, ?)";
	
    public static final String UPDATE_QUERY = "UPDATE " + TABLE + "SET room_id = ?, text = ?, sent_time = ?, attachment = ?, user_id = ?    WHERE id = ?";
    
    public static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    public static final String GET_BY_TEXT = "SELECT FROM " + TABLE + " WHERE text = ?";
    
    public static final String GET_BY_USER = "SELECT FROM " + TABLE + " WHERE user_id = ?";
    
    public static final String GET_BY_ROOM = "SELECT FROM " + TABLE + " WHERE room_id = ?";
    
    public static final String GET_BY_TIME = "SELECT FROM " + TABLE + " WHERE sent_time = ?";
    
    public static final String GET_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE id = ?";
    
    public static final String GET_ID_QUERY = "SELECT id FROM " + TABLE + " WHERE room_id = ? AND text = ? AND sent_time = ? AND attachment = ? AND user_id = ?";
    
    public static final String GET_ALL_QUERY = "SELECT * FROM " + TABLE;
}
