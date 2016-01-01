package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.util.command.Command;

public class RemoveCommand implements Command {

	private ICell cell;
	private IPlayer player;
	private ICharacter character;
	private StrategoController sc;
	private GameState oldGameState;
	
	public RemoveCommand(ICell cell, IPlayer player, ICharacter character, StrategoController sc) {
		this.cell = cell;
		this.player = player;
		this.character = character;
		this.sc = sc;
		oldGameState = sc.getGameState();
	}
	
	@Override
	public void doCommand() {
		cell.removeCharacter();
		player.addCharacter(character);
	}

	@Override
	public void undoCommand() {
		cell.setCharacter(character);
		player.removeCharacter(character);
		sc.setState(oldGameState);
	}

}
