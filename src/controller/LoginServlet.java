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

@WebServlet(name="LoginServlet", urlPatterns="/LoginServlet", 
description="Login Servlet") 
public class LoginServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	
    public LoginServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	
    	HttpSession session = request.getSession();
    	
		if (session.getAttribute("user")!= null){	
			response.sendRedirect("exchange/SimpleSearchServlet");
		}
		else {
			
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Login.jsp");
			view.forward(request,response);
			
		}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	
    	request.setCharacterEncoding("UTF-8");
    	
    	Connection connection = (Connection) getServletContext().getAttribute("dbConn");
    	UserDAO userDao = new JDBCUserDAOImpl();
    	userDao.setConnection(connection);
    	
    	String username = request.getParameter("username");
    	String password = PasswordEncryptation.sha256(request.getParameter("password"));
    	
    	User user = null;
    	if(username.contains("@")){
    		user = userDao.getByEmail(username);
    	}
    	else{
    		user = userDao.get(username);
    	}
    	    	
    	logger.info("credentials: "+username+" - "+password);
		

		if ((user != null) 
				&& (user.getPassword().equals(password)))
	    {
			HttpSession session = request.getSession();
			session.setAttribute("user",user);
			//response.sendRedirect("exchange/SimpleSearchServlet");
			response.sendRedirect("pages/index.html");
		} 
		else {
			request.setAttribute("errorMessage","Usuario o contraseña incorrectos");
			logger.info("entra siempre en el else");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Login.jsp");
			view.forward(request,response);
		}	
    }
    
	
}
