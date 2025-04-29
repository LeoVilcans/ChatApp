package jtt.vikachaze.queries;

public interface UserQueries {
	public static final String TABLE = "users";

    public static final String INSERT_QUERY = "INSERT INTO " + TABLE + " (username, password) VALUES (?, ?)";

    public static final String UPDATE_QUERY = "UPDATE " + TABLE + " SET username = ?, password = ? WHERE id = ?";

    public static final String DELETE_QUERY = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    public static final String GET_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE username = ?";
    public static final String GET_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE id = ?";
    public static final String GET_BY_USERNAME_QUERY = "SELECT * FROM " + TABLE + " WHERE username = ?";
    
    public static final String GET_ALL_QUERY = "SELECT * FROM " + TABLE;
}