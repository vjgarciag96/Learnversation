package exceptions;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


public class CustomBadRequestException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -402752772426499120L;

	public CustomBadRequestException() {
		super(Response
				  .status(Response.Status.BAD_REQUEST)
				  .build());
		
	}
	
	public static String getValidationMessage(List<String> messages){
		String message = "";
		if(messages.size() > 1){
		for(int i = 0; i< messages.size(); i++){
			message = message + messages.get(i) + ", ";
		}
		}
		else
			message = messages.get(0);
		return message;
	}
	
	public CustomBadRequestException(String messages) {
		super(Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"status\" : \"404\", \"userMessage\" : \""+messages+"\"}")
				.type("application/json")
				.build());
	}
}
