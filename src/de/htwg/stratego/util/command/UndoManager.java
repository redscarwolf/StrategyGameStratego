package de.htwg.stratego.util.command;

import java.util.Deque;
import java.util.LinkedList;

public class UndoManager {

	private Deque<Command> undoStack = new LinkedList<>();
	
	public void doCommand(Command newCommand) {
		newCommand.doCommand();
		undoStack.push(newCommand);
	}

	public void undoCommand() {
		if (!undoStack.isEmpty()) {
			Command command = undoStack.poll();
			command.undoCommand();
		}
	}
	
	public void clear() {
		undoStack.clear();
	}
	
}
