package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Users_Languages;

public class JDBCUsers_LanguagesDAOImpl implements Users_LanguagesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUsers_LanguagesDAOImpl.class.getName());

	@Override
	public List<Users_Languages> getAll() {
		if (conn == null)
			return null;

		ArrayList<Users_Languages> users_LanguagesList = new ArrayList<Users_Languages>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages");

			while (rs.next()) {
				Users_Languages users_Languages = new Users_Languages(rs.getLong("idu"), rs.getLong("idl"),
						rs.getLong("speakingLevel"), rs.getLong("writingLevel"), rs.getLong("listeningLevel"), rs.getLong("readingLevel"));
				users_LanguagesList.add(users_Languages);
			}

		} catch (SQLException e) {
			logger.info("Error al obtener los lenguajes del usuario " + e.toString());
		}

		return users_LanguagesList;
	}
	
	@Override
	public List<Users_Languages> getAllByUser(long idu) {

		if (conn == null)
			return null;

		ArrayList<Users_Languages> users_LanguagesList = new ArrayList<Users_Languages>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages WHERE idu=" + idu);

			while (rs.next()) {
				Users_Languages users_Languages = new Users_Languages(rs.getLong("idu"), rs.getLong("idl"),
						rs.getLong("speakingLevel"), rs.getLong("writingLevel"), rs.getLong("listeningLevel"), rs.getLong("readingLevel"));
				users_LanguagesList.add(users_Languages);
				logger.info("fetching users_LanguagesList- idu: " + users_Languages.getIdu() + ", idl: "
						+ users_Languages.getIdl());
			}

		} catch (SQLException e) {
			logger.info("Error al obtener los lenguajes del usuario " + e.toString());
		}

		return users_LanguagesList;
	}

	@Override
	public Users_Languages get(long idu, long idl, long speakingLevel, long writingLevel, long listeningLevel, long readingLevel) {
		if (conn == null)
			return null;

		Users_Languages users_Languages = null;
		String getQuery;
		if(idl >= 0)
			getQuery = "SELECT * FROM Users_Languages WHERE idu=? AND " + "idl=?";
		else
			getQuery = "SELECT * FROM Users_Languages WHERE idu=? AND " + "idl>=?";
		
		if (speakingLevel >= 0)
			getQuery += " AND speakingLevel >= ?";

		if (writingLevel >= 0)
			getQuery += "AND writingLevel >= ?";

		if (listeningLevel >= 0)
			getQuery += "AND listeningLevel >= ?";
		
		if(readingLevel >= 0)
			getQuery += "AND readingLevel >= ?";
		
		PreparedStatement preparedStatement;

		try {
			int levelIndex = 3;
			preparedStatement = conn.prepareStatement(getQuery);

			preparedStatement.setLong(1, idu);
			preparedStatement.setLong(2, idl);
			
			if (speakingLevel >= 0) {
				preparedStatement.setLong(levelIndex, speakingLevel);
				levelIndex++;
			}
			if (writingLevel >= 0) {
				preparedStatement.setLong(levelIndex, writingLevel);
				levelIndex++;
			}
			if (listeningLevel >= 0) {
				preparedStatement.setLong(levelIndex, listeningLevel);
				levelIndex++;
			}
			if (readingLevel >= 0) {
				preparedStatement.setLong(levelIndex, readingLevel);
				levelIndex++;
			}
			
			ResultSet rs = preparedStatement.executeQuery();

			if (!rs.next())
				return null;
			users_Languages = new Users_Languages();

			users_Languages.setIdu(rs.getLong("idu"));
			users_Languages.setIdl(rs.getLong("idl"));
			users_Languages.setSpeakingLevel(rs.getLong("speakingLevel"));
			users_Languages.setWritingLevel(rs.getLong("writingLevel"));
			users_Languages.setListeningLevel(rs.getLong("listeningLevel"));
			users_Languages.setReadingLevel(rs.getLong("readingLevel"));

			logger.info("fetching _users_Languages by idu: " + users_Languages.getIdu() + "  and idl: "
					+ users_Languages.getIdl() + " ");
		
		} catch (SQLException e) {
			logger.warning("Error al obtener un lenguaje concreto del usuario " + idl + ". Error: " + e.toString());
		}

		return users_Languages;
	}
	

	@Override
	public boolean add(Users_Languages users_Languages) {
		boolean done = false;

		if (conn != null) {

			String addUserLanguageQuery = "INSERT INTO Users_Languages (idu, idl, "
					+ "speakingLevel, writingLevel, listeningLevel, readingLevel) VALUES(?, ?, ?, ?, ?, ?)";

			try {

				PreparedStatement preparedStatement = conn.prepareStatement(addUserLanguageQuery);

				preparedStatement.setLong(1, users_Languages.getIdu());
				preparedStatement.setLong(2, users_Languages.getIdl());
				preparedStatement.setLong(3, users_Languages.getSpeakingLevel());
				preparedStatement.setLong(4, users_Languages.getWritingLevel());
				preparedStatement.setLong(5, users_Languages.getListeningLevel());
				preparedStatement.setLong(6, users_Languages.getReadingLevel());

				preparedStatement.executeUpdate();

				logger.info("creating Users_Languages:(" + users_Languages.getIdu() + " " + users_Languages.getIdl());

				done = true;

			} catch (SQLException e) {
				logger.info("Error añadiendo un lenguaje a un usuario: " + e.toString());
			}
		}
		return done;
	}

	@Override
	public boolean save(Users_Languages users_Languages) {
		boolean done = false;
		if (conn != null) {
			String saveQuery = "UPDATE Users_Languages SET " + "speakingLevel = ?, writingLevel = ?, "
					+ "listeningLevel = ?, readingLevel = ? WHERE idu = ?  AND idl = ?";

			PreparedStatement preparedStatement;

			try {
				preparedStatement = conn.prepareStatement(saveQuery);
				preparedStatement.setLong(1, users_Languages.getSpeakingLevel());
				preparedStatement.setLong(2, users_Languages.getWritingLevel());
				preparedStatement.setLong(3, users_Languages.getListeningLevel());
				preparedStatement.setLong(4, users_Languages.getReadingLevel());
				preparedStatement.setLong(5, users_Languages.getIdu());
				preparedStatement.setLong(6, users_Languages.getIdl());

				if (preparedStatement.executeUpdate() > 0)
					done = true;

			} catch (SQLException e) {
				logger.warning("Error al actualizar el lenguaje del usuario: " + e.toString());
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idu, long idl) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Users_Languages WHERE idu =" + idu + " AND idl=" + idl);
				logger.info("deleting Users_Languages: " + idu + " , idl=" + idl);
				done = true;
			} catch (SQLException e) {
				logger.warning("Error eliminando el lenguaje de un usuario: " + e.toString());
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Users_Languages getByUserIdAndLanguageId(long idu, long idl) {
		if (conn == null)
			return null;

		Users_Languages userLanguage = null;

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users_Languages WHERE idu=" + idu + " AND idl="+ idl);

			if (rs.next()) {
				userLanguage = new Users_Languages(rs.getLong("idu"), rs.getLong("idl"),
						rs.getLong("speakingLevel"), rs.getLong("writingLevel"), rs.getLong("listeningLevel"), rs.getLong("readingLevel"));				
			}

		} catch (SQLException e) {
			logger.info("Error al obtener los lenguajes del usuario " + e.toString());
		}

		return userLanguage;
	}

}
