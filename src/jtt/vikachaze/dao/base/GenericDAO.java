package jtt.vikachaze.dao.base;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
	/**
	 * 
	 * Inserts into a database values from generic <code>T</code> type object <code>value</code>
	 * 
	 * @param value Generic <code>T</code> type object that holds database table data
	 * @return Integer of Inserted values on database table. if value <code> = 0</code>,no data inserted in database
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	int insert(T value) throws SQLException;
	
	/**
	 * 
	 * Updates a database value from generic <code>T</code> type object <code>value</code>
	 * 
	 * @param value Generic <code>T</code> type object that holds database table data with id
	 * @return Integer of Updated  values on database table. if value <code> = 0</code>,no data updated in database
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	int update(T value) throws SQLException;
	
	/**
	 * 
	 * Deletes a database value from generic <code>T</code> type object
	 * 
	 * @param value Generic <code>T</code> type object that holds database table id
	 * @return Integer of deleted values on database table. if value <code> = 0</code>,no data deleted in database
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	int delete(T value) throws SQLException;
	
	/**
	 * 
	 * Gets a database id of generic <code>T</code> type object
	 * 
	 * @param value Generic <code>T</code> type object that holds database values
	 * @return Integer ID of given database object.
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	int getID(T value) throws SQLException;
	
	/**
	 * 
	 * Gets a database entry with equal ID
	 * 
	 * @param Integer ID of database object
	 * @return value Generic <code>T</code> type object of given ID
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	T getByID(int id) throws SQLException;
	
	/**
	 * 
	 * Gets all generic <code>T</code> type object database entries
	 * 
	 * @return Generic <code>T</code> type object ArrayList with all entries in database
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	List<T> getAllData() throws SQLException;
	
	/**
	 * 
	 * Starts a batch mode which doesn't create a new database connection for each query. <br>
	 * Basically, the DAO keeps re-using the same connection.
	 * <b>Used as an optimization feature. </b>
	 * 
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	void startBatchMode() throws SQLException;
	
	/**
	 * 
	 * Stops the batch mode and closes the batch connection.
	 * <b>Used as an optimization feature. </b>
	 * 
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	void stopBatchMode() throws SQLException; 
}