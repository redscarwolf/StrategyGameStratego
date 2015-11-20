package de.htwg.stratego.model;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.model.Cell;
import de.htwg.stratego.model.Field;
import de.htwg.stratego.model.impl.Sergeant;
import junit.framework.TestCase;

public class FieldTest extends TestCase {

	private Field field;
	private Character character1;
	private Character character2;
	
	@BeforeClass
	public void setUp() {
		field = new Field(3, 2);
		character1 = new Sergeant(Character.PLAYER_ONE);
		character2 = new Sergeant(Character.PLAYER_TWO);
		
		field.getCell(0, 0).setCharacter(character1);
		field.getCell(1, 1).setCharacter(character2);
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
		assertEquals(field.getCell(1, 1), new Cell(1,1));	
	}
	
//	@Test(expected = IndexOutOfBoundsException.class)
//	public void testGetCellIndexOutOfBounds1() {
//		field.getCell(-5, -5);
//	}
//	
//	@Test(expected = IndexOutOfBoundsException.class)
//	public void testGetCellIndexOutOfBounds2() {
//		field.getCell(5, 5);
//	}
//	
//	@Test(expected = IndexOutOfBoundsException.class)
//	public void testGetCellIndexOutOfBounds3() {
//		field.getCell(2, 5);
//	}
//	
//	@Test(expected = IndexOutOfBoundsException.class)
//	public void testGetCellIndexOutOfBounds4() {
//		field.getCell(2, -1);
//	}
	
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
	
	@Test
	public void testToString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  0  1  2 \n");
		sb.append(" +--+--+--+\n");
		sb.append("0|#4|  |  |\n");
		sb.append(" +--+--+--+\n");
		sb.append("1|  |!4|  |\n");
		sb.append(" +--+--+--+\n");
		assertEquals(sb.toString(), field.toString());
	}
	
	@Test
	public void testGetFieldString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  0  1  2 \n");
		sb.append(" +--+--+--+\n");
		sb.append("0|XX|  |  |\n");
		sb.append(" +--+--+--+\n");
		sb.append("1|  |!4|  |\n");
		sb.append(" +--+--+--+\n");
		assertEquals(sb.toString(), field.getFieldString(Character.PLAYER_TWO));
	}
	
	@Test
	public void testEquals() {
		assertTrue(field.equals(new Field(3, 2)));
		//width and height not Equal
		assertFalse(field.equals(new Field(99, 88)));
		//width not Equal
		assertFalse(field.equals(new Field(99, 2)));
		//height not Equal
		assertFalse(field.equals(new Field(1, 88)));
	}
	
}
