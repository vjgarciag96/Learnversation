package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.Comment;
import model.User;
import util.Pair;

/**
 * Servlet implementation class EditCommentServlet
 */
@WebServlet(name = "EditCommentServlet", urlPatterns = "/exchange/EditCommentServlet", description = "Edit Comment Servlet")
public class EditCommentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditCommentServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user;

		if ((user = (User) session.getAttribute("user")) != null) {

			long idc = Long.valueOf(request.getParameter("commentIdc"));

			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(connection);
			Comment commentToEdit = commentDao.get(idc);
			if (user.getIdu() == commentToEdit.getSender()) {

				UserDAO userDao = new JDBCUserDAOImpl();
				userDao.setConnection(connection);
				User receiver = userDao.get(commentToEdit.getReceiver());

				request.setAttribute("commentAndReceiver",
						new Pair<String, Comment>(receiver.getUsername(), commentToEdit));

				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/EditComment.jsp");
				view.forward(request, response);
			} else {
				response.sendRedirect("SimpleSearchServlet");
			}
		} else {
			response.sendRedirect("LoginServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null) {
			long userVisitedIdu = Long.valueOf(request.getParameter("userVisitedIdu"));
			long commentIdc = Long.valueOf(request.getParameter("commentIdc"));
			String commentText = request.getParameter("comment");
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(connection);
			Comment editedComment = commentDao.get(commentIdc);
			editedComment.setText(commentText);
			List<String> validationMessages = new ArrayList<String>();
			if (editedComment.validate(validationMessages)) {
				if (commentDao.save(editedComment)) {
					logger.info("******Comentario editado con éxito**********");
				} else {
					logger.warning("-----------Error editando el comentario--------------");
				}

				response.sendRedirect("UserProfileServlet?userVisitedIdu=" + userVisitedIdu
						+ "&messageState=El mensaje ha sido editado con exito");
			}
			else{
				UserDAO userDao = new JDBCUserDAOImpl();
				userDao.setConnection(connection);
				User receiver = userDao.get(editedComment.getReceiver());
				request.setAttribute("commentAndReceiver",
						new Pair<String, Comment>(receiver.getUsername(), editedComment));
				String errorMessage = new String();
				for (int i = 0;i<validationMessages.size();i++){
					errorMessage+=validationMessages.get(i)+"\n";
				}
				request.setAttribute("errorMessage", errorMessage);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/EditComment.jsp");
				view.forward(request, response);
			}
		} else {
			response.sendRedirect("LoginServlet");
		}
	}

}
