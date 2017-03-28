package de.htwg.stratego.controller.state.impl;

import de.htwg.stratego.controller.impl.AbstractStrategoController;
import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.model.EGameState;
import de.htwg.stratego.model.IPlayer;

public class PlayerStart implements GameState {

	private AbstractStrategoController sc;
	private IPlayer player;
	
	public PlayerStart(IPlayer player, AbstractStrategoController sc) {
		this.sc = sc;
		this.player = player;
	}

	@Override
	public boolean isMoveAllowed() {
		return false;
	}

	@Override
	public boolean isAddAllowed() {
		return true;
	}
	
	@Override
	public boolean isSwapAllowed() {
		return true;
	}

	@Override
	public boolean isRemoveAllowed() {
		return true;
	}

	@Override
	public boolean isFinishAllowed() {
		return true;
	}

	@Override
	public String toStringPlayerStatus() {
		return "Set your characters, player " + player.getName() + "!";
	}

	@Override
	public void changeState() {
		IPlayer[] player = sc.getPlayer();
		if (sc.getCurrentPlayer() == player[player.length - 1]) {
			sc.setState(new PlayerTransfer(sc.nextChangePlayer(), sc));
			sc.setVisibilityOfAllCharacters(false);
		} else {
			sc.setState(new PlayerStart(sc.nextChangePlayer(), sc));
			sc.toggleVisibilityOfCharacters(sc.getCurrentPlayer(), true);
		}
	}

	@Override
	public String getName() {
		return "start";
	}

	@Override
	public EGameState getEGameState() {
		return EGameState.PLAYER_START;
	}

}
