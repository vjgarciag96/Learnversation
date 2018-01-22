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

@WebServlet(name="AddLanguageServlet", urlPatterns="/exchange/AddLanguageServlet", 
description="Add Language Servlet") 
public class AddLanguageServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	
    public AddLanguageServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	HttpSession session = request.getSession();
    	User user = null;
		if ((user = (User) session.getAttribute("user"))!= null){
			
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			
			LanguageDAO languageDao = new JDBCLanguageDAOImpl();
			languageDao.setConnection(connection);
			List<Language> languageList = languageDao.getAll();
			
			LevelDAO levelDao = new JDBCLevelDAOImpl();
			levelDao.setConnection(connection);
			List<Level> languageLevels = levelDao.getAll();
			
			Users_LanguagesDAO usersLanguageDao = new JDBCUsers_LanguagesDAOImpl();
			usersLanguageDao.setConnection(connection);
			List<Users_Languages> usersLanguages = usersLanguageDao.getAllByUser(user.getIdu());
			
			Language currentLanguage = null;
			Users_Languages currentUserLanguage = null;
			
			for(int i=0;i<languageList.size();i++){
				currentLanguage = languageList.get(i);
				boolean removed = false;
				int j = 0;
				while(j<usersLanguages.size() && !removed){
					currentUserLanguage = usersLanguages.get(j);
					if(currentUserLanguage.getIdl() == currentLanguage.getIdl()){
						languageList.remove(i);
						i--;
						removed = true;						
					}
					else j++;					
				}
			}
			
			
			request.setAttribute("languageList", languageList);
			request.setAttribute("languageLevels", languageLevels);
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/AddLanguage.jsp");
			view.forward(request,response);
			logger.info("Si el usuario está registrado");
		}
		else { 
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Login.jsp");
			view.forward(request,response);
			logger.info("Si el usuario no está registrado");
		}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
       	HttpSession session = request.getSession();
       	request.setCharacterEncoding("UTF-8");
    
		User user = null;

		if ((user = (User) session.getAttribute("user")) != null ){
			
	    	Connection connection = (Connection) getServletContext().getAttribute("dbConn");
	    	
			Users_LanguagesDAO usersLanguagesDao = new JDBCUsers_LanguagesDAOImpl();			
			usersLanguagesDao.setConnection(connection);
			
			long idl = Long.valueOf(request.getParameter("languageIdl"));
			
			logger.info("langIdl:"+idl);
			
			long idu = -1;						
			idu = user.getIdu();
			
			logger.info("idu: "+idu);
			
			if(idu >= 0 && idl >= 0){
				
				long speakingLevel = Long.valueOf(request.getParameter("speakingLevel"));
				long writingLevel = Long.valueOf(request.getParameter("writingLevel"));
				long listeningLevel = Long.valueOf(request.getParameter("listeningLevel"));
				long readingLevel = Long.valueOf(request.getParameter("listeningLevel"));
				
				Users_Languages usersLanguage = new Users_Languages(idu, idl, speakingLevel, writingLevel, listeningLevel, readingLevel);
				
				if(usersLanguagesDao.add(usersLanguage)){
				
				logger.info("Añadiendo lenguaje "+idl+", nivel hablado "
						+speakingLevel+", nivel escrito: "+writingLevel+
						", nivel comprensión "+listeningLevel);						
				}
			}
			response.sendRedirect("UserProfileServlet");
		} 
		else {
			logger.info("No hay usuario registrado");
			response.sendRedirect("LoginServlet");
		}	
    }
    
}
