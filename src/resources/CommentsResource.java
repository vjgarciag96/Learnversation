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

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import exceptions.CustomBadRequestException;
import model.Comment;
import model.User;

@Path("/comments")
public class CommentsResource {

	@Context
	ServletContext servletContext;

	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAll(@Context HttpServletRequest request){
		List<Comment> comments = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(connection);
		
		comments = commentDao.getAll();
		
		return comments;
	}
	
	@GET
	@Path("/{commentid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment getCommentById(@PathParam("commentid") long commentId,
			@Context HttpServletRequest request) {

		Comment comment = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(connection);

		comment = commentDao.get(commentId);

		return comment;
	}
	
	@GET
	@Path("/receiver")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAllCommentsByReceiver(@QueryParam("id") long receiverId,
			@Context HttpServletRequest request) {

		List<Comment> comments = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(connection);

		comments = commentDao.getAllByReceiver(receiverId);

		return comments;
	}
	
	@GET
	@Path("/sender")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAllCommentsBySender(@QueryParam("id") long senderId,
			@Context HttpServletRequest request) {

		List<Comment> comments = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(connection);

		comments = commentDao.getAllBySender(senderId);

		return comments;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(Comment comment, @Context HttpServletRequest request) throws CustomBadRequestException {

		Response response = null;

		HttpSession session = request.getSession();
		User sender = (User) session.getAttribute("user");

		if (sender != null) {

			Connection connection = (Connection) servletContext.getAttribute("dbConn");
			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(connection);

			List<String> validationMessages = new ArrayList<String>();

			if (comment.validate(validationMessages)) {
				long commentId = commentDao.add(comment);

				response = Response.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(commentId)).build())
						.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(commentId)).build())
						.build();
			} else{
				String message = CustomBadRequestException.getValidationMessage(validationMessages);
				throw new CustomBadRequestException(message);
			}
		}

		return response;
	}

	@PUT
	@Path("/{commentid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editComment(Comment editedComment, @PathParam("commentid") long commentId,
			@Context HttpServletRequest request) throws SQLException, CustomBadRequestException, WebApplicationException {

		Response response = null;

		HttpSession session = request.getSession();
		User editor = (User) session.getAttribute("user");

		if (editor != null && editor.getIdu() == editedComment.getSender()) {

			Connection connection = (Connection) servletContext.getAttribute("dbConn");
			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(connection);

			List<String> validationMessages = new ArrayList<String>();

			if (commentDao.get(editedComment.getIdc()) != null) {
				if (editedComment.validate(validationMessages)) {
					if (!commentDao.save(editedComment))
						throw new SQLException("Error editando un comentario");
				}
				else{
					String message = CustomBadRequestException.getValidationMessage(validationMessages);
					throw new CustomBadRequestException(message);
				}
			}
			else
				throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		else
			throw new CustomBadRequestException("No hay permisos para editar este comentario");

		return response;

	}

	@DELETE
	@Path("/{commentid: [0-9]+}")
	public Response deleteComment(@PathParam("commentid") long commentToDeleteId, @Context HttpServletRequest request)
			throws SQLException, CustomBadRequestException {

		Response response = null;

		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(connection);
		
		Comment commentToDelete = commentDao.get(commentToDeleteId);
		
		if(commentToDelete != null){
			
			HttpSession session = request.getSession();
			User loggedUser = (User) session.getAttribute("user");

		if (loggedUser.getIdu() == commentToDelete.getSender()) {
				if (!commentDao.delete(commentToDeleteId))
					throw new SQLException("Error eliminando el comentario de la base de datos");
				else
					response = Response.noContent().build();
		} else {
			throw new CustomBadRequestException("Este usuario no tiene privilegios para realizar esa acción");
		}
		}
		else
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		return response;

	}
}
