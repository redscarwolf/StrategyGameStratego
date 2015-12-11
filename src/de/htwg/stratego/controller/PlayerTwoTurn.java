package de.htwg.stratego.controller;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public class PlayerTwoTurn implements GameState {

	private StrategoController sc;
	
	public PlayerTwoTurn(StrategoController sc) {
		this.sc = sc;
	}

	@Override
	public IPlayer getCurrentPlayer() {
		return sc.getPlayerTwo();
	}

	@Override
	public String getFieldString() {
		return sc.getField().getFieldString(sc.getPlayerTwo());
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
		boolean moveSucces = sc.moveChar(fromX, fromY, toX, toY, getCurrentPlayer());
		if (moveSucces) {
			sc.setState(new PlayerOneTurn(sc));
		}
		if (sc.lost(sc.getPlayerOne())) {
			sc.setGameStatus(GameStatus.GAME_OVER);
			sc.setState(new PlayerTwoWinner(sc));
		}
	}

	@Override
	public String toStringPlayerStatus() {
		return "It's your turn, player 2!";
	}

	@Override
	public void changeState() {
//		sc.setState(new PlayerOneTurn(sc));
	}
	
}
