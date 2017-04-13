package de.htwg.stratego.model.impl;

import java.awt.Color;

import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.IPlayerFactory;

public class PlayerFactory implements IPlayerFactory {

	@Override
	public IPlayer create() {
		return new Player();
	}
	
	@Override
	public IPlayer create(String name, String symbol) {
		return new Player(name, symbol);
	}
}
