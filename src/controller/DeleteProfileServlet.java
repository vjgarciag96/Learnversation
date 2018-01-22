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

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;
import util.PasswordEncryptation;

@WebServlet(name = "DeleteProfileServlet", urlPatterns = "/exchange/DeleteProfileServlet", description = "Delete Profile Servlet")
public class DeleteProfileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public DeleteProfileServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null) {
	
			request.setAttribute("checkType", "profile");

			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/CheckDelete.jsp");
			view.forward(request, response);
		} else {
			response.sendRedirect("LoginServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		User user = null;

		if ((user = (User) session.getAttribute("user")) != null) {
			String password = PasswordEncryptation.sha256(request.getParameter("password"));
			logger.info("Password: "+password+", userPass: "+user.getPassword());
			long idu = user.getIdu();
			logger.info("userId: " + idu);
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			UserDAO userDao = new JDBCUserDAOImpl();
			userDao.setConnection(connection);
			User userFromDb = userDao.get(idu);
			if(password.equals(userFromDb.getPassword())){
				userDao.delete(idu);
				session.invalidate();
				request.setAttribute("infoMessage", "Cuenta eliminada con éxito");
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Login.jsp");
				view.forward(request, response);
			}
			else{
				logger.warning("Contraseña incorrecta");
				request.setAttribute("checkType", "profile");
				request.setAttribute("errorMessage", "Contraseña incorrecta");
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/CheckDelete.jsp");
				view.forward(request, response);
			}
			
		}
	}
}
