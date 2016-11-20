package de.htwg.stratego.controller.rules.impl;

import de.htwg.stratego.controller.impl.GameState;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;

public class DefaultSwapRule extends AbstractRule {

	private ICell fromCell;
	private ICell toCell;
	private IPlayer player;
	private GameState gameState;
	
	public DefaultSwapRule(int fromX, int fromY, int toX, int toY, IPlayer player, IField field, GameState gameState) {
		fromCell = field.getCell(fromX, fromY);
		toCell = field.getCell(toX, toY);
		this.player = player;
		this.gameState = gameState;
	}

	@Override
	public boolean verify() {
		if (!gameState.isSwapAllowed()) {
			message = "Swap is not allowed.";
			return false;
		}
		
		if (!(fromCell.containsCharacter())) {
			message = "There is no character to swap.";
			return false;
		}

		if (fromCell == toCell) {
			message = "Swap not possible. Selected cells are equal.";
			return false;
		}

		if (!(fromCell.getCharacter().belongsTo(player))) {
			message = "Selected character does not belong to you.";
			return false;
		}

		if (toCell.containsCharacter()) {
			if (!(toCell.getCharacter().belongsTo(player))) {
				message = "Second character does not belong to you.";
				return false;
			}
		}

		message = "Correct Swap.";
		return true;
	}

}
