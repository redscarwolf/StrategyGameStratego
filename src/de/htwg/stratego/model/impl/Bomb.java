package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Character;

public class Bomb extends AbstractCharacter {

	public Bomb(int player) {
		super(Character.BOMB_RANK, false, player);
	}

}
