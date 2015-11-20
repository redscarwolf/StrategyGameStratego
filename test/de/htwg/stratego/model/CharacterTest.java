package de.htwg.stratego.model;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.model.impl.*;
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

		new Spy(Character.PLAYER_ONE);
		new Scout(Character.PLAYER_ONE);
		new Miner(Character.PLAYER_ONE);
		new Lieutenant(Character.PLAYER_ONE);
		new Captain(Character.PLAYER_ONE);
		new Major(Character.PLAYER_ONE);
		new Colonel(Character.PLAYER_ONE);
		new General(Character.PLAYER_ONE);
		new Marshal(Character.PLAYER_ONE);
		new Bomb(Character.PLAYER_ONE);
	}
	
	@Test
	public void testGetPlayer() {
		assertEquals(Character.PLAYER_ONE, flag.getPlayer());
	}
	
	@Test
	public void testGetRang() {
		assertEquals(Character.FLAG_RANK, flag.getRank());
		assertEquals(Character.SERGEANT_RANK, sergeant.getRank());
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
		String charString = "#" + Character.FLAG_RANK;
		assertEquals(charString, flag.toString());
		
		charString = "!" + Character.SERGEANT_RANK;
		assertEquals(charString, sergeant.toString());
		
		charString = "0" + Character.SERGEANT_RANK;
		assertEquals(charString, sergeant2.toString());
	}

}
