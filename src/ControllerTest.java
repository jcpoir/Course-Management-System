import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ControllerTest {
	
	Controller controller = new Controller();
	    
	@Test
	void testCreateFileInfoArrays() {
		
		controller.createFileInfoArrays();
		
		assertEquals(controller.students.size(), 3);
		assertEquals(controller.profs.size(), 32);
		assertEquals(controller.admins.size(), 3);
		assertEquals(controller.courses.size(), 50);
	}
}