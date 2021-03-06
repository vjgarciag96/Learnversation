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

import dao.JDBCLanguageDAOImpl;
import dao.JDBCLevelDAOImpl;
import dao.JDBCUsers_LanguagesDAOImpl;
import dao.LanguageDAO;
import dao.LevelDAO;
import dao.Users_LanguagesDAO;
import model.Language;
import model.Level;
import model.User;
import model.Users_Languages;

@WebServlet(name="EditLanguageServlet", urlPatterns="/exchange/EditLanguageServlet", 
description="Edit Language Servlet") 
public class EditLanguageServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	
    public EditLanguageServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	HttpSession session = request.getSession();
		User user = null;

		if ((user = (User) session.getAttribute("user")) != null) {

			long idl = Long.valueOf(request.getParameter("languageId"));
			long speakingLevelId = Long.valueOf(request.getParameter("speakingLevel"));
			long writingLevelId = Long.valueOf(request.getParameter("writingLevel"));
			long listeningLevelId = Long.valueOf(request.getParameter("listeningLevel"));
			long readingLevelId = Long.valueOf(request.getParameter("readingLevel"));

			logger.info("languageId: " + idl);

			long idu = user.getIdu();
			logger.info("userId: " + idu);

			Connection connection = (Connection) getServletContext().getAttribute("dbConn");

			LanguageDAO languageDao = new JDBCLanguageDAOImpl();
			languageDao.setConnection(connection);
			Language language = languageDao.get(idl);

			LevelDAO levelDao = new JDBCLevelDAOImpl();
			levelDao.setConnection(connection);
			List<Level> levels = levelDao.getAll();

			Users_LanguagesDAO usersLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
			usersLanguagesDao.setConnection(connection);
			Users_Languages userLanguage = usersLanguagesDao.get(idu, idl, speakingLevelId, writingLevelId,
					listeningLevelId, readingLevelId);
			if (userLanguage != null) {
				Level speakingLevel = levelDao.get(speakingLevelId);
				Level writingLevel = levelDao.get(writingLevelId);
				Level listeningLevel = levelDao.get(listeningLevelId);
				Level readingLevel = levelDao.get(readingLevelId);
				
				session.setAttribute("languageId", language.getIdl());
				request.setAttribute("languageLevels", levels);
				request.setAttribute("language", language);
				request.setAttribute("speakingLevel", speakingLevel.getIdlv());
				request.setAttribute("writingLevel", writingLevel.getIdlv());
				request.setAttribute("listeningLevel", listeningLevel.getIdlv());
				request.setAttribute("readingLevel", readingLevel.getIdlv());
				
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/EditLanguage.jsp");
				view.forward(request, response);
			}
			else{
				response.sendRedirect("UserProfileServlet");
			}
		} else {
			response.sendRedirect("LoginServlet");
		}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	HttpSession session = request.getSession();
		User user = null;
		long idl = -1;

		if ((user = (User) session.getAttribute("user")) != null
				&& (idl = (long) session.getAttribute("languageId")) >= 0) {
			long idu = user.getIdu();
			logger.info("userId: " + idu);
			long speakingLevelId = Long.valueOf(request.getParameter("speakingLevel"));
			long writingLevelId = Long.valueOf(request.getParameter("writingLevel"));
			long listeningLevelId = Long.valueOf(request.getParameter("listeningLevel"));
			long readingLevelId = Long.valueOf(request.getParameter("readingLevel"));
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");

			Users_LanguagesDAO usersLanguagesDao = new JDBCUsers_LanguagesDAOImpl();
			usersLanguagesDao.setConnection(connection);
			Users_Languages userLanguage = new Users_Languages(idu, idl, speakingLevelId, writingLevelId, listeningLevelId, readingLevelId);
			if(usersLanguagesDao.save(userLanguage))
				logger.info("El usuario "+user.getUsername()+" ha sido editado con exito");
			else
				logger.warning("La edicion no se ha llevado a cabo correctamente");
			session.removeAttribute("languageId");
		}
		response.sendRedirect("UserProfileServlet");
    }
    
}
