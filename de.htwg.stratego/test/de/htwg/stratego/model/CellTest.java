package de.htwg.stratego.model;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.model.Cell;
import junit.framework.TestCase;

public class CellTest extends TestCase {

	private Cell cell;

	@BeforeClass
	public void setUp() {
		cell = new Cell(1, 2);
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
	public void testEquals() {
		assertTrue(cell.equals(new Cell(1, 2)));
		assertFalse(cell.equals(new Cell(1, 3)));
	}

}
