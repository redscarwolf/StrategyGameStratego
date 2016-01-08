package de.htwg.stratego.controller.impl;

import org.junit.Before;
import org.junit.Test;

import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.PlayerFactory;
import de.htwg.stratego.model.impl.Rank;
import junit.framework.TestCase;

public class AddCommandTest extends TestCase {

	private StrategoController sc;
	
	@Before
	public void setUp() {
		sc = new StrategoController(new Field(10, 10), new PlayerFactory());
	}

	@Test
	public void testUndoCommand() {
		sc.setState(new PlayerStart(sc.getCurrentPlayer(), sc));
		sc.add(1, 1, Rank.SCOUT);
		sc.undo();

		assertFalse(sc.getIField().getCell(1, 1).containsCharacter());
	}
	
}
