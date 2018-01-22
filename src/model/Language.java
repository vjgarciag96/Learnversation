package model;

import java.util.List;

public class Language {
	private long idl;
	private String langname;
	
	
	
	public boolean validate(List<String> validationMessages) {
	if (langname == null || langname.trim().isEmpty()) {
		validationMessages.add("Rellena el nombre de usuario.");
	} else if (langname.length() > 16) {
		validationMessages.add("El nombre del lenguaje no puede sobrepasar 16 caracteres.");
	} else if (langname.length() < 3) {
		validationMessages.add("El nombre del lenguaje debe ser de al menos 3 caracteres.");
	} else if (langname.contains(" ")) {
		validationMessages.add("El nombre del lenguaje no puede contener espacios.");
	} else if (!langname.matches("[a-zA-Z][a-zA-Z]*")) {
		validationMessages.add("Nombre de lenguaje no válido.");
	}

	if (validationMessages.isEmpty())
		return true;
	else
		return false;
	}
	
	public long getIdl() {
		return idl;
	}
	public void setIdl(long idl) {
		this.idl = idl;
	}
	public String getLangname() {
		return langname;
	}
	public void setLangname(String langname) {
		this.langname = langname;
	}
	
	
	


}
