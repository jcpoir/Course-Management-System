package roles;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import courses.Course;
import files.FileInfoReader;

/**
 * Represents the account of a student in the system.
 * @author josephpoirier
 *
 */
public class Student extends User{
	
	ArrayList <Course> courses;
	ArrayList <String> grades;
	
	public Student() {
	}
	
	/**
	 * Pseudo-main method. Carries out action based on user choice
	 * @param student, a User object
	 * @param choice, integer choice
	 * @param courses, ArrayList of course objects
	 * @return done, if user is done making actions
	 */
	public static boolean execute(User student, int choice, ArrayList<Course> courses) {
		
		// define output boolean (has action been executed successfully)
		boolean done = false;
		
		// view all courses
		if (choice == 1) {
			
			System.out.println("\n[✓] Displaying all courses:\n");
			
			// get courses as string array using Course class (static)
			Course.displayCourses(courses);
		}
		
		// add courses
		else if (choice == 2) {	
			
			// get user input for course code
			Scanner sc = new Scanner(System.in);
			System.out.println("\nPlease enter the code of the course you wish to add:");
			String ID = sc.nextLine();
			
			// if course doesn't have a time conflict, try to add it
			if (!Course.doesCourseConflict(Course.getCourseByID(courses, ID), student.getCourses())) {
				student.addCourseByID(ID, courses);
			}
			
			else System.out.println("\n[✘] Course not added — lecture conflicts with current schedule.\n");
		}
		
		// view enrolled courses
		else if (choice == 3) {
			
			System.out.println("\n[✓] Displaying enrolled courses:\n");
			
			Course.displayCourses(student.getCourses());
			
		}
		
		// drop course in your list
		else if (choice == 4) {
			
			// get user input for course code
			Scanner sc = new Scanner(System.in);
			System.out.println("\nPlease enter the code of the course you wish to drop:");
			String ID = sc.nextLine();
			
			// try to drop class by ID
			student.dropCourseByID(ID);
		}
		
		// view grades
		else if (choice == 5) {
			
			System.out.println("\n[✓] Displaying grades:\n");
			
			student.displayGrades();
			System.out.println("");
		}
		
		// return to previous menu
		else if (choice == 6) {
			
			System.out.println("\n[<] Returning to previous . . .");
			
			// end loop
			done = true;
		}
		
		else System.out.println("\n[✘] Invalid selection — out of bounds.\n");
		
		return done;
	}
	
	/**
	 * Process the last field of the student text document into
	 * an arraylist of courses
	 * @param courseInfo, a string containing course IDs and grades
	 * @param courses, an ArrayList of all course objects
	 * @return outList, an ArrayList of enrolled course objects
	 */
	public static ArrayList <Course> processCourseInfo(String courseInfo, ArrayList<Course> courses) {
		
		// define output list
		ArrayList <Course> outList = new ArrayList <Course>();
		String ID;
		
		String[] courseInfoArray = courseInfo.split(",");
		
		// get IDs for each course, for use in getting course objects
		for (String courseInfo1 : courseInfoArray) {
			
			ID = courseInfo1.split(":")[0].trim();
			outList.add(Course.getCourseByID(courses, ID));
		}
		
		return outList;
	}
	
	/**
	 * Process the fourth term of the student info text file into an array of grade
	 * strings
	 * @param courseInfo, raw data from text file
	 * @return ArrayList<String> outList, an arraylist of course-grade pairs
	 */
	public static ArrayList <String> processGrades(String courseInfo) {
		
		// define output list
		ArrayList<String> outList = new ArrayList<String>();
		
		// split courses into String array
		String[] courseInfoArray = courseInfo.split(",");
		
		// remove whitespace from each course and add to output
		for (String courseInfo1 : courseInfoArray) {
			outList.add(courseInfo1.trim());
		}
		
		return outList;
	}		

	/**
	 * Displays grade array to console as printed message
	 */
	public void displayGrades() {
		
		for (String grade : this.grades) {
			System.out.println(grade);
		}
	}
	
	/**
	 * If course with given ID is present in courselist, drops course
	 * otherwise, prints error message and does nothing
	 */
	public void dropCourseByID(String ID) {
		
		// track if drop is successful
		boolean dropped = false;
		
		ArrayList<Course> courses = getCourses();
		
		int i = 0;
		// look through all courses for ID match
		for (Course course: courses) {
			if (course.getID().equals(ID)) {
				
				// if a match is found, remove it
				dropped = true;
				
				System.out.println("\n[✓] " + ID + " dropped successfully!\n");
				break;
			}
			i++;
		}
		
		// if a match is found, remove (can't remove from arraylist while iterating, so this is the workaround
		if (dropped) {
			courses.remove(i);
			
			// also remove grade for the class from your grades
			ArrayList<String> grades = getGrades();
			grades.remove(i);
			setGrades(grades);
		}
		
		// if course not found, print error message
		if (!dropped) {
			System.out.println("\n[✘] Drop unsuccessful for course ID: " + ID + ".\n");
		}
		
		setCourses(courses);
	}
	
	/**
	 * Tries to add course object to student courses attribute list. If null, does nothing and
	 * prints error message to console.
	 */
	public void addCourseByID(String ID, ArrayList<Course> courses) {
		
		// try to get course to be added
		Course course = Course.getCourseByID(courses, ID);
		
		if (course != null) {
			
			// get student courses, and add new course
			ArrayList<Course> courses_ = getCourses();
			courses_.add(course);
			setCourses(courses_);
			
			// add course to grades, too
			ArrayList<String> grades_ = getGrades();
			grades_.add(course.getID() + ": N/A");
			setGrades(grades_);
			
			System.out.println("\n[✓] Course added successfully!\n");
		}
		
		// if ID isn't in reference list, print error message
		else System.out.println("\n[✘] Course not added — invalid ID: " + ID + ".\n");
	}
	
	/**
	 * Get an ArrayList of all students that are enrolled in a specific class (by ID)
	 * @param courseID, String ID code for course
	 * @param students, ArrayList<Student>
	 * @return outList, ArrayList of enrolled students
	 */
	public static ArrayList<User> getStudentsByCourseID(String courseID, ArrayList<User> students) {
		
		// define output list
		ArrayList<User> outList = new ArrayList<User>();
		
		for (User student : students) for (Course course : student.getCourses()){
			
			// for all courses of all students, if course ID matches specified ID, add student to output
			if (course.getID().equals(courseID)) outList.add(student);
		}		
		return outList;		
	}
	
	/**
	 * Display all students (print to console)
	 * @param students, ArrayList of User objects
	 */
	public static void displayStudents(ArrayList<User> students) {
		
		for (User student: students) System.out.println(student.getName());
	}
	
	// getters & setters
	
	public void setCourses(ArrayList<Course> courseList) {this.courses = courseList;};
	public ArrayList<Course> getCourses() {return this.courses;};
	
	public void setGrades(ArrayList<String> grades) {this.grades = grades;}
	public ArrayList<String> getGrades() {return this.grades;}

	@Override
	protected void setProf(String prof) {
		// TODO Auto-generated method stub
		
	};
}
