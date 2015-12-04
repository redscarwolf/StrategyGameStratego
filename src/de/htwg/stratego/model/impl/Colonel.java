package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Colonel extends AbstractCharacter {

	public Colonel(Player player) {
		super(Rank.COLONEL, true, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Colonel(p));
		}
	}

}
