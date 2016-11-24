package de.htwg.stratego.controller.rules.impl;

import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;

public class DefaultRuleSystem extends AbstractRuleSystem {

	public DefaultRuleSystem(IField field) {
		super(field);
	}

	@Override
	public boolean verifyAdd(int x, int y, int rank, IPlayer player, boolean isPlayerOne, GameState gameState) {
		return verify(new DefaultAddRule(x, y, rank, player, isPlayerOne, field, gameState));
	}

	@Override
	public boolean verifySwap(int fromX, int fromY, int toX, int toY, IPlayer player, GameState gameState) {
		return verify(new DefaultSwapRule(fromX, fromY, toX, toY, player, field, gameState));
	}

	@Override
	public boolean verifyRemove(int x, int y, IPlayer player, GameState gameState) {
		return verify(new DefaultRemoveRule(x, y, player, field, gameState));
	}

	@Override
	public boolean verifyMove(int fromX, int fromY, int toX, int toY, IPlayer player, GameState gameState) {
		return verify(new DefaultMoveRule(fromX, fromY, toX, toY, player, field, gameState));
	}

}
