package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Character;

public class Captain extends AbstractCharacter {

	public Captain(int rang, boolean moveable, int player) {
		super(Character.CAPTAIN_RANK, true, player);
	}

}
