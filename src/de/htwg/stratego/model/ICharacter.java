package de.htwg.stratego.model;

import de.htwg.stratego.model.impl.Player;

public interface ICharacter {
	
	int getRank();
	boolean isMoveable();
	void setVisible(boolean visible);
	boolean isVisible();
	IPlayer getPlayer();
	boolean belongsTo(IPlayer player);

}
