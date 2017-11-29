package guru.springframework.exceptions;

public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -526092243645365329L;
	private final String message; 

	public NotFoundException() {
		message = "";
	}

	public NotFoundException(String notFoundException) {
		this.message = notFoundException;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
