package roles;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import courses.Course;
import files.FileInfoReader;

class StudentTest {
	
	FileInfoReader fir = new FileInfoReader();

	@Test
	void testProcessCourseInfo() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		String courseInfo_ = "CIS519 : A, CIS522 : B, CIS545 : A+";
		ArrayList<Course> enrolledCourses = Student.processCourseInfo(courseInfo_, courses);
		
		assertEquals(enrolledCourses.get(0).getID(), "CIS519");
		assertEquals(enrolledCourses.get(1).getID(), "CIS522");
		assertEquals(enrolledCourses.get(2).getID(), "CIS545");
	}
	
	@Test
	void testGrades() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		String courseInfo_ = "CIS519 : A, CIS522 : B, CIS545 : A+";
		ArrayList<String> grades = Student.processGrades(courseInfo_);
		
		assertEquals(grades.get(0), "CIS519 : A");
		assertEquals(grades.get(1), "CIS522 : B");
		assertEquals(grades.get(2), "CIS545 : A+");
	}
	
	@Test
	void testDropCourseByID() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		String courseInfo_ = "CIS519 : A, CIS522 : B, CIS545 : A+";
		ArrayList<Course> enrolledCourses = Student.processCourseInfo(courseInfo_, courses);
		ArrayList<String> grades = Student.processGrades(courseInfo_);
		
		Student student = new Student();
		student.setCourses(enrolledCourses);
		student.setGrades(grades);
		
		assertEquals(enrolledCourses.get(0).getID(), "CIS519");
		student.dropCourseByID("CIS519");
		assertEquals(enrolledCourses.get(0).getID(), "CIS522");
		student.dropCourseByID("CIS522");
		assertEquals(enrolledCourses.get(0).getID(), "CIS545");
	}
	
	@Test
	void testAddCourseByID() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		String courseInfo_ = "CIS519 : A, CIS522 : B, CIS545 : A+";
		ArrayList<Course> enrolledCourses = Student.processCourseInfo(courseInfo_, courses);
		ArrayList<String> grades = Student.processGrades(courseInfo_);
		
		Student student = new Student();
		student.setCourses(enrolledCourses);
		student.setGrades(grades);
		
		assertEquals(enrolledCourses.size(), 3);
		student.addCourseByID("CIT590", courses);
		assertEquals(enrolledCourses.size(), 4);
		student.addCourseByID("CIS520", courses);
		assertEquals(enrolledCourses.size(), 5);
	}
	
	@Test
	void testGetStudentsByID() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		String[][] studentInfo = fir.readFile("stud");
		ArrayList<User> students = fir.createStudentArray(studentInfo, courses);
		
		ArrayList<User> studentsList = Student.getStudentsByCourseID("CIS519", students);
		assertEquals(studentsList.size(), 1);
		studentsList = Student.getStudentsByCourseID("CIS520", students);
		assertEquals(studentsList.size(), 0);
		studentsList = Student.getStudentsByCourseID("CIS522", students);
		assertEquals(studentsList.size(), 0);
	}

}
