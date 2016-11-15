package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.util.command.Command;

public class SwapCommand implements Command {

	private ICell cell1;
	private ICell cell2;
	private StrategoController sc;
	private GameState oldGameState;

	public SwapCommand(ICell cell1, ICell cell2, StrategoController sc) {
		this.cell1 = cell1;
		this.cell2 = cell2;
		this.sc = sc;
		oldGameState = sc.getGameState();
	}
	
	@Override
	public void doCommand() {
		ICharacter character1 = cell1.removeCharacter();
		ICharacter character2 = cell2.removeCharacter();
		cell1.setCharacter(character2);
		cell2.setCharacter(character1);
	}

	@Override
	public void undoCommand() {
		doCommand();
		sc.setState(oldGameState);
	}

}
