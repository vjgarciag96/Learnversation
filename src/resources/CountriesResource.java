package resources;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.CountryDAO;
import dao.JDBCCountryDAOImpl;
import model.Country;

@Path("/countries")
public class CountriesResource {
	
	@Context
	ServletContext servletContext;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Country> getAllCountries(@Context HttpServletRequest request){
		
		List<Country> countries = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		CountryDAO countryDao = new JDBCCountryDAOImpl();
		countryDao.setConnection(connection);
		
		countries = countryDao.getAll();
		
		return countries;
	}
	
	@GET
	@Path("/{countryid: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Country getCountry(@PathParam("countryid") long countryId, @Context HttpServletRequest request){
		
		Country country = null;
		
		Connection connection = (Connection) servletContext.getAttribute("dbConn");
		CountryDAO countryDao = new JDBCCountryDAOImpl();
		countryDao.setConnection(connection);
		
		country = countryDao.get(countryId);
		
		return country;
	}
}
