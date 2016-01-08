package de.htwg.stratego.controller.impl;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.PlayerFactory;
import de.htwg.stratego.model.impl.Rank;
import junit.framework.TestCase;

public class StrategoControllerTest extends TestCase {
	private StrategoController sc;
	private IField field;

	private IPlayer playerOne;
	private IPlayer playerTwo;
	
	private GameState playerOneStart;
	private GameState playerTwoStart;
	private GameState playerOneTurn;
	private GameState playerTwoTurn;

	@BeforeClass
	public void setUp() {
		sc = new StrategoController(new Field(10, 10), new PlayerFactory());
		field = sc.getIField();

		playerOne = sc.getPlayer()[0];
		playerTwo = sc.getPlayer()[1];
		
		playerOneStart = new PlayerStart(playerOne, sc);
		playerOneTurn = new PlayerTurn(playerOne, sc);
		playerTwoStart = new PlayerStart(playerTwo, sc);
		playerTwoTurn = new PlayerTurn(playerTwo, sc);

		// add characters for player one
		sc.setState(playerOneStart);
		sc.add(5, 1, Rank.FLAG);
		sc.add(1, 0, Rank.SERGEANT);

		// add characters for player two
		sc.setState(playerTwoStart);
		sc.setCurrentPlayer(1);
		sc.add(2, 1, Rank.FLAG);
		sc.add(0, 2, Rank.SERGEANT);
		sc.add(5, 2, Rank.SERGEANT);
	}

	@Test
	public void testReset() {
		sc.reset();
		
		assertEquals(sc.getCurrentPlayer(), sc.getPlayer()[0]);
		for (int x = 0; x < field.getWidth(); x++) {
			for (int y = 0; y < field.getHeight(); y++) {
				assertFalse(sc.getIField().getCell(x, y).containsCharacter());
			}
		}
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
	public void testUndo() {
		sc.undo();
		assertFalse(sc.getIField().getCell(5, 2).containsCharacter());
		assertEquals(sc.getStatusString(), "Undo.");
	}
	
	@Test
	public void testMove() {
		// illegal state, move not allowed
		sc.setState(playerOneStart);
		sc.setCurrentPlayer(0);
		sc.move(1, 0, 2, 0);
		assertFalse(sc.getIField().getCell(2, 0).containsCharacter());
		assertEquals("Moving of Characters is not allowed.",
				sc.getStatusString());

		// correct move
		sc.setState(playerOneTurn);
		sc.setCurrentPlayer(0);
		sc.move(1, 0, 1, 1);
		assertTrue(sc.getIField().getCell(1, 1).containsCharacter());

		// wrong move
		sc.setState(playerTwoTurn);
		sc.setCurrentPlayer(1);
		sc.move(0, 2, 0, 5);
		assertEquals("Your move was not possible. Try again.",
				sc.getStatusString());

		// correct move, game over, player one win
		sc.setState(playerOneTurn);
		sc.setCurrentPlayer(0);
		sc.move(1, 1, 2, 1);
		assertEquals("GAME OVER!", sc.getStatusString());

		// correct move, game over, player two win
		sc.setCurrentPlayer(1);
		sc.setState(playerTwoTurn);
		sc.move(5, 2, 5, 1);
		assertEquals("GAME OVER!", sc.getStatusString());
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
		assertEquals(sc.getIField().getCell(5, 1).getCharacter().getRank(),
				Rank.FLAG);

		// cell not passable
		sc.add(2, 4, Rank.BOMB);
		assertFalse(sc.getIField().getCell(2, 4).containsCharacter());

		// correct add
		sc.add(9, 9, Rank.BOMB);
		assertEquals(sc.getIField().getCell(9, 9).getCharacter().getRank(),
				Rank.BOMB);
	}

	@Test
	public void testRemove() {
		// illegal state, remove not allowed
		sc.setState(playerOneTurn);
		sc.setCurrentPlayer(0);
		sc.removeNotify(5, 1);
		assertTrue(sc.getIField().getCell(5, 1).containsCharacter());

		// character belongs not to current player
		sc.setState(playerOneStart);
		sc.setCurrentPlayer(0);
		sc.removeNotify(0, 2);
		assertTrue(sc.getIField().getCell(0, 2).containsCharacter());

		// cell contains no character
		sc.setState(playerOneStart);
		sc.setCurrentPlayer(0);
		sc.removeNotify(9, 9);
		assertEquals("There is no character or "
					+ "you are not allowed to remove this character.",
				sc.getStatusString());

		// coccerct remove
		sc.removeNotify(5, 1);
		assertFalse(sc.getIField().getCell(5, 1).containsCharacter());
	}

	@Test
	public void testLost() {
		sc.setState(playerOneStart);
		sc.setCurrentPlayer(0);
		sc.remove(5, 1);
		assertTrue(sc.lost(sc.getCurrentPlayer()));
	}

	@Test
	public void testToggleVisibilityOfCharacters() {
		sc.toggleVisibilityOfCharacters(sc.getCurrentPlayer(), true);

		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				ICell cell = field.getCell(x, y);
				if (!cell.containsCharacter()) {
					continue;
				}
				ICharacter character = cell.getCharacter();
				if (character.belongsTo(sc.getCurrentPlayer())) {
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
		assertEquals(sc.getPlayerStatusString(),
				playerOneStart.toStringPlayerStatus());
	}

	@Test
	public void testToStringCharacterList() {
		assertEquals(sc.getCharacterListString(sc.getCurrentPlayer()), sc
				.getCurrentPlayer().getCharacterListAsString());
	}
	
	@Test
	public void testChangeStateNotify() {
		sc.setState(playerOneStart);
		sc.setCurrentPlayer(0);
		sc.changeStateNotify();
		assertTrue(sc.getGameState() instanceof PlayerStart);
	}
	
	@Test
	public void testGetFieldWidth() {
		assertEquals(sc.getFieldWidth(), 10);
	}
	
	@Test
	public void testGetFieldHeight() {
		assertEquals(sc.getFieldHeight(), 10);
	}

}
