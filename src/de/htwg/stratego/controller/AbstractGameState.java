package de.htwg.stratego.controller;

public abstract class AbstractGameState implements GameState {

	protected StrategoController sc;
	
	public AbstractGameState(StrategoController sc) {
		this.sc = sc;
	}
	
}
