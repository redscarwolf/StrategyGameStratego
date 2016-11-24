package de.htwg.stratego.controller.impl;

import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.util.command.Command;

public class MoveCommand implements Command {

	private ICell fromCell;
	private ICell toCell;
	private ICharacter fromCharacter;
	private ICharacter toCharacter;
	private AbstractStrategoController sc;
	private GameState oldGameState;
	private String statusMove = "Your move was not possible. Try again.";

	public MoveCommand(int fromX, int fromY, int toX, int toY, AbstractStrategoController sc) {
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
		// Conditions of toCharacter
		if (toCharacter == null) {
			// if Cell is empty move Character to new position
			fromCell.setCharacter(null);
			toCell.setCharacter(fromCharacter);
			statusMove = movedFromToString();
		} else {
			int fightResult = fight(fromCharacter, toCharacter);
			if (fightResult > 0) { // success
				statusMove = movedFromToString() + foughtWithString(">");
				sc.removeCharacterToPlayer(toCell.getX(), toCell.getY());
				fromCell.setCharacter(null);
				toCell.setCharacter(fromCharacter);
			} else if (fightResult < 0) { // lost
				statusMove = movedFromToString() + foughtWithString("<");
				sc.removeCharacterToPlayer(fromCell.getX(), fromCell.getY());
			} else { // equal
				statusMove = movedFromToString() + foughtWithString("=");
				sc.removeCharacterToPlayer(fromCell.getX(), fromCell.getY());
				sc.removeCharacterToPlayer(toCell.getX(), toCell.getY());
			}
		}
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
