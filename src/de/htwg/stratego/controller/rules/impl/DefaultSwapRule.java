package de.htwg.stratego.controller.rules.impl;

import de.htwg.stratego.controller.impl.AbstractStrategoController;
import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;

public class DefaultSwapRule extends AbstractRule {

	private ICell fromCell;
	private ICell toCell;
	private IPlayer player;
	private GameState gameState;
	private AbstractStrategoController strategoController;

	public DefaultSwapRule(int fromX, int fromY, int toX, int toY, IPlayer player, AbstractStrategoController strategoController) {
		fromCell = strategoController.getIField().getCell(fromX, fromY);
		toCell = strategoController.getIField().getCell(toX, toY);
		this.player = player;
		this.gameState = strategoController.getGameState();
		this.strategoController = strategoController;
	}

	@Override
	public boolean verify() {
		if (!gameState.isSwapAllowed()) {
			message = "Swap is not allowed.";
			return false;
		}

		if (player.hasSetupFinished()) {
			message = "Please wait the other Player isn't finished with setup.";
			return false;
		}

		if (!strategoController.isInCorrectZone(toCell.getX(), toCell.getY(), player)) {
			message = "You can only swap characters in your zone.";
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
