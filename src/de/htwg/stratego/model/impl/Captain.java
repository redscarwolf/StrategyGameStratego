package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Captain extends AbstractCharacter {

	public Captain(Player player) {
		super(Rank.CAPTAIN, true, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Captain(p));
		}
	}

}
