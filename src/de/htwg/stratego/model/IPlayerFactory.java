package de.htwg.stratego.model;

public interface IPlayerFactory {

	IPlayer create();
	IPlayer create(String name, String symbol);
}
