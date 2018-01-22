package dao;

import java.sql.Connection;
import java.util.List;

import model.ProfileImage;

public interface ProfileImageDAO {
	
	/**
	 * Asocia la conexi�n a la base de datos con este DAO.
	 * 
	 * @param conn
	 *            Conexi�n a la base de datos.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * 
	 * @return
	 */
	public List<ProfileImage> getAll();
	
	/**
	 * 
	 * @param idi
	 * @return
	 */
	public ProfileImage get(long idi);
	
}
