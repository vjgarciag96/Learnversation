package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.ProfileImage;

public class JDBCProfileImageDAOImpl implements ProfileImageDAO{

	private Connection connection;
	private static final Logger logger = Logger.getLogger(JDBCLanguageDAOImpl.class.getName());

	@Override
	public void setConnection(Connection conn) {
		this.connection = conn;
	}
	
	@Override
	public List<ProfileImage> getAll() {
		List<ProfileImage> images = new ArrayList<ProfileImage>();
		String getAllQuery = "SELECT * FROM ProfileImage";
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			preparedStatement = connection.prepareStatement(getAllQuery);
			resultSet = preparedStatement.executeQuery();
			ProfileImage image;
			while (resultSet.next()) {
				image = new ProfileImage(resultSet.getLong("idi"), resultSet.getString("imageName"));
				images.add(image);
			}
		} catch (SQLException e) {
			logger.info("Error al obtener todos los niveles " + e.toString());
		}
		return images;
	}

	@Override
	public ProfileImage get(long idi) {
		ProfileImage image = null;
		String getQuery = "SELECT * FROM ProfileImage WHERE idi=?";
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			preparedStatement = connection.prepareStatement(getQuery);
			preparedStatement.setLong(1, idi);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next())
				return null;
			image = new ProfileImage(resultSet.getLong("idi"), resultSet.getString("imageName"));			
		} catch (SQLException e) {
			logger.info("Error al obtener todos los niveles " + e.toString());
		}
		return image;
	}

}
