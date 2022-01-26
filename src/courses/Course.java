package courses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import files.FileInfoReader;
import roles.User;

/**
 * Represents a university course in CIS or CIT
 * @author josephpoirier
 *
 */
public class Course {
	
	private String ID;
	private String name;
	private String prof;
	private String days;
	private String start;
	private String end;
	private String capacity;
	private int students = 0;
	
	// constructor
	public Course() {
		
	}
	
	// getters & setters
	
	public void setID(String ID) {this.ID = ID;};
	public String getID() {return this.ID;};
	
	public String getName() {return this.name;};
	public void setName(String name) {this.name = name;};
	
	public String getProf() {return this.prof;};
	public void setProf(String prof) {this.prof = prof;};
	
	public String getDays() {return this.days;};
	public void setDays(String days) {this.days = days;};
	
	public String getStart() {return this.start;};
	public void setStart(String start) {this.start = start;};
	
	public String getEnd() {return this.end;};
	public void setEnd(String end) {this.end = end;};
	
	public String getCapacity() {return this.capacity;};
	public void setCapacity(String capacity) {this.capacity = capacity;};
	
	public int getStudents() {return this.students;};
	public void setStudents(int students) {this.students = students;};
	
	/**
	 * Displays an Arraylist of courses as a printed console message
	 * @param courses, a Course Array
	 */
	public static void displayCourses(ArrayList<Course> courses) {
		
		for (Course course : courses) {
			System.out.println(course.getID() + " • " + course.getName() + 
			"\nDays: " + course.getDays() + " Time: " + course.getStart() + " – " + course.getEnd() + 
			" Capacity: " + course.getCapacity() + " Students: " + course.getStudents() + " Lecturer: " + course.getProf() + "\n");
		}
	}
	
	/**
	 * Returns the course that corresponded with a provided ID. If not course matches, returns null
	 * @param courses, a Course ArrayList
	 * @param ID, a course ID
	 * @return course that corresponds with provided ID
	 */
	public static Course getCourseByID(ArrayList<Course> courses, String ID) {
		
		// define output course
		Course outCourse = null;
		
		// look through all courses for ID match
		for (Course course: courses) {
			if (course.getID().equals(ID)) {
				outCourse = course;
			}
		}
		return outCourse;
	}
	
	/**
	 * Returns a list of courses taught py specified professor. If none, returns empty list
	 * @param courses, a Course ArrayList
	 * @param profName, String professorName
	 * @return outList, an ArrayList of Course objects, all taught by specified professor
	 */
	public static ArrayList<Course> getCoursesByProf(ArrayList<Course> courses, String profName) {
		
		// define output list
		ArrayList<Course> outList = new ArrayList<Course>();
		
		// look through all courses for professor matches
		for (Course course: courses) {
			if (course.getProf().equals(profName)) {
				outList.add(course);
			}
		}
		return outList;
	}
	
	/**
	 * Determines if two course objects conflict on time
	 * @param course, a course object
	 * @param courses, an arraylist of other enrolled courses
	 * @return conflicts, boolean do courses conflict?
	 */
	public static boolean doesCourseConflict(Course course, ArrayList<Course> courses) {
		
		// define output boolean
		boolean conflicts = false;
		
		// if course exists, do this stuff
		if (course != null) {
			
			// get days offered
			String days = course.getDays();
			
			// get start and end times for course, converted to int for the sake of comparison
			int start = stringTimeToInt(course.getStart());
			int end = stringTimeToInt(course.getEnd());
			
			// initialize conditions for assessing conflicts
			boolean startConflicts; boolean endConflicts; boolean isOverlap;
			
			for (Course course_ : courses) {
				
				// get days offered
				String days_ = course_.getDays();
				
				isOverlap = doDaysOverlap(days, days_);
				
				// get start and end times for courses in input arraylist
				int start_ = stringTimeToInt(course_.getStart());
				int end_ = stringTimeToInt(course_.getEnd());
				
				// if either end of new class falls within another class' lecture, there is a conflict
				startConflicts = start >= start_ && start < end_;
				endConflicts = end > start_ && end <= end_;
				
				// there is a conflict if:
				// (1) days conflict (isOverlap), and
				// (2) times conflict (startConflicts, endConflicts)
				if ((startConflicts && isOverlap) || (endConflicts && isOverlap)) {
					conflicts = true;
				}
			}
		}
		return conflicts;
	}
	
	/**
	 * If course with given ID is present in courselist, deletes course
	 * otherwise, prints error message and does nothing
	 */
	public static void deleteCourseByID(String ID, ArrayList<Course> courses) {
		
		// track if drop is successful
		boolean dropped = false;
		
		int i = 0;
		// look through all courses for ID match
		for (Course course: courses) {
			if (course.getID().equals(ID)) {
				
				// if a match is found, remove it
				dropped = true;
				
				System.out.println("\n[✓] " + ID + " deleted successfully!\n");
				break;
			}
			i++;
		}
		
		// if a match is found, remove (can't remove from arraylist while iterating, so this is the workaround
		if (dropped) {
			courses.remove(i);
		}
		
		// if course not found, print error message
		if (!dropped) {
			System.out.println("\n[✘] Drop unsuccessful for course ID: " + ID + ".\n");
		}
	}
		
	/**
	 * Checks to see if identical user is in list, by ID
	 * @param professors
	 * @param ID
	 * return boolean result, is user already in list
	 */
	public static boolean isIn(ArrayList<Course> courses, String ID) {
		
		// initialize output boolean
		boolean result = false;
		
		// check for name match in users
		for (Course course : courses) {
			if (course.getID().equals(ID)) {
				result = true;
			}
		}
		
		// return boolean
		return result;
	}
	
	/**
	 * Converts a time in the form h:mm to an integer
	 * @param time
	 * @return
	 */
	public static int stringTimeToInt(String time) {
		
		return Integer.valueOf(time.split(":")[0]) * 60 + Integer.valueOf(time.split(":")[1]);
	}
	
	/**
	 * Takes two day strings in the form "MWF", "TR" and returns whether or not they overlap
	 * @param days1 a day string
	 * @param days2 a day string
	 * @return isOverlap, boolean is there any overlap in class days
	 */
	public static boolean doDaysOverlap(String days1, String days2) {
		
		// define output boolean
		boolean isOverlap = false;
		
		// split arrays into characters
		char[] daysArray1 = days1.toCharArray();
		char[] daysArray2 = days2.toCharArray();
		
		// if any days are shared, return true for overlap
		for (char day1 : daysArray1) for (char day2 : daysArray2) {
			if (day1 == day2) isOverlap = true;
		}
		
		return isOverlap;
	}
}
