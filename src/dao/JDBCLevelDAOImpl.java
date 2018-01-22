package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Level;

public class JDBCLevelDAOImpl implements LevelDAO {

	private Connection connection;
	private static final Logger logger = Logger.getLogger(JDBCLanguageDAOImpl.class.getName());

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Level> getAll() {
		List<Level> levels = new ArrayList<Level>();
		String getAllQuery = "SELECT * FROM Level";
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			preparedStatement = connection.prepareStatement(getAllQuery);
			resultSet = preparedStatement.executeQuery();
			Level level;
			while (resultSet.next()) {
				level = new Level(resultSet.getLong("idlv"), resultSet.getString("levelName"));
				levels.add(level);
			}
		} catch (SQLException e) {
			logger.info("Error al obtener todos los niveles " + e.toString());
		}
		return levels;
	}

	@Override
	public Level get(long idlv) {
		Level level = null;
		String getQuery = "SELECT * FROM Level WHERE idlv=?";
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			preparedStatement = connection.prepareStatement(getQuery);
			preparedStatement.setLong(1, idlv);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next())
				return null;
			level = new Level(resultSet.getLong("idlv"), resultSet.getString("levelName"));			
		} catch (SQLException e) {
			logger.info("Error al obtener todos los niveles " + e.toString());
		}
		return level;
	}

}
