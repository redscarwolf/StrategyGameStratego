package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.util.command.Command;

public class RemoveCommand implements Command {

	private ICell cell;
	private IPlayer player;
	private ICharacter character;
	private AbstractStrategoController sc;
	private GameState oldGameState;
	
	public RemoveCommand(ICell cell, AbstractStrategoController sc) {
		this.cell = cell;
		this.character = cell.getCharacter();
		this.player = character.getPlayer();
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
