package de.htwg.stratego.model;

public interface ICharacter {
	
	int getRank();
	boolean isMoveable();
	void setVisible(boolean visible);
	boolean isVisible();
	IPlayer getPlayer();
	boolean belongsTo(IPlayer player);

}
