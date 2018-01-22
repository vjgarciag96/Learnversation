package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

@WebServlet(name = "EditProfileServlet", urlPatterns = "/exchange/EditProfileServlet", description = "Edit Profile Servlet")
public class EditProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public EditProfileServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/EditProfile.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Login.jsp");
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");

		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(connection);

		HttpSession session = request.getSession();

		User user;

		if ((user = (User) session.getAttribute("user")) != null) {

			long userIdu = user.getIdu();
			String username = request.getParameter("username");
			String password = user.getPassword();
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			String birthdate = request.getParameter("birthDate");
			String country = request.getParameter("country");
			String exchangeTypes = new String();

			if (request.getParameterValues("exchangeTypes") != null) {
				ArrayList<String> arrayExchangeTypes = new ArrayList<String>(
						Arrays.asList(request.getParameterValues("exchangeTypes")));
				Iterator<String> it = arrayExchangeTypes.iterator();
				while (it.hasNext()) {
					if (exchangeTypes.length() > 0)
						exchangeTypes = exchangeTypes + ", " + it.next();
					else
						exchangeTypes = it.next();
				}
			} else
				exchangeTypes = "No tiene preferencias";

			logger.info("Aqui es de donde se saca del form: " + country);

			User updatedUser = new User(userIdu, username, password, email, gender, country, birthdate, exchangeTypes);
			List<String> validationMessages = new ArrayList<String>();
			
			if (updatedUser.validateEdition(validationMessages)) {
				if (userDao.save(updatedUser)) {
					//para que no se pierda la foto de perfil en la sesión
					updatedUser = userDao.get(updatedUser.getIdu());
					logger.info("Usuario actualizado correctamente");

					session.removeAttribute("user");
					session.setAttribute("user", updatedUser);

					response.sendRedirect("UserProfileServlet");

				} else {
					logger.info("Se ha producido algún error durante la actualización");
					response.sendRedirect("EditProfileServlet");
				}
			} else {
				String errorMessage = new String();

				for (int i = 0; i < validationMessages.size(); i++) {
					errorMessage += validationMessages.get(i) + "\n";
				}
				
				request.setAttribute("errorMessage", errorMessage);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/EditProfile.jsp");
				view.forward(request, response);				
			}
		} else {
			logger.info("usuario no registrado");
			response.sendRedirect("LoginServlet");
		}
	}
}
