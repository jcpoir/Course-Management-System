package roles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import courses.Course;
import files.FileInfoReader;

/**
 * Represents the account of an administrator in the system.
 * @author josephpoirier
 *
 */
public class Admin extends User{
	
	/**
	 * Constructor
	 */
	public Admin() {
	}
	
	/**
	 * Gets user input to create and configure a new course
	 * then adds to the list. If course of same ID is currently in
	 * the system, doesn't add.
	 */
	public static void addCourse(ArrayList<Course> courses) {
		
		// define scanner, start getting input
		Scanner sc = new Scanner(System.in);
		System.out.println("\nPlease enter the ID of the course you'd like to add:");
		String ID = sc.nextLine();
		
		if (Course.isIn(courses, ID)) {
			System.out.println("\n[✘] Error — " + ID + " is already in the system!\n");
		}
		
		else {
			System.out.println("\nPlease enter the name of the course you'd like to add:");
			String name = sc.nextLine();
			
			System.out.println("\nPlease enter the name of the professor you'd like to add:");
			String prof = sc.nextLine();
			
			System.out.println("\nPlease enter a start time for the class:");
			String start  = sc.nextLine();
			
			System.out.println("\nPlease enter an end time for the class:");
			String end  = sc.nextLine();
			
			System.out.println("\nPlease enter days when the class will meet (i.e. MWF):");
			String days = sc.nextLine();
			
			System.out.println("\nPlease enter the course capacity:");
			String capacity = sc.nextLine();
			
			// make new professor object according to user specs
			Course newCourse = new Course();
			
			// set attributes
			newCourse.setName(name);
			newCourse.setID(ID);
			newCourse.setProf(prof);
			newCourse.setStart(start);
			newCourse.setEnd(end);
			newCourse.setDays(days);
			newCourse.setCapacity(capacity);
			
			// add new professor to arraylist
			courses.add(newCourse);
			
			System.out.println("\n[✓] Course " + name + " successfully added to the database.\n");
		}
	}
	
	/**
	 * Gets user input to create and configure a new professor
	 * then adds to the list. If professor of same ID is currently in
	 * the system, doesn't add.
	 */
	public static void addProfessor(ArrayList<User> professors) {
		
		// define scanner, start getting input
		Scanner sc = new Scanner(System.in);
		System.out.println("\nPlease enter the ID of the professor you'd like to add:");
		String ID = sc.nextLine();
		
		if (User.isIn(professors, ID)) {
			System.out.println("\n[✘] Error — " + ID + " is already in the system!\n");
		}
		
		else {
			System.out.println("\nPlease enter the name of the professor you'd like to add:");
			String name = sc.nextLine();
			
			System.out.println("\nPlease enter the username of the professor you'd like to add:");
			String username = sc.nextLine();
			
			System.out.println("\nPlease enter the password of the professor you'd like to add:");
			String password = sc.nextLine();
			
			// make new professor object according to user specs
			User newProf = new Prof();
			
			// set attributes
			newProf.setName(name);
			newProf.setID(ID);
			newProf.setUsername(username);
			newProf.setPassword(password);
			
			// add new professor to arraylist
			professors.add(newProf);
			
			System.out.println("\n[✓] Professor " + name + " successfully added to the database.\n");
		}
	}
	
