package dao;

import java.sql.Connection;
import java.util.List;

import model.Comment;



public interface CommentDAO {

	/**
	 * Asocia la conexión a la base de datos con este DAO.
	 * 
	 * @param conn
	 *            Conexión a la base de datos.
	 */
	public void setConnection(Connection conn);

	/**
	 * Obtiene todos los comentarios de la base de datos.
	 * 
	 * @return Lista con todos los usuarios de la base de datos.
	 */
	public List<Comment> getAll();

	/**
	 * Obtiene todos los comentarios realizados por un usuario dado.
	 * 
	 * @param sender
	 *            Identificador del usuario del que se quiere recuperar los
	 *            comentarios realizados.
	 * 
	 * @return Lista con todos los comentarios enviados por el usuario.
	 */
	public List<Comment> getAllBySender(long sender);

	/**
	 * Obtiene todos los comentarios recibidos por un usuario dado.
	 * 
	 * @param receiver
	 *            Identificador del usuario del que se quiere recuperar los comentarios recibidos.
	 * 
	 * @return Lista con todos los comentarios recibidos por el usuario.
	 */
	public List<Comment> getAllByReceiver(long receiver);
	
	/**
	 * Obtiene todos los comentarios realizados por un usuario dado y recibidos por otro usuario dado.
	 * 
	 * @param sender
	 *            Identificador del usuario del que se quiere recuperar los comentarios realizados.
	 * 
	 * @param receiver
	 *            Identificador del usuario del que se quiere recuperar los comentarios recibidos.
	 * 
	 * @return Lista con todos los comentarios enviados por un usuario y recibidos por el otro usuario.
	 */
	public List<Comment> getAllBySenderReceiver(long sender,long receiver);

	/**
	 * Obtiene un comentario de la base de datos.
	 * 
	 * @param idc
	 *            Identificador del comentario.
	 * 
	 * @return Comentario con el identificador pasado.
	 */
	public Comment get(long idc);

	/**
	 * Obtiene todos los comentarios que contengan un texto dado.
	 * 
	 * @param search
	 *            Cadena de texto a buscar en los comentarios.
	 * 
	 * @return Lista con todos los comentarios que contienen una cadena de texto.
	 */	
	public List<Comment> getAllBySearch(String search);
	/**
	 * Añade un comentario a la base de datos.
	 * 
	 * @param comment
	 *            Objeto que contiene la información relativa al comentario que
	 *            se pretende añadir.
	 * 
	 * @return Identificador de comentario introducido o -1 si ha fallado la
	 *         operación.
	 *         
	 */
	public long add(Comment comment);

	/**
	 * Actualiza un comentario ya existente.
	 * 
	 * @param comment
	 *            Comentario que se pretende actualizar.
	 * 
	 * @return True si la operación ha tenido éxito. False en caso contrario.
	 */
	public boolean save(Comment comment);

	/**
	 * Elimina un comentario de la base de datos.
	 * 
	 * @param id
	 *            Identificador del comentario que se pretende eliminar.
	 * 
	 * @return True si la operación ha tenido éxito. False en caso contrario.
	 */
	public boolean delete(long id);
}