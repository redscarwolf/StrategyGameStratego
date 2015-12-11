package de.htwg.stratego.controller;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public class PlayerOneTurn implements GameState {

	private StrategoController sc;
	
	public PlayerOneTurn(StrategoController sc) {
		this.sc = sc;
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
		boolean moveSucces = sc.moveChar(fromX, fromY, toX, toY, getCurrentPlayer());
		if (moveSucces) {
			sc.setState(new PlayerTwoTurn(sc));
		}
		if (sc.lost(sc.getPlayerTwo())) {
			sc.setGameStatus(GameStatus.GAME_OVER);
			sc.setState(new PlayerOneWinner(sc));
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
