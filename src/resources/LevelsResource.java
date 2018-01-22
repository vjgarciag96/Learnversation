package resources;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.JDBCLevelDAOImpl;
import dao.LevelDAO;
import model.Level;

@Path("/levels")
public class LevelsResource {

	@Context
	ServletContext servletContext;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Level> getAllLevels(@Context HttpServletRequest request){
		
		List<Level> levels = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		LevelDAO levelDao = new JDBCLevelDAOImpl();
		levelDao.setConnection(connection);
		
		levels = levelDao.getAll();
		
		return levels;
	}
	
	@GET
	@Path("/{levelid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Level getLevel(@PathParam("levelid") long levelid, @Context HttpServletRequest request){
		
		Level level = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		LevelDAO levelDao = new JDBCLevelDAOImpl();
		levelDao.setConnection(connection);
		
		level = levelDao.get(levelid);
		
		return level;
	}
}
