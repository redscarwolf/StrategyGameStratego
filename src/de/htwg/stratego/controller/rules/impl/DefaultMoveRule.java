package de.htwg.stratego.controller.rules.impl;

import de.htwg.stratego.controller.impl.GameState;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Rank;

public class DefaultMoveRule extends AbstractRule {

	private ICell fromCell;
	private ICell toCell;
	private ICharacter fromCharacter;
	private ICharacter toCharacter;
	private IPlayer player;
	private IField field;
	private GameState gameState;
	
	public DefaultMoveRule(int fromX, int fromY, int toX, int toY, IPlayer player, IField field, GameState gameState) {
		fromCell = field.getCell(fromX, fromY);
		toCell = field.getCell(toX, toY);
		fromCharacter = fromCell.getCharacter();
		toCharacter = toCell.getCharacter();
		this.player = player;
		this.field = field;
		this.gameState = gameState;
	}
	
	@Override
	public boolean verify() {
		if (!gameState.isMoveAllowed()) {
			message = "Move is not allowed.";
			return false;
		}
		
		// does selected cell contain a character
		if (fromCharacter == null) {
			return false;
		}

		// is Char moveable
		if (!fromCharacter.isMoveable()) {
			return false;
		}

		// is character a char of the player
		if (!fromCharacter.belongsTo(player)) {
			return false;
		}

		// isCell passable
		if (!toCell.isPassable()) {
			return false;
		}

		// correct range of move
		if (fromCharacter.getRank() == Rank.SCOUT) {
			if (!correctScoutMove()) {
				return false;
			}
		} else {
			if (!correctNormalMove()) {
				return false;
			}
		}
		
		// if Cell is not empty fight with toCharacter
		// only if toCharacter.getPlayer() is not equal to
		// fromCharacter.getPlayer()
		if (toCharacter != null)
			if (toCharacter.getPlayer() == fromCharacter.getPlayer()) {
				return false;
			}
		
		message = "Correct move.";
		return true;
	}

	private boolean correctScoutMove() {
		int fromX = fromCell.getX();
		int fromY = fromCell.getY();
		int toX = toCell.getX();
		int toY = toCell.getY();

		int dx = toX - fromX;
		int dy = toY - fromY;
		if (dx != 0 && dy != 0 || dx == dy) {
			return false;
		}

		if (dx == 0) {
			// normieren
			int absdy = Math.abs(dy);
			dy = dy / absdy;
			for (int i = dy; Math.abs(i) < absdy; i += dy) {
				ICell cell = field.getCell(fromX, fromY + i);
				if (!cell.isPassable() || cell.containsCharacter()) {
					return false;
				}
			}
		}
		if (dy == 0) {
			int absdx = Math.abs(dx);
			dx = dx / absdx;
			for (int i = dx; Math.abs(i) < absdx; i += dx) {
				ICell cell = field.getCell(fromX + i, fromY);
				if (!cell.isPassable() || cell.containsCharacter()) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean correctNormalMove() {
		int dx = Math.abs(fromCell.getX() - toCell.getX());
		int dy = Math.abs(fromCell.getY() - toCell.getY());
		if (dx > 1 || dy > 1 || dx == dy) {
			return false;
		}
		return true;
	}
	
}
