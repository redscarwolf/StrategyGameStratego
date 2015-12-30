package de.htwg.stratego.controller.impl;

import junit.framework.TestCase;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.controller.impl.GameState;
import de.htwg.stratego.controller.impl.PlayerOneStart;
import de.htwg.stratego.controller.impl.PlayerOneTurn;
import de.htwg.stratego.controller.impl.PlayerOneWinner;
import de.htwg.stratego.controller.impl.PlayerTwoStart;
import de.htwg.stratego.controller.impl.PlayerTwoTurn;
import de.htwg.stratego.controller.impl.PlayerTwoWinner;
import de.htwg.stratego.controller.impl.StrategoController;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.PlayerFactory;
import de.htwg.stratego.model.impl.Rank;

public class StrategoControllerTest extends TestCase {
	private StrategoController sc;
	private IField field;
	
	private GameState playerOneStart;
	private GameState playerTwoStart;
	private GameState playerOneTurn;
	private GameState playerTwoTurn;
	
	@BeforeClass
	public void setUp() {
		sc = new StrategoController(new Field(10, 10), new PlayerFactory());
		field = sc.getIField();
		
		playerOneStart = new PlayerOneStart(sc);
		playerTwoStart = new PlayerTwoStart(sc);
		playerOneTurn = new PlayerOneTurn(sc); 
		playerTwoTurn = new PlayerTwoTurn(sc);
		
		// add characters for player one
		sc.add(5, 1, Rank.FLAG);
		sc.add(1, 0, Rank.SERGEANT);
		
		// add characters for player two
		sc.setState(new PlayerTwoStart(sc));
		sc.add(2, 1, Rank.FLAG);
		sc.add(0, 2, Rank.SERGEANT);
		sc.add(5, 2, Rank.SERGEANT);
	}
	
	@Test
	public void testGetStatusString() {
		assertEquals("Welcome to HTWG Stratego!", sc.getStatusString());
	}
	
	@Test
	public void testGetField() {
		assertEquals(field, sc.getIField());
	}
	
	@Test
	public void testGetFieldString() {
		assertEquals(field.toString(), sc.getFieldString());
	}
	
	@Test
	public void testMove() {
		// illegal state, move not allowed
		sc.setState(playerOneStart);
		sc.move(1, 0, 2, 0);
		assertFalse(sc.getIField().getCell(2, 0).containsCharacter());
		assertEquals("Illegal Argument! Moving of Characters is not allowed, jet. Try again.",
				sc.getStatusString());
	
		// correct move
		sc.setState(playerOneTurn);
		sc.move(1, 0, 1, 1);
		assertTrue(sc.getIField().getCell(1, 1).containsCharacter());
		assertTrue(sc.getGameState() instanceof PlayerTwoTurn);
		
		// wrong move
		sc.move(0, 2, 0, 5);
		assertEquals("Illegal Argument! Your move is not possible.",
				     sc.getStatusString());
		
		// correct move, game over, player one win
		sc.setState(playerOneTurn);
		sc.move(1, 1, 2, 1);
		assertEquals("GAME OVER!", sc.getStatusString());
		assertTrue(sc.getGameState() instanceof PlayerOneWinner);

		// correct move, game over, player two win
		sc.setState(playerTwoTurn);
		sc.move(5, 2, 5, 1);
		assertEquals("GAME OVER!", sc.getStatusString());
		assertTrue(sc.getGameState() instanceof PlayerTwoWinner);
	}
	
	@Test
	public void testAdd() {
		// illegal state, add not allowed
		sc.setState(playerOneTurn);
		sc.add(9, 9, Rank.BOMB);
		assertFalse(sc.getIField().getCell(9, 9).containsCharacter());
		
		// character not in player list
		sc.setState(playerOneStart);
		sc.add(9, 9, Rank.FLAG);
		assertFalse(sc.getIField().getCell(9, 9).containsCharacter());
		
		// cell contains already a character
		sc.add(5, 1, Rank.BOMB);
		assertEquals(sc.getIField().getCell(5, 1).getCharacter().getRank(), Rank.FLAG);
		
		// cell not passable
		sc.add(2, 4, Rank.BOMB);
		assertFalse(sc.getIField().getCell(2, 4).containsCharacter());
		
		// correct add
		sc.add(9, 9, Rank.BOMB);
		assertEquals(sc.getIField().getCell(9, 9).getCharacter().getRank(), Rank.BOMB);
	}
	
	@Test
	public void testRemove() {
		// illegal state, remove not allowed
		sc.setState(playerOneTurn);
		sc.removeNotify(5, 1);
		assertTrue(sc.getIField().getCell(5, 1).containsCharacter());
		
		// character belongs not to current player
		sc.setState(playerOneStart);
		sc.removeNotify(0, 2);
		assertTrue(sc.getIField().getCell(0, 2).containsCharacter());
		
		// cell contains no character
		sc.setState(playerOneStart);
		sc.removeNotify(9, 9);
		assertEquals("Illegal Argument! There is no character or "
				+ "you are not allowed to remove this character.",
				sc.getStatusString());
		
		// coccerct remove
		sc.removeNotify(5, 1);
		assertFalse(sc.getIField().getCell(5, 1).containsCharacter());
	}
	
	@Test
	public void testLost() {
		sc.setState(playerOneStart);
		sc.remove(5, 1);
		assertTrue(sc.lost(sc.getCurrentPlayer()));
	}
	
	@Test
	public void testToggleVisibilityOfCharacters() {
		sc.toggleVisibilityOfCharacters(sc.getPlayerOne(), true);

		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				ICell cell = field.getCell(x, y);
				if (!cell.containsCharacter()) {
					continue;
				}
				ICharacter character = cell.getCharacter();
				if (character.belongsTo(sc.getPlayerOne())) {
					assertTrue(character.isVisible());
				} else {
					assertFalse(character.isVisible());
				}
			}
		}
	}
	
	@Test
	public void testSetVisibilityOfAllCharacters() {
		sc.setVisibilityOfAllCharacters(false);

		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				ICell cell = field.getCell(x, y);
				if (!cell.containsCharacter()) {
					continue;
				}
				assertFalse(cell.getCharacter().isVisible());
			}
		}
	}
	
	@Test
	public void testToStringPlayerStatus() {
		sc.setState(playerOneStart);
		assertEquals(sc.getPlayerStatusString(), playerOneStart.toStringPlayerStatus());
	}
	
	@Test
	public void testToStringCharacterList() {
		assertEquals(sc.getCharacterListString(sc.getPlayerOne()), sc.getPlayerOne().getCharacterListAsString());
	}
}
