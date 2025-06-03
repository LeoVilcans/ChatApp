package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Room;

public interface RoomDAO extends GenericDAO<Room>{
	final String TABLE = "rooms";
	
	/**
	 * 
	 * Gets a database entry with equal title
	 * 
	 * @param String title of database object
	 * @return Value <code>Room</code> type object with given title
	 * @throws SQLException problem with database connection or SQL script syntax error
	 */
	List<Room> getByTitle(String title) throws SQLException;
}
