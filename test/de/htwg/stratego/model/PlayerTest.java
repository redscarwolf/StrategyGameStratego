package de.htwg.stratego.model;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.model.impl.character.Bomb;
import de.htwg.stratego.model.impl.character.Flag;
import de.htwg.stratego.model.impl.character.Scout;
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
		player.addCharacter(new Bomb(player));
		player.addCharacter(new Flag(player));
		assertTrue(player.hasCharacter(Rank.BOMB));
		player.removeCharacter(player.getCharacter(Rank.FLAG));
		assertFalse(player.hasCharacter(Rank.FLAG));
	}
	
	@Test
	public void testGetCharacterListAsString() {
		Bomb.buildSeveral(6, player);
		Scout.buildSeveral(4, player);
		Flag.buildSeveral(1, player);
		
		String listStr = "|11|11|11|11|11|11|2|2|2|2|0|";
		assertEquals(listStr, player.getCharacterListAsString());
	}
	
	@Test
	public void testToString() {
		String symbolStr = "?";
		assertEquals(symbolStr, player.toString());
	}
}
