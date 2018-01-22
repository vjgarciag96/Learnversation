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
import dao.JDBCLanguageDAOImpl;
import dao.JDBCLevelDAOImpl;
import dao.JDBCProfileImageDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.LanguageDAO;
import dao.LevelDAO;
import dao.ProfileImageDAO;
import dao.UserDAO;
import dao.Users_LanguagesDAO;
import model.Comment;
import model.Language;
import model.Level;
import model.ProfileImage;
import model.User;
import model.Users_Languages;
import util.Pair;

@WebServlet(name = "UserProfileServlet", urlPatterns = "/exchange/UserProfileServlet", description = "User Profile Servlet")
public class UserProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public UserProfileServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User user;

		if ((user = (User) session.getAttribute("user")) != null) {
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			
			logger.info("birthDate = "+user.getBirthDate()+
					", exchangeTypes ="+user.getExchangeTypes());
			long userIdu;
			String userVisitedIdu = request.getParameter("userVisitedIdu");

			if (userVisitedIdu != null) {
				userIdu = Long.valueOf(userVisitedIdu);
				logger.info("--------------------------------" + userVisitedIdu + "----------------------------------");
			} else {
				userIdu = user.getIdu();
			}

			Users_LanguagesDAO userLanguageDao = new JDBCUsers_LanguagesDAOImpl();
			userLanguageDao.setConnection(connection);

			List<Users_Languages> userLanguageList = userLanguageDao.getAllByUser(userIdu);

			LanguageDAO languageDao = new JDBCLanguageDAOImpl();
			languageDao.setConnection(connection);

			List<Language> languageList = new ArrayList<Language>();

			for (int i = 0; i < userLanguageList.size(); i++) {
				Users_Languages currentUserLanguage = userLanguageList.get(i);
				Language currentLanguage = languageDao.get(currentUserLanguage.getIdl());
				languageList.add(currentLanguage);
			}

			LevelDAO levelDao = new JDBCLevelDAOImpl();
			levelDao.setConnection(connection);

			List<Level> levelsList = levelDao.getAll();

			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(connection);
			List<Comment> commentsList = commentDao.getAllByReceiver(userIdu);
			List<Pair<String, Comment>> commentsAndSendersList = new ArrayList<Pair<String, Comment>>();

			UserDAO userDao = new JDBCUserDAOImpl();
			userDao.setConnection(connection);

			for (int i = 0; i < commentsList.size(); i++) {
				Comment currentComment = commentsList.get(i);
				User currentUser = userDao.get(currentComment.getSender());
				commentsAndSendersList.add(new Pair<String, Comment>(currentUser.getUsername(), currentComment));
			}
			
			ProfileImageDAO profileImageDao = new JDBCProfileImageDAOImpl();
			profileImageDao.setConnection(connection);
			List<ProfileImage> profileImages = profileImageDao.getAll();
			

			request.setAttribute("languageList", languageList);
			request.setAttribute("userLanguageList", userLanguageList);
			request.setAttribute("levelsList", levelsList);
			request.setAttribute("commentsAndSenders", commentsAndSendersList);
			request.setAttribute("profileImages", profileImages);

			if (userVisitedIdu == null) {
				request.setAttribute("profileType", "editing");
			} else {
				User userVisited = userDao.get(userIdu);
				request.setAttribute("userVisited", userVisited);
			}

			String messageState = request.getParameter("messageState");

			if (messageState != null)
				request.setAttribute("messageState", messageState);
			else {
				String errorMessage = request.getParameter("errorMessage");
				if (errorMessage != null)
					request.setAttribute("errorMessage", errorMessage);
			}

			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/UserProfile.jsp");
			view.forward(request, response);
			logger.info("Si el usuario está registrado");
		} else {
			response.sendRedirect("LoginServlet");
			logger.info("Si el usuario no está registrado");
		}
	}
}
