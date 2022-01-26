package courses;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import files.FileInfoReader;

class CourseTest {
	
	FileInfoReader fir = new FileInfoReader();
	Course course = new Course();

	@Test
	void testGetCourseByID() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		Course course1 = Course.getCourseByID(courses, "CIS519");
		assertEquals(course1.getID(), "CIS519");
		assertEquals(course1.getCapacity(), "120");
		
		Course course2 = Course.getCourseByID(courses, "CIS545");
		assertEquals(course2.getID(), "CIS545");
		assertEquals(course2.getCapacity(), "300");
	}
	
	@Test
	void testGetCourseByProf() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		ArrayList<Course> courses_= Course.getCoursesByProf(courses, "Swapneel Sheth");
		
		assertEquals(courses_.get(0).getName(), "Linux/Unix Skills");
		assertEquals(courses_.get(1).getEnd(), "12:00");
		assertEquals(courses_.get(2).getStart(), "13:00");
		assertEquals(courses_.get(3).getCapacity(), "15");
	}
	
	@Test
	void testDoesCourseConflict() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		Course test = new Course();
		test.setDays("MW");
		test.setStart("8:00");
		test.setEnd("9:00");
		
		assertFalse(Course.doesCourseConflict(test, courses));
		
		test.setEnd("12:00");
		
		assertTrue(Course.doesCourseConflict(test, courses));	
	}
	
	@Test
	void testDeleteCourseByID() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		ArrayList<Course> courses_ = (ArrayList<Course>) courses.clone();
		
		Course.deleteCourseByID("0", courses);
		
		assertEquals(courses.get(1).getName(), "Introduction to Software Development");
		assertEquals(courses.get(2).getEnd(), "11:00");
		assertEquals(courses.get(3).getStart(),"11:00");
		assertEquals(courses.get(4).getCapacity(), courses.get(3).getCapacity());
	}
	
	@Test
	void testIsIn() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		ArrayList<Course> courses_ = (ArrayList<Course>) courses.clone();
		
		Course.deleteCourseByID("0", courses);
		
		assertTrue(Course.isIn(courses, "CIS519"));
		assertTrue(Course.isIn(courses, "CIT590"));
		assertFalse(Course.isIn(courses, "CIT5000"));
	}
	
	@Test
	void testStringTimeToInt() {

		assertEquals(Course.stringTimeToInt("1:00"), 60);
		assertEquals(Course.stringTimeToInt("2:30"), 150);
		assertEquals(Course.stringTimeToInt("10:15"), 615);
		
	}
	
	@Test
	void testDoDaysOverlap() {

		assertTrue(Course.doDaysOverlap("MWF", "MWF"));
		assertFalse(Course.doDaysOverlap("TR", "MWF"));
		assertTrue(Course.doDaysOverlap("MWF", "MR"));
	}
}
