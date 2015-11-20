package de.htwg.stratego.model;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.model.impl.Flag;
import junit.framework.TestCase;

public class CellTest extends TestCase {

	private Cell cell;
	private Character flag;
	
	@BeforeClass
	public void setUp() {
		cell = new Cell(1, 2);
		flag = new Flag(1);
	}

	@Test
	public void testGetX() {
		assertEquals(1,cell.getX());
	}

	@Test
	public void testGetY() {
		assertEquals(2 , cell.getY());
	}

	@Test
	public void testSetGetCharacter() {
		cell.setCharacter(flag);
		assertEquals(flag,cell.getCharacter());
	}
	
	@Test
	public void testEquals() {
		assertTrue(cell.equals(new Cell(1, 2)));
		
		//x and y not Equal
		assertFalse(cell.equals(new Cell(99, 88)));
		//x not Equal
		assertFalse(cell.equals(new Cell(99, 2)));
		//y not Equal
		assertFalse(cell.equals(new Cell(1, 88)));
	}

}
