import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import courses.Course;
import files.FileInfoReader;
import roles.Admin;
import roles.Prof;
import roles.Student;
import roles.User;

/**
 * Represents a full record organization object. This object directly orchestrates the
 * UI aspect of the project.
 * @author josephpoirier
 *
 */
public class Controller {
	
	static Scanner sc = new Scanner(System.in);
	static FileInfoReader fir = new FileInfoReader();
	
	ArrayList <User> students;
	ArrayList <User> admins;
	ArrayList <User> profs;
	ArrayList <Course> courses;
	
	/**
	 * Constructor
	 */
	public Controller() {
	}
	
	/**
	 * Prints a horizontal line to the console
	 */
	public static void printDashLine() {		
		for (int i = 0; i < 30; i++) System.out.print("—");
	}
	
	/**
	 * Prints opening message, with header and options menu
	 */
	public static void printGreeting() {
		
		// Header
		printDashLine();
		System.out.println("");
		System.out.println("Students Management System");
		printDashLine();
		System.out.println("");
		
		// Menu
		System.out.println("1 – Login as a student");
		System.out.println("2 – Login as a professor");
		System.out.println("3 – Login as a administrator");
		System.out.println("4 – Quit the system");
		System.out.println("");
	}
	
	/**
	 * Prints all menu options for users of type student
	 * @param student
	 */
	public static void printStudentOptions(User student) {
		
		// Header
		printDashLine();
		System.out.println("");
		System.out.println("Welcome, " + student.getName());
		printDashLine();
		System.out.println("");
		
		// Menu
		System.out.println("1 – View all courses");
		System.out.println("2 – Add courses to your list");
		System.out.println("3 – View enrolled courses");
		System.out.println("4 – Drop courses in your list");
		System.out.println("5 – View grades");
		System.out.println("6 – Return to previous");
		System.out.println("");
	}
	
	/**
	 * Prints all menu options for users of type prof
	 * @param student
	 */
	public static void printProfOptions(User prof) {
		
		// Header
		printDashLine();
		System.out.println("");
		System.out.println("Welcome, " + prof.getName());
		printDashLine();
		System.out.println("");
		
		// Menu
		System.out.println("1 – View given courses");
		System.out.println("2 – View student list of given course");
		System.out.println("3 – Return to the previous menu");
		System.out.println("");
	}
	
	/**
	 * Prints all menu options for users of type admin
	 * @param student
	 */
	public static void printAdminOptions(User admin) {
		
		// Header
		printDashLine();
		System.out.println("");
		System.out.println("Welcome, " + admin.getName());
		printDashLine();
		System.out.println("");
		
		// Menu
		System.out.println("1 – View all courses");
		System.out.println("2 – Add new course");
		System.out.println("3 – Delete course");
		System.out.println("4 – Add a new professor");
		System.out.println("5 – Delete a professor");
		System.out.println("6 – Add a new student");
		System.out.println("7 – Delete a student");
		System.out.println("8 — Return to previous menu");
		System.out.println("");
	}
	
	/**
	 * Prints a defined message and gets&returns the user's next integer input
	 * @param message, String message prompt
	 * @return ans, int answer
	 */
	public static int getUserChoice(String message) {
		
		int ans = 0;
		
		// print message and record next int
		System.out.println(message);
		
		// get new lines until latest line may be interpreted as an integer
		while (sc.hasNextInt() == false) {
			sc.nextLine();
		}
		
		// store the line as an integer, then return
		ans = sc.nextInt();
		return ans;	
	}
	
	/**
	 * Initializes all file info arrays based in txt file data
	 */
	public void createFileInfoArrays() {
		
		try {
			
			setCourses(fir.createCourseArray(fir.readFile("course")));
			setStudents(fir.createStudentArray(fir.readFile("stud"), getCourses()));
			setAdmins(fir.createAdminArray(fir.readFile("admin")));
			setProfs(fir.createProfArray(fir.readFile("prof")));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// getters & setters
	
	public void setStudents(ArrayList<User> students) {this.students = students;};
	public ArrayList<User> getStudents() {return this.students;};
	
	public void setAdmins(ArrayList<User> admins) {this.admins = admins;};
	public ArrayList<User> getAdmins() {return this.admins;};
	
	public void setProfs(ArrayList<User> profs) {this.profs = profs;};
	public ArrayList<User> getProfs() {return this.profs;};
	
	public void setCourses(ArrayList<Course> courses) {this.courses = courses;};
	public ArrayList<Course> getCourses() {return this.courses;};
	
	/**
	 * main logistics function
	 * @return 
	 * @throws IOException 
	 */
	public void run() throws IOException {
		
		int choice; int choice2; boolean done = false; boolean done2 = false;
		
		// initialize students, admins, courses, profs
		createFileInfoArrays();
		
		// get integer choices until a choice corresponds with one of the menu items
		while (done == false) {
			
			// print opening message
			printGreeting();
			
			// get integer choice from console
			choice = getUserChoice("Please enter your option, eg. '1'.");
			System.out.println("");
			
			// student
			if (choice == 1) {
				
				// login dialogue
				System.out.println("Logging in as a student . . .\n"); 
				User student = User.logIn(getStudents());
				
				done2 = false;
				
				// cycle through secondary menu
				while (!done2) {
					
					// print secondary menu
					printStudentOptions(student);
					
					// get user choice for secondary menu
					choice2 = getUserChoice("Please enter your option, eg. '1'.");
					
					// execute desired action
					done2 = Student.execute(student, choice2, getCourses());
						
				}			
			}
			
			// professor
			else if (choice == 2) {
				
				// login dialogue
				System.out.println("Logging in as a professor . . .\n"); 
				User prof = User.logIn(getProfs());
				
				done2 = false;
				
				// cycle through secondary menu
				while (!done2) {
					
					// print secondary menu
					printProfOptions(prof);
					
					// get user choice for secondary menu
					choice2 = getUserChoice("Please enter your option, eg. '1'.");
					
					// execute desired action
					done2 = Prof.execute(prof, choice2, getCourses(), getStudents());
				}
			}
			
			// administrator
			else if (choice == 3) {
				
				// login dialogue
				System.out.println("Logging in as an administrator . . .\n"); 
				User admin = User.logIn(getAdmins());
				
				done2 = false;
				
				// cycle through secondary menu
				while (!done2) {
					
					// print secondary menu
					printAdminOptions(admin);
					
					// get user choice for secondary menu
					choice2 = getUserChoice("Please enter your option, eg. '1'.");
					
					// execute desired action
					done2 = Admin.execute(admin, choice2, getCourses(), getStudents(), getProfs());
				}
			}
			
			// quit
			else if (choice == 4) {
				System.out.println("Quitting . . ."); System.exit(0);
			}
			
			// OOB error (only one that doesn't break the loop)
			else System.out.println("[ERR] Entry out of bounds.");
			
			System.out.println("");
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		
		Controller controller = new Controller();
		controller.run();
		
	}
}
