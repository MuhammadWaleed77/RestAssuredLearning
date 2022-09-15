package pojo;

public class GetCourse {

	// Serelization and Decerelization

	private String expertise;
	private Cources Courses; // change return type to class for nested json {}
	private String instructor;
	private String linkedIn;
	private String url;

	// for getter setter select var right click sources ->getter setter

	// NOTE: when you have nested json {"course: abc[0], def[2]"} as the json has
	// data but courses are nested so create new pojo class for nested
	// if json returns string it ok else if it returns nested json/array then create
	// new pojo class

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public Cources getCourses() {
		return Courses;
	}

	public void setCourses(Cources courses) {
		Courses = courses;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	private String services;

}
