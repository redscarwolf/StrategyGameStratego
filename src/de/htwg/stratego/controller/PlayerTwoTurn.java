package de.htwg.stratego.controller;

import de.htwg.stratego.model.Character;
import de.htwg.stratego.model.impl.Player;

public class PlayerTwoTurn extends AbstractGameState {

	public PlayerTwoTurn(StrategoController sc) {
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
		boolean b = sc.moveChar(fromX, fromY, toX, toY, getCurrentPlayer());
		if (b) {
			sc.setState(new PlayerOneTurn(sc));
		}
	}

	@Override
	public String toStringPlayerStatus() {
		return "It's your turn, player 2!";
	}

	@Override
	public void changeState() {
		sc.setState(new PlayerOneTurn(sc));
	}
	
}
