package de.htwg.stratego.model;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;
import junit.framework.TestCase;

public class CharacterTest extends TestCase{
	
	private Character flag;
	private Character sergeant;
	
	@BeforeClass
	public void setUp() {
		flag = new Flag();
		sergeant = new Sergeant();
	}
	
	@Test
	public void testGetRang() {
		assertEquals(Character.FLAG_RANG, flag.getRang());
		assertEquals(Character.SERGEANT_RANG,sergeant.getRang());
	}
	
	@Test
	public void testIsMoveable() {
		//non moveable
		assertFalse(flag.isMoveable());
		
		//is moveable
		assertTrue(sergeant.isMoveable());
	}
}
