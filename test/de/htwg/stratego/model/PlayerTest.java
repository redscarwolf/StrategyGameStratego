package de.htwg.stratego.model;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.model.impl.character.Sergeant;
import junit.framework.TestCase;

public class PlayerTest extends TestCase {

	private Player player;
	private ICharacter character;

	@BeforeClass
	public void setUp() {
		this.player = new Player();
		character = new Sergeant(player);
	}

	@Test
	public void testGetCharacter() {
		player.addCharacter(character);
		int rank = character.getRank();
		ICharacter characterErg = player.getCharacter(rank);
		assertEquals(character, characterErg);
	}

	@Test
	public void testRemoveCharacter() {
		player.addCharacter(character);
		assertTrue(player.removeCharacter(character));
	}

	@Test
	public void testRemoveCharacterByIndex() {
		player.addCharacter(character);
		int lastindex = player.getCharacterList().size() - 1;
		assertEquals(character, player.removeCharacter(lastindex));
	}

	@Test
	public void testAddCharacter() {
		player.addCharacter(null);
	}
	
	@Test
	public void testContainsCharacter() {
		assertTrue(player.containsCharacter(Rank.BOMB));
		player.removeCharacter(player.getCharacter(Rank.FLAG));
		assertFalse(player.containsCharacter(Rank.FLAG));
	}
	
	@Test
	public void testGetCharacterListAsString() {
		String listStr = "|11|11|11|11|11|11|10|9|8|8|7|7|7|6|6|6|6|5|5|5|5|"
				+ "4|4|4|4|3|3|3|3|3|2|2|2|2|2|2|2|2|1|0|";
		assertEquals(listStr, player.getCharacterListAsString());
	}
	
	@Test
	public void testToString() {
		String symbolStr = "?";
		assertEquals(symbolStr, player.toString());
	}
}
