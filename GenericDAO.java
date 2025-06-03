package jtt.kamisovs.dao.base;

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
	int update(T value) throws SQLException;
	int delete(int id) throws SQLException;
	int getID(T value) throws SQLException;
	T getByID(int id) throws SQLException;
	List<T> getAllData() throws SQLException;
}
