package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Character;

public class Lieutenant extends AbstractCharacter {

	public Lieutenant(int player) {
		super(Character.LIEUTENANT_RANK, true, player);
	}

}
