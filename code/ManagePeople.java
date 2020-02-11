import java.io.File;
import java.io.FileNotFoundException;
import person.Person;
import person.Staff;
import person.Student;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public final class ManagePeople {
	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		/*
		 * 9A. Access Control. Prevents this main class from being executed without the
		 * use of a Java Security Manager
		 */
		SecurityManager christiansis = System.getSecurityManager();
		if (christiansis == null) {
			System.out.println("No security manager available.");
			System.exit(0);
		}

		// Load (deserialized) object from data directory into objects in ArrayList
		ArrayList<Object> people = loadPeople();
		String option = null;

		// Main menu loop starts here
		do {
			option = showMenu();

			switch (option) {
			case "1": // List people
				listPeople(people);
				break;
			case "2": // Add a new person (of any type) to the ArrayList
				addPerson(people);
				break;
			case "3": // Delete someone
				deletePerson(people);
				break;
			}

		} while (!option.equals("0")); // End of main menu loop

		try {
			if (s != null)
				s.close();
			// System.out.println("closed");
		} catch (Exception e) {
		}

	} // End of main

	// ==========================================================
	// Display menu and get selection from user
	private static String showMenu() {
		// Scanner s = new Scanner(System.in);

		String option = null;

		System.out.println("\nMENU\n----");
		System.out.println("1. List people");
		System.out.println("2. Add person/student/staff");
		System.out.println("3. Delete person/student/staff");
		System.out.println("0. Exit");
		option = s.nextLine();
		return option;
	}

	// ==========================================================
	// List all people of all types
	private static void listPeople(ArrayList<Object> people) {
		System.out.println("LIST OF PEOPLE\n--------------");

		for (Object p : people) {
			System.out.println(p.toString());
		}
	}

	// ==========================================================
	// Add person
	private static void addPerson(ArrayList<Object> people) throws FileNotFoundException, IOException {
		// Scanner s = new Scanner(System.in);
		String personType = null;
		String name = null;
		String age = null;

		System.out.println("ADD PERSON/STUDENT/STAFF\n------------------------");

		// Get specific type of person to add
		System.out.println("1. Add Person");
		System.out.println("2. Add Student");
		System.out.println("3. Add Staff");
		do {
			personType = s.nextLine();
		} while (!personType.matches("^[1-3]$"));

		// Get name and age for people of every type...
		System.out.println("Enter name: ");
		name = s.nextLine();

		System.out.println("Enter age: ");
		age = s.nextLine();

		// Get any extra data as required, add new person (of any type) to ArrayList
		switch (personType) {
		case "1": // Add Person
			Person person = Person.getInstance(name, age);
			/*
			 * 5A. Validate user inputs. Prevents strange input from performing malicious
			 * behaviour.
			 */
			if (person.getName().equals(name) && person.getAge().equals(age)) {
				people.add(person);
			} else {
				System.err.println("Invalid Person.");
			}
			break;

		case "2": // Add Student
			String id = null;
			String gpa = null;

			System.out.println("Enter ID: ");
			id = s.nextLine();

			System.out.println("Enter GPA: ");
			gpa = s.nextLine();

			Student student = Student.getInstance(name, age, id, gpa);
			/*
			 * 5A. Validate user inputs. Prevents strange input from performing malicious
			 * behaviour.
			 */
			if (student.getName().equals(name) && student.getAge().equals(age) && student.getId().equals(id)
					&& student.getGpa().equals(gpa)) {
				people.add(student);
			} else {
				System.err.println("Invalid Student.");
			}
			break;
		case "3": // Add Staff
			String sid = null;
			String salary = null;

			System.out.println("Enter Staff ID: ");
			sid = s.nextLine();

			System.out.println("Enter Salary: ");
			salary = s.nextLine();

			Staff staff = Staff.getInstance(name, age, sid, salary);
			/*
			 * 5A. Validate user inputs. Prevents strange input from performing malicious
			 * behaviour.
			 */
			if (staff.getName().equals(name) && staff.getAge().equals(age) && staff.getSid().equals(sid)
					&& staff.getSalary().equals(salary)) {
				people.add(staff);
			} else {
				System.err.println("Invalid Staff.");
			}
			break;
		}
		// Save new object list to data directory (rewrite directory contents)
		savePeople(people);
	}

	// ==========================================================
	// Delete person
	private static void deletePerson(ArrayList<Object> people) throws FileNotFoundException, IOException {
		String name = null;
		// Scanner s = new Scanner(System.in);

		System.out.println("DELETE PERSON\n-------------");
		System.out.print("Enter name of person to delete: ");
		name = s.nextLine();

		// Find object in ArrayList with name matching entered name and remove it from
		// ArrayList
		for (Object p : people) {
			if (p instanceof Person) {
				Person pp = (Person) p;
				if (pp.getName().equals(name)) {
					people.remove(p);
					break;
				}
			} else if (p instanceof Student) {
				Student sp = (Student) p;
				if (sp.getName().equals(name)) {
					people.remove(p);
					break;
				}
			} else if (p instanceof Staff) {
				Staff sfp = (Staff) p;
				if (sfp.getName().equals(name)) {
					people.remove(p);
					break;
				}
			}
		}

		// Resave object list to data directory (rewrites directory contents)
		savePeople(people);
	}

	// ==========================================================
	// ==========================================================
	// Saved serialized copy of object to disk
	private static void serializeObject(Object o) throws FileNotFoundException, IOException {
		// Get name attribute from object, use as serialized file name (assume no
		// conflicts)

		String name = "DEFAULT";
		if (o instanceof Person) {
			Person pp = (Person) o;
			name = pp.getName();
		} else if (o instanceof Student) {
			Student sp = (Student) o;
			name = sp.getName();
		} else if (o instanceof Staff) {
			Staff sfp = (Staff) o;
			name = sfp.getName();
		}

		FileOutputStream fout = new FileOutputStream("../data/" + name + ".ser");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(o);

		oos.close();
	}

	// ==========================================================
	// Load serialized version of object from disk
	private static Object deserializeObject(String filename)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		Object o = null;

		FileInputStream fis = new FileInputStream("../data/" + filename);
		ObjectInputStream in = new ObjectInputStream(fis);
		o = in.readObject();
		in.close();
		fis.close();

		return o;
	}

	// ==========================================================
	// Load serialized versions of all objects stored under the data subdirectory
	private static ArrayList<Object> loadPeople() throws IOException, ClassNotFoundException {
		ArrayList<Object> people = new ArrayList<Object>();

		File f = new File("../data/"); // Data directory location
		File[] files = f.listFiles(); // Get directory listing of data subdirectory

		// Let list of all files in data directory, deserialize each one back into an
		// object and
		// store reference in ArrayList.
		for (File file : files) {
			String filename = file.getName();

			Object o = deserializeObject(filename);
			people.add(o);
			// System.out.println(people);
		}

		return people;
	}

	// ==========================================================
	// Save serialized versions of all objects stored in ArrayList<Person> to disk
	private static void savePeople(ArrayList<Object> people) throws FileNotFoundException, IOException {
		// 1. Delete all existing files from data directory
		File f = new File("../data/");
		File[] files = f.listFiles();

		for (File file : files)
			file.delete();

		// 2. Write out new serialized copies of all objects in ArrayList
		for (Object p : people) {
			serializeObject(p);
		}
	}

}
