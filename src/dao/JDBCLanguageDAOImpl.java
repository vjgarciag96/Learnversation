package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Language;

public class JDBCLanguageDAOImpl implements LanguageDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCLanguageDAOImpl.class.getName());
	
	@Override
	public Language get(long idl) {
		if (conn == null) return null;
		
		Language language = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.Language WHERE idl ="+idl);			 
			if (!rs.next()) return null; 
			language  = new Language();	 
			language.setIdl(rs.getLong("idl"));
			language.setLangname(rs.getString("langname"));
			
			logger.info("fetching Language by idl: "+idl+" -> "+language.getIdl()+" "+language.getLangname());
		} catch (SQLException e) {
			logger.info("Error al obtener un lenguaje por idl: "+e.toString());
		}
		return language;
	}
	
	@Override
	public Language get(String langname) {
		if (conn == null) return null;
		
		Language language = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.Language WHERE langname ='"+langname+"'");			 
			if (!rs.next()) return null; 
			language  = new Language();	 
			language.setIdl(rs.getLong("idl"));
			language.setLangname(rs.getString("langname"));
			
			logger.info("fetching Language by langname: "+ langname + " -> "+ language.getIdl()+" "+language.getLangname());
		} catch (SQLException e) {
			logger.info("Error al obtener un lenguaje por nombre: "+e.toString());
		}
		return language;
	}
	
	
	public List<Language> getAll() {

		if (conn == null) return null;
		
		ArrayList<Language> languages = new ArrayList<Language>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized(conn){
			  stmt = conn.createStatement();
			  rs = stmt.executeQuery("SELECT * FROM Public.Language");
			}
			while ( rs.next() ) {
				Language language = new Language();
				language.setIdl(rs.getLong("idl"));
				language.setLangname(rs.getString("langname"));
				
				languages.add(language);
				logger.info("fetching languages: "+language.getIdl()+" "+language.getLangname());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return languages;
	}
	

	@Override
	public long add(Language language) {
		long idl=-1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Language (langname) VALUES('"
									+language.getLangname()+"')",Statement.RETURN_GENERATED_KEYS);
				
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    idl = genKeys.getInt(1);
				
				
				logger.info("creating Language("+idl+"): "+language.getIdl()+" "+language.getLangname());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return idl;
	}

	@Override
	public boolean save(Language language) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE Language SET langname='"+language.getLangname()
									+"' WHERE idl = "+language.getIdl());
				logger.info("updating Language: "+language.getIdl()+" "+language.getLangname());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return done;

	}

	@Override
	public boolean delete(long idl) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Language WHERE idl ="+idl);
				logger.info("deleting Language: "+idl);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

	
}
