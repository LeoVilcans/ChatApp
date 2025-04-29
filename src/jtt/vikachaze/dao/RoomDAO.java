package jtt.vikachaze.dao;

import java.sql.SQLException;

import jtt.vikachaze.dao.base.GenericDAO;
import jtt.vikachaze.dto.Room;

public interface RoomDAO extends GenericDAO<Room>{
	final String TABLE = "rooms";
	
	Room getByTitle(String title) throws SQLException;
}
