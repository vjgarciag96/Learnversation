package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.JDBCProfileImageDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.ProfileImageDAO;
import dao.UserDAO;
import model.ProfileImage;
import model.User;

@WebServlet(name = "ChangeProfileImageServlet", urlPatterns = "/exchange/ChangeProfileImageServlet", description = "Change Profile Image Servlet")
public class ChangeProfileImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public ChangeProfileImageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();


		if (session.getAttribute("user") != null) {

			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			ProfileImageDAO profileImageDAO = new JDBCProfileImageDAOImpl();
			profileImageDAO.setConnection(connection);
			
			if (Integer.valueOf(request.getParameter("confirmation")) == 0) {				
				List<ProfileImage> profileImages = profileImageDAO.getAll();

				for (int i = 0; i < profileImages.size(); i++) {
					logger.info("---------IMAGE NAME:" + profileImages.get(i).getImageName() + "-------------------");
				}

				request.setAttribute("profileImages", profileImages);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/ChangeProfileImage.jsp");
				view.forward(request, response);
			} else {
				if (Integer.valueOf(request.getParameter("confirmation")) == 1) {
					long idi = Long.valueOf(request.getParameter("idi"));
					logger.info("------------idi = "+idi+"---------------------");
					request.setAttribute("checkType", "profileImage");
					ProfileImage profileImage = profileImageDAO.get(idi);
					request.setAttribute("profileImage", profileImage);
					logger.info("------------"+profileImage.getImageName()+"--------------");
					RequestDispatcher view = request.getRequestDispatcher(
							"/WEB-INF/CheckDelete.jsp");
					view.forward(request, response);
				}
			}

		} else {
			response.sendRedirect("LoginServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();

		User user = null;

		if ((user = (User) session.getAttribute("user")) != null) {
			long idi = Long.valueOf(request.getParameter("idi"));
			logger.info("------------------Seleccionada imagen " + idi + "------------------------------");

			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			UserDAO userDao = new JDBCUserDAOImpl();
			userDao.setConnection(connection);

			if (!userDao.setProfileImage(user.getIdu(), idi)) {				
				response.sendRedirect("UserProfileServlet?errorMessage=No se ha podido cambiar la foto de perfil. Inténtelo de nuevo.");
			} else {
				User updatedUser = userDao.get(user.getIdu());
				logger.info("***********************"+updatedUser.getPassword()+"***************************");
				session.removeAttribute("user");
				session.setAttribute("user", updatedUser);
				response.sendRedirect("UserProfileServlet?messageState=Foto de perfil actualizada con exito");				
			}
		} else {
			response.sendRedirect("LoginServlet");
		}
	}

}
