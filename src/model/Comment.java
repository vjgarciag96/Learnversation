package model;

import java.util.List;

public class Comment {
	
	
	
	private long idc;
	private long sender;
	private long receiver;
	private String timeStamp;
	private String text;
	
	
	public Comment(long sender, long receiver, String text){
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
	}
	
	public Comment(){
		
	}
	
	public boolean validate(List<String> validationMessages) {

		if (text == null || text.trim().isEmpty() || text.length() < 11) {
			validationMessages.add("El comentario debe incluir más de 10 caracteres.");
		} else if (text.length() > 400) {
			validationMessages.add("El contenido no puede sobrepasar los 400 caracteres.");
		} else if(text.contains("-")) {
			validationMessages.add("El mensaje no puede contener guiones");
		} else if(text.contains("<") || text.contains(">")){
			validationMessages.add("El mensaje no puede contener <,>");
		} else if(text.contains("'")){
			validationMessages.add("El mensaje no puede contener comillas simples");
		}

		if (validationMessages.isEmpty())
			return true;
		else
			return false;
	}

	public long getIdc() {
		return idc;
	}

	public void setIdc(long idc) {
		this.idc = idc;
	}

	public long getSender() {
		return sender;
	}

	public void setSender(long sender) {
		this.sender = sender;
	}

	public long getReceiver() {
		return receiver;
	}

	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
}
