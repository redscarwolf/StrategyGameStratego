package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Sergeant extends AbstractCharacter {

	public Sergeant(Player player) {
		super(Rank.SERGEANT, true, player);
	}
}
