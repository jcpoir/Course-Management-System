package roles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import courses.Course;
import files.FileInfoReader;

/**
 * Represents the account of a professor in the system.
 * @author josephpoirier
 *
 */
public class Prof extends User{
	
	/**
	 * Constructor
	 */
	public Prof() {	
	}

	/**
	 * Pseudo-main method. Carries out action based on user choice
	 * @param prof, a user object
	 * @param choice, integer choice
	 * @param courses, ArrayList of course objects
	 * @return done, if user is done making actions
	 */
	public static boolean execute(User prof, int choice, ArrayList<Course> courses, ArrayList<User> students) {
		
		// define output boolean (has action been executed successfully)
		boolean done = false;
		
		// view all courses
		if (choice == 1) {
			
			System.out.println("\n[✓] Displaying given courses:\n");
			
			// get courses taught by professor
			ArrayList<Course> courses_ = Course.getCoursesByProf(courses, prof.getName());
			
			// get courses as string array using Course class (static)
			Course.displayCourses(courses_);
		}
		
		// get student names for course
		else if (choice == 2) {
			
			// get course ID from professor
			Scanner sc = new Scanner(System.in);
			System.out.println("\nPlease enter the course ID:");
			String ID = sc.nextLine();
			
			// get all courses taught by prof
			ArrayList<Course> taughtCourses = Course.getCoursesByProf(courses, prof.getName());
				
			// check if ID matches one of prof's courses
			boolean validCourse = false;
			
			// iterate through courses. If one has same ID as specified, entry is valid
			for (Course course : taughtCourses) {
				if (course.getID().equals(ID)) validCourse = true;
			}
			
			if (validCourse) {
				System.out.println("\n[✓] Displaying students in " + ID + ":");
				
				// get students enrolled in class
				ArrayList<User> enrolledStudents = Student.getStudentsByCourseID(ID, students);
				
				// print out students by name
				Student.displayStudents(enrolledStudents);
				
				System.out.println("");
			}
			
			else System.out.println("\n[✘] Invalid course ID.\n");
			
		}
		
		// return to main
		else if (choice == 3) {
			
			System.out.println("\n[<] Returning to previous . . .");
			
			// end loop
			done = true;
			
		}
		
		else System.out.println("\n[✘] Invalid selection — out of bounds.\n");
		
		return done;
	}

	@Override
	public ArrayList<Course> getCourses() {
		return null;
	}

	@Override
	void displayGrades() {
	}

	@Override
	void dropCourseByID(String ID) {
	}

	@Override
	void addCourseByID(String iD2, ArrayList<Course> courses) {
	}

	@Override
	protected ArrayList<String> getGrades() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setCourses(ArrayList<Course> courses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setGrades(ArrayList<String> grades_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setProf(String prof) {
		// TODO Auto-generated method stub
		
	}
}
