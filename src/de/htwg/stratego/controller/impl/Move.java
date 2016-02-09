package de.htwg.stratego.controller.impl;

import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.util.command.Command;

public class Move implements Command {

	private ICell fromCell;
	private ICell toCell;
	private ICharacter fromCharacter;
	private ICharacter toCharacter;
	private StrategoController sc;
	private GameState oldGameState;
	private String statusMove = "Your move was not possible. Try again.";

	public Move(int fromX, int fromY, int toX, int toY, StrategoController sc) {
		this.sc = sc;
		fromCell = sc.getIField().getCell(fromX, fromY);
		toCell = sc.getIField().getCell(toX, toY);
		fromCharacter = fromCell.getCharacter();
		toCharacter = toCell.getCharacter();
		oldGameState = sc.getGameState();
	}

	public String getMoveStatusString() {
		return statusMove;
	}

	private String movedFromToString() {
		String turn = "It is your turn Player " + sc.getPlayer()[sc.nextPlayer()] + " .\n";
		return turn + "Player " + fromCharacter.getPlayer().toString() + " moved from (" + fromCell.getX() + ","
				+ fromCell.getY() + ") to (" + toCell.getX() + "," + toCell.getY() + ")";
	}

	private String foughtWithString(String fightResultSymbol) {
		return String.format("\nResult of Fight: %s%s %s %s%s", fromCharacter.getPlayer(), fromCharacter.getRank(), fightResultSymbol,
				toCharacter.getPlayer(), toCharacter.getRank());
	}

	public boolean execute() {
		if (!isValid()) {
			return false;
		}

		// Conditions of toCharacter
		if (toCharacter == null) {
			// if Cell is empty move Character to new position
			fromCell.setCharacter(null);
			toCell.setCharacter(fromCharacter);
			statusMove = movedFromToString();
		} else {
			// if Cell is not empty fight with toCharacter
			// only if toCharacter.getPlayer() is not equal to
			// fromCharacter.getPlayer()
			if (toCharacter.getPlayer() == fromCharacter.getPlayer()) {
				return false;
			}

			int fightResult = fight(fromCharacter, toCharacter);
			if (fightResult > 0) { // success
				statusMove = movedFromToString() + foughtWithString(">");
				sc.remove(toCell.getX(), toCell.getY(), toCharacter.getPlayer());
				fromCell.setCharacter(null);
				toCell.setCharacter(fromCharacter);
			} else if (fightResult < 0) { // lost
				statusMove = movedFromToString() + foughtWithString("<");
				sc.remove(fromCell.getX(), fromCell.getY(), fromCharacter.getPlayer());
			} else { // equal
				statusMove = movedFromToString() + foughtWithString("=");
				sc.remove(fromCell.getX(), fromCell.getY(), fromCharacter.getPlayer());
				sc.remove(toCell.getX(), toCell.getY(), toCharacter.getPlayer());
			}
		}

		return true;
	}

	private boolean isValid() {
		// check is move inside of Field

		// Conditions of fromCharacter
		// does selected cell contain a character
		if (fromCharacter == null) {
			return false;
		}

		// is Char moveable
		if (!fromCharacter.isMoveable()) {
			return false;
		}

		// is character a char of the player
		if (!fromCharacter.belongsTo(sc.getCurrentPlayer())) {
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
				ICell cell = sc.getIField().getCell(fromX, fromY + i);
				if (!cell.isPassable() || cell.containsCharacter()) {
					return false;
				}
			}
		}
		if (dy == 0) {
			int absdx = Math.abs(dx);
			dx = dx / absdx;
			for (int i = dx; Math.abs(i) < absdx; i += dx) {
				ICell cell = sc.getIField().getCell(fromX + i, fromY);
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

	private int fight(ICharacter chAttacker, ICharacter chDefender) {
		// get Character rank
		int rankAttacker = chAttacker.getRank();
		int rankDefender = chDefender.getRank();

		if (rankAttacker == Rank.MINER && rankDefender == Rank.BOMB) {
			return 1;
		}

		if (rankAttacker == Rank.SPY && rankDefender == Rank.MARSHAL) {
			return 1;
		}

		if (rankAttacker > rankDefender) {
			// success
			return 1;
		} else if (rankAttacker < rankDefender) {
			// lost
			return -1;
		} else {
			// equal both lose
			return 0;
		}
	}

	@Override
	public void doCommand() {
		return;
	}

	@Override
	public void undoCommand() {
		fromCell.setCharacter(fromCharacter);
		toCell.setCharacter(toCharacter);
		fromCharacter.getPlayer().removeCharacter(fromCharacter);
		if (toCharacter != null) {
			toCharacter.getPlayer().removeCharacter(toCharacter);
		}
		sc.setState(oldGameState);
	}

}
