package jtt.zeltins.dao.base;

import java.sql.SQLException;
import java.util.List;

import jtt.zeltins.bto.Kurss;

public interface GenericDAO<T> {
/**
 * Insert into a database values from generic <code>T</code> type object <code>value</code>
 * 
 * @param value Generic <code>T</code> type object that holds database table data
 * @return Integer of inserted values in database table. If return <code>0</code>, no data inserted to database
 * @throws SQLException problem with database connection or SQL script syntax error.
 */
int insert(T value)throws SQLException;
int update(T value)throws SQLException;
int delete(T value)throws SQLException;
int getID(T value)throws SQLException;
T getByID(int id)throws SQLException;
List<T> getAllData()throws SQLException;
}