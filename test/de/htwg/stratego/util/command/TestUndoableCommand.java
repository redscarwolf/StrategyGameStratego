package de.htwg.stratego.util.command;

public class TestUndoableCommand implements Command {
	
	TestReceiver sum;
	
	public TestUndoableCommand(TestReceiver sum) {
		this.sum = sum;
	}

	@Override
	public void doCommand() {
		sum.add(1);
	}

	@Override
	public void undoCommand() {
		sum.add(-1);
	}

}
