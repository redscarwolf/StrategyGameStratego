package test;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.Cell;
import junit.framework.TestCase;

public class CellTest extends TestCase {
	
	private Cell cell;
	
	@BeforeClass
	public void setUp() {
		cell =  new Cell(1, 2);
	}

	@Test
	public void testgetX() {
		assertTrue(cell.getX()== 1);
	}
	
	

}
