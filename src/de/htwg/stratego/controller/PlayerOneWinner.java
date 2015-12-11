package de.htwg.stratego.controller;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public class PlayerOneWinner implements GameState {

	private StrategoController sc;
	
	public PlayerOneWinner(StrategoController sc) {
		this.sc = sc;
	}
	
	@Override
	public IPlayer getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFieldString() {
		return sc.getField().getFieldString(null);
	}

	@Override
	public ICharacter remove(int x, int y) {
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
		return "Player 1 won!";
	}

	@Override
	public void changeState() {
		// TODO Auto-generated method stub
		
	}

}
