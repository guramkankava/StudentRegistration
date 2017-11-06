package ge.gngapps.exception;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExceptionMessage {
	
	private String message;
	private int errorCode;
	private String documentation;
	
	public ExceptionMessage( ) {
		
	}
	
	public ExceptionMessage(String message, int errorCode, String documentation) {
		super();
		this.message = message;
		this.errorCode = errorCode;
		this.documentation = documentation;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
	
	
	
	
}
