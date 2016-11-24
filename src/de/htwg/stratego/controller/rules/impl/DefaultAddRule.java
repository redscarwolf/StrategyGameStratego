package de.htwg.stratego.controller.rules.impl;

import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;

public class DefaultAddRule extends AbstractRule {

	private ICell cell;
	private int rank;
	private IPlayer player;
	private GameState gameState;
	
	public DefaultAddRule(int x, int y, int rank, IPlayer player, IField field, GameState gameState) {
		cell = field.getCell(x, y);
		this.rank = rank;
		this.player = player;
		this.gameState = gameState;
	}
	
	@Override
	public boolean verify() {
		if (!gameState.isAddAllowed()) {
			message = "Add is not allowed.";
			return false;
		}

		if (player.hasSetupFinished()) {
			message = "Please wait the other Player isn't finished with setup.";
			return false;
		}

		if (!player.hasCharacter(rank)) {
			message = "All characters of type <" + rank + "> are on the field.";
			return false;
		}
		
		if (cell.containsCharacter()) {
			message = "Field already has a character.";
			return false;
		}

		if (!cell.isPassable()) {
			message = "Cell " + cell.getX() + "," + cell.getY() + " is not passable.";
			return false;
		}
		
		message = "Correct Add.";
		return true;
	}

}
