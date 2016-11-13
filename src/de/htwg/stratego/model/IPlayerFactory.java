package de.htwg.stratego.model;

import java.awt.Color;

public interface IPlayerFactory {

	IPlayer create();
	IPlayer create(String name, String symbol, Color color);
}
