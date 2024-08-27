package mikera.gui.console;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConsoleDataTest {
	
	@Test public void testCreateData() {
		ConsoleData cd=new ConsoleData();
		cd.init(80, 25);
		
		// should be filled with zero chars
		assertEquals(0,cd.getCharAt(0, 0));
	}
	
	@Test public void testFill() {
		ConsoleData cd=new ConsoleData();
		cd.init(80, 25);
		
		cd.fillArea('X', null, null, null, 1, 1, 10, 10);
		
		// should be filled with zero chars
		assertEquals(0,cd.getCharAt(0, 0));
		assertEquals('X',cd.getCharAt(1, 1));
		assertEquals('X',cd.getCharAt(10, 10));
		assertEquals(0,cd.getCharAt(11, 11));
	}
}
