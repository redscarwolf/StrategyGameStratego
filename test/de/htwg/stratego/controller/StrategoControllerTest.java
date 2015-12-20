package de.htwg.stratego.controller;

import junit.framework.TestCase;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.impl.FieldFactory;
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
		sc = new StrategoController(new FieldFactory());
		field = sc.getField();
		
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
	public void testGetStatus() {
		assertEquals(GameStatus.WELCOME, sc.getStatus());
	}
	
	@Test
	public void testGetField() {
		assertEquals(field, sc.getField());
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
		assertFalse(sc.getField().getCell(2, 0).containsCharacter());
		assertEquals(sc.getStatus(), GameStatus.ILLEGAL_ARGUMENT);
	
		// correct move
		sc.setState(playerOneTurn);
		sc.move(1, 0, 1, 1);
		assertTrue(sc.getField().getCell(1, 1).containsCharacter());
		assertTrue(sc.getGameState() instanceof PlayerTwoTurn);
		
		// wrong move
		sc.move(0, 2, 0, 5);
		assertEquals(sc.getStatus(), GameStatus.ILLEGAL_ARGUMENT);
		
		// correct move, game over, player one win
		sc.setState(playerOneTurn);
		sc.move(1, 1, 2, 1);
		assertEquals(sc.getStatus(), GameStatus.GAME_OVER);
		assertTrue(sc.getGameState() instanceof PlayerOneWinner);

		// correct move, game over, player two win
		sc.setState(playerTwoTurn);
		sc.move(5, 2, 5, 1);
		assertEquals(sc.getStatus(), GameStatus.GAME_OVER);
		assertTrue(sc.getGameState() instanceof PlayerTwoWinner);
	}
	
	@Test
	public void testAdd() {
		// illegal state, add not allowed
		sc.setState(playerOneTurn);
		sc.add(9, 9, Rank.BOMB);
		assertFalse(sc.getField().getCell(9, 9).containsCharacter());
		
		// character not in player list
		sc.setState(playerOneStart);
		sc.add(9, 9, Rank.FLAG);
		assertFalse(sc.getField().getCell(9, 9).containsCharacter());
		
		// cell contains already a character
		sc.add(5, 1, Rank.BOMB);
		assertEquals(sc.getField().getCell(5, 1).getCharacter().getRank(), Rank.FLAG);
		
		// cell not passable
		sc.add(2, 4, Rank.BOMB);
		assertFalse(sc.getField().getCell(2, 4).containsCharacter());
		
		// correct add
		sc.add(9, 9, Rank.BOMB);
		assertEquals(sc.getField().getCell(9, 9).getCharacter().getRank(), Rank.BOMB);
	}
	
	@Test
	public void testRemove() {
		// illegal state, remove not allowed
		sc.setState(playerOneTurn);
		sc.removeNotify(5, 1);
		assertTrue(sc.getField().getCell(5, 1).containsCharacter());
		
		// character belongs not to current player
		sc.setState(playerOneStart);
		sc.removeNotify(0, 2);
		assertTrue(sc.getField().getCell(0, 2).containsCharacter());
		
		// cell contains no character
		sc.setState(playerOneStart);
		sc.removeNotify(9, 9);
		assertEquals(sc.getStatus(), GameStatus.ILLEGAL_ARGUMENT);
		
		// coccerct remove
		sc.removeNotify(5, 1);
		assertFalse(sc.getField().getCell(5, 1).containsCharacter());
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
		assertEquals(sc.toStringPlayerStatus(), playerOneStart.toStringPlayerStatus());
	}
	
	@Test
	public void testToStringCharacterList() {
		assertEquals(sc.toStringCharacterList(sc.getPlayerOne()), sc.getPlayerOne().getCharacterListAsString());
	}
}
