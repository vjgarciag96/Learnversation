package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.Comment;
import model.User;
import util.Pair;

@WebServlet(name = "DeleteCommentServlet", urlPatterns = "/exchange/DeleteCommentServlet", description = "Delete Comment Servlet")
public class DeleteCommentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public DeleteCommentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null) {

			Connection connection = (Connection) getServletContext().getAttribute("dbConn");

			long idc = Long.valueOf(request.getParameter("commentIdc"));
			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(connection);

			Comment commentToDelete = null;
			
			if ((commentToDelete = commentDao.get(idc)) != null) {
				
				User receiver = null;
				
				UserDAO userDao = new JDBCUserDAOImpl();
				userDao.setConnection(connection);
				
				receiver = userDao.get(commentToDelete.getReceiver());
				
				request.setAttribute("checkType", "comment");
				request.setAttribute("commentAndReceiver", new Pair<String, Comment>(receiver.getUsername(), commentToDelete));
				
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/CheckDelete.jsp");
				view.forward(request, response);
			} else {
				response.sendRedirect("LoginServlet");
			}
		} else {
			response.sendRedirect("LoginServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null) {
			
			long idc = Long.valueOf(request.getParameter("commentIdc"));
			long receiverIdu = Long.valueOf(request.getParameter("receiverIdu"));
			
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(connection);
			commentDao.delete(idc);
			logger.info("Comentario eliminado con éxito");
			response.sendRedirect("UserProfileServlet?userVisitedIdu="+receiverIdu+"&messageState=El mensaje se ha eliminado con exito");
		}
		else
		response.sendRedirect("LoginServlet");
	}
}
