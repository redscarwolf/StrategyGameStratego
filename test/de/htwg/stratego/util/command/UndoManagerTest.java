package de.htwg.stratego.util.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UndoManagerTest {
	
	UndoManager undoManager;
	TestReceiver sum;
	TestUndoableCommand testObj;
	
	@Before
	public void setup() {
		undoManager = new UndoManager();
		sum = new TestReceiver();
		testObj = new TestUndoableCommand(sum);		
	}

	@Test
	public void testDoUndoRedo() {
		undoManager.doCommand(testObj);
		assertEquals(1, sum.getSum());
		undoManager.undoCommand();
		assertEquals(0, sum.getSum());
	}
	
	@Test
	public void testEmptyUndoStack() {
		undoManager.undoCommand();
		assertEquals(0, sum.getSum());
	}

}
