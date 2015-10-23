package de.htwg.stratego.model;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.model.Cell;
import de.htwg.stratego.model.Field;
import junit.framework.TestCase;

public class FieldTest extends TestCase {

	private Field field;
	
	@BeforeClass
	public void setUp() {
		field = new Field(3, 2);
	}
	
	@Test
	public void testGetWidth() {
		assertEquals(3 , field.getWidth() );
	}
	
	@Test
	public void testGetHeight() {
		assertEquals(2,field.getHeight());
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
				assertNotNull(cell);
				assertEquals(x, cell.getX());
				assertEquals(y, cell.getY());
			}
		}
	}
	
}
