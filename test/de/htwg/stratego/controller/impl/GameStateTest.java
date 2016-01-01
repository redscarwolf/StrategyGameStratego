package de.htwg.stratego.controller.impl;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.PlayerFactory;

public class GameStateTest extends TestCase {

	private StrategoController sc;
	
	private GameState playerOneStart;
	private GameState playerTwoStart;
	private GameState playerOneTurn;
	private GameState playerTwoTurn;
	private GameState playerOneWinner;
	private GameState playerTwoWinner;
	
	@BeforeClass
	public void setUp() {
		sc = new StrategoController(new Field(10, 10), new PlayerFactory());

		playerOneStart = new PlayerOneStart(sc);
		playerTwoStart = new PlayerTwoStart(sc);
		playerOneTurn = new PlayerOneTurn(sc);
		playerTwoTurn = new PlayerTwoTurn(sc);
		playerOneWinner = new PlayerOneWinner(sc);
		playerTwoWinner = new PlayerTwoWinner(sc);
	}
	
	@Test
	public void testGetCurrentPlayer() {
		assertEquals(playerOneStart.getCurrentPlayer(), sc.getPlayerOne());
		assertEquals(playerOneTurn.getCurrentPlayer(), sc.getPlayerOne());
		assertEquals(playerTwoStart.getCurrentPlayer(), sc.getPlayerTwo());
		assertEquals(playerTwoTurn.getCurrentPlayer(), sc.getPlayerTwo());
		assertNull(playerOneWinner.getCurrentPlayer());
		assertNull(playerTwoWinner.getCurrentPlayer());
	}
	
	@Test
	public void testIsMoveAllowed() {
		assertFalse(playerOneStart.isMoveAllowed());
		assertTrue(playerOneTurn.isMoveAllowed());
		assertFalse(playerTwoStart.isMoveAllowed());
		assertTrue(playerTwoTurn.isMoveAllowed());
		assertFalse(playerOneWinner.isMoveAllowed());
		assertFalse(playerTwoWinner.isMoveAllowed());
	}
	
	@Test
	public void testIsAddAllowed() {
		assertTrue(playerOneStart.isAddAllowed());
		assertFalse(playerOneTurn.isAddAllowed());
		assertTrue(playerTwoStart.isAddAllowed());
		assertFalse(playerTwoTurn.isAddAllowed());
		assertFalse(playerOneWinner.isAddAllowed());
		assertFalse(playerTwoWinner.isAddAllowed());
	}
	
	@Test
	public void testIsRemoveAllowed() {
		assertTrue(playerOneStart.isRemoveAllowed());
		assertFalse(playerOneTurn.isRemoveAllowed());
		assertTrue(playerTwoStart.isRemoveAllowed());
		assertFalse(playerTwoTurn.isRemoveAllowed());
		assertFalse(playerOneWinner.isRemoveAllowed());
		assertFalse(playerTwoWinner.isRemoveAllowed());
	}
	
	@Test
	public void testToStringPlayerStatus() {
		assertEquals(playerOneStart.toStringPlayerStatus(), "Set your characters, player 1!");
		assertEquals(playerOneTurn.toStringPlayerStatus(), "It's your turn, player 1!");
		assertEquals(playerTwoStart.toStringPlayerStatus(), "Set your characters, player 2!");
		assertEquals(playerTwoTurn.toStringPlayerStatus(), "It's your turn, player 2!");
		assertEquals(playerOneWinner.toStringPlayerStatus(), "Player 1 won!");
		assertEquals(playerTwoWinner.toStringPlayerStatus(), "Player 2 won!");
	}
	
	@Test
	public void testChangeState() {
		playerOneStart.changeState();
		assertTrue(sc.getGameState() instanceof PlayerTwoStart);
		playerOneTurn.changeState();
		assertTrue(sc.getGameState() instanceof PlayerTwoTurn);
		playerTwoStart.changeState();
		assertTrue(sc.getGameState() instanceof PlayerOneTurn);
		playerTwoTurn.changeState();
		assertTrue(sc.getGameState() instanceof PlayerOneTurn);
	}
	
}
