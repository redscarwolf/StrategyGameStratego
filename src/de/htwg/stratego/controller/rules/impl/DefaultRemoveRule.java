package de.htwg.stratego.controller.rules.impl;

import de.htwg.stratego.controller.impl.GameState;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;

public class DefaultRemoveRule extends AbstractRule {

	private ICell cell;
	private IPlayer player;
	private GameState gameState;

	public DefaultRemoveRule(int x, int y, IPlayer player, IField field, GameState gameState) {
		this.cell = field.getCell(x, y);
		this.player = player;
		this.gameState = gameState;
	}
	
	@Override
	public boolean verify() {
		if (!gameState.isRemoveAllowed()) {
			message = "Remove is not allowed.";
			return false;
		}
		
		if (!cell.containsCharacter()) {
			message = "There is no character to remove.";
			return false;
		}
		
		if (!cell.getCharacter().belongsTo(player)) {
			message = "This character does not belong to you.";
			return false;
		}
		
		message = "Correct remove.";
		return true;
	}

}
