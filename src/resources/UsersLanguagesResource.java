package resources;

import java.sql.Connection;
import java.sql.SQLException;
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


import dao.JDBCUsers_LanguagesDAOImpl;
import dao.Users_LanguagesDAO;
import exceptions.CustomBadRequestException;
import model.User;
import model.Users_Languages;

@Path("/userslanguages")
public class UsersLanguagesResource {

	@Context
	ServletContext servletContext;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Users_Languages> getAllUsersLanguages(@Context HttpServletRequest request) {
		List<Users_Languages> usersLanguages = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		Users_LanguagesDAO usersLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
		usersLanguagesDao.setConnection(connection);

		usersLanguages = usersLanguagesDao.getAll();

		return usersLanguages;
	}

	@GET
	@Path("/params")
	@Produces(MediaType.APPLICATION_JSON)
	public Users_Languages getUserLanguage(@QueryParam("idu") long userId,@DefaultValue("-1") @QueryParam("idl") long languageId,
			@DefaultValue("-1") @QueryParam("speakingLevel") long speakingLevel, @DefaultValue("-1") @QueryParam("writingLevel") long writingLevel,
			@DefaultValue("-1") @QueryParam("listeningLevel") long listeningLevel, @DefaultValue("-1") @QueryParam("readingLevel") long readingLevel,
			@Context HttpServletRequest request) {
		Users_Languages userLanguage = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		Users_LanguagesDAO usersLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
		usersLanguagesDao.setConnection(connection);

		userLanguage = usersLanguagesDao.get(userId, languageId, speakingLevel, writingLevel, listeningLevel,
				readingLevel);
		
		return userLanguage;
	}

	@GET
	@Path("/{userid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Users_Languages> getUsersLanguagesByUser(@PathParam("userid") long userId,
			@Context HttpServletRequest request) {
		List<Users_Languages> usersLanguages = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		Users_LanguagesDAO usersLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
		usersLanguagesDao.setConnection(connection);

		usersLanguages = usersLanguagesDao.getAllByUser(userId);

		return usersLanguages;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUserLanguage(Users_Languages userLanguage, @Context HttpServletRequest request) {
		Response response = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		Users_LanguagesDAO usersLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
		usersLanguagesDao.setConnection(connection);


		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (user != null && user.getIdu() == userLanguage.getIdu()) {

		if (usersLanguagesDao.add(userLanguage)) {
			response = Response.created(uriInfo.getAbsolutePathBuilder()
					.path(Long.toString(userLanguage.getIdu()) + "/" + Long.toString(userLanguage.getIdl())).build())
					.contentLocation(uriInfo.getAbsolutePathBuilder()
							.path(Long.toString(userLanguage.getIdu()) + "/" + Long.toString(userLanguage.getIdl()))
							.build())
					.build();
		} else
			throw new CustomBadRequestException("Error añadiendo el idioma");
		}
		return response;
	}

	@PUT
	@Path("/{userid: [0-9]+}/{languageid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserLanguage(Users_Languages updatedUserLanguage, @PathParam("userid") long userId,
			@PathParam("languageid") long languageId, @Context HttpServletRequest request) throws SQLException {
		Response response = null;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null && user.getIdu() == updatedUserLanguage.getIdu()) {

			Connection connection = (Connection) servletContext.getAttribute("dbConn");
			Users_LanguagesDAO usersLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
			usersLanguagesDao.setConnection(connection);

			if (!usersLanguagesDao.save(updatedUserLanguage)) {
				throw new SQLException("Error actualizando los idiomas");
			}

		} else
			throw new CustomBadRequestException("No hay permisos suficientes para modificar el idioma.");
		return response;
	}

	@DELETE
	@Path("/{userid: [0-9]+}/{languageid: [0-9]+}")
	public Response deleteUserLanguage(@PathParam("userid") long userId, @PathParam("languageid") long languageId,
			@Context HttpServletRequest request) throws SQLException, CustomBadRequestException {

		Response response = null;
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		Users_LanguagesDAO usersLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
		usersLanguagesDao.setConnection(connection);

		Users_Languages userLanguageToDelete = usersLanguagesDao.getByUserIdAndLanguageId(userId, languageId);
		if (userLanguageToDelete != null) {
			HttpSession session = request.getSession();
			User loggedUser = (User) session.getAttribute("user");

			if (loggedUser.getIdu() == userLanguageToDelete.getIdu()) {
				if (!usersLanguagesDao.delete(userId, languageId))
					throw new SQLException("Error eliminando el languaje del usuario de la base de datos");
				else
					response = Response.noContent().build();
			} else {
				throw new CustomBadRequestException("No hay permisos suficientes para realizar esta operacion");
			}
		} else
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		
		return response;

	}

}
