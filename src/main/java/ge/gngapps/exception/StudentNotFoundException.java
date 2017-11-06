package ge.gngapps.exception;

public class StudentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6716233662062423553L;
	
	public StudentNotFoundException () {
		super();
	}
	
	public StudentNotFoundException (String message) {
		super(message);
	}

}
