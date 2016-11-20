package de.htwg.stratego.controller.impl;

import org.junit.Before;
import org.junit.Test;

import de.htwg.stratego.controller.rules.impl.DefaultMoveRule;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.PlayerFactory;
//erstellt nur eine Klasse und verwendet diese weiter
import de.htwg.stratego.model.impl.Rank;
import junit.framework.TestCase;

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
		assertFalse(new DefaultMoveRule(0, 0, 0, 1, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
	}
	
	@Test
	public void testCharacterIsNotMoveable() {
		sc.add(0, 0, Rank.FLAG);
		assertFalse(new DefaultMoveRule(0, 0, 0, 1, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
	}
	
	@Test
	public void testCharacterBelongsNotToCurrentPlayer() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(0, 0, Rank.SCOUT);
		sc.setState(new PlayerTurn(sc.nextChangePlayer(), sc));
		assertFalse(new DefaultMoveRule(0, 0, 0, 1, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
	}
	
	@Test
	public void testCellIsNotPassable() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(1, 4, Rank.SCOUT);
		sc.setState(new PlayerTurn(sc.getCurrentPlayer(), sc));
		assertFalse(new DefaultMoveRule(1, 4, 2, 4, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
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
		assertFalse(new DefaultMoveRule(1, 4, 0, 2, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		assertFalse(new DefaultMoveRule(1, 4, 1, 4, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		
		// something is blocking the path
		assertFalse(new DefaultMoveRule(1, 4, 1, 9, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		assertFalse(new DefaultMoveRule(2, 2, 2, 9, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		assertFalse(new DefaultMoveRule(1, 4, 9, 4, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		assertFalse(new DefaultMoveRule(2, 2, 7, 2, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		
		// correct range of move
		assertTrue(new DefaultMoveRule(1, 4, 1, 0, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		assertTrue(new DefaultMoveRule(1, 4, 0, 4, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
	}
	
	@Test
	public void testCorrectNormalMove() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(1, 1, Rank.SERGEANT);
		sc.setState(new PlayerTurn(sc.getCurrentPlayer(), sc));
		
		// illegal range of move
		assertFalse(new DefaultMoveRule(1, 1, 1, 1, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		assertFalse(new DefaultMoveRule(1, 1, 0, 0, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		assertFalse(new DefaultMoveRule(1, 1, 6, 1, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		assertFalse(new DefaultMoveRule(1, 1, 1, 5, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
		
		// correct range of move
		assertTrue(new DefaultMoveRule(1, 1, 2, 1, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
	}
	
	@Test
	public void testToCharacterBelongsToCurrentPlayer() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(1, 1, Rank.SERGEANT);
		sc.add(2, 1, Rank.SERGEANT);
		sc.setState(new PlayerTurn(sc.getCurrentPlayer(), sc));
		
		assertFalse(new DefaultMoveRule(1, 1, 2, 1, sc.getCurrentPlayer(), sc.getIField(), sc.getGameState()).verify());
	}
	
	@Test
	public void testEqualFight() {
		sc.add(1, 1, Rank.SERGEANT);
		sc.changeState();
		sc.add(2, 1, Rank.SERGEANT);
		sc.changeState();
		
		new MoveCommand(1, 1, 2, 1, sc).doCommand();
		assertFalse(field.getCell(1, 1).containsCharacter());
		assertFalse(field.getCell(2, 1).containsCharacter());
	}

	@Test
	public void testWinFight() {
		sc.add(1, 1, Rank.SERGEANT);
		sc.changeState();
		sc.add(2, 1, Rank.SPY);
		sc.changeState();
		
		new MoveCommand(1, 1, 2, 1, sc).doCommand();
		assertEquals(Rank.SERGEANT, field.getCell(2, 1).getCharacter().getRank());
	}
	
	@Test
	public void testLooseFight() {
		sc.add(1, 1, Rank.SPY);
		sc.changeState();
		sc.add(2, 1, Rank.SERGEANT);
		sc.changeState();
		
		new MoveCommand(1, 1, 2, 1, sc).doCommand();
		assertEquals(Rank.SERGEANT, field.getCell(2, 1).getCharacter().getRank());
	}

	@Test
	public void testMinerBombFight() {
		sc.add(1, 1, Rank.MINER);
		sc.changeState();
		sc.add(2, 1, Rank.BOMB);
		sc.changeState();
		
		new MoveCommand(1, 1, 2, 1, sc).doCommand();
		assertEquals(Rank.MINER, field.getCell(2, 1).getCharacter().getRank());
	}
	
	@Test
	public void testSpyMarshalFight() {
		sc.add(1, 1, Rank.SPY);
		sc.changeState();
		sc.add(2, 1, Rank.MARSHAL);
		sc.changeState();
		
		new MoveCommand(1, 1, 2, 1, sc).doCommand();
		assertEquals(Rank.SPY, field.getCell(2, 1).getCharacter().getRank());
	}
	
	@Test
	public void testUndoCommand() {
		sc.add(1, 1, Rank.SERGEANT);
		sc.changeState();
		sc.add(2, 1, Rank.SPY);
		sc.changeState();
		
		MoveCommand move = new MoveCommand(1, 1, 2, 1, sc);
		move.doCommand();
		move.undoCommand();

		assertEquals(Rank.SERGEANT, field.getCell(1, 1).getCharacter().getRank());
		assertEquals(Rank.SPY, field.getCell(2, 1).getCharacter().getRank());
		
		move = new MoveCommand(1, 1, 1, 0, sc);
		move.doCommand();
		move.undoCommand();
		assertEquals(Rank.SERGEANT, field.getCell(1, 1).getCharacter().getRank());
	}
}
