package roles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import courses.Course;
import files.FileInfoReader;

/**
 * Represents a generalized account (without specific type). Subclasses of user are:
 * Student, Professor, Admin. 
 * @author josephpoirier
 *
 */
public abstract class User {
	
	String ID;
	String name;
	String username;
	String password;
	FileInfoReader fir;
	
	/**
	 * Prompts user with login dialogue. First prints user and waits for user input
	 * If user inputs "q", terminates program. Else, prompts password dialogue. If 
	 * username and password are both in userinfo and match, then return username
	 * 
	 * @param fir, a FileInfoReader
	 * @param usernames, an array of all usernames in the file
	 * @param passwords, an array of all passwords in the file
	 * @param fileType, String filetype
	 * @return
	 */
	public static User logIn(ArrayList<User> users) {
		
		User outUser = null;
		
		// has user logged in successfully?
		boolean isLoggedIn = false;
		
		// has user entered correct username and incorrect password? Used to print proper error msg
		boolean isInvalidPassword = false;
		
		String username = null; String password;
		Scanner sc;
		
		// try to log user in until successful login is achieved
		while(!isLoggedIn) {
			
			// instructions for "q" option
			System.out.println("Please enter your username and password, or type \"q\" to quit.\n");
			
			username = null; password = null;
		
			// get username from user
			System.out.print("Username: ");	
			
			// when user enters name, save to username and proceed;
			sc = new Scanner(System.in);
			while (username == null) {
				username = sc.nextLine().trim();
			}
			
			// if user enters "q", terminate
			if (username.equals("q")) {System.out.println("\nQuitting . . ."); System.exit(0);};
			
			// get password from user
			System.out.print("Password: ");
			
			while (password == null) {
				password = sc.nextLine().trim();
			}
			
			// if user enters "q", terminate
			if (password.equals("q")) {System.out.println("\nQuitting . . ."); System.exit(0);};
			
			// current user username and password
			String username_; String password_;
			
			// define conditions for successful login
			Boolean usernameMatch; Boolean passwordMatch;
			
			// for printing correct error message
			isInvalidPassword = false;
			
			// check for matches
			for (User user : users) {
				
				// get user attributes
				username_ = user.getUsername();
				password_ = user.getPassword();
				
				// check if username and password match those of current user
				usernameMatch = username.equals(username_);
				passwordMatch = password.equals(password_);
				
				if (usernameMatch) {
					if (passwordMatch) {
						
						// set user object for output = matching user
						System.out.println("\n[✓] Login successful!\n");
						outUser = user;
						isLoggedIn = true;
					}
					else {
						System.out.println("\n[✘] Invalid password!\n");
						isInvalidPassword = true;
					}
				}
			}
			
			// if login is unsuccessful and the problem isn't an invalid password, username must be wrong
			if (!isLoggedIn & !isInvalidPassword) System.out.println("\n[✘] Invalid username!\n");
		}
		
		return outUser;
	}
	
	/**
	 * If user with given ID is present in list, drops user
	 * otherwise, prints error message and does nothing
	 */
	public static void deleteByID(String ID, ArrayList<User> users) {
		
		// track if drop is successful
		boolean dropped = false;
		
		// get name for output message
		String name = null;
			
		int i = 0;
		// look through all courses for ID match
		for (User user : users) {
			if (user.getID().equals(ID)) {
				
				// if a match is found, remove it
				dropped = true;
				
				name = user.getName();
				
				System.out.println("\n[✓] " + name + " deleted successfully!\n");
				break;
			}
			i++;
		}
		
		// if a match is found, remove (can't remove from arraylist while iterating, so this is the workaround
		if (dropped) {
			users.remove(i);
		}
		
		// if course not found, print error message
		if (!dropped) {
			System.out.println("\n[✘] Drop unsuccessful because ID: " + ID + " was not found in the database.\n");
		}
	}
	
	/**
	 * Checks to see if identical user is in list, by ID
	 * @param professors
	 * @param ID
	 * return boolean result, is user already in list
	 */
	public static boolean isIn(ArrayList<User> users, String ID) {
		
		// initialize output boolean
		boolean result = false;
		
		// check for name match in users
		for (User user : users) {
			if (user.getID().equals(ID)) {
				result = true;
			}
		}
		
		// return boolean
		return result;
	}
	
	abstract ArrayList<Course> getCourses();
	abstract void displayGrades();
	abstract void dropCourseByID(String ID);
	
	// getters and setters
	
	// Getters & setters
	
	public void setID(String ID) {this.ID = ID;};
	public String getID() {return this.ID;};
	
	public String getName() {return this.name;};
	public void setName(String name) {this.name = name;};
	
	public String getUsername() {return this.username;};
	public void setUsername(String username) {this.username = username;};
	
	public String getPassword() {return this.password;};
	public void setPassword(String password) {this.password = password;}

	abstract void addCourseByID(String iD2, ArrayList<Course> courses);

	protected abstract ArrayList<String> getGrades();

	protected abstract void setCourses(ArrayList<Course> courses);

	protected abstract void setGrades(ArrayList<String> grades_);

	protected abstract void setProf(String prof);

}
