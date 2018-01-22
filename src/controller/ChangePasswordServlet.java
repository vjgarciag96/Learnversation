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

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;
import util.PasswordEncryptation;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = "/exchange/ChangePasswordServlet", description = "Change Password Servlet")
public class ChangePasswordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public ChangePasswordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user != null) {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/ChangePassword.jsp");
			view.forward(request, response);
			logger.info("Si el usuario está registrado");
		} else {
			response.sendRedirect("LoginServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user != null) {
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			UserDAO userDao = new JDBCUserDAOImpl();
			userDao.setConnection(connection);
			User userFromDb = userDao.get(user.getIdu());
			String currentPassword = PasswordEncryptation.sha256(request.getParameter("currentPassword"));
			String newPassword = request.getParameter("newPassword");
			String repeatedNewPassword = request.getParameter("repeatedNewPassword");
			logger.info("currentPass: " + currentPassword + ", newPass:" + newPassword + ", " + "repeatedPass: "
					+ repeatedNewPassword + "\n La buena: " + userFromDb.toString());

			if (!currentPassword.equals(userFromDb.getPassword()) || !newPassword.equals(repeatedNewPassword)) {
				if (!currentPassword.equals(userFromDb.getPassword()))
					request.setAttribute("errorMessage", "La contraseña actual introducida no es correcta");
				else {
					if (!newPassword.equals(repeatedNewPassword))
						request.setAttribute("errorMessage", "La nueva contraseña no coincide");
				}
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/ChangePassword.jsp");
				view.forward(request, response);
				logger.info("Ha habido algun error cambiando la contraseña");

			} else {
				List<String> validationMessages = new ArrayList<String>();
					userFromDb.setPassword(newPassword);
				if (userFromDb.validate(validationMessages)) {
					userFromDb.setPassword(PasswordEncryptation.sha256(newPassword));

					if (userDao.save(userFromDb)) {
						logger.info("Cambiando contraseña...");
						response.sendRedirect(
								"UserProfileServlet?messageState=Se ha llevado a cabo el cambio de password de forma satisfactoria");
					} else {
						logger.info("Error en la actualización en la bd");
						request.setAttribute("errorMessage",
								"Ha habido algun error interno que no ha permitido llevar a cabo el cambio, por favor, intentelo de nuevo");
						RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/ChangePassword.jsp");
						view.forward(request, response);
					}
				}
				else{
					String errorMessage = new String();
					
					for (int i = 0; i < validationMessages.size(); i++) {
						errorMessage+=validationMessages.get(i)+"\n";
					}
					request.setAttribute("errorMessage", errorMessage);
					RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/ChangePassword.jsp");
					view.forward(request, response);
				}
			}
		} else
			response.sendRedirect("LoginServlet");

	}
}
