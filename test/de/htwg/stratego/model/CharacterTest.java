package de.htwg.stratego.model;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.model.impl.*;
import junit.framework.TestCase;

public class CharacterTest extends TestCase {
	
	private ICharacter flag;
	private ICharacter sergeant;
	private Player playerOne;
	private Player playerTwo;

	@BeforeClass
	public void setUp() {
		playerOne = new Player("PlayerOne", "#");
		playerTwo = new Player("PlayerTwo", "!");
		
		flag = CharacterFactory.create(Rank.FLAG, playerOne);
		sergeant = CharacterFactory.create(Rank.SERGEANT, playerTwo);
	}
	
	@Test
	public void testGetPlayer() {
		assertEquals(playerOne, flag.getPlayer());
	}
	
	@Test
	public void testGetRang() {
		assertEquals(Rank.FLAG, flag.getRank());
		assertEquals(Rank.SERGEANT, sergeant.getRank());
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
		String charString = "# " + Rank.FLAG;
		assertEquals(charString, flag.toString());
		
		charString = "! " + Rank.SERGEANT;
		assertEquals(charString, sergeant.toString());
		
		sergeant.setVisible(false);
		charString = " X ";
		assertEquals(charString, sergeant.toString());
	}

	@Test
	public void testSetVisible() {
		sergeant.setVisible(false);
		assertFalse(sergeant.isVisible());
	}
	
	@Test
	public void testIsVisible() {
		assertTrue(flag.isVisible());
	}
	
	@Test
	public void testBelongsTo() {
		assertTrue(flag.belongsTo(playerOne));
		assertFalse(sergeant.belongsTo(playerOne));
	}
	
}
