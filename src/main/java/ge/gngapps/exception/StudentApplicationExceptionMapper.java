package ge.gngapps.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ge.gngapps.service.StudentService;

@Provider
public class StudentApplicationExceptionMapper implements ExceptionMapper<Throwable> {
	private static final Logger logger = LogManager.getLogger(StudentService.class.getName());
	@Override
	public Response toResponse(Throwable exception) {
		logger.error(exception.getMessage());
		return Response.serverError().entity(new ExceptionMessage(exception.getMessage(), 500, "gngapps.ge")).build();
	}

	

}
