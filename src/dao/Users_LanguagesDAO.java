package dao;

import java.sql.Connection;
import java.util.List;

import model.Users_Languages;



public interface Users_LanguagesDAO {

	/**
	 * Asocia la conexi�n a la base de datos con este DAO.
	 * 
	 * @param conn
	 *            Conexi�n a la base de datos.
	 */
	public void setConnection(Connection conn);
	
	public List<Users_Languages> getAll();

	/**
	 * Obtiene todos los usuarios-lenguajes-nivel en funci�n de un usuario dado.
	 * 
	 * @param idu
	 *            Identificador del usuario del que se quieren recuperar todos los usuarios-lenguajes-nivel
	 * 
	 * @return Lista con todos los usuarios-lenguajes-nivel en funci�n de un usuario dado.
	 */
	public List<Users_Languages> getAllByUser(long idu);
	
	/**
	 * 
	 * @param idu
	 * @param idl
	 * @param speakingLevel
	 * @param writingLevel
	 * @param listeningLevel
	 * @return
	 */
	public Users_Languages get(long idu, long idl, long speakingLevel, long writingLevel, long listeningLevel, long readingLevel);
	
	/**
	 * 
	 * @param idu
	 * @param idl
	 * @return
	 */
	public Users_Languages getByUserIdAndLanguageId(long idu, long idl);
	
	/**
	 * A�ade un usuario-lenguaje-nivel a la base de datos.
	 * 
	 * @param user_language
	 *            Objeto que contiene la informaci�n relativa al usuario-lenguaje-nivel que
	 *            se pretende a�adir.
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean add(Users_Languages user_language);

	/**
	 * Actualiza un usuario-lenguaje-nivel ya existente.
	 * 
	 * @param user_language
	 *            usuario-lenguaje-nivel que se pretende actualizar.
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean save(Users_Languages user_language);

	/**
	 * Elimina un usuario-lenguaje-nivel de la base de datos.
	 * 
	 *  @param idu
	 *            Identificador del usuario del que se quiere eliminar usuario-lenguaje-nivel
	 * 	 * 
	 * @param idl
	 *            Identificador del lenguaje del que se quiere eliminar usuario-lenguaje-nivel
	 * 
	 * 
	 * @return True si la operaci�n ha tenido �xito. False en caso contrario.
	 */
	public boolean delete(long idu, long idl);
}