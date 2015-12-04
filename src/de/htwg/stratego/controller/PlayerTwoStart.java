package de.htwg.stratego.controller;

import de.htwg.stratego.model.Character;
import de.htwg.stratego.model.impl.Player;

public class PlayerTwoStart extends AbstractGameState {

	public PlayerTwoStart(StrategoController sc) {
		super(sc);
	}

	@Override
	public Player getCurrentPlayer() {
		return sc.getPlayerTwo();
	}

	@Override
	public String getFieldString() {
		return sc.getField().getFieldString(sc.getPlayerTwo());
	}

	@Override
	public Character remove(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int x, int y, int rank) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveChar(int fromX, int fromY, int toX, int toY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toStringPlayerStatus() {
		return "Set your characters, player 2!";
	}

	@Override
	public void changeState() {
		sc.setState(new PlayerOneTurn(sc));
	}
	
}
