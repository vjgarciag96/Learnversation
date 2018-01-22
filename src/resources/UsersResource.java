package resources;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import exceptions.CustomBadRequestException;
import model.User;

@Path("/users")
public class UsersResource {

	@Context
	ServletContext servletContext;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers(@Context HttpServletRequest request) {
		List<User> users = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(connection);

		users = userDao.getAll();

		return users;
	}

	@GET
	@Path("/{userid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("userid") long userid, @Context HttpServletRequest request) {
		User user = null;
		
		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("user");
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(connection);

		user = userDao.get(userid);
		if(loggedUser.getIdu() != user.getIdu())
			user.setPassword("********");

		return user;
	}

	@GET
	@Path("/email")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserByEmail(@QueryParam("email") String email, @Context HttpServletRequest request) {
		User user = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(connection);

		user = userDao.getByEmail(email);
		user.setPassword("********");

		return user;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsersBySearch(@DefaultValue("") @QueryParam("country") String country,
			@DefaultValue("") @QueryParam("exchangeType1") String exchangeType1,
			@DefaultValue("") @QueryParam("exchangeType2") String exchangeType2,
			@DefaultValue("") @QueryParam("username") String username, @Context HttpServletRequest request) {
		List<User> searchResult = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(connection);

		searchResult = userDao.getUsers(country, exchangeType1, exchangeType2, username);

		return searchResult;
	}

	@GET
	@Path("/logged")
	@Produces(MediaType.APPLICATION_JSON)
	public User getLoggedUser(@Context HttpServletRequest request) {
		User user = null;

		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("user");

		if (loggedUser != null) {
			Connection connection = (Connection) servletContext.getAttribute("dbConn");
			UserDAO userDao = new JDBCUserDAOImpl();
			userDao.setConnection(connection);

			user = userDao.get(loggedUser.getIdu());
		}
		
		return user;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User newUser, @Context HttpServletRequest request) {

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(connection);

		Response response = null;
		
		if(userDao.get(newUser.getUsername()) == null){
			if(userDao.getByEmail(newUser.getEmail()) == null){
			List<String> validationMessages = new ArrayList<String>();
			if (!newUser.validateEdition(validationMessages)){
				String message = CustomBadRequestException.getValidationMessage(validationMessages);
				throw new CustomBadRequestException(message);
			}
			long idu = userDao.add(newUser);
	
			response = Response.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(idu)).build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(idu)).build()).build();
			}
			else
				throw new CustomBadRequestException("Ese correo electrónico ya está registrado");						
		}
		else
			throw new CustomBadRequestException("Ese nombre de usuario ya existe");
		
		return response;
	}

	@PUT
	@Path("/{userid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User updatedUser, @PathParam("userid") long userId, @Context HttpServletRequest request)
			throws SQLException, CustomBadRequestException, WebApplicationException {

		Response response = null;

		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("user");
		
		if (loggedUser.getIdu() == updatedUser.getIdu()) {
			
			Connection connection = (Connection) servletContext.getAttribute("dbConn");
			UserDAO userDao = new JDBCUserDAOImpl();
			userDao.setConnection(connection);
			
			if(userDao.get(updatedUser.getUsername()) == null || updatedUser.getUsername().equals(loggedUser.getUsername())){
				if(userDao.getByEmail(updatedUser.getEmail()) == null || updatedUser.getEmail().equals(loggedUser.getEmail())){
			List<String> validationMessages = new ArrayList<String>();
			if (updatedUser.validateEdition(validationMessages)) {
				if (userDao.get(userId) != null) {
					if (!userDao.save(updatedUser)) {
						throw new CustomBadRequestException("El email ya está registrado o el nombre de usuario ya está en uso");
					}
				} else
					throw new WebApplicationException(Response.Status.NOT_FOUND);

			} else {
				String message = CustomBadRequestException.getValidationMessage(validationMessages);
				throw new CustomBadRequestException(message);
			}
				}
				else
					throw new CustomBadRequestException("Este email ya está registrado con otra cuenta");
			}
			else
				throw new CustomBadRequestException("Nombre de usuario ya en uso en otra cuenta");
		} else {
			throw new CustomBadRequestException("Error con el identificador");
		}
		return response;
	}

	@DELETE
	@Path("/{userid: [0-9]+}")
	public Response deleteUser(@PathParam("userid") long userToDeleteId, @Context HttpServletRequest request)
			throws SQLException, CustomBadRequestException {

		Response response = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(connection);

		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("user");

		if (loggedUser.getIdu() == userToDeleteId) {
			if (userDao.get(userToDeleteId) != null) {
				if (!userDao.delete(userToDeleteId))
					throw new SQLException("Error eliminando el usuario de la base de datos");
				else
					response = Response.noContent().build();
			} else
				throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			throw new CustomBadRequestException("No tienes permisos suficientes para realizar esta acción");
		}

		return response;

	}

}
