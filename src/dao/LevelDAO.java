package dao;

import java.sql.Connection;
import java.util.List;

import model.Level;

public interface LevelDAO {
	/**
	 * Asocia la conexi�n a la base de datos con este DAO.
	 * 
	 * @param conn
	 *            Conexi�n a la base de datos.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * Metodo que permite obtener todos los niveles 
	 * de idiomas existentes en la BD
	 * @return
	 */
	public List<Level> getAll();	
	
	/**
	 * 
	 * @param idlv
	 * @return
	 */
	public Level get(long idlv);
}
