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

import dao.JDBCProfileImageDAOImpl;
import dao.ProfileImageDAO;
import model.ProfileImage;

@Path("/profileimage")
public class ProfileImageResource {

	@Context
	ServletContext servletContext;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProfileImage> getAllProfileImages(@Context HttpServletRequest request){
		List<ProfileImage> profileImages = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		ProfileImageDAO profileImageDao = new JDBCProfileImageDAOImpl();
		profileImageDao.setConnection(connection);
		
		profileImages = profileImageDao.getAll();
		
		return profileImages;
	}
	
	@GET
	@Path("/{imageid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProfileImage getProfileImage(@PathParam("imageid") long imageId, @Context HttpServletRequest  request){
		
		ProfileImage profileImage = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		ProfileImageDAO profileImageDao = new JDBCProfileImageDAOImpl();
		profileImageDao.setConnection(connection);
		
		profileImage = profileImageDao.get(imageId);
		
		return profileImage;
	}
		
}
