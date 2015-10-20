package test;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.Cell;
import de.htwg.stratego.Field;
import junit.framework.TestCase;

public class FieldTest extends TestCase {

	private Field field;
	
	@BeforeClass
	public void setUp() {
		field = new Field(3, 2);
	}
	
	@Test
	public void testGetWidth() {
		assertTrue(field.getWidth() == 3);
	}
	
	@Test
	public void testGetHeight() {
		assertTrue(field.getHeight() == 2);
	}
	
	@Test
	public void testGetCell() {
//		assertEquals(field.getCell(1, 1), new Cell(1,1));
		assertTrue(field.getCell(1, 1).equals(new Cell(1, 1)));
		
	}
	
	@Test
	public void testCells() {
		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				Cell cell = field.getCell(x, y);
				assertTrue(cell != null);
				assertTrue(cell.getX() == x);
				assertTrue(cell.getY() == y);
			}
		}
	}
	
}
