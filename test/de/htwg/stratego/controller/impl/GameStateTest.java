package de.htwg.stratego.controller.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.controller.state.impl.PlayerStart;
import de.htwg.stratego.controller.state.impl.PlayerTransfer;
import de.htwg.stratego.controller.state.impl.PlayerTurn;
import de.htwg.stratego.controller.state.impl.PlayerWinner;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.PlayerFactory;
import junit.framework.TestCase;

public class GameStateTest extends TestCase {

	private SingleDeviceStrategoController sc;

	private GameState playerStart;
	private GameState playerTurn;
	private GameState playerTransfer;
	private GameState playerWinner;

	@BeforeClass
	public void setUp() {
		sc = new SingleDeviceStrategoController(new Field(10, 10), new PlayerFactory());

		playerStart = new PlayerStart(sc.getCurrentPlayer(), sc);
		playerTransfer = new PlayerTransfer(sc.getCurrentPlayer(), sc);
		playerTurn = new PlayerTurn(sc.getCurrentPlayer(), sc);
		playerWinner = new PlayerWinner(sc.getCurrentPlayer());
	}

	@Test
	public void testIsMoveAllowed() {
		assertFalse(playerStart.isMoveAllowed());
		assertFalse(playerTransfer.isMoveAllowed());
		assertTrue(playerTurn.isMoveAllowed());
		assertFalse(playerWinner.isMoveAllowed());
	}

	@Test
	public void testIsAddAllowed() {
		assertTrue(playerStart.isAddAllowed());
		assertFalse(playerTransfer.isAddAllowed());
		assertFalse(playerTurn.isAddAllowed());
		assertFalse(playerWinner.isAddAllowed());
	}

	@Test
	public void testIsRemoveAllowed() {
		assertTrue(playerStart.isRemoveAllowed());
		assertFalse(playerTransfer.isRemoveAllowed());
		assertFalse(playerTurn.isRemoveAllowed());
		assertFalse(playerWinner.isRemoveAllowed());
	}

	@Test
	public void testToStringPlayerStatus() {
		assertEquals(playerStart.toStringPlayerStatus(), "Set your characters, player " + sc.getCurrentPlayer().getName() + "!");
		assertEquals(playerTransfer.toStringPlayerStatus(), sc.getCurrentPlayer().getName() + " please press Button to continue.");
		assertEquals(playerTurn.toStringPlayerStatus(), "It's your turn, player " + sc.getCurrentPlayer().getName() + "!");
		assertEquals(playerWinner.toStringPlayerStatus(), "Player " + sc.getCurrentPlayer().getName() + " won!");
	}

	@Test
	public void testChangeState() {
		playerStart.changeState();
		assertTrue(sc.getGameState() instanceof PlayerStart);
		playerTransfer.changeState();
		assertTrue(sc.getGameState() instanceof PlayerTurn);
		playerTurn.changeState();
		assertTrue(sc.getGameState() instanceof PlayerTransfer);
	}

}
