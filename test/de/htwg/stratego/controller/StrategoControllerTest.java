package de.htwg.stratego.controller;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.impl.FieldFactory;
import de.htwg.stratego.model.impl.Rank;
import junit.framework.TestCase;

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
		sc.add(0, 0, Rank.FLAG);
		sc.add(1, 0, Rank.SERGEANT);
		
		// add characters for player two
		sc.setState(new PlayerTwoStart(sc));
		sc.add(1, 2, Rank.FLAG);
		sc.add(0, 2, Rank.SERGEANT);

		System.out.println(field.getFieldString(null));
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
		assertEquals(field.getFieldString(sc.getPlayerTwo()), sc.getFieldString());
	}
	
	@Test
	public void testMoveChar() {
		boolean moveSuccess;
		sc.setState(playerOneTurn);

		// move a non movable character
		moveSuccess = sc.moveChar(0, 0, 0, 1, sc.getPlayerOne());
		assertFalse(moveSuccess);
		
		// move a null character (the selected cell contains no character)
		moveSuccess = sc.moveChar(1, 1, 2, 1, sc.getPlayerOne());
		assertFalse(moveSuccess);
		
		// move a character, that is not assigned to player one
		moveSuccess = sc.moveChar(0, 2, 0, 1, sc.getPlayerOne());
		assertFalse(moveSuccess);
		
		// incorrect range of move
		moveSuccess = sc.moveChar(1, 0, 3, 0, sc.getPlayerOne());
		assertFalse(moveSuccess);
		
		// move the character to a cell, that contains a character that is assigned to player one
			moveSuccess = sc.moveChar(1, 0, 0, 0, sc.getPlayerOne());
			assertFalse(moveSuccess);
	
		// correct turn, move character to empty cell
		moveSuccess = sc.moveChar(1, 0, 1, 1, sc.getPlayerOne());
		assertTrue(moveSuccess);
		
		// correct turn, fight with the other character
		moveSuccess = sc.moveChar(1, 1, 1, 2, sc.getPlayerOne());
		assertTrue(moveSuccess);
	}
}
