package jtt.vikachaze.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {
	private static String url = "jdbc:mysql://34.51.175.75:3306/vikachaze";
	private static String user = "anon"; 
	private static String password = "anon";
	private static String charset = "utf-8";
	
	public Database() {}
	
	public static Connection getConnection() throws SQLException {
		Connection conn;
		
		Properties properties = new Properties();
		properties.put("user", user);
		properties.put("password", password);
		properties.put("charset", charset);
		
		conn = DriverManager.getConnection(url, properties); // Nodrošina savienojumu
		
		return conn;
	}
	
	public static void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}
	
	public static void closeStatement(Statement statement) throws SQLException {
		statement.close();
	}
	
	public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.close();
	}
	
	public static void closeResultSet(ResultSet resultSet) throws SQLException {
		resultSet.close();
	}
}