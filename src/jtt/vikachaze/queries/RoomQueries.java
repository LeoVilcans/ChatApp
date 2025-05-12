package jtt.vikachaze.queries;

public interface RoomQueries {
	public static final String TABLE = "rooms";
	
	public static final String INSERT_QUERY = "INSERT INTO " + TABLE + " (title, icon) VALUES (?, ?)";
	
    public static final String UPDATE_QUERY = "UPDATE " + TABLE + " title = ?, icon = ? WHERE id = ?";
    
    public static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    public static final String GET_BY_TITLE = "SELECT FROM " + TABLE + " WHERE title = ?";
    
    public static final String GET_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE id = ?";
    
    public static final String GET_ID_QUERY = "SELECT id FROM " + TABLE + " WHERE title = ?";
    
    public static final String GET_ALL_QUERY = "SELECT * FROM " + TABLE;
}