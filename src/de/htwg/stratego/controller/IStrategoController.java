package de.htwg.stratego.controller;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.util.observer.IObservable;

public interface IStrategoController extends IObservable {

	void reset();
	void undo();
	boolean add(int x, int y, int rank);
	boolean move(int fromX, int fromY, int toX, int toY);
	boolean removeNotify(int x, int y);
	void changeStateNotify();

	String getStatusString();
	String getFieldString();
	String getPlayerStatusString();
	
	void setNameOfPlayerOne(String name);
	void setNameOfPlayerTwo(String name);
	String getNameOfPlayerOne();
	String getNameOfPlayerTwo();
	String getCharacterListString(IPlayer player);
	IPlayer getCurrentPlayer();
	IPlayer getPlayerOne();
	IPlayer getPlayerTwo();
	String toJson();
	
	int numberOfCharactersOnField(int rank, IPlayer player);
	boolean containsCharacter(int x, int y);
	ICharacter getCharacter(int x, int y);
	String nameOfCharacter(int rank);
	int maxNumberOfCharactersPerPlayer(int rank);
	
	boolean isPassable(int x, int y);
	
	// only for Gui
	int getFieldHeight();
	int getFieldWidth();
}