	/**
	 * Gets user input to create and configure a new student
	 * then adds to the list. If student of same ID is currently in
	 * the system, doesn't add.
	 */
	public static void addStudent(ArrayList<User> students, ArrayList<Course> courses) {
		
		// define scanner, start getting input
		Scanner sc = new Scanner(System.in);
		System.out.println("\nPlease enter the ID of the student you'd like to add:");
		String ID = sc.nextLine();
		
		if (User.isIn(students, ID)) {
			System.out.println("\n[✘] Error — " + ID + " is already in the system!\n");
		}
		
		else {
			System.out.println("\nPlease enter the name of the student you'd like to add:");
			String name = sc.nextLine();
			
			System.out.println("\nPlease enter the username of the student you'd like to add:");
			String username = sc.nextLine();
			
			System.out.println("\nPlease enter the password of the student you'd like to add:");
			String password = sc.nextLine();
			
			// make new professor object according to user specs
			User newStud = new Student();
			
			// set attributes
			newStud.setName(name);
			newStud.setID(ID);
			newStud.setUsername(username);
			newStud.setPassword(password);
			
			// add new professor to arraylist
			students.add(newStud);
			
			// add courses
			boolean done = false;
			
			String courseID; String grade;
			while (!done) {
				
				// get course ID and get course
				System.out.println("\nPlease enter the ID of a course that the student has taken (or press 'n' if done:)");
				courseID = sc.nextLine();
				
				// break execution if user enters n, as specified
				if (courseID.equals("n")) {
					break;
				}
				
				Course course = Course.getCourseByID(courses, courseID);
				
				// if course isn't in database, print message and try again
				if (course == null) {
					System.out.println("\n[✘] Invalid selection — course ID not in database.");
					continue;
				}
				
				// otherwise, get grade
				System.out.println("\nStudent's grade for " + courseID + ":");
				grade = sc.nextLine();
				
				// assign courses to Student object
				ArrayList<Course> studCourses = newStud.getCourses();
				
				// if no courses, assign new array
				if (studCourses == null) {
					ArrayList<Course> studCourses_ = new ArrayList<Course>();
					studCourses_.add(course);
					newStud.setCourses(studCourses_);
				}
				
				else studCourses.add(course);
				
				// assign grades to Student object
				ArrayList<String> grades = newStud.getGrades();
				
				// if no courses, assign new array
				if (grades == null) {
					ArrayList<String> grades_ = new ArrayList<String>();
					grades_.add(grade);
					newStud.setGrades(grades_);
				}
				
				else grades.add(courseID + ":" + grade);
			}
			
			System.out.println("\n[✓] Student " + name + " successfully added to the database.\n");
		}
	}
	
	/**
	 * Pseudo-main method. Carries out action based on user choice
	 * @param student, a User object
	 * @param choice, integer choice
	 * @param courses, ArrayList of course objects
	 * @return done, if user is done making actions
	 */
	public static boolean execute(User admin, int choice, ArrayList<Course> courses, ArrayList<User> students, ArrayList <User> profs) {
		
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
			
			addCourse(courses);
		}
		
		// delete course
		else if (choice == 3) {	
			
			// get user input for course ID
			Scanner sc = new Scanner(System.in);
			System.out.println("\nPlease enter the ID of the professor you'd like to delete:");
			String ID = sc.nextLine();
			
			// delete course
			Course.deleteCourseByID(ID.trim(), courses);
			
		}
		
		// add professor
		else if (choice == 4) {
			
			addProfessor(profs);
		}
		
		// delete professor
		else if (choice == 5) {
			
			// get user input for student ID
			Scanner sc = new Scanner(System.in);
			System.out.println("\nPlease enter the ID of the course you'd like to delete:");
			String ID = sc.nextLine();
			
			// delete course
			User.deleteByID(ID.trim(), profs);
			
		}
		
		// add student
		else if (choice == 6) {
			addStudent(students, courses);
		}
		
		// delete student
		else if (choice == 7) {
			
			// get user input for student ID
			Scanner sc = new Scanner(System.in);
			System.out.println("\nPlease enter the ID of the student you'd like to delete:");
			String ID = sc.nextLine();
			
			// delete student
			User.deleteByID(ID.trim(), students);
		}
		
		// return to previous menu
		else if (choice == 8) {
			
			System.out.println("\n[<] Returning to previous . . .");
			
			// end loop
			done = true;
		}
		
		else System.out.println("\n[✘] Invalid selection — out of bounds.\n");
		
		return done;
	}

	@Override
	ArrayList<Course> getCourses() {
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
