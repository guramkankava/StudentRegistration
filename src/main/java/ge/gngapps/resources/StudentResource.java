package ge.gngapps.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ge.gngapps.exception.StudentApplicationException;
import ge.gngapps.model.Student;
import ge.gngapps.service.StudentService;

@Path("/students")
public class StudentResource {
	@Context
	private ServletContext context; 
	@Inject
	private StudentService studentService;
	
	private static final Logger logger = LogManager.getLogger(StudentResource.class.getName());
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudent( @PathParam("id") int id) {
			Student student = studentService.getStudent(id, context);
			logger.info("Get Student object : " + student);
			return Response.ok().entity(student).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{firstName}/{lastName}")
	public Response registerStudent (  @PathParam("firstName") String firstName, @PathParam("lastName") String lastName ) {
		try {
			Student registerStudent = studentService.registerStudent(firstName, lastName, context);
			logger.info("Created student : " + registerStudent);
			return Response.created(new URI("/students/" + registerStudent.getId().toString())).entity(registerStudent).build();
		} catch (URISyntaxException e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} 
	}
	
	@PUT
	@Path("/{id}/{firstName}/{lastName}")
	public Response editStudent (@PathParam("id") int id, @PathParam("firstName") String firstName, @PathParam("lastName") String lastName ) {
		try {
			studentService.editStudent(id, firstName, lastName, context);
			logger.info("Edited student : {" + id + ", " + firstName + ", " + lastName + "}");
			return Response.ok().build();
		} catch (StudentApplicationException e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
