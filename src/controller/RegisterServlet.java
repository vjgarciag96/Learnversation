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
import util.PasswordEncryptation;

@WebServlet(name = "RegisterServlet", urlPatterns = "/RegisterServlet", description = "RegisterServlet")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public RegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null) {

			response.sendRedirect("/WEB-INF/SimpleSearch.jsp");
		} else {

			//RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Register.jsp");
			//view.forward(request, response);
			response.sendRedirect("pages/registerIndex.html");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		request.setCharacterEncoding("UTF-8");

		String password = request.getParameter("password");
		String repeatedPassword = request.getParameter("repeatedPassword");		
		String username = request.getParameter("username");
		String birthDate = request.getParameter("birthDate");
		String country = request.getParameter("country");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");

		String exchangeTypes = new String();

		if (request.getParameterValues("exchangeTypes") != null) {
			ArrayList<String> arrayExchangeTypes = new ArrayList<String>(
					Arrays.asList(request.getParameterValues("exchangeTypes")));
			Iterator<String> it = arrayExchangeTypes.iterator();
			while (it.hasNext()) {
				exchangeTypes = exchangeTypes + "\n" + it.next();
			}
		} else
			exchangeTypes = "No tiene preferencias";
				
		if (password.equals(repeatedPassword)) {
			
			logger.info("username: " + username + "\n" + "gender: " + gender + "\n" + "email: " + email + "\n"
					+ "tipo de intercambio:" + exchangeTypes + "\n");
			User user = new User(username, password, email, gender, country, birthDate, exchangeTypes);			
			List<String> validationMessages = new ArrayList<String>();

			if (user.validate(validationMessages)) {
				logger.info("Insertando nuevo usuario en la BD");

				Connection connection = (Connection) getServletContext().getAttribute("dbConn");
				UserDAO userDao = new JDBCUserDAOImpl();
				userDao.setConnection(connection);				
				
				user.setPassword(PasswordEncryptation.sha256(password));
				long idu = userDao.add(user);
				user.setIdu(idu);
				user.setPassword("******");
				logger.info("idu del nuevo usuario:" + idu);

				HttpSession session = request.getSession();
				session.setAttribute("user", user);

				response.sendRedirect("exchange/SimpleSearchServlet");
			} else {
				logger.info("Ha habido un error en la validación");
				String errorMessage = new String();
				
				for (int i = 0; i < validationMessages.size(); i++) {
					errorMessage+=validationMessages.get(i)+"\n";
				}
				
				request.setAttribute("errorMessage", errorMessage);
				
				request.setAttribute("username", username);
				request.setAttribute("birthDate", birthDate);
				request.setAttribute("country", country);
				request.setAttribute("gender", gender);
				request.setAttribute("email", email);
				request.setAttribute("exchangeTypes", exchangeTypes);
				
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Register.jsp");
				view.forward(request, response);
			}
		}
		else{
			request.setAttribute("errorMessage", "Las contraseñas no coinciden");
			
			request.setAttribute("username", username);
			request.setAttribute("birthDate", birthDate);
			request.setAttribute("country", country);
			request.setAttribute("gender", gender);
			request.setAttribute("email", email);
			request.setAttribute("exchangeTypes", exchangeTypes);
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Register.jsp");
			view.forward(request, response);
		}
	}

}
