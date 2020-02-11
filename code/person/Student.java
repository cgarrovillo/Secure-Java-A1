package person;

import java.io.Serializable;

/* 4E. Limit extensibility of classes
 * Prevents this class from being extended 
 */
public final class Student implements Serializable {
	static final long serialVersionUID = 1L; // Use for serialization.

	private Person p;
	/*
	 * 6A. Prefer immutability for value types. Prevents this class' attributes from
	 * being manipulated at any time
	 */
	private final String id;
	private final String gpa;

	/*
	 * 7A. Avoid exposing constructors of sensitive types. Provides for Student
	 * object instantiation by returning a new instance of a Student object using a
	 * static constructor.
	 */
	public static Student getInstance(String name, String age, String id, String gpa) {
		return new Student(name, age, id, gpa);
	}

	// Constuctors
	protected Student() {
		id = "";
		gpa = "0.0";
	}

	protected Student(String name, String age, String id, String gpa) {
		p = Person.getInstance(name, age);

		checkID(id);
		checkGPA(gpa);

		this.id = id;
		this.gpa = gpa;
	}

	// Getters
	public String getName() {
		return p.getName();
	}

	public String getAge() {
		return p.getAge();
	}

	public String getId() {
		return id;
	}

	public String getGpa() {
		return gpa;
	}

	@Override
	public String toString() {
		return "Student: " + getName() + "," + getAge() + "," + id + "," + gpa;
	}

	// Rules for data
	/*
	 * 5A. Validate user inputs. Prevents strange input from performing malicious
	 * behaviour.
	 */
	private void checkID(String id) {
		try {
			if (id == null || id.length() != 9)
				throw new IllegalArgumentException("Invalid Student ID value");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	private void checkGPA(String gpa) {
		try {

			float gpaValue = Float.parseFloat(gpa);

			if (gpa == null || gpa.length() == 0 || gpaValue < 0.0f || gpaValue > 4.0f)
				throw new IllegalArgumentException("Invalid Student GPA value");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

}
