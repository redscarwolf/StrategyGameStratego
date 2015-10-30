package de.htwg.stratego.model;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;
import junit.framework.TestCase;

public class CharacterTest extends TestCase{
	
	private Character flag;
	private Character sergeant;
	private int player;
	
	@BeforeClass
	public void setUp() {
		player = 1;
		flag = new Flag(player);
		sergeant = new Sergeant(player);
		
	}
	
	@Test
	public void testGetPlayer() {
		assertEquals(1, flag.getPlayer());
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
