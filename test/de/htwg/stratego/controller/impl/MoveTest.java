package de.htwg.stratego.controller.impl;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.PlayerFactory;
//erstellt nur eine Klasse und verwendet diese weiter
import de.htwg.stratego.model.impl.Rank;

public class MoveTest extends TestCase {

	private StrategoController sc;
	private Field field;
	
	@Before
	public void setUp() {
		field = new Field(10, 10);
		sc = new StrategoController(field, new PlayerFactory());
	}
	
	@Test
	public void testSelectedCellContainsNoCharacter() {
		assertFalse(new Move(0, 0, 0, 1, sc).execute());
	}
	
	@Test
	public void testCharacterIsNotMoveable() {
		sc.add(0, 0, Rank.FLAG);
		assertFalse(new Move(0, 0, 0, 1, sc).execute());
	}
	
	@Test
	public void testCharacterBelongsNotToCurrentPlayer() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(0, 0, Rank.SCOUT);
		sc.setState(new PlayerTurn(sc.nextPlayer(), sc));
		assertFalse(new Move(0, 0, 0, 1, sc).execute());
	}
	
	@Test
	public void testCellIsNotPassable() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(1, 4, Rank.SCOUT);
		sc.setState(new PlayerTurn(sc.getCurrentPlayer(), sc));
		assertFalse(new Move(1, 4, 2, 4, sc).execute());
	}
	
	@Test
	public void testCorrectScoutMove() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(1, 4, Rank.SCOUT);
		sc.add(2, 2, Rank.SCOUT);
		sc.add(1, 6, Rank.MINER);
		sc.add(4, 2, Rank.MINER);
		sc.setState(new PlayerTurn(sc.getCurrentPlayer(), sc));
		
		// illegal range of move
		assertFalse(new Move(1, 4, 0, 2, sc).execute());
		assertFalse(new Move(1, 4, 1, 4, sc).execute());
		
		// something is blocking the path
		assertFalse(new Move(1, 4, 1, 9, sc).execute());
		assertFalse(new Move(2, 2, 2, 9, sc).execute());
		assertFalse(new Move(1, 4, 9, 4, sc).execute());
		assertFalse(new Move(2, 2, 7, 2, sc).execute());
		
		// correct range of move
		assertTrue(new Move(1, 4, 1, 0, sc).execute());
		assertTrue(new Move(1, 0, 9, 0, sc).execute());
	}
	
	@Test
	public void testCorrectNormalMove() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(1, 1, Rank.SERGEANT);
		sc.setState(new PlayerTurn(sc.getCurrentPlayer(), sc));
		
		// illegal range of move
		assertFalse(new Move(1, 1, 1, 1, sc).execute());
		assertFalse(new Move(1, 1, 0, 0, sc).execute());
		assertFalse(new Move(1, 1, 6, 1, sc).execute());
		assertFalse(new Move(1, 1, 1, 5, sc).execute());
		
		// correct range of move
		assertTrue(new Move(1, 1, 2, 1, sc).execute());
	}
	
	@Test
	public void testToCharacterBelongsToCurrentPlayer() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(1, 1, Rank.SERGEANT);
		sc.add(2, 1, Rank.SERGEANT);
		sc.setState(new PlayerTurn(sc.getCurrentPlayer(), sc));
		
		assertFalse(new Move(1, 1, 2, 1, sc).execute());
	}
	
	@Test
	public void testEqualFight() {
		sc.add(1, 1, Rank.SERGEANT);
		sc.changeState();
		sc.add(2, 1, Rank.SERGEANT);
		sc.changeState();
		
		new Move(1, 1, 2, 1, sc).execute();
		assertFalse(field.getCell(1, 1).containsCharacter());
		assertFalse(field.getCell(2, 1).containsCharacter());
	}

	@Test
	public void testWinFight() {
		sc.add(1, 1, Rank.SERGEANT);
		sc.changeState();
		sc.add(2, 1, Rank.SPY);
		sc.changeState();
		
		new Move(1, 1, 2, 1, sc).execute();
		assertEquals(Rank.SERGEANT, field.getCell(2, 1).getCharacter().getRank());
	}
	
	@Test
	public void testLooseFight() {
		sc.add(1, 1, Rank.SPY);
		sc.changeState();
		sc.add(2, 1, Rank.SERGEANT);
		sc.changeState();
		
		new Move(1, 1, 2, 1, sc).execute();
		assertEquals(Rank.SERGEANT, field.getCell(2, 1).getCharacter().getRank());
	}

	@Test
	public void testMinerBombFight() {
		sc.add(1, 1, Rank.MINER);
		sc.changeState();
		sc.add(2, 1, Rank.BOMB);
		sc.changeState();
		
		new Move(1, 1, 2, 1, sc).execute();
		assertEquals(Rank.MINER, field.getCell(2, 1).getCharacter().getRank());
	}
	
	@Test
	public void testSpyMarshalFight() {
		sc.add(1, 1, Rank.SPY);
		sc.changeState();
		sc.add(2, 1, Rank.MARSHAL);
		sc.changeState();
		
		new Move(1, 1, 2, 1, sc).execute();
		assertEquals(Rank.SPY, field.getCell(2, 1).getCharacter().getRank());
	}
	
	@Test
	public void testUndoCommand() {
		sc.add(1, 1, Rank.SERGEANT);
		sc.changeState();
		sc.add(2, 1, Rank.SPY);
		sc.changeState();
		
		Move move = new Move(1, 1, 2, 1, sc);
		move.execute();
		move.undoCommand();

		assertEquals(Rank.SERGEANT, field.getCell(1, 1).getCharacter().getRank());
		assertEquals(Rank.SPY, field.getCell(2, 1).getCharacter().getRank());
		
		move = new Move(1, 1, 1, 0, sc);
		move.execute();
		move.undoCommand();
		assertEquals(Rank.SERGEANT, field.getCell(1, 1).getCharacter().getRank());
	}
}
