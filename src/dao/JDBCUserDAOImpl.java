package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.User;

public class JDBCUserDAOImpl implements UserDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());

	@Override
	public User get(long idu) {
		if (conn == null)
			return null;

		User user = null;

		try {
			String getUserByIduQuery = "SELECT * FROM Public.User WHERE idu = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(getUserByIduQuery);
			preparedStatement.setLong(1, idu);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next())
				return null;
			user = new User(resultSet.getLong("idu"), resultSet.getString("username"), resultSet.getString("password"),
					resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("country"),
					resultSet.getString("birthdate"), resultSet.getString("exchangeTypes"), resultSet.getLong("idi"));

			logger.info("fetching User by idu: " + idu + " -> " + user.getIdu() + " " + user.getUsername() + " "
					+ user.getEmail() + " " + user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User get(String username) {
		if (conn == null)
			return null;

		User user = null;

		try {
			String getUserByUsernameQuery = "SELECT * FROM Public.User WHERE username = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(getUserByUsernameQuery);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next())
				return null;
			user = new User(resultSet.getLong("idu"), resultSet.getString("username"), resultSet.getString("password"),
					resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("country"),
					resultSet.getString("birthdate"), resultSet.getString("exchangeTypes"), resultSet.getLong("idi"));
			logger.info("fetching User by name: " + username + " -> " + user.getIdu() + " " + user.getUsername() + " "
					+ user.getEmail() + " " + user.getPassword() + "\n país:" + user.getCountry() + "\n exchangeTypes:"
					+ user.getExchangeTypes());
		} catch (SQLException e) {
			logger.info("Error al obtener al usuario " + username + "\n" + "Error: " + e.toString());
		}
		return user;
	}

	@Override
	public User getByEmail(String email) {
		if (conn == null)
			return null;

		User user = null;

		try {
			String getUserByUsernameQuery = "SELECT * FROM Public.User WHERE email = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(getUserByUsernameQuery);
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next())
				return null;
			user = new User(resultSet.getLong("idu"), resultSet.getString("username"), resultSet.getString("password"),
					resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("country"),
					resultSet.getString("birthdate"), resultSet.getString("exchangeTypes"), resultSet.getLong("idi"));
			logger.info("fetching User by email: " + email + " -> " + user.getIdu() + " " + user.getUsername() + " "
					+ user.getEmail() + " " + user.getPassword() + "\n país:" + user.getCountry() + "\n exchangeTypes:"
					+ user.getExchangeTypes());
		} catch (SQLException e) {
			logger.info("Error al obtener al usuario con mail:" + email + "\n" + "Error: " + e.toString());
		}
		return user;
	}

	public List<User> getAll() {

		if (conn == null)
			return null;

		List<User> users = new ArrayList<User>();
		try {
			Statement statement;
			ResultSet resultSet;
			synchronized (conn) {
				statement = conn.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM Public.User");
			}
			while (resultSet.next()) {
				User user = new User(resultSet.getLong("idu"), resultSet.getString("username"),
						resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("country"),
						resultSet.getString("birthdate"), resultSet.getString("exchangeTypes"),
						resultSet.getLong("idi"));
				users.add(user);
				logger.info("fetching users: " + user.getIdu() + " " + user.getUsername() + " " + user.getEmail() + " "
						+ user.getPassword());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public List<User> getUsers(String country, String exchangeTypeFirst, String exchangeTypeSecond, String username) {
		if (conn == null)
			return null;

		List<User> users = new ArrayList<User>();

		try {
			String getUsersQuery = "SELECT * FROM Public.User WHERE country LIKE ? AND username LIKE ?";
			if (!exchangeTypeFirst.equals("") && !exchangeTypeSecond.equals(""))
				getUsersQuery = getUsersQuery + "AND (exchangeTypes LIKE ? OR exchangeTypes LIKE ?)";
			else
				getUsersQuery = getUsersQuery + "AND (exchangeTypes LIKE ?)";

			PreparedStatement preparedStatement = conn.prepareStatement(getUsersQuery);
			preparedStatement.setString(1, "%" + country + "%");
			preparedStatement.setString(2, "%" + username + "%");
			if (!exchangeTypeFirst.equals("") && !exchangeTypeSecond.equals("")) {
				preparedStatement.setString(3, "%" + exchangeTypeFirst + "%");
				preparedStatement.setString(4, "%" + exchangeTypeSecond + "%");
			}
			else
				if(!exchangeTypeFirst.equals(""))
					preparedStatement.setString(3, "%" + exchangeTypeFirst + "%");
				else
					preparedStatement.setString(3, "%" + exchangeTypeSecond + "%");
			logger.info("country: " + country);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User(resultSet.getLong("idu"), resultSet.getString("username"),
						resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("gender"),
						resultSet.getString("country"), resultSet.getString("birthdate"),
						resultSet.getString("exchangeTypes"), resultSet.getLong("idi"));
				user.setPassword("********");
				logger.info("fetching User by name: " + user.getUsername() + " -> " + user.getIdu() + " "
						+ user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + "\n país:"
						+ user.getCountry());
				users.add(user);
			}
		} catch (SQLException e) {
			logger.info("Error al obtener los usuarios \n" + "Error: " + e.toString());
		}
		return users;
	}

	@Override
	public List<User> getUsers(String country, List<String> exchangeTypes, String username) {
		if (conn == null)
			return null;

		List<User> users = new ArrayList<User>();

		try {
			String getUsersQuery = "SELECT * FROM Public.User WHERE country LIKE ? AND username LIKE ?";
			if (exchangeTypes != null && exchangeTypes.size() > 0) {
				getUsersQuery = getUsersQuery + " AND (";
				for (int i = 0; i < exchangeTypes.size(); i++) {
					getUsersQuery = getUsersQuery + "exchangeTypes LIKE ?";
					if (i < exchangeTypes.size() - 1)
						getUsersQuery = getUsersQuery + " OR ";
				}
				getUsersQuery = getUsersQuery + ")";
			}
			PreparedStatement preparedStatement = conn.prepareStatement(getUsersQuery);
			preparedStatement.setString(1, "%" + country + "%");
			preparedStatement.setString(2, "%" + username + "%");

			if (exchangeTypes != null) {
				for (int i = 0; i < exchangeTypes.size(); i++) {
					preparedStatement.setString(i + 3, "%" + exchangeTypes.get(i) + "%");
				}
			}

			logger.info("country: " + country);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User(resultSet.getLong("idu"), resultSet.getString("username"),
						resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("gender"),
						resultSet.getString("country"), resultSet.getString("birthdate"),
						resultSet.getString("exchangeTypes"), resultSet.getLong("idi"));
				user.setPassword("********");
				logger.info("fetching User by name: " + user.getUsername() + " -> " + user.getIdu() + " "
						+ user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + "\n país:"
						+ user.getCountry());
				users.add(user);
			}
		} catch (SQLException e) {
			logger.info("Error al obtener los usuarios \n" + "Error: " + e.toString());
		}
		return users;
	}

	@Override
	public long add(User user) {
		long idu = -1;
		if (conn != null) {
			String addUserQuery = "INSERT INTO User(username, email, password,"
					+ " gender, country, birthDate, exchangeTypes) " + "VALUES('" + user.getUsername() + "','"
					+ user.getEmail() + "','" + user.getPassword() + "','" + user.getGender() + "','"
					+ user.getCountry() + "','" + user.getBirthDate() + "','" + user.getExchangeTypes() + "')";
			try {
				Statement statement = conn.prepareStatement(addUserQuery, PreparedStatement.RETURN_GENERATED_KEYS);

				statement = conn.createStatement();
				statement.executeUpdate(addUserQuery, Statement.RETURN_GENERATED_KEYS);

				ResultSet genKeys = statement.getGeneratedKeys();

				if (genKeys.next())
					idu = genKeys.getInt(1);

				logger.info("creating User(" + idu + "): " + user.getUsername() + " " + user.getEmail() + " "
						+ user.getPassword());

			} catch (SQLException e) {
				logger.warning("error al añadir usuario : " + e.toString());
			}
		}
		return idu;
	}

	@Override
	public boolean save(User user) {
		boolean done = false;
		if (conn != null) {

			String saveUserQuery = "UPDATE User SET username = ?, email = ?, "
					+ "password = ?, gender = ?, country = ?, " + "birthDate = ?, exchangeTypes = ?, idi = ? WHERE idu = ?";
			try {

				PreparedStatement preparedStatement = conn.prepareStatement(saveUserQuery);
				logger.info("user country:" + user.getCountry());
				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setString(2, user.getEmail());
				preparedStatement.setString(3, user.getPassword());
				preparedStatement.setString(4, user.getGender());
				preparedStatement.setString(5, user.getCountry());
				preparedStatement.setString(6, user.getBirthDate());
				preparedStatement.setString(7, user.getExchangeTypes());
				preparedStatement.setLong(8, user.getIdi());
				preparedStatement.setLong(9, user.getIdu());

				preparedStatement.executeUpdate();

				logger.info("updating User: " + user.getIdu() + " " + user.getUsername() + " " + user.getEmail() + " "
						+ user.getPassword());

				done = true;
			} catch (SQLException e) {
				logger.info("error al actualizar usuario : " + e.toString());
			}

		}
		return done;

	}

	@Override
	public boolean setProfileImage(long idu, long idi) {
		boolean done = false;
		if (conn != null) {
			String setProfileImageQuery = "UPDATE User SET idi = ? WHERE idu = ?";

			try {
				PreparedStatement preparedStatement = conn.prepareStatement(setProfileImageQuery);
				preparedStatement.setLong(1, idi);
				preparedStatement.setLong(2, idu);
				int result = preparedStatement.executeUpdate();
				if (result > 0)
					done = true;
			} catch (SQLException e) {
				logger.warning("Error actualizando la imagen de perfil de usuario: " + e.toString());
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idu) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM User WHERE idu =" + idu);
				logger.info("deleting User: " + idu);
				done = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

}
