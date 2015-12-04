package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Lieutenant extends AbstractCharacter {

	public Lieutenant(Player player) {
		super(Rank.LIEUTENANT, true, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Lieutenant(p));
		}
	}
}
