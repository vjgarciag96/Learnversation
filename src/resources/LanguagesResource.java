package resources;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.JDBCLanguageDAOImpl;
import dao.LanguageDAO;
import model.Language;

@Path("/languages")
public class LanguagesResource {

	@Context
	ServletContext servletContext;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Language> getAllLanguages(@Context HttpServletRequest request){
		
		List<Language> languages = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		LanguageDAO languageDao = new JDBCLanguageDAOImpl();
		languageDao.setConnection(connection);
		
		languages = languageDao.getAll();
		
		return languages;
	}
	
	@GET
	@Path("/langname")
	@Produces(MediaType.APPLICATION_JSON)
	public Language getLanguageByName(@QueryParam("ln") String languageName){
		Language language = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		LanguageDAO languageDao = new JDBCLanguageDAOImpl();
		languageDao.setConnection(connection);
		
		language = languageDao.get(languageName);
		
		return language;
	}
	
	@GET
	@Path("/{languageid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Language getLanguageByIdl(@PathParam("languageid") long languageId, @Context HttpServletRequest request){
		
		Language language = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		LanguageDAO languageDao = new JDBCLanguageDAOImpl();
		languageDao.setConnection(connection);
		
		language = languageDao.get(languageId);
		
		return language;
	}
}
