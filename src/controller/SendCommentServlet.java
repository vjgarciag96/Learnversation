package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
import model.Comment;
import model.User;

@WebServlet(name = "SendCommentServlet", urlPatterns = "/exchange/SendCommentServlet", description = "Send Comment Servlet")
public class SendCommentServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public SendCommentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null) {			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/UserProfile.jsp");
			view.forward(request, response);
		} else {
			response.sendRedirect("LoginServlet");
			logger.info("Si el usuario no está registrado");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User user;

		if ((user = (User) session.getAttribute("user")) != null) {
			long userVisitedIdu = Long.valueOf(request.getParameter("userVisitedIdu"));
			String commentContent = request.getParameter("comment");

			logger.info("*****************************Enviando mensaje a :"+userVisitedIdu+"*********************"
					+"\n"+"------------------Mensaje: "+commentContent+"------------------"
					);
			
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(connection);
			
			Comment comment = new Comment(user.getIdu(), userVisitedIdu, commentContent);
			
			List<String> validationMessages = new ArrayList<String>();
			if(comment.validate(validationMessages)){
				commentDao.add(comment);
				response.sendRedirect("UserProfileServlet?userVisitedIdu="+userVisitedIdu+"&messageState=El mensaje se ha publicado correctamente");
			}
			else{
				String messageState = new String();
				for (int i = 0;i < validationMessages.size();i++){
					messageState+=validationMessages.get(i)+"\n";
				}
				response.sendRedirect("UserProfileServlet?userVisitedIdu="+userVisitedIdu+"&errorMessage="+messageState);
			}
			
		} else {
			response.sendRedirect("LoginServlet");
			logger.info("Si el usuario no está registrado");
		}
	}

}
