package person;

import java.io.Serializable;

/* 4E. Limit extensibility of classes
 * Prevents this class from being extended 
 */
public final class Person implements Serializable {
	private static final long serialVersionUID = 1L; // Use for serialization.

	/*
	 * 6A. Prefer immutability for value types. Prevents this class' attributes from
	 * being manipulated at any time
	 */
	private final String name;
	private final String age;

	/*
	 * 7A. Avoid exposing constructors of sensitive types. Provides for Person
	 * object instantiation by returning a new instance of a Person object using a
	 * static constructor.
	 */
	public static Person getInstance(String name, String age) {
		return new Person(name, age);
	}

	// Constuctors
	protected Person() {
		name = "";
		age = "0";
	}

	protected Person(String name, String age) {
		// Check provided argument values are correct.
		checkName(name);
		checkAge(age);

		this.name = name;
		this.age = age;
	}

	// Getters
	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Person: " + name + "," + age;
	}

	// Rules for data
	/*
	 * 5A. Validate user inputs. Prevents strange input from performing malicious
	 * behaviour.
	 */
	private void checkName(String name) {

		try {
			if (name == null || name.length() == 0)
				throw new IllegalArgumentException("Missing Person name");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	private void checkAge(String age) {
		try {
			if (age == null || age.length() == 0)
				throw new IllegalArgumentException("Missing Person age");

			int ageValue = Integer.parseInt(age);

			if (ageValue < 0 || ageValue > 125)
				throw new IllegalArgumentException("Person age value out of range");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

}
