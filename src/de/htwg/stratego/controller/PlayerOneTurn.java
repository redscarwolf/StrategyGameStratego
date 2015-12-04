package de.htwg.stratego.controller;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public class PlayerOneTurn extends AbstractGameState {

	public PlayerOneTurn(StrategoController sc) {
		super(sc);
	}

	@Override
	public IPlayer getCurrentPlayer() {
		return sc.getPlayerOne();
	}

	@Override
	public String getFieldString() {
		return sc.getField().getFieldString(sc.getPlayerOne());
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
		boolean b = sc.moveChar(fromX, fromY, toX, toY, getCurrentPlayer());
		if (b) {
			sc.setState(new PlayerTwoTurn(sc));
		}
	}

	@Override
	public String toStringPlayerStatus() {
		return "It's your turn, player 1!";
	}

	@Override
	public void changeState() {
//		sc.setState(new PlayerTwoTurn(sc));
	}
	
}
