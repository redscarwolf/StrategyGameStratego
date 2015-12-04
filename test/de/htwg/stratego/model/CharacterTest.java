package de.htwg.stratego.model;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.model.impl.*;
import de.htwg.stratego.model.impl.character.Bomb;
import de.htwg.stratego.model.impl.character.Captain;
import de.htwg.stratego.model.impl.character.Colonel;
import de.htwg.stratego.model.impl.character.Flag;
import de.htwg.stratego.model.impl.character.General;
import de.htwg.stratego.model.impl.character.Lieutenant;
import de.htwg.stratego.model.impl.character.Major;
import de.htwg.stratego.model.impl.character.Marshal;
import de.htwg.stratego.model.impl.character.Miner;
import de.htwg.stratego.model.impl.character.Scout;
import de.htwg.stratego.model.impl.character.Sergeant;
import de.htwg.stratego.model.impl.character.Spy;
import junit.framework.TestCase;

public class CharacterTest extends TestCase{
	
	private ICharacter flag;
	private ICharacter sergeant;
	private Player playerOne;
	private Player playerTwo;

	@BeforeClass
	public void setUp() {
		playerOne = new Player("#");
		playerTwo = new Player("!");
		
		flag = new Flag(playerOne);
		sergeant = new Sergeant(playerTwo);

		new Spy(playerOne);
		new Scout(playerOne);
		new Miner(playerOne);
		new Lieutenant(playerOne);
		new Captain(playerOne);
		new Major(playerOne);
		new Colonel(playerOne);
		new General(playerOne);
		new Marshal(playerOne);
		new Bomb(playerOne);
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
	}

}
