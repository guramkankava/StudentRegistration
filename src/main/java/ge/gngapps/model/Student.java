package ge.gngapps.model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
	
	private Integer id;
	private String firstName;
	private String lastName;
	
	public Student () {}
	
	public Student (int id, String firstName, String lastName ) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
