package files;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import courses.Course;
import roles.User;

class FileInfoReaderTest {

	FileInfoReader fir = new FileInfoReader();
	Course course = new Course();

	@Test
	void testCreateStudentArray() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		String[][] studentInfo = fir.readFile("stud");
		ArrayList<User> students = fir.createStudentArray(studentInfo, courses);
		
		assertEquals(students.get(2).getName(), "Joseph Poirier");
		assertEquals(students.get(2).getUsername(), "jcpoir");
		assertNotEquals(students.get(1).getName(), "Joseph Poirier");
	}
	
	@Test
	void testCreateAdminArray() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		String[][] adminInfo = fir.readFile("admin");
		ArrayList<User> admins = fir.createAdminArray(adminInfo);
		
		assertEquals(admins.get(2).getName(), "admin");
		assertEquals(admins.get(2).getUsername(), "admin03");
		assertNotEquals(admins.get(1).getName(), "Joseph Poirier");
	}
	
	@Test
	void testCreateProfArray() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		String[][] profInfo = fir.readFile("prof");
		ArrayList<User> profs = fir.createProfArray(profInfo);
		
		assertEquals(profs.get(2).getName(), "Stephanie Weirich");
		assertEquals(profs.get(2).getUsername(), "Weirich");
		assertNotEquals(profs.get(1).getName(), "Dinesh Jayaraman");
	}

	@Test
	void testCreateCourseArray() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		assertEquals(courses.get(2).getName(), "Mathematical Foundations of Computer Science");
		assertEquals(courses.get(2).getStart(), "10:00");
		assertNotEquals(courses.get(1).getName(), "Linear Algebra");
	}
}
