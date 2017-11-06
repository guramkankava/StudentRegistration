package ge.gngapps.exception;

public class StudentApplicationException extends RuntimeException {
	
	private static final long serialVersionUID = 1681171031249249122L;

	public StudentApplicationException() {
		super();
	}
	
	public StudentApplicationException(String message) {
		super(message);
	}
}
