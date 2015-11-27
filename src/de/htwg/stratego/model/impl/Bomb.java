package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Bomb extends AbstractCharacter {

	public Bomb(Player player) {
		super(Rank.BOMB, false, player);
	}

}
