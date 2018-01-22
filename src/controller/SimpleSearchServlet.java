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
import model.Language;
import model.Level;
import model.ProfileImage;
import model.User;
import model.Users_Languages;

@WebServlet(name = "SimpleSearchServlet", urlPatterns = "/exchange/SimpleSearchServlet", description = "Simple Search Servlet")
public class SimpleSearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public SimpleSearchServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection connection = (Connection) getServletContext().getAttribute("dbConn");

		LanguageDAO languageDao = new JDBCLanguageDAOImpl();
		languageDao.setConnection(connection);
		List<Language> languageList = languageDao.getAll();

		LevelDAO levelDao = new JDBCLevelDAOImpl();
		levelDao.setConnection(connection);
		List<Level> languageLevels = levelDao.getAll();

		request.setAttribute("languageList", languageList);
		request.setAttribute("languageLevels", languageLevels);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/SimpleSearch.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		User user;
		if ((user = (User) session.getAttribute("user")) != null) {

			request.setCharacterEncoding("UTF-8");

			Connection connection = (Connection) getServletContext().getAttribute("dbConn");

			List<User> userList = null;

			UserDAO userDao = new JDBCUserDAOImpl();
			userDao.setConnection(connection);

			String language = request.getParameter("languageIdl");
			String speakingLevel = request.getParameter("speakingLevel");
			String writingLevel = request.getParameter("writingLevel");
			String listeningLevel = request.getParameter("listeningLevel");
			String readingLevel = request.getParameter("readingLevel");
			String country = request.getParameter("country");
			String exchangeTypes = new String();
			List<String> exchangeTypesArray = null;

			if (request.getParameterValues("exchangeTypes") != null) {
				exchangeTypesArray = new ArrayList<String>(Arrays.asList(request.getParameterValues("exchangeTypes")));
				Iterator<String> it = exchangeTypesArray.iterator();
				while (it.hasNext()) {
					String exchangeItem = it.next();
					exchangeTypes = exchangeTypes + "\n" + exchangeItem;
				}
			} else {
				exchangeTypes = "No tiene preferencias";
			}

			String username = request.getParameter("username");

			long idl = -1;
			long speakingLevelIdlv = -1;
			long writingLevelIdlv = -1;
			long listeningLevelIdlv = -1;
			long readingLevelIdlv = -1;

			if (language != null && !language.equals("") && !language.equals("---"))
				idl = Long.valueOf(language);
			if (speakingLevel != null && !speakingLevel.equals("") && !speakingLevel.equals("---"))
				speakingLevelIdlv = Long.valueOf(speakingLevel);
			if (writingLevel != null && !writingLevel.equals("") && !writingLevel.equals("---"))
				writingLevelIdlv = Long.valueOf(writingLevel);
			if (listeningLevel != null && !listeningLevel.equals("") && !listeningLevel.equals("---"))
				listeningLevelIdlv = Long.valueOf(listeningLevel);
			if (readingLevel != null && !readingLevel.equals("") && !readingLevel.equals("---"))
				readingLevelIdlv = Long.valueOf(readingLevel);

			userList = userDao.getUsers(country, exchangeTypesArray, username);

			User currentUser;

			Users_LanguagesDAO usersLanguageDao = new JDBCUsers_LanguagesDAOImpl();
			usersLanguageDao.setConnection(connection);

			for (int i = 0; i < userList.size(); i++) {
				currentUser = userList.get(i);
				if (usersLanguageDao.get(currentUser.getIdu(), idl, speakingLevelIdlv, writingLevelIdlv,
						listeningLevelIdlv, readingLevelIdlv) == null || currentUser.getIdu() == user.getIdu()) {
					userList.remove(i);
					i--;
				}
			}

			request.setAttribute("userList", userList);

			LanguageDAO languageDao = new JDBCLanguageDAOImpl();
			languageDao.setConnection(connection);
			List<Language> languageList = languageDao.getAll();

			LevelDAO levelDao = new JDBCLevelDAOImpl();
			levelDao.setConnection(connection);
			List<Level> languageLevels = levelDao.getAll();

			ProfileImageDAO profileImageDao = new JDBCProfileImageDAOImpl();
			profileImageDao.setConnection(connection);
			List<ProfileImage> profileImages = profileImageDao.getAll();

			request.setAttribute("languageList", languageList);
			request.setAttribute("languageLevels", languageLevels);
			request.setAttribute("profileImages", profileImages);

			List<Users_Languages> usersLanguages = new ArrayList<Users_Languages>();

			for (int i = 0; i < userList.size(); i++) {
				currentUser = userList.get(i);
				List<Users_Languages> userLanguages = usersLanguageDao.getAllByUser(currentUser.getIdu());
				usersLanguages.addAll(userLanguages);
			}

			if (userList.size() == 0) {
				request.setAttribute("zeroUsersFound",
						"No se ha encontrado ningún usuario que coincida con tus criterios de búsqueda");
			}

			request.setAttribute("usersLanguages", usersLanguages);

			if (idl >= 0)
				request.setAttribute("language", languageDao.get(idl).getIdl());
			if (speakingLevelIdlv >= 0)
				request.setAttribute("speakingLevel", levelDao.get(speakingLevelIdlv).getIdlv());
			if (writingLevelIdlv >= 0)
				request.setAttribute("writingLevel", levelDao.get(writingLevelIdlv).getIdlv());
			if (listeningLevelIdlv >= 0)
				request.setAttribute("listeningLevel", levelDao.get(listeningLevelIdlv).getIdlv());
			if (readingLevelIdlv >= 0)
				request.setAttribute("readingLevel", levelDao.get(readingLevelIdlv).getIdlv());

			request.setAttribute("country", country);
			request.setAttribute("exchangeTypes", exchangeTypes);
			request.setAttribute("username", username);
			logger.info("La búsqueda tiene " + userList.size() + " resultados");

			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/SearchResult.jsp");
			view.forward(request, response);
		} else {
			response.sendRedirect("LoginServlet");
		}
	}
}
