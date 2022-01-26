package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import roles.Admin;
import roles.Prof;
import roles.Student;
import roles.User;
import courses.Course;

/**
 * 
 * @author josephpoirier
 * Object that is used to read specific files containing student/professor/admin/course data
 */
public class FileInfoReader {
	
	// initialize filepaths for all data
	private Path adminInfoPath; private Path courseInfoPath; private Path profInfoPath; Path studentInfoPath;
	
	/**
	 * Constructor
	 * all paths are predefined based on default filenames in the assignment
	 */
	public FileInfoReader() {
		
		this.adminInfoPath = Paths.get("admininfo.txt");
		this.courseInfoPath = Paths.get("courseinfo.txt");
		this.profInfoPath = Paths.get("profinfo.txt");
		this.studentInfoPath = Paths.get("studentinfo.txt");
	}
	
	/**
	 * Reads file based on specified filetype and returns all lines in a string array
	 * @param fileType, String type of data: "admin", "course", "prof", "stud"
	 * @return
	 * @throws IOException
	 */
	public String[][] readFile(String fileType) throws IOException {
		
		String[] lines = null;
		Path filepath = null;
		
		// check if input fileType is a near match to any of the main datatypes:
		// admin, course, prof, student

		// admin
		if (fileType.toLowerCase().contains("admin")) {
			filepath = this.adminInfoPath;
		}
		
		// course
		else if (fileType.toLowerCase().contains("course")) {
			filepath = this.courseInfoPath;
		}
		
		// prof
		else if (fileType.toLowerCase().contains("prof")) {
			filepath = this.profInfoPath;
		}
		
		// student
		else if (fileType.toLowerCase().contains("stud")) {
			filepath = this.studentInfoPath;
		}
		
		// invalid type
		else {
			System.out.println("invalid filetype");
			
			// end function (avoids errors)
			return null;
		}
		
		// define arraylist, so that any number of lines may be added
		ArrayList<String> linesList = new ArrayList<String>();
		
		// add lines to arraylist, one at a time
		Stream <String> stream = Files.lines(filepath);
		stream.forEach(linesList::add);
		
		// define output array dimensions
		int length = linesList.size();
		int width = linesList.get(0).split(";").length;
		
		// An arrayList of String arrays
		String[][] outArray = new String[length][width];
		
		// contents of a line, after split
		String[] contents;
		
		int i = 0;
		for (String line : linesList) {
			
			// fields in all data files are split by semicolons
			contents = line.split(";");
			
			int j = 0;
			for (String term : contents) {
				
				// remove whitespace from each field and add to output array
				outArray[i][j] = term.trim();
				j++;
			}	
			i++;
		}
		
		return outArray;
	}
	
	/**
	 * From raw file data (2D String array), defines an array of students
	 * type and returns
	 * @param fileInfoArray
	 * @return an arrayList of students
	 */
	public ArrayList<User> createStudentArray(String[][] fileInfoArray, ArrayList<Course> courseList)  {
		
		// define output list
		ArrayList<User> studentList = new ArrayList<User>();
		
		// define student
		Student stud;
		
		for (String[] row : fileInfoArray) {
			
			stud = new Student();
			
			// load file data into new student object
			stud.setID(row[0]);
			stud.setName(row[1]);
			stud.setUsername(row[2]);
			stud.setPassword(row[3]);
			
			ArrayList <Course> enrolledCourses = Student.processCourseInfo(row[4], courseList);
			stud.setCourses(enrolledCourses);
			ArrayList <String> grades = Student.processGrades(row[4]);
			stud.setGrades(grades);
			
			// add student to output
			studentList.add(stud);
		}
		return studentList;
	}
	
	/**
	 * From raw file data (2D String array), defines an array of admins
	 * type and returns
	 * @param fileInfoArray
	 * @return an arrayList of admins
	 */
	public ArrayList<User> createAdminArray(String[][] fileInfoArray)  {
		
		// define output list
		ArrayList<User> adminList = new ArrayList<User>();
		
		// define student
		Admin admin;
		
		for (String[] row : fileInfoArray) {
			
			admin = new Admin();
			
			// load file data into new admin object
			admin.setID(row[0]);
			admin.setName(row[1]);
			admin.setUsername(row[2]);
			admin.setPassword(row[3]);
			
			// add student to output
			adminList.add(admin);
		}
		return adminList;
	}
	
	/**
	 * From raw file data (2D String array), defines an array of profs
	 * type and returns
	 * @param fileInfoArray
	 * @return an arrayList of profs
	 */
	public ArrayList<User> createProfArray(String[][] fileInfoArray)  {
		
		// define output list
		ArrayList<User> profList = new ArrayList<User>();
		
		// define student
		Prof prof;
		
		for (String[] row : fileInfoArray) {
			
			prof = new Prof();
			
			// load file data into new prof object
			prof.setName(row[0]);
			prof.setID(row[1]);
			prof.setUsername(row[2]);
			prof.setPassword(row[3]);
			
			// add student to output
			profList.add(prof);
		}
		return profList;
	}
	
	/**
	 * From raw file data (2D String array), defines an array of courses
	 * type and returns
	 * @param fileInfoArray
	 * @return an arrayList of courses
	 */
	public ArrayList<Course> createCourseArray(String[][] fileInfoArray)  {
		
		// define output list
		ArrayList<Course> courseList = new ArrayList<Course>();
		
		// define student
		Course course;
		
		for (String[] row : fileInfoArray) {
			
			course = new Course();
			
			// load file data into new course object
			course.setStudents(0);
			course.setID(row[0]);
			course.setName(row[1]);
			course.setProf(row[2]);
			course.setDays(row[3]);
			course.setStart(row[4]);
			course.setEnd(row[5]);
			course.setCapacity(row[6]);
			
			// add student to output
			courseList.add(course);
		}
		return courseList;
	}
}
