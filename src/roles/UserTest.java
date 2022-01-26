package roles;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import courses.Course;
import files.FileInfoReader;

class UserTest {

	FileInfoReader fir = new FileInfoReader();

	@Test
	void testDeleteByID() throws IOException {
		
		String[][] courseInfo = fir.readFile("course");
		ArrayList<Course> courses = fir.createCourseArray(courseInfo);
		
		String[][] profInfo = fir.readFile("prof");
		ArrayList<User> profs = fir.createProfArray(profInfo);
		
		assertEquals(profs.get(0).getName(), "Clayton Greenberg");
		User.deleteByID("001", profs);
		assertEquals(profs.get(0).getName(), "Harry Smith");
		User.deleteByID("002", profs);
		assertEquals(profs.get(0).getName(), "Stephanie Weirich");
	}
	
	@Test
	void testIsIn() throws IOException {
		
		String[][] profInfo = fir.readFile("prof");
		ArrayList<User> profs = fir.createProfArray(profInfo);
		
		assertTrue(User.isIn(profs, "001"));
		User.deleteByID("001", profs);
		assertFalse(User.isIn(profs, "001"));
		assertTrue(User.isIn(profs, "002"));
		User.deleteByID("002", profs);
		assertFalse(User.isIn(profs, "002"));
	}
}
