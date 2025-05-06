package jtt.vikachaze.dao;

import java.sql.SQLException;
import java.util.List;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Room;

public interface RoomDAO extends GenericDAO<Room>{
	final String TABLE = "rooms";
	
	List<Room> getByTitle(String title) throws SQLException;
}
