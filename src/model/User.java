package model;

import java.util.List;

public class User {

	private long idu;
	private String username;
	private String password;
	private String email;
	private String gender;
	private String country;
	private String birthDate;
	private String exchangeTypes;
	private long idi;

	public User() {

	}

	public User(String username, String password, String email, String gender, String country, String birthDate) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.birthDate = birthDate;
	}

	public User(String username, String password, String email, String gender, String country, String birthDate,
			String exchangeTypes) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.birthDate = birthDate;
		this.exchangeTypes = exchangeTypes;
	}

	public User(String username, String password, String email, String gender, String country, String birthDate,
			String exchangeTypes, long idi) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.birthDate = birthDate;
		this.exchangeTypes = exchangeTypes;
		this.idi = idi;
	}
	
	public User(long idu, String username, String email, String gender, String country,
			String birthDate, String exchangeTypes, long idi) {
		this.idu = idu;
		this.username = username;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.birthDate = birthDate;
		this.exchangeTypes = exchangeTypes;
		this.idi = idi;
	}
	
	public User(long idu, String username, String password, String email, String gender, String country,
			String birthDate, String exchangeTypes, long idi) {
		this.idu = idu;
		this.username = username;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.birthDate = birthDate;
		this.exchangeTypes = exchangeTypes;
		this.idi = idi;
	}

	public User(long idu, String username, String password, String email, String gender, String country,
			String birthDate, String exchangeTypes) {
		this.idu = idu;
		this.username = username;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.birthDate = birthDate;
		this.exchangeTypes = exchangeTypes;
	}

	public boolean validate(List<String> validationMessages) {
		if (username == null || username.trim().isEmpty()) {
			validationMessages.add("Rellena el nombre de usuario.");
		} else if (username.length() > 16) {
			validationMessages.add("El nombre de usuario no puede sobrepasar 16 caracteres.");
		} else if (username.length() < 3) {
			validationMessages.add("El nombre de usuario debe ser de al menos 3 caracteres.");
		} else if (username.contains(" ")) {
			validationMessages.add("El nombre de usuario no puede contener espacios.");
		} else if (!username.matches("[a-zA-Z][a-zA-Z0-9_-]*")) {
			validationMessages.add("Nombre de usuario no válido. Solo son validos nombres con letras, numeros, - y _.");
		}

		if (password == null || password.trim().isEmpty()) {
			validationMessages.add("Rellena la contraseña.");
		} else if (password.length() > 40) {
			validationMessages.add("La contraseña no puede sobrepasar los 40 caracteres.");
		} else if (password.length() < 6) {
			validationMessages.add("La contraseña debe tener al menos 6 caracteres.");
		} else if (password.contains(" ")) {
			validationMessages.add("La contraseña no puede contener espacios.");
		} else if (!password.matches("(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*")) {
			validationMessages.add("Contraseña no valida. Debe contener al menos una mayúscula y un número.");
		}

		if (email == null || email.trim().isEmpty()) {
			validationMessages.add("Rellena el email.");
		} else if (email.length() > 50) {
			validationMessages.add("El email no puede sobrepasar los 50 car�cteres.");
		} else if (email.contains(" ")) {
			validationMessages.add("El email no puede contener espacios.");
		} else if (!email.matches("[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+")) {
			validationMessages.add("Email no válido.");
		}

		if (validationMessages.isEmpty())
			return true;
		else
			return false;
	}

	public boolean validateEdition(List<String> validationMessages) {
		if (username == null || username.trim().isEmpty()) {
			validationMessages.add("Rellena el nombre de usuario.");
		} else if (username.length() > 16) {
			validationMessages.add("El nombre de usuario no puede sobrepasar 16 caracteres.");
		} else if (username.length() < 3) {
			validationMessages.add("El nombre de usuario debe ser de al menos 3 caracteres.");
		} else if (username.contains(" ")) {
			validationMessages.add("El nombre de usuario no puede contener espacios.");
		} else if (!username.matches("[a-zA-Z][a-zA-Z0-9_-]*")) {
			validationMessages.add("Nombre de usuario no válido. Solo son validos nombres con letras, numeros, - y _.");
		}

		if (email == null || email.trim().isEmpty()) {
			validationMessages.add("Rellena el email.");
		} else if (email.length() > 50) {
			validationMessages.add("El email no puede sobrepasar los 50 car�cteres.");
		} else if (email.contains(" ")) {
			validationMessages.add("El email no puede contener espacios.");
		} else if (!email.matches("[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+")) {
			validationMessages.add("Email no válido.");
		}

		if (validationMessages.isEmpty())
			return true;
		else
			return false;
	}

	public long getIdu() {
		return idu;
	}

	public void setIdu(long idu) {
		this.idu = idu;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getExchangeTypes() {
		return exchangeTypes;
	}

	public void setExchangeTypes(String exchangeTypes) {
		this.exchangeTypes = exchangeTypes;
	}

	@Override
	public String toString() {
		return "Usuario " + username + ", email " + email;
	}

	/**
	 * @return the idi
	 */
	public long getIdi() {
		return idi;
	}

	/**
	 * @param idi
	 *            the idi to set
	 */
	public void setIdi(long idi) {
		this.idi = idi;
	}

}
