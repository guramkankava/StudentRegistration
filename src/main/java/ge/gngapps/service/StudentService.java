package ge.gngapps.service;

import java.io.BufferedWriter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import ge.gngapps.exception.StudentApplicationException;
import ge.gngapps.exception.StudentNotFoundException;
import ge.gngapps.model.Student;

@Stateless
public class StudentService {

	private static final Logger logger = LogManager.getLogger(StudentService.class.getName());

	public Student getStudent(int studentId, ServletContext context) {
		Path path = Paths.get(context.getRealPath("/WEB-INF/Storage.json"));
		ObjectMapper mapper = new ObjectMapper();
		try ( Stream<String> lines = Files.lines(path) ) {
			Optional<Student> student = lines.filter(line -> !line.isEmpty()).map(line -> {
				try {
					return mapper.readValue(line, Student.class);
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
				return null;
			}).filter(user -> user.getId() == studentId).findFirst();
			if ( !student.isPresent() ) {
				throw new StudentNotFoundException("Student with the id : " + studentId + " not found");
			}
			return student.get();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new StudentApplicationException(e.getMessage());
		} 
	}

	public Student registerStudent(String firstName, String lastName, ServletContext context) throws StudentApplicationException {
		ObjectMapper mapper = new ObjectMapper();
		Path path = Paths.get(context.getRealPath("/WEB-INF/Storage.json"));
		try (Stream<String> lines = Files.lines(path); BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
			int studentId = lines.flatMap(line -> Arrays.stream(line.split(":"))).
					flatMap(line -> Arrays.stream(line.split(","))).
					filter(word -> word.matches("^-?\\d+$")).
					mapToInt(id -> Integer.parseInt(id)).
					max().
					getAsInt() + 1;
			Student student = new Student(studentId, firstName, lastName);
			String userAsJson = mapper.writeValueAsString(student);
			writer.write(userAsJson + "\n");
			return student;
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new StudentApplicationException(e.getMessage());
		}
	}

	public void editStudent(int id, String firstName, String lastName, ServletContext context) throws StudentApplicationException {
		Path path = Paths.get(context.getRealPath("/WEB-INF/Storage.json"));
		try ( Stream<String> lines = Files.lines(path) ) {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File(context.getRealPath("/WEB-INF/Storage.json"));
			file.delete();
			file.createNewFile();
			lines.filter(line -> !line.isEmpty()).
			map(line -> {
				try {
					return mapper.readValue(line, Student.class);
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
				return null;
			}).
			map(student -> {
				if (!firstName.isEmpty() && !lastName.isEmpty() && student.getId() == id) {
					student.setFirstName(firstName);
					student.setLastName(lastName);
					
					return student;
				}
				return student;
			}).sorted(Comparator.comparing(Student::getId)).forEach(user -> {
				try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
					String userAsJson = mapper.writeValueAsString(user);
					logger.info(userAsJson);
					writer.write(userAsJson + "\n");
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			});
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new StudentApplicationException(e.getMessage());
		}
	}

}
