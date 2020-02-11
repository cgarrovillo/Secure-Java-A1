package person;

import java.io.Serializable;

/* 4E. Limit extensibility of classes
 * Prevents this class from being extended 
 */
public final class Staff implements Serializable {
	static final long serialVersionUID = 1L; // Use for serialization.

	private Person p;

	/*
	 * 6A. Prefer immutability for value types. Prevents this class' attributes from
	 * being manipulated at any time
	 */
	private final String sid;
	private final String salary;

	/*
	 * 7A. Avoid exposing constructors of sensitive types. Provides for Person
	 * object instantiation by returning a new instance of a Person object using a
	 * static constructor.
	 */
	public static Staff getInstance(String name, String age, String sid, String salary) {
		return new Staff(name, age, sid, salary);
	}

	// Constuctors
	private Staff() {
		sid = "";
		salary = "0.0";
	}

	private Staff(String name, String age, String sid, String salary) {
		p = Person.getInstance(name, age);

		checkSID(sid);
		checkSalary(salary);

		this.sid = sid;
		this.salary = salary;
	}

	// Getters
	public String getName() {
		return p.getName();
	}

	public String getAge() {
		return p.getAge();
	}

	public String getSid() {
		return sid;
	}

	public String getSalary() {
		return salary;
	}

	@Override
	public String toString() {
		return "Staff: " + p.getName() + "," + p.getAge() + "," + sid + "," + salary;
	}

	// Rules for data
	/*
	 * 5A. Validate user inputs. Prevents strange input from performing malicious
	 * behaviour.
	 */
	private void checkSID(String sid) {
		try {
			if (sid == null || sid.length() != 9)
				throw new IllegalArgumentException("Invalid Staff ID value");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	private void checkSalary(String salary) {
		try {
			float salaryValue = Float.parseFloat(salary);

			if (salary == null || salary.length() == 0 || salaryValue < 0.0f || salaryValue > 100000.0f)
				throw new IllegalArgumentException("Invalid Staff salary value");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

}
