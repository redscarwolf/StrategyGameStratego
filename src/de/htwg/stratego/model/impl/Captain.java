package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Captain extends AbstractCharacter {

	public Captain(int player) {
		super(Rank.CAPTAIN, true, player);
	}

}