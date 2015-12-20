package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Player;

public abstract class AbstractCharacter implements ICharacter {

	protected int rank;
	protected boolean moveable;
	protected boolean visible;
	protected Player player;

	public AbstractCharacter(int rank, boolean moveable, Player player) {
		this.rank = rank;
		this.moveable = moveable;
		this.player = player;
		visible = true;
	}

	@Override
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public int getRank() {
		return rank;
	}
	
	@Override
	public boolean isMoveable() {
		return moveable;
	}
	
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}
	
	@Override
	public boolean belongsTo(IPlayer player) {
		return this.player == player;
	}
	
	@Override
	public String toString() {
		if (visible) {
			return String.format("%s%2s", player, Integer.toString(rank));
		}
		return " X ";
	}
	
}
