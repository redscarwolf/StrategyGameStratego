package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Lieutenant extends AbstractCharacter {

	public Lieutenant(Player player) {
		super(Rank.LIEUTENANT, true, player);
	}

}
