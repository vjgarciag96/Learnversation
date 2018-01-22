package dao;

import java.sql.Connection;
import java.util.List;

import model.Country;

public interface CountryDAO {
	
	public void setConnection(Connection connection);

	public Country get(long idCountry);

	public List<Country> getAll();

}
