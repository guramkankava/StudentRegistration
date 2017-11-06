package ge.gngapps.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ge.gngapps.resources.StudentResource;

@Provider
public class StudentNotFoundExceptionMapper implements ExceptionMapper<StudentNotFoundException> {
	private static final Logger logger = LogManager.getLogger(StudentResource.class.getName());
	@Override
	public Response toResponse(StudentNotFoundException exception) {
		logger.error(exception.getMessage());
		return Response.status(Status.NOT_FOUND).entity( new ExceptionMessage( exception.getMessage(), 404, "gngapps.ge") ).build();
	}

}
