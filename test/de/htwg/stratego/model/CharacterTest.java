package de.htwg.stratego.model;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;
import junit.framework.TestCase;

public class CharacterTest extends TestCase{
	
	private Character flag;
	private Character sergeant;
	private Character sergeant2;
	
	@BeforeClass
	public void setUp() {
		flag = new Flag(Character.PLAYER_ONE);
		sergeant = new Sergeant(Character.PLAYER_TWO);
		sergeant2 = new Sergeant(0);
	}
	
	@Test
	public void testGetPlayer() {
		assertEquals(Character.PLAYER_ONE, flag.getPlayer());
	}
	
	@Test
	public void testGetRang() {
		assertEquals(Character.FLAG_RANG, flag.getRang());
		assertEquals(Character.SERGEANT_RANG, sergeant.getRang());
	}
	
	@Test
	public void testIsMoveable() {
		//non moveable
		assertFalse(flag.isMoveable());
		
		//is moveable
		assertTrue(sergeant.isMoveable());
	}
	
	@Test
	public void testToString() {
		String charString = "#" + Character.FLAG_RANG;
		assertEquals(charString, flag.toString());
		
		charString = "!" + Character.SERGEANT_RANG;
		assertEquals(charString, sergeant.toString());
		
		charString = "0" + Character.SERGEANT_RANG;
		assertEquals(charString, sergeant2.toString());
	}

}