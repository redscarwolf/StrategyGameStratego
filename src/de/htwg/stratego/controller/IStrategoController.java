package de.htwg.stratego.controller;

import de.htwg.stratego.controller.impl.GameStatus;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.util.observer.IObservable;

public interface IStrategoController extends IObservable {

	void add(int x, int y, int rank);
	void move(int fromX, int fromY, int toX, int toY);
	void removeNotify(int x, int y);
	void changeStateNotify();

	String getPlayerStatusString();
	String getFieldString();
	
	String toStringCharacterList(IPlayer player);
	// TODO delete #############
	GameStatus getStatus(); // only for StatusMessage
	IPlayer getPlayerOne(); // only for toStringList
	IPlayer getPlayerTwo();
}
