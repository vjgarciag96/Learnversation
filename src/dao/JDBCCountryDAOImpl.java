package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Country;

public class JDBCCountryDAOImpl implements CountryDAO{

	private Connection connection;
	private static final Logger logger = Logger.getLogger(JDBCLanguageDAOImpl.class.getName());
	
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Country get(long idCountry) {
		Country country = null;
		String getQuery = "SELECT * FROM Country WHERE idCountry=?";
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			preparedStatement = connection.prepareStatement(getQuery);
			preparedStatement.setLong(1, idCountry);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next())
				return null;
			country = new Country(resultSet.getLong("idCountry"), resultSet.getString("countryName"));			
		} catch (SQLException e) {
			logger.info("Error al obtener todos los niveles " + e.toString());
		}
		return country;
	}

	@Override
	public List<Country> getAll() {
		List<Country> countries = new ArrayList<Country>();
		String getAllQuery = "SELECT * FROM Country";
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			preparedStatement = connection.prepareStatement(getAllQuery);
			resultSet = preparedStatement.executeQuery();
			Country country;
			while (resultSet.next()) {
				country = new Country(resultSet.getLong("idCountry"), resultSet.getString("countryName"));
				countries.add(country);
			}
		} catch (SQLException e) {
			logger.info("Error al obtener todos los niveles " + e.toString());
		}
		return countries;	
	}

}
