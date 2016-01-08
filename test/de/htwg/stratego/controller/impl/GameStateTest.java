package de.htwg.stratego.controller.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.PlayerFactory;
import junit.framework.TestCase;

public class GameStateTest extends TestCase {

	private StrategoController sc;

	private GameState playerStart;
	private GameState playerTurn;
	private GameState playerWinner;

	@BeforeClass
	public void setUp() {
		sc = new StrategoController(new Field(10, 10), new PlayerFactory());

		playerStart = new PlayerStart(sc.getCurrentPlayer(), sc);
		playerTurn = new PlayerTurn(sc.getCurrentPlayer(), sc);
		playerWinner = new PlayerWinner(sc.getCurrentPlayer());
	}

	@Test
	public void testIsMoveAllowed() {
		assertFalse(playerStart.isMoveAllowed());
		assertTrue(playerTurn.isMoveAllowed());
		assertFalse(playerWinner.isMoveAllowed());
	}

	@Test
	public void testIsAddAllowed() {
		assertTrue(playerStart.isAddAllowed());
		assertFalse(playerTurn.isAddAllowed());
		assertFalse(playerWinner.isAddAllowed());
	}

	@Test
	public void testIsRemoveAllowed() {
		assertTrue(playerStart.isRemoveAllowed());
		assertFalse(playerTurn.isRemoveAllowed());
		assertFalse(playerWinner.isRemoveAllowed());
	}

	@Test
	public void testToStringPlayerStatus() {
		assertEquals(playerStart.toStringPlayerStatus(), "Set your characters, player " + sc.getCurrentPlayer() + "!");
		assertEquals(playerTurn.toStringPlayerStatus(), "It's your turn, player " + sc.getCurrentPlayer() + "!");
		assertEquals(playerWinner.toStringPlayerStatus(), "Player " + sc.getCurrentPlayer() + " won!");
	}

	@Test
	public void testChangeState() {
		playerStart.changeState();
		assertTrue(sc.getGameState() instanceof PlayerStart);
		playerTurn.changeState();
		assertTrue(sc.getGameState() instanceof PlayerTurn);
	}

}
